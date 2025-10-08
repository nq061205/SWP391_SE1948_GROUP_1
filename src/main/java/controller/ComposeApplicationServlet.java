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
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ComposeApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                    int approvedBy;
                    if (empDAO.getEmployeeByEmail(email) != null) {
                        Employee approver = empDAO.getEmployeeByEmail(request.getParameter("email"));
                        approvedBy = approver.getEmpId();
                    } else {
                        request.setAttribute("message", "Email is not available.");
                        request.getRequestDispatcher("Views/composeleaveapplication.jsp").forward(request, response);
                        return;
                    }
                    leaveDAO.composeLeaveRequest(
                            1,
                            leaveType,
                            content,
                            startDate,
                            endDate,
                            approvedBy
                    );
                    request.getRequestDispatcher("Views/applicationsuccess.jsp").forward(request, response);
                    return;
                } catch (IllegalArgumentException ex) {
                }
                break;
            }

            case "OT": {
                String dateStr = request.getParameter("date");
                String hoursStr = request.getParameter("othour");
                try {
                    Date otDate = Date.valueOf(dateStr);
                    double otHours = Double.parseDouble(hoursStr);
                    String email = request.getParameter("email");

                    request.setAttribute("email", email);
                    request.setAttribute("date", otDate);
                    request.setAttribute("othour", otHours);
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
                            1,
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
