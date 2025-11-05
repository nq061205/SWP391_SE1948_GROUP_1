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
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Views/login.jsp");
            return;
        }
        String type = request.getParameter("type");
        request.setAttribute("receiver", empDAO.getEmployeeReceiverByRole(user.getRole().getRoleName(), user.getDept().getDepId()));
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
                    Employee approver = empDAO.getEmployeeByEmail(email);
                    request.setAttribute("receiver", empDAO.getEmployeeReceiverByRole(user.getRole().getRoleName(), user.getDept().getDepId()));
                    long diff = endDate.getTime() - startDate.getTime();
                    double days = (double) diff / (1000 * 60 * 60 * 24) + 1;
                    if (days > user.getPaidLeaveDays() && "Annual Leave".equalsIgnoreCase(leaveType)) {
                        request.setAttribute("messageLeave", "Exceeding the number of leave days");
                        request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
                        return;
                    } else if (startDate.after(endDate)) {
                        request.setAttribute("messageDate", "Start date must before end date");
                        request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
                        return;
                    }
                    leaveDAO.composeLeaveRequest(
                            user.getEmpId(),
                            leaveType,
                            content,
                            startDate,
                            endDate,
                            approver.getEmpId()
                    );
                    request.getRequestDispatcher("Views/applicationsuccess.jsp").forward(request, response);
                    return;
                } catch (IllegalArgumentException ex) {
                }
                break;
            }

            case "OT": {
                String dateStr = request.getParameter("date");
                String hoursStr = request.getParameter("hours");
                try {
                    Date otDate = Date.valueOf(dateStr);
                    double otHours = Double.parseDouble(hoursStr);
                    String email = request.getParameter("email");

                    request.setAttribute("email", email);
                    request.setAttribute("date", otDate);
                    request.setAttribute("othour", otHours);
                    Employee approver = empDAO.getEmployeeByEmail(email);
                    otDAO.composeOTRequest(
                            user.getEmpId(),
                            otDate,
                            otHours,
                            approver.getEmpId()
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
