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
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
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

        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();

        String search = trimOrNull(request.getParameter("search"));
        String status = trimOrNull(request.getParameter("status"));
        String type = trimOrNull(request.getParameter("type"));
        String startStr = trimOrNull(request.getParameter("startDate"));
        String endStr = trimOrNull(request.getParameter("endDate"));

        java.sql.Date startDate = parseSqlDate(startStr); // null-safe
        java.sql.Date endDate = parseSqlDate(endStr);   // null-safe

        if (startDate != null && endDate != null && startDate.after(endDate)) {
            java.sql.Date tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        int page = parseIntOrDefault(request.getParameter("page"), 1);
        int size = Math.max(1, Math.min(parseIntOrDefault(request.getParameter("size"), 10), 50));
        int offset = (page - 1) * size;

        int empId = user.getEmpId();

        List<LeaveRequest> applications = leaveDAO.findLeaveByEmpFilteredPaged(
                empId, search, status, type, startDate, endDate, offset, size
        );

        int total = leaveDAO.countLeaveByEmpFiltered(empId, search, status, type, startDate, endDate);
        int totalPages = (int) Math.ceil(total / (double) size);

        request.setAttribute("listapplication", applications);
        request.setAttribute("page", page);
        request.setAttribute("size", size);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("type", type);
        request.setAttribute("startDate", startDate == null ? null : startDate.toString());
        request.setAttribute("endDate", endDate == null ? null : endDate.toString());

        request.setAttribute("user", user);

        request.getRequestDispatcher("Views/leaverequestapplication.jsp").forward(request, response);
    }

    protected void doOTRequestApplication(HttpServletRequest request, HttpServletResponse response, Employee user)
            throws ServletException, IOException {

        OTRequestDAO otDAO = new OTRequestDAO();

        String search = trimOrNull(request.getParameter("search"));
        String status = trimOrNull(request.getParameter("status"));
        String startStr = trimOrNull(request.getParameter("startDate"));
        String endStr = trimOrNull(request.getParameter("endDate"));

        Date startDate = parseSqlDate(startStr);
        Date endDate = parseSqlDate(endStr);

        if (startDate != null && endDate != null && startDate.after(endDate)) {
            Date tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        int page = parseIntOrDefault(request.getParameter("page"), 1);
        int size = parseIntOrDefault(request.getParameter("size"), 10);
        int offset = (page - 1) * size;

        int empId = user.getEmpId();

        List<OTRequest> applications = otDAO.findOTByEmpPaged(
                empId, offset, size, search, status, startDate, endDate
        );

        int total = otDAO.countOTByEmpFiltered(empId, search, status, startDate, endDate);
        int totalPages = (int) Math.ceil(total / (double) size);

        request.setAttribute("listapplication", applications);
        request.setAttribute("page", page);
        request.setAttribute("size", size);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("startDate", startDate == null ? null : startDate.toString());
        request.setAttribute("endDate", endDate == null ? null : endDate.toString());

        request.setAttribute("user", user);

        request.getRequestDispatcher("Views/otrequestapplication.jsp").forward(request, response);
    }

    private String trimOrNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private Date parseSqlDate(String s) {
        try {
            return (s == null) ? null : java.sql.Date.valueOf(s);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private int parseIntOrDefault(String val, int def) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return def;
        }
    }
}
