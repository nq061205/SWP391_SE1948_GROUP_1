/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import api.GoogleLogin;
import dal.EmployeeDAO;
import model.Employee;
import model.GoogleAccount;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hgduy
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cooky : cookies) {
                if ("rememberUser".equals(cooky.getName())) {
                    request.setAttribute("rememberEmpCode", cooky.getValue());
                }
                if ("rememberPass".equals(cooky.getName())) {
                    request.setAttribute("rememberEmpPass", cooky.getValue());
                }
            }
        }
        if (code == null) {
            request.getRequestDispatcher("Views/login.jsp").forward(request, response);
            return;
        }
        GoogleLogin google = new GoogleLogin();
        EmployeeDAO ldao = new EmployeeDAO();
        GoogleAccount googleAcc = google.getUserInfo(google.getToken(code));
        Employee emp = ldao.getEmployeeByEmail(googleAcc.getEmail());
        if (emp == null) {
            request.setAttribute("errorMessage", "Account does not valid");
            request.getRequestDispatcher("Views/login.jsp").forward(request, response);
            return;
        }
        HttpSession ses = request.getSession();
        ses.setAttribute("employee", emp);
        response.sendRedirect("homepage");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String emp_code = request.getParameter("emp_code");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");
            EmployeeDAO lDao = new EmployeeDAO();
            HttpSession session = request.getSession();
            Employee employee = lDao.getEmployeeByUsernamePassword(emp_code, password);
            if (employee != null) {
                session.setAttribute("employee", employee);
                if ("on".equals(remember)) {
                    Cookie cookieUser = new Cookie("rememberUser", emp_code);
                    cookieUser.setMaxAge(7 * 24 * 60 * 60);
                    response.addCookie(cookieUser);

                    Cookie cookiePass = new Cookie("rememberPass", password);
                    cookiePass.setMaxAge(7 * 24 * 60 * 60);
                    response.addCookie(cookiePass);
                } else {
                    Cookie ckUser = new Cookie("rememberUser", "");
                    ckUser.setMaxAge(0);
                    response.addCookie(ckUser);
                    Cookie ckPass = new Cookie("rememberPass", "");
                    ckPass.setMaxAge(0);
                    response.addCookie(ckPass);
                }
                response.sendRedirect("homepage");
            } else {
                request.setAttribute("errorMessage", "Employee Code or Password invalid");
                request.getRequestDispatcher("Views/login.jsp").forward(request, response);
        } 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
