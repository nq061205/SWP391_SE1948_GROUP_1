/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OTRequestDAO;
import dal.LeaveRequestDAO;
import dal.EmployeeDAO;
import model.*;
import java.io.IOException;
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
public class ComposeApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Views/login.jsp");
            return;
        }
        String type = request.getParameter("type");
        if ("LEAVE".equalsIgnoreCase(type)) {
            request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
        } else if ("OT".equalsIgnoreCase(type)) {
            request.getRequestDispatcher("Views/composeotapplication.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        EmployeeDAO empDAO = new EmployeeDAO();
        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        OTRequestDAO otDAO = new OTRequestDAO();
        String appType = type.trim().toUpperCase();
        request.setAttribute("type", appType);
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        switch (appType) {
            case "LEAVE": {
                String leaveType = request.getParameter("type_leave");
                String content = request.getParameter("content");
                String startStr = request.getParameter("startdate");
                String endStr = request.getParameter("enddate");
                try {
                    Date startDate = Date.valueOf(startStr);
                    Date endDate = Date.valueOf(endStr);
                    String email = request.getParameter("email");
                    request.setAttribute("type_leave", leaveType);
                    request.setAttribute("startdate", startDate);
                    request.setAttribute("enddate", endDate);
                    request.setAttribute("content", content);
                    int approvedBy = 0;
                    if (empDAO.getEmployeeByEmail(email) == null) {
                        request.setAttribute("messageEmail", "Email is not available");
                        request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
                    } else if (endDate.before(startDate)) {
                        request.setAttribute("email", email);
                        request.setAttribute("messageDate", "End date must after start date");
                        request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
                    } else {
                        Employee approver = empDAO.getEmployeeByEmail(request.getParameter("email"));
                        approvedBy = approver.getEmpId();
                        leaveDAO.composeLeaveRequest(
                                user.getEmpId(),
                                leaveType,
                                content,
                                startDate,
                                endDate,
                                approvedBy
                        );
                        request.getRequestDispatcher("Views/applicationsuccess.jsp").forward(request, response);
                    }
                } catch (IllegalArgumentException ex) {
                }
                break;
            }

            case "OT": {
                String dateStr = request.getParameter("date");
                String hoursStr = request.getParameter("hours");
                try {
                    Date otDate = Date.valueOf(dateStr);
                    String email = request.getParameter("email");
                    Double otHours = Double.parseDouble(hoursStr);

                    request.setAttribute("email", email);
                    request.setAttribute("date", otDate);
                    request.setAttribute("hours", hoursStr);
                    int approvedBy;
                    if (empDAO.getEmployeeByEmail(request.getParameter("email")) != null) {
                        Employee approver = empDAO.getEmployeeByEmail(request.getParameter("email"));
                        approvedBy = approver.getEmpId();
                    } else {
                        request.setAttribute("message", "Email is not available.");
                        request.getRequestDispatcher("Views/composeotapplication.jsp").forward(request, response);
                        return;
                    }
                    otDAO.composeOTRequest(
                            user.getEmpId(),
                            otDate,
                            otHours,
                            approvedBy
                    );
                    request.getRequestDispatcher("Views/applicationsuccess.jsp").forward(request, response);
                    return;
                } catch (IllegalArgumentException ex) {
                }
                break;
            }
            default: {
                request.setAttribute("message", "Unsupported application type: " + appType);
                request.getRequestDispatcher("Views/composeapplication.jsp").forward(request, response);
            }
        }
    }

}
