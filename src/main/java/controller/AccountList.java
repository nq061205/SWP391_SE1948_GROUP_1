/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DeptDAO;
import dal.EmployeeDAO;
import dal.RoleDAO;
import model.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Role;

/**
 *
 * @author Admin
 */
public class AccountList extends HttpServlet {

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
            out.println("<title>Servlet EmployeeList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeList at " + request.getContextPath() + "</h1>");
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
        HttpSession ses = request.getSession();
        EmployeeDAO empDAO = new EmployeeDAO();
        DeptDAO deptDAO = new DeptDAO();
        RoleDAO rDAO = new RoleDAO();
        String searchkey = request.getParameter("searchkey");
        String statusStr = request.getParameter("status");
        Boolean status = null;
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            status = Boolean.parseBoolean(statusStr);
        }
        String[] deptId = request.getParameterValues("deptId");
        String[] roleId = request.getParameterValues("roleId");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        String type = request.getParameter("type");
        String empCode = request.getParameter("empCode");
        int quantityOfPage = 3;
        int currentPage = 1;
        String currentPageStr = request.getParameter("page");
        if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(currentPageStr);
            } catch (NumberFormatException e) {
            }
        }
        List<Employee> empList;
        int totalSearchResults = 0;
        int totalFilterResults =0;
        int totalPages =0;
        int totalResults =0;
        if (searchkey != null && !searchkey.trim().isEmpty()) {
            empList = empDAO.searchEmployeeWithPaging(searchkey, currentPage, quantityOfPage,status, deptId, roleId,sortBy,order);
            totalSearchResults = empDAO.countSearchRecordOfEmployee(searchkey);
            totalPages = (int) Math.ceil((double) totalSearchResults / quantityOfPage);
        } else if (status != null || (deptId != null && deptId.length > 0) || (roleId != null && roleId.length > 0)) {
            empList = empDAO.filterEmployeesWithPaging(status, deptId, roleId, currentPage, quantityOfPage);
            totalFilterResults = empDAO.countFilterRecordOfEmployee(status,deptId,roleId);
            totalPages = (int) Math.ceil((double) totalFilterResults / quantityOfPage);
        } else if (sortBy != null) {
            empList = empDAO.getSortedEmployeeWithPaging(sortBy, order,currentPage,quantityOfPage);
            totalResults = empDAO.countAllRecordOfEmployee();
            totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        } else {
            empList = empDAO.getEmployeeByPage(currentPage, quantityOfPage);
            totalResults = empDAO.countAllRecordOfEmployee();
            totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        }

        if ("edit".equalsIgnoreCase(type) && empCode != null) {
            Employee editEmp = empDAO.getEmployeeByEmpCode(empCode);
            request.setAttribute("editEmp", editEmp);
        }

        List<Role> roleList = rDAO.getAllRoles();
        Map<String, Role> uniqueRolesMap = new LinkedHashMap<>();
        for (Role r : roleList) {
            uniqueRolesMap.putIfAbsent(r.getRoleName(), r);
        }

        List<Role> uniqueRoles = new ArrayList<>(uniqueRolesMap.values());

        request.setAttribute("totalSearchResults", totalSearchResults);
        request.setAttribute("searchkey", searchkey);
        request.setAttribute("roleId", roleId);

        request.setAttribute("deptId", deptId);
        request.setAttribute("status", status);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("order", order);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", currentPage);

        ses.setAttribute("empList", empList);
        ses.setAttribute("roleList", uniqueRoles);
        ses.setAttribute("deptList", deptDAO.getAllDepartment());

        empDAO.close();
        deptDAO.close();
        rDAO.close();

        request.getRequestDispatcher("Views/accountList.jsp").forward(request, response);
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
        HttpSession ses = request.getSession();
        String action = request.getParameter("action");
        EmployeeDAO empDAO = new EmployeeDAO();
        RoleDAO rDAO = new RoleDAO();

        String empCode = request.getParameter("empCode");
        if ("save".equalsIgnoreCase(action)) {
            String email = request.getParameter("email");
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            Employee emp = empDAO.getEmployeeByEmpCode(empCode);
            if (emp != null) {
                emp.setEmail(email);
                Role role = rDAO.getRoleByRoleId(roleId);
                emp.setRole(role);
                empDAO.updateEmployee(emp);
            }
        } else if ("toggle".equalsIgnoreCase(action)) {
            boolean status = Boolean.parseBoolean(request.getParameter("newstatus"));
            Employee emp = empDAO.getEmployeeByEmpCode(empCode);
            if (emp != null) {
                emp.setStatus(status);
                empDAO.updateEmployee(emp);
            }
        }
        int quantityOfPage = 3;
        int currentPage = 1;
        String currentPageStr = request.getParameter("page");
        if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(currentPageStr);
            } catch (NumberFormatException e) {
            }
        }
        List<Employee> empList = empDAO.getEmployeeByPage(currentPage, quantityOfPage);
        ses.setAttribute("empList", empList);
        int totalResults = empDAO.countAllRecordOfEmployee();
        int totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        request.setAttribute("page", currentPage);
        request.setAttribute("totalPages", totalPages);
        empDAO.close();
        rDAO.close();
        response.sendRedirect("accountlist?page=" + currentPage);

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
