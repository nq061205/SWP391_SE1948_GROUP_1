/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.EmployeeDAO;
import jakarta.servlet.ServletContext;
import model.Employee;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.sql.Date;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import javax.imageio.ImageIO;

/**
 *
 * @author nq061205
 */
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 50 * 1024 * 1024
)
public class ProfileServlet extends HttpServlet {

    private static final long MAX_AVATAR_SIZE = 2L * 1024 * 1024;
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/jpg", "image/pjpeg", "image/png", "image/gif", "image/webp"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Views/login.jsp");
            return;
        }
        session.setAttribute("user", user);
        request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Views/login.jsp");
            return;
        }
        EmployeeDAO employeeDAO = new EmployeeDAO();
        String click = request.getParameter("click");
        request.setAttribute("click", click);

        if ("save".equals(click)) {
            boolean validate = false;
            String fullname = request.getParameter("fullname");
            boolean gender = "Male".equals(request.getParameter("gender"));
            String dobStr = request.getParameter("dob");
            String phone = request.getParameter("phone");

            Part filePart = request.getPart("image");

            request.setAttribute("fullname", fullname);
            request.setAttribute("gender", gender ? "Male" : "Female");
            request.setAttribute("dob", dobStr);
            request.setAttribute("phone", phone);

            String errName = validateFullname(fullname);
            String errPhone = validatePhone(phone);
            String errImg = validateAvatarPart(filePart);

            if (errName != null) {
                request.setAttribute("fullnameErr", errName);
                validate = true;
            }
            if (errPhone != null) {
                request.setAttribute("phoneErr", errPhone);
                validate = true;
            }
            if (errImg != null) {
                request.setAttribute("avatarErr", errImg);
                validate = true;
            }

            Date dob = null;
            if (dobStr != null && !dobStr.isBlank()) {
                try {
                    dob = Date.valueOf(dobStr);
                    if (dob.after(Date.valueOf(LocalDate.now()))) {
                        request.setAttribute("dobErr", "Date of birth cannot be in the future");
                        validate = true;
                    }
                } catch (IllegalArgumentException e) {
                }
            }
            if (validate) {
                request.setAttribute("click", "save");
                request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
                return;
            }

            String avatarRelativePath = null;
            if (filePart == null || filePart.getSize() == 0) {
                avatarRelativePath = user.getImage();
            } else {
                avatarRelativePath = saveAvatar(filePart, getServletContext(), user.getEmpCode());
            }
            employeeDAO.updateEmployeeInformation(
                    user.getEmpId(),
                    fullname.trim(),
                    gender,
                    dob,
                    phone.trim(),
                    avatarRelativePath
            );

            Employee updatedUser = employeeDAO.getEmployeeByEmpId(user.getEmpId());
            if (updatedUser != null) {
                session.setAttribute("user", updatedUser);
            }

            request.setAttribute("click", "");
            request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
            return;
        }

        request.setAttribute("click", "save");
        request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
        return;
    }

    private String validateFullname(String fullname) {
        String fn = fullname.trim();
        if (fn.length() < 2 || fn.length() > 50) {
            return "Full name must be 2–50 characters";
        }
        if (!fn.matches("^[\\p{L}][\\p{L}'\\- ]*[\\p{L}]$")) {
            return "Full name contains invalid characters";
        }
        return null;
    }

    private String validatePhone(String phone) {
        String p = phone.trim();
        if (!(p.matches("^(0)(3|5|7|8|9)\\d+$"))) {
            return "Invalid mobile number";
        }
        return null;
    }

    private String validateAvatarPart(Part filePart) {
        if (filePart.getSize() > MAX_AVATAR_SIZE) {
            return "Avatar must be ≤ 2MB";
        }
        return null;
    }

    private String saveAvatar(Part filePart, ServletContext ctx, String empCode) throws IOException {
        String ct = filePart.getContentType().toLowerCase();
        String ext = "";
        switch (ct) {
            case "image/jpeg":
                ext = ".jpg";
                break;
            case "image/png":
                ext = ".png";
                break;
            case "image/gif":
                ext = ".gif";
                break;
            case "image/webp":
                ext = ".webp";
                break;
            default:
                ext = "";
        }
        String safeName = "ht_" + empCode + ext;

        String uploadPath = ctx.getRealPath("/images/avatar");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        filePart.write(uploadPath + File.separator + safeName);
        return "images/avatar/" + safeName;
    }

}
