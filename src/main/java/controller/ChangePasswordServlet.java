/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.EmployeeDAO;
import model.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author hgduy
 */
public class ChangePasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassword at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
        } else {
            request.getRequestDispatcher("Views/changepassword.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee emp = (Employee) session.getAttribute("user");
        String currentPass = request.getParameter("currentPassword");
        String newPass = request.getParameter("newPassword");
        String confirmPass = request.getParameter("confirmPassword");
        if (newPass.equals(currentPass)) {
            request.setAttribute("errorMessage", "The new password cannot be the same as the old password ");
            request.getRequestDispatcher("Views/changepassword.jsp").forward(request, response);
            return;
        }
        if (!currentPass.equals(emp.getPassword())) {
            request.setAttribute("errorMessage", "password is incorect");
            request.getRequestDispatcher("Views/changepassword.jsp").forward(request, response);
            return;
        }

        String passwordPattern = "^[A-Z].{6,}[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/~`]$";
        if (newPass == null || !newPass.matches(passwordPattern) || newPass.length() < 8) {
            request.setAttribute("errorMessage",
                    "Password must be at least 8 characters long, start with a capital letter, and end with a special character.");
            request.getRequestDispatcher("Views/changepassword.jsp").forward(request, response);
            return;
        }
        if (!newPass.equals(confirmPass)) {
            request.setAttribute("errorMessage", "New password and confirm password is not match");
            request.getRequestDispatcher("Views/changepassword.jsp").forward(request, response);
            return;
        }

        EmployeeDAO eDao = new EmployeeDAO();
        boolean success = eDao.updatePassword(emp.getEmpCode(), newPass);
        if (success) {
            emp.setPassword(newPass);
            session.setAttribute("user", emp);
            request.setAttribute("successMessage", "New password has been updated");
        } else {
            request.setAttribute("errorMessage", "Lỗi khi cập nhật mật khẩu!");
        }
        request.getRequestDispatcher("Views/changepassword.jsp").forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
