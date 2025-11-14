/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.EmployeeDAO;
import model.*;
import dal.LeaveRequestDAO;
import dal.OTRequestDAO;
import dal.RolePermissionDAO;
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
 * @author Lenovo
 */
public class EditApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String type = request.getParameter("type");
        Employee user = (Employee) session.getAttribute("user");
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 7)) {
            session.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        EmployeeDAO empDAO = new EmployeeDAO();
        OTRequestDAO otRequestDAO = new OTRequestDAO();
        LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("isEdit", "true");
        request.setAttribute("receiver", empDAO.getEmployeeReceiverByRole(user.getRole().getRoleName(), user.getDept().getDepId()));
        switch (type) {
            case "LEAVE":
                LeaveRequest leaveRequest = leaveRequestDAO.getLeaveRequestByLeaveId(id);
                request.setAttribute("email", leaveRequest.getApprovedBy().getEmail());
                request.setAttribute("type_leave", leaveRequest.getLeaveType());
                request.setAttribute("startdate", leaveRequest.getStartDate());
                request.setAttribute("enddate", leaveRequest.getEndDate());
                request.setAttribute("content", leaveRequest.getReason());
                request.setAttribute("id", leaveRequest.getLeaveId());
                request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
                break;
            case "OT":
                OTRequest otRequest = otRequestDAO.getOTRequestByOTId(id);
                request.setAttribute("email", otRequest.getApprovedBy().getEmail());
                request.setAttribute("date", otRequest.getDate());
                request.setAttribute("hours", otRequest.getOtHours());
                request.setAttribute("id", otRequest.getOtId());
                request.getRequestDispatcher("Views/composeotapplication.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        OTRequestDAO otDAO = new OTRequestDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        if (leaveDAO.getLeaveRequestByLeaveId(id) == null && otDAO.getOTRequestByOTId(id) == null) {
            response.sendRedirect("Views/error-404.jsp");
            return;
        }
        try {
            switch (type.toUpperCase()) {
                case "LEAVE": {
                    LeaveRequest leave = leaveDAO.getLeaveRequestByLeaveId(id);
                    String leaveType = request.getParameter("type_leave");
                    String content = request.getParameter("content").trim();
                    Date startDate = Date.valueOf(request.getParameter("startdate"));
                    Date endDate = Date.valueOf(request.getParameter("enddate"));
                    Employee approver = empDAO.getEmployeeByEmail((String) request.getParameter("email"));
                    request.setAttribute("isEdit", "true");
                    request.setAttribute("type_leave", leaveType);
                    request.setAttribute("startdate", startDate);
                    request.setAttribute("enddate", endDate);
                    request.setAttribute("content", content);
                    request.setAttribute("receiver", approver);
                    if (endDate.before(startDate)) {
                        request.setAttribute("messageDate", "End date must be after start date");
                        request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
                        return;
                    } else if (content.length() > 200) {
                        request.setAttribute("messageContent", "Length of content less than 200 character");
                        request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
                        return;
                    } else {
                        HttpSession session = request.getSession();
                        session.setAttribute("flashMessage", "Updated Successfully!");
                        leaveDAO.updateLeaveRequest(id, leaveType, content, startDate, endDate, leave.getNote());
                        response.sendRedirect("application?typeapplication=leave");
                    }
                    break;
                }
                case "OT": {
                    Date date = Date.valueOf(request.getParameter("date"));
                    double hours = Double.parseDouble(request.getParameter("hours"));
                    otDAO.updateOTRequest(id, date, hours);
                    HttpSession session = request.getSession();
                    session.setAttribute("flashMessage", "Updated Successfully!");
                    response.sendRedirect("application?typeapplication=ot");
                    break;
                }
                default:
                    response.sendRedirect("application");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
