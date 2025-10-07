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
        if("leave".equals(typeApplication)){
            doLeaveRequestApplication(request, response, emp);
        }else if("ot".equals(typeApplication)){
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
        List<LeaveRequest> applications = leaveDAO.getLeaveRequestByEmpId(1);
        request.setAttribute("listapplication", applications);
        request.setAttribute("user", emp);
        request.getRequestDispatcher("Views/leaverequestapplication.jsp").forward(request, response);
    }
    
    protected void doOTRequestApplication(HttpServletRequest request, HttpServletResponse response, Employee user)
            throws ServletException, IOException {
        EmployeeDAO empDAO = new EmployeeDAO();
        OTRequestDAO OTDAO = new OTRequestDAO();
        Employee emp = empDAO.getEmployeeByEmpId(1);
        List<OTRequest> applications = OTDAO.getOTRequestByEmpId(1);
        request.setAttribute("listapplication", applications);
        request.setAttribute("user", emp);
        request.getRequestDispatcher("Views/otrequestapplication.jsp").forward(request, response);
    }
}
