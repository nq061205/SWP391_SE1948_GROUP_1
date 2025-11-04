package controller;

import dal.EmployeeDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import model.Employee;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024,
        maxFileSize = 20 * 1024 * 1024,
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

        if (!"save".equals(click)) {
            request.setAttribute("click", "save");
            request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
            return;
        }

        boolean hasError = false;

        String fullname = safe(request.getParameter("fullname"));
        boolean gender = "Male".equals(request.getParameter("gender"));
        String dobStr = request.getParameter("dob");
        String phone = safe(request.getParameter("phone"));
        Part filePart = request.getPart("image");

        request.setAttribute("fullname", fullname);
        request.setAttribute("gender", gender ? "Male" : "Female");
        request.setAttribute("dob", dobStr);
        request.setAttribute("phone", phone);

        String errName = validateFullname(fullname);
        String errPhone = validatePhone(phone);
        String errImg = validateAvatarPart(filePart); // kiểm tra size + content-type

        if (errName != null) {
            request.setAttribute("fullnameErr", errName);
            hasError = true;
        }
        if (errPhone != null) {
            request.setAttribute("phoneErr", errPhone);
            hasError = true;
        }
        if (errImg != null) {
            request.setAttribute("avatarErr", errImg);
            hasError = true;
        }

        Date dob = null;
        if (dobStr != null && !dobStr.isBlank()) {
            try {
                dob = Date.valueOf(dobStr);
                if (dob.after(Date.valueOf(LocalDate.now()))) {
                    request.setAttribute("dobErr", "Date of birth cannot be in the future");
                    hasError = true;
                }
            } catch (IllegalArgumentException ignored) {
            }
        }

        if (hasError) {
            request.setAttribute("click", "save");
            request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
            return;
        }

        String avatarRelativePath;
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
        session.setAttribute("user", updatedUser);
        request.setAttribute("click", "");
        request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
    }

    private static String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private String validateFullname(String fullname) {
        String fn = safe(fullname);
        if (fn.length() < 2 || fn.length() > 50) {
            return "Full name must be 2–50 characters";
        }
        if (!fn.matches("^[\\p{L}][\\p{L}'\\- ]*[\\p{L}]$")) {
            return "Full name contains invalid characters";
        }
        return null;
    }

    private String validatePhone(String phone) {
        String p = safe(phone);
        if (!p.matches("^(0)(3|5|7|8|9)\\d{8}$")) {
            return "Invalid mobile number";
        }
        return null;
    }

    private String validateAvatarPart(Part filePart) throws IOException {
        if (filePart == null || filePart.getSize() == 0) {
            return null; // không bắt buộc đổi ảnh
        }
        if (filePart.getSize() > MAX_AVATAR_SIZE) {
            return "Avatar must be ≤ 2MB";
        }
        String ct = lower(filePart.getContentType());
        if (!ALLOWED_CONTENT_TYPES.contains(ct)) {
            return "Only JPG/PNG/GIF/WEBP are allowed";
        }
        return null;
    }

    private String lower(String s) {
        return s == null ? "" : s.toLowerCase();
    }

    private String saveAvatar(Part filePart, ServletContext ctx, String empCode) throws IOException {
        String ct = lower(filePart.getContentType());
        String ext;
        if (ct.contains("png")) {
            ext = ".png";
        } else if (ct.contains("gif")) {
            ext = ".gif";
        } else if (ct.contains("webp")) {
            ext = ".webp";
        } else {
            ext = ".jpg"; // mặc định cho jpeg/jpg/pjpeg
        }
        String safeName = "ht_" + empCode + ext;

        String uploadPath = ctx.getRealPath("/images/avatar");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File[] oldFiles = uploadDir.listFiles((dir, name)
                -> name.toLowerCase().startsWith(("ht_" + empCode).toLowerCase() + "."));
        if (oldFiles != null) {
            for (File f : oldFiles) {
                try {
                    if (f.isFile()) {
                        f.delete();
                    }
                } catch (Exception ignored) {
                }
            }
        }

        filePart.write(uploadPath + File.separator + safeName);

        return "images/avatar/" + safeName;
    }
}
