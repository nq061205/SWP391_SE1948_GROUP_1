/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.EmployeeDAO;
import model.Employee;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.*;
import java.nio.file.Paths;
import java.sql.Date;
import java.io.File;

/**
 *
 * @author nq061205
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        session.setAttribute("user", user);
        request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Views/login.jsp");
            return;
        }
        String click = request.getParameter("click");
        request.setAttribute("click", click);
        if ("save".equals(click)) {
            String fullname = request.getParameter("fullname");
            boolean gender = "Male".equals(request.getParameter("gender"));
            String dobStr = request.getParameter("dob");
            Date dob = null;
            if (dobStr != null && !dobStr.isEmpty()) {
                try {
                    dob = java.sql.Date.valueOf(dobStr);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            String phone = request.getParameter("phone");
            Part filePart = request.getPart("image");
            String avatarRelativePath = "";

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("/images/avatar");

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                filePart.write(uploadPath + File.separator + fileName);
                avatarRelativePath = "images/avatar/" + fileName;
            }
            employeeDAO.updateEmployeeInformation(
                    user.getEmpId(),
                    fullname,
                    gender,
                    dob,
                    phone,
                    avatarRelativePath
            );
            Employee updatedUser = employeeDAO.getEmployeeByEmpId(user.getEmpId());
            if (updatedUser != null) {
                session.setAttribute("user", updatedUser);
            }
        }
        if (!"save".equals(click)) {
            request.setAttribute("click", "save");
        } else {
            request.setAttribute("click", "");
        }
        request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
    }
}
