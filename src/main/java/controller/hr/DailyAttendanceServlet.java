/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hr;

import dal.DailyAttendanceDAO;
import dal.EmployeeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.DailyAttendance;
import model.Employee;

/**
 *
 * @author admin
 */
public class DailyAttendanceServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DailyAttendance</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DailyAttendance at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String monthParam = request.getParameter("month");
        String yearParam = request.getParameter("year");
        String department = request.getParameter("department");
        String search = request.getParameter("search");
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");

        LocalDate now = LocalDate.now();
        int selectedMonth = (monthParam != null && !monthParam.isEmpty()) ? Integer.parseInt(monthParam) : now.getMonthValue();
        int selectedYear = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : now.getYear();
        int startYear = 2020;
        int endYear = now.getYear();

        int page = 1;
        int pageSize = 20;
        try {
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
            if (pageSizeParam != null && !pageSizeParam.trim().isEmpty()) {
                pageSize = Integer.parseInt(pageSizeParam);
            }
        } catch (NumberFormatException ex) {
            page = 1;
            pageSize = 20;
        }
        if (page < 1) {
            page = 1;
        }
        if (pageSize < 1 || pageSize > 100) {
            pageSize = 20;
        }

        try {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            
            long totalRecords = employeeDAO.countEmployees(search, department);
            int totalPages = (totalRecords > 0) ? (int) Math.ceil((double) totalRecords / pageSize) : 1;
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }
            int offset = (page - 1) * pageSize;

            List<Employee> employees = employeeDAO.getEmployees(offset, pageSize, search, department);

            List<Integer> empIds = employees.stream().map(e -> e.getEmpId()).collect(Collectors.toList());
            DailyAttendanceDAO dailyDAO = new DailyAttendanceDAO();
            List<DailyAttendance> dailyList = dailyDAO.getAttendanceByEmpIds(empIds, selectedMonth, selectedYear);

            Map<Integer, List<DailyAttendance>> groupedAttendance = new LinkedHashMap<>();
            for (DailyAttendance d : dailyList) {
                int empId = d.getEmployee().getEmpId();
                groupedAttendance.computeIfAbsent(empId, k -> new ArrayList<>()).add(d);
            }

            YearMonth yearMonth = YearMonth.of(selectedYear, selectedMonth);
            int daysInMonth = yearMonth.lengthOfMonth();
            List<Integer> weekendDays = new ArrayList<>();
            for (int d = 1; d <= daysInMonth; d++) {
                LocalDate date = LocalDate.of(selectedYear, selectedMonth, d);
                DayOfWeek dow = date.getDayOfWeek();
                if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
                    weekendDays.add(d);
                }
            }

            request.setAttribute("employees", employees);
            request.setAttribute("groupedAttendance", groupedAttendance);
            request.setAttribute("daysInMonth", daysInMonth);
            request.setAttribute("weekendDays", weekendDays);

            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("totalPages", totalPages);

            request.setAttribute("startYear", startYear);
            request.setAttribute("endYear", endYear);
            request.setAttribute("selectedMonth", selectedMonth);
            request.setAttribute("selectedYear", selectedYear);
            request.setAttribute("search", search != null ? search : "");
            request.setAttribute("selectedDepartment", department != null ? department : "");

            request.getRequestDispatcher("Views/HR/dailyAttendance.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error loading data: " + ex.getMessage());
            request.getRequestDispatcher("Views/HR/dailyAttendance.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
