/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.EmployeeDAO;
import Models.Employee;
import jakarta.mail.Session;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;

/**
 *
 * @author nq061205
 */
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        session.setAttribute("user", user);
        response.sendRedirect("Views/profile.jsp");
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
            java.sql.Date dob = null;
            if (dobStr != null && !dobStr.isEmpty()) {
                try {
                    dob = java.sql.Date.valueOf(dobStr);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            String phone = request.getParameter("phone");
            String image = request.getParameter("image");
            employeeDAO.updateEmployeeInformation(
                    user.getEmpId(),
                    fullname,
                    gender,
                    dob,
                    phone,
                    image
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
