/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.DeptDAO;
import dal.EmployeeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Employee;

/**
 *
 * @author Lenovo
 */
public class DashboardServlet extends HttpServlet {
   
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String logMessage = (String) session.getAttribute("logMessage");
        if (logMessage != null) {
            request.setAttribute("logMessage", logMessage);
            session.removeAttribute("logMessage");
        }
        EmployeeDAO empDAO = new EmployeeDAO();
        int totalEmployee = empDAO.countAllRecordOfEmployee();
        DeptDAO deptDAO = new DeptDAO();
        int totalDept = deptDAO.countAllDepartments();
        request.setAttribute("totalDept", totalDept);
        request.setAttribute("totalEmployee", totalEmployee);
        request.getRequestDispatcher("Views/Admin/adminDashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

}
