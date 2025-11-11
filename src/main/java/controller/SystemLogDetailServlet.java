/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.EmployeeDAO;
import dal.LeaveRequestDAO;
import dal.OTRequestDAO;
import dal.RolePermissionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import model.Employee;
import model.LeaveRequest;
import model.OTRequest;

/**
 *
 * @author Lenovo
 */
public class SystemLogDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 9)) {
            session.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        String leaveId = request.getParameter("id");
        LeaveRequest leave = leaveDAO.getLeaveRequestByLeaveId(Integer.parseInt(leaveId));
        request.setAttribute("leave", leave);
        request.getRequestDispatcher("Views/systemlogdetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));
        String note = request.getParameter("note");

        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        LeaveRequest leave = leaveDAO.getLeaveRequestByLeaveId(id);
        EmployeeDAO empDAO = new EmployeeDAO();

        request.setAttribute("leave", leave);

        if ("Invalid".equalsIgnoreCase(action)) {
            if ("Annual Leave".equalsIgnoreCase(leave.getLeaveType())) {
                empDAO.updateIncreasePaidLeaveDaysByEmployeeId(
                        leave.getEmployee().getEmpId(), leave.getDayRequested()
                );
            }
            leaveDAO.updateLeaveStatus(id, action, note);
            response.sendRedirect(request.getContextPath() + "/systemlog");
            return;
        }

        if ("Updated".equalsIgnoreCase(action)) {
            String startStr = request.getParameter("startdate");
            String endStr = request.getParameter("enddate");

            Date startDate = null;
            Date endDate = null;

            if (startStr != null && !startStr.isEmpty()) {
                startDate = Date.valueOf(startStr);
            }
            if (endStr != null && !endStr.isEmpty()) {
                endDate = Date.valueOf(endStr);
            }

            if (startDate == null || endDate == null) {
                request.setAttribute("messageDate", "Start date and end date cannot be empty!");
                request.getRequestDispatcher("Views/systemlogdetail.jsp").forward(request, response);
                return;
            }
            if (startDate.after(endDate)) {
                request.setAttribute("messageDate", "Start date must be before end date!");
                request.getRequestDispatcher("Views/systemlogdetail.jsp").forward(request, response);
                return;
            }

            double oldDays = leave.getDayRequested();
            long diff = endDate.getTime() - startDate.getTime();
            double newDays = (double) diff / (1000 * 60 * 60 * 24) + 1;

            if ("Annual Leave".equalsIgnoreCase(leave.getLeaveType())) {
                double available = leave.getEmployee().getPaidLeaveDays();

                if (newDays > oldDays) {
                    double need = newDays - oldDays;
                    if (available < need) {
                        request.setAttribute("messageDate", "Exceeding the number of available leave days!");
                        request.getRequestDispatcher("Views/systemlogdetail.jsp").forward(request, response);
                        return;
                    }
                    empDAO.updateDecreasePaidLeaveDaysByEmployeeId(
                            leave.getEmployee().getEmpId(), need
                    );

                } else if (newDays < oldDays) {
                    double refund = oldDays - newDays;
                    empDAO.updateIncreasePaidLeaveDaysByEmployeeId(
                            leave.getEmployee().getEmpId(), refund
                    );
                }
            }

            leaveDAO.updateLeaveRequest(id,
                    leave.getLeaveType(),
                    leave.getReason(),
                    startDate,
                    endDate,
                    note);

            response.sendRedirect(request.getContextPath() + "/systemlog");
        }
    }

}
