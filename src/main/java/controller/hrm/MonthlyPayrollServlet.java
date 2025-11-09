/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hrm;

import dal.EmployeeDAO;
import dal.DeptDAO;
import dal.PayrollDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Department;
import model.Employee;
import model.Payroll;

/**
 *
 * @author admin
 */
public class MonthlyPayrollServlet extends HttpServlet {

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
            out.println("<title>Servlet MonthlyPayrollServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MonthlyPayrollServlet at " + request.getContextPath() + "</h1>");
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

        DeptDAO deptDAO = new DeptDAO();
        List<Department> departments = deptDAO.getAllDepartment();

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

        EmployeeDAO employeeDAO = new EmployeeDAO();
        long totalRecords = employeeDAO.countEmployees(search, department);
        int totalPages = (totalRecords > 0) ? (int) Math.ceil((double) totalRecords / pageSize) : 1;
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }
        int offset = (page - 1) * pageSize;

        List<Employee> employees = employeeDAO.getEmployees(offset, pageSize, search, department);
        List<Integer> empIds = employees.stream().map(e -> e.getEmpId()).collect(Collectors.toList());

        PayrollDAO payrollDAO = new PayrollDAO();
        List<Payroll> salaryList = payrollDAO.getPayrollByEmpIds(empIds, selectedMonth, selectedYear);

        Map<Integer, List<Payroll>> groupedSalary = new HashMap<>();
        for (Payroll p : salaryList) {
            int empId = p.getEmployee().getEmpId();
            groupedSalary.computeIfAbsent(empId, k -> new ArrayList<>()).add(p);
        }

        Map<Integer, Double> totalRegularSalaryMap = new HashMap<>();
        Map<Integer, Double> totalOTEarningMap = new HashMap<>();
        Map<Integer, Double> totalAllowanceMap = new HashMap<>();
        Map<Integer, Double> totalInsuranceBaseMap = new HashMap<>();
        Map<Integer, Double> totalSIMap = new HashMap<>();
        Map<Integer, Double> totalHIMap = new HashMap<>();
        Map<Integer, Double> totalUIMap = new HashMap<>();
        Map<Integer, Double> totalTaxableIncomeMap = new HashMap<>();
        Map<Integer, Double> totalTaxMap = new HashMap<>();
        Map<Integer, Double> totalNetSalaryMap = new HashMap<>();

        for (Map.Entry<Integer, List<Payroll>> entry : groupedSalary.entrySet()) {
            double regularSalary = 0;
            double otEarning = 0;
            double allowance = 0;
            double insuranceBase = 0;
            double si = 0;
            double hi = 0;
            double ui = 0;
            double taxableIncome = 0;
            double tax = 0;
            double netSalary = 0;

            for (Payroll p : entry.getValue()) {
                regularSalary += p.getRegularSalary();
                otEarning += p.getOtEarning();
//                allowance += p.getAllowance();
                insuranceBase += p.getInsuranceBase();
                si += p.getSi();
                hi += p.getHi();
                ui += p.getUi();
                taxableIncome += p.getTaxIncome();
                tax += p.getTax();
//                netSalary += p.getNetSalary();
            }

            totalRegularSalaryMap.put(entry.getKey(), regularSalary);
            totalOTEarningMap.put(entry.getKey(), otEarning);
            totalAllowanceMap.put(entry.getKey(), allowance);
            totalInsuranceBaseMap.put(entry.getKey(), insuranceBase);
            totalSIMap.put(entry.getKey(), si);
            totalHIMap.put(entry.getKey(), hi);
            totalUIMap.put(entry.getKey(), ui);
            totalTaxableIncomeMap.put(entry.getKey(), taxableIncome);
            totalTaxMap.put(entry.getKey(), tax);
            totalNetSalaryMap.put(entry.getKey(), netSalary);
        }

        // Tính tổng cộng cho toàn bộ danh sách
        double sumRegularSalary = totalRegularSalaryMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumOTEarning = totalOTEarningMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumAllowance = totalAllowanceMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumInsuranceBase = totalInsuranceBaseMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumSI = totalSIMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumHI = totalHIMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumUI = totalUIMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumTaxableIncome = totalTaxableIncomeMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumTax = totalTaxMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumNetSalary = totalNetSalaryMap.values().stream().mapToDouble(Double::doubleValue).sum();

        request.setAttribute("departments", departments);
        request.setAttribute("employees", employees);
        request.setAttribute("salaryList", salaryList);
        request.setAttribute("groupedSalary", groupedSalary);
        request.setAttribute("totalRegularSalaryMap", totalRegularSalaryMap);
        request.setAttribute("totalOTEarningMap", totalOTEarningMap);
        request.setAttribute("totalAllowanceMap", totalAllowanceMap);
        request.setAttribute("totalInsuranceBaseMap", totalInsuranceBaseMap);
        request.setAttribute("totalSIMap", totalSIMap);
        request.setAttribute("totalHIMap", totalHIMap);
        request.setAttribute("totalUIMap", totalUIMap);
        request.setAttribute("totalTaxableIncomeMap", totalTaxableIncomeMap);
        request.setAttribute("totalTaxMap", totalTaxMap);
        request.setAttribute("totalNetSalaryMap", totalNetSalaryMap);

        request.setAttribute("sumRegularSalary", sumRegularSalary);
        request.setAttribute("sumOTEarning", sumOTEarning);
        request.setAttribute("sumAllowance", sumAllowance);
        request.setAttribute("sumInsuranceBase", sumInsuranceBase);
        request.setAttribute("sumSI", sumSI);
        request.setAttribute("sumHI", sumHI);
        request.setAttribute("sumUI", sumUI);
        request.setAttribute("sumTaxableIncome", sumTaxableIncome);
        request.setAttribute("sumTax", sumTax);
        request.setAttribute("sumNetSalary", sumNetSalary);

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

        request.getRequestDispatcher("Views/HRM/PayrollManagement.jsp").forward(request, response);
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
