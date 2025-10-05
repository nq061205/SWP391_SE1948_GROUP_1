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

/**
 *
 * @author nq061205
 */
public class ProfileServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        HttpSession session = request.getSession();
        Employee employee = employeeDAO.getEmployeeByEmpId(1);
        session.setAttribute("user", employee);
        response.sendRedirect("Views/profile.jsp");
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String click = request.getParameter("click").equals("save")?"":"save";
        request.setAttribute("click", click);
        request.getRequestDispatcher("Views/profile.jsp").forward(request, response);
    }

    
}
