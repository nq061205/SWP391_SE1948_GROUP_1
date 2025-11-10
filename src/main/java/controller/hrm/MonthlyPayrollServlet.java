/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hrm;

import dal.EmployeeDAO;
import dal.DeptDAO;
import dal.PayrollDAO;
import helper.PayrollService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
        String action = request.getParameter("action");

        DeptDAO deptDAO = new DeptDAO();
        List<Department> departments = deptDAO.getAllDepartment();

        LocalDate now = LocalDate.now();
        int selectedMonth = (monthParam != null && !monthParam.isEmpty())
                ? Integer.parseInt(monthParam)
                : now.getMonthValue();
        int selectedYear = (yearParam != null && !yearParam.isEmpty())
                ? Integer.parseInt(yearParam)
                : now.getYear();
        int startYear = 2020;
        int endYear = now.getYear();

        PayrollService payrollService = new PayrollService();
        if ("calculate".equals(action)) {
            try {
                EmployeeDAO employeeDAO = new EmployeeDAO();
                List<Employee> allEmployees = employeeDAO.getAllEmployees();
                List<Integer> empIds = allEmployees.stream()
                        .map(emp -> emp.getEmpId()) 
                        .collect(Collectors.toList());

                payrollService.calculatePayrollForMonth(selectedMonth, selectedYear, empIds);

                request.setAttribute("successMessage",
                        "Payroll calculated successfully for " + empIds.size() + " employees!");
            } catch (Exception e) {
                request.setAttribute("errorMessage",
                        "Error calculating payroll: " + e.getMessage());
                e.printStackTrace();
            }
        }

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
        List<Integer> empIds = employees.stream()
                .map(Employee::getEmpId)
                .collect(Collectors.toList());

        PayrollDAO payrollDAO = new PayrollDAO();
        List<Payroll> payrollList = payrollDAO.getPayrollByEmpIds(empIds, selectedMonth, selectedYear);

        Map<Integer, Payroll> payrollMap = new HashMap<>();
        for (Payroll p : payrollList) {
            payrollMap.put(p.getEmployee().getEmpId(), p);
        }

        double sumRegularSalary = 0;
        double sumOTEarning = 0;
        double sumAllowance = 0;
        double sumGrossSalary = 0;
        double sumSI = 0;
        double sumHI = 0;
        double sumUI = 0;
        double sumTotalInsurance = 0;
        double sumTaxableIncome = 0;
        double sumTax = 0;
        double sumNetSalary = 0;

        for (Payroll p : payrollList) {
            sumRegularSalary += p.getRegularSalary();
            sumOTEarning += p.getOtEarning();

            double allowance = p.getInsuranceBase() - p.getRegularSalary();
            sumAllowance += allowance;

            double grossSalary = p.getRegularSalary() + p.getOtEarning() + allowance;
            sumGrossSalary += grossSalary;

            sumSI += p.getSi();
            sumHI += p.getHi();
            sumUI += p.getUi();
            double totalInsurance = p.getSi() + p.getHi() + p.getUi();
            sumTotalInsurance += totalInsurance;

            sumTaxableIncome += p.getTaxIncome();
            sumTax += p.getTax();

            double netSalary = grossSalary - totalInsurance - p.getTax();
            sumNetSalary += netSalary;
        }

        int standardWorkDays = payrollService.getStandardWorkDays(selectedMonth, selectedYear);

        request.setAttribute("departments", departments);
        request.setAttribute("employees", employees);
        request.setAttribute("payrollMap", payrollMap);
        request.setAttribute("payrollList", payrollList);

        request.setAttribute("sumRegularSalary", sumRegularSalary);
        request.setAttribute("sumOTEarning", sumOTEarning);
        request.setAttribute("sumAllowance", sumAllowance);
        request.setAttribute("sumGrossSalary", sumGrossSalary);
        request.setAttribute("sumSI", sumSI);
        request.setAttribute("sumHI", sumHI);
        request.setAttribute("sumUI", sumUI);
        request.setAttribute("sumTotalInsurance", sumTotalInsurance);
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

        request.setAttribute("standardWorkDays", standardWorkDays);

        request.getRequestDispatcher("Views/HRM/payrollManagement.jsp").forward(request, response);
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
