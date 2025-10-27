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
public class AccountListServlet extends HttpServlet {

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
        if ("All".equalsIgnoreCase(statusStr)) {
            status = null;
        } else if (statusStr != null && !statusStr.trim().isEmpty()) {
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
        String oldSearchKey = (String) ses.getAttribute("oldSearchKey");
        Integer oldDeptCount = (Integer) ses.getAttribute("oldDeptCount");
        Integer oldRoleCount = (Integer) ses.getAttribute("oldRoleCount");
        Boolean oldStatus = (Boolean) ses.getAttribute("oldStatus");
        int newDeptCount = (deptId == null ? 0 : deptId.length);
        int newRoleCount = (roleId == null ? 0 : roleId.length);
        Boolean newStatus = status;
        String currentPageStr = request.getParameter("page");
        boolean isFilterChanged = (oldDeptCount == null || oldDeptCount != newDeptCount)
                || (oldRoleCount == null || oldRoleCount != newRoleCount)
                || ((oldStatus == null && newStatus != null) || (oldStatus != null && !oldStatus.equals(newStatus)))
                || (oldSearchKey == null ? searchkey != null && !searchkey.isEmpty()
                        : !oldSearchKey.equals(searchkey));
        if (isFilterChanged) {
            currentPage = 1;
        } else {
            if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
                try {
                    currentPage = Integer.parseInt(currentPageStr);
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }
        }
        if (deptId!= null && deptId[0].isEmpty()) {
            deptId=null;
        }
        if (roleId!= null && roleId[0].isEmpty()) {
            roleId=null;
        }

        int totalResults = 0;
        if (searchkey != null && !searchkey.trim().isEmpty()) {
            totalResults = empDAO.countSearchAndFilterAccount(searchkey, status, deptId, roleId);
        } else if (status != null || (deptId != null && deptId.length > 0) || (roleId != null && roleId.length > 0)) {
            totalResults = empDAO.countSearchAndFilterAccount(searchkey, status, deptId, roleId);
        } else if (sortBy != null) {
            totalResults = empDAO.countAllRecordOfEmployee();
        } else {
            totalResults = empDAO.countAllRecordOfEmployee();
        }
        int totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        List<Employee> empList = empDAO.ManageEmployeeWithPaging(searchkey, currentPage, quantityOfPage, status, deptId, roleId, sortBy, order);

        List<Role> roleList = rDAO.getAllRoles();
        Map<String, Role> uniqueRolesMap = new LinkedHashMap<>();
        for (Role r : roleList) {
            uniqueRolesMap.putIfAbsent(r.getRoleName(), r);
        }

        List<Role> uniqueRoles = new ArrayList<>(uniqueRolesMap.values());

        if (empList == null || empList.isEmpty()) {
            request.setAttribute("message", "No results found!");
        }

        request.setAttribute("totalSearchResults", totalResults);
        request.setAttribute("searchkey", searchkey);
        request.setAttribute("roleId", roleId);
        request.setAttribute("deptId", deptId);
        request.setAttribute("status", status);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("order", order);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", currentPage);

        ses.setAttribute("oldDeptCount", newDeptCount);
        ses.setAttribute("oldRoleCount", newRoleCount);
        ses.setAttribute("oldStatus", newStatus);
        ses.setAttribute("oldSearchKey", searchkey);
        ses.setAttribute("empList", empList);
        ses.setAttribute("roleList", uniqueRoles);
        ses.setAttribute("deptList", deptDAO.getAllDepartment());


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
        DeptDAO dDAO = new DeptDAO();

        int quantityOfPage = 3;
        int currentPage = 1;
        String currentPageStr = request.getParameter("page");
        if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(currentPageStr);
            } catch (Exception ignored) {
            }
        }

        String searchkey = request.getParameter("searchkey");
        String statusStr = request.getParameter("status");
        Boolean status = null;
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            status = Boolean.parseBoolean(statusStr);
        }
        String[] deptId = request.getParameterValues("deptId");
        String[] roleIds = request.getParameterValues("roleId");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        String empCode = request.getParameter("empCode");
        if ("toggle".equalsIgnoreCase(action)) {
            boolean newStatus = Boolean.parseBoolean(request.getParameter("newstatus"));

            Employee emp = empDAO.getEmployeeByEmpCode(empCode);
            if (emp != null) {
                emp.setStatus(newStatus);
                empDAO.updateEmployee(emp);
            }

            List<Employee> empList = empDAO.ManageEmployeeWithPaging(searchkey, currentPage, quantityOfPage, status, deptId, roleIds, sortBy, order);
            int totalSearchRecords = empDAO.countSearchAndFilterAccount(searchkey, status, deptId, roleIds);
            int totalEmployees = empDAO.countSearchAndFilterAccount(searchkey, status, deptId, roleIds);
            int totalPages = (int) Math.ceil((double) totalEmployees / quantityOfPage);

            ses.setAttribute("empList", empList);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("page", currentPage);
            request.setAttribute("status", status);
            request.setAttribute("deptId", deptId);
            request.setAttribute("roleId", roleIds);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("order", "asc");
            request.setAttribute("totalSearchResults", totalSearchRecords);
            request.setAttribute("searchkey", searchkey);

            request.getRequestDispatcher("Views/accountList.jsp").forward(request, response);
            return;
        }
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
