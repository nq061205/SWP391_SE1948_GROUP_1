/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import model.Employee;
import model.LeaveRequest;
import model.OTRequest;
import dal.EmployeeDAO;
import dal.LeaveRequestDAO;
import dal.OTRequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee emp = empDAO.getEmployeeByEmpId(1);
        String typeApplication = request.getParameter("typeapplication");
        if ("leave".equalsIgnoreCase(typeApplication)) {
            doLeaveRequestApplication(request, response, emp);
        } else if ("ot".equalsIgnoreCase(typeApplication)) {
            doOTRequestApplication(request, response, emp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doLeaveRequestApplication(HttpServletRequest request, HttpServletResponse response, Employee user)
            throws ServletException, IOException {
        EmployeeDAO empDAO = new EmployeeDAO();
        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        Employee emp = empDAO.getEmployeeByEmpId(1);
        
        int page = parseIntOrDefault(request.getParameter("page"), 1);
        int size = parseIntOrDefault(request.getParameter("size"), 10);
        int offset = (page - 1) * size;
        
        List<LeaveRequest> applications = leaveDAO.findLeaveByEmpPaged(1, offset, size);
        List<LeaveRequest> list = leaveDAO.findLeaveByEmpPaged(user.getEmpId(), offset, size);
        
        int total = leaveDAO.countLeaveByEmp(user.getEmpId());
        int totalPages = (int) Math.ceil(total / (double) size);
        
        request.setAttribute("items", list);
        request.setAttribute("page", page);
        request.setAttribute("size", size);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("listapplication", applications);
        request.setAttribute("user", emp);
        request.getRequestDispatcher("Views/leaverequestapplication.jsp").forward(request, response);
    }

    protected void doOTRequestApplication(HttpServletRequest request, HttpServletResponse response, Employee user)
            throws ServletException, IOException {
        EmployeeDAO empDAO = new EmployeeDAO();
        OTRequestDAO OTDAO = new OTRequestDAO();
        int page = parseIntOrDefault(request.getParameter("page"), 1);
        int size = parseIntOrDefault(request.getParameter("size"), 10);
        int offset = (page - 1) * size;
        
        Employee emp = empDAO.getEmployeeByEmpId(1);
        List<OTRequest> applications = OTDAO.findOTByEmpPaged(1, offset, size);
        List<OTRequest> list = OTDAO.findOTByEmpPaged(user.getEmpId(), offset, size);
        
        int total = OTDAO.countOTByEmp(user.getEmpId());
        int totalPages = (int) Math.ceil(total / (double) size);

        request.setAttribute("items", list);
        request.setAttribute("page", page);
        request.setAttribute("size", size);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("listapplication", applications);
        request.setAttribute("user", emp);
        request.getRequestDispatcher("Views/otrequestapplication.jsp").forward(request, response);
    }

    private int parseIntOrDefault(String val, int def) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return def;
        }
    }
}
