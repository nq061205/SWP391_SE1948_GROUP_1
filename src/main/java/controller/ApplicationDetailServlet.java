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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */
public class ApplicationDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        OTRequestDAO OTDAO = new OTRequestDAO();
        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        String leaveId = request.getParameter("leaveId");
        String OTId = request.getParameter("OTId");
        Employee employee = employeeDAO.getEmployeeByEmpId(1);
        if(OTId != null && !OTId.isEmpty()){
            OTRequest ot = OTDAO.getOTRequestByOTId(Integer.parseInt(OTId), employee.getEmpId());
            request.setAttribute("type", "ot");
            request.setAttribute("ot", ot);
            
        }else if(leaveId != null && !leaveId.isEmpty()){
            LeaveRequest leave = leaveDAO.getLeaveRequestByLeaveId(Integer.parseInt(leaveId), employee.getEmpId());
            request.setAttribute("type", "leave");
            request.setAttribute("leave", leave);
        }
        request.getRequestDispatcher("Views/applicationdetail.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }
}
