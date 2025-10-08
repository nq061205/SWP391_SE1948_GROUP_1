/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DeptDAO;
import dal.EmployeeDAO;
import dal.RecruitmentPostDAO;
import dal.RoleDAO;
import model.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Department;
import model.Role;

/**
 *
 * @author Admin
 */
public class EmployeeList extends HttpServlet {

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
        Boolean status = (statusStr != null) ? Boolean.parseBoolean(statusStr) : null;
        String[] deptId = request.getParameterValues("deptId");
        String[] roleId = request.getParameterValues("roleId");
        String sortBy = request.getParameter("sortBy");
        String order= request.getParameter("order");
        List<Employee> empList = new ArrayList<>();
        if (searchkey != null && !searchkey.trim().isEmpty()) {
            empList = empDAO.searchEmployee(searchkey);
        } else if (status != null || (deptId != null && deptId.length > 0) || (roleId != null && roleId.length > 0)) {
            empList = empDAO.filterEmployees(status, deptId, roleId);
        }
        else if (sortBy != null ) {
            empList=empDAO.getSortedEmployee(sortBy, order);
        }
        else {
            empList = empDAO.getAllEmployees();
        }
        int totalResults = empList.size();
        
        List<Role> roleList = rDAO.getAllRoles();
        Map<String, Role> uniqueRolesMap = new LinkedHashMap<>();
        for (Role r : roleList) {
            uniqueRolesMap.putIfAbsent(r.getRoleName(), r);
        }

        List<Role> uniqueRoles = new ArrayList<>(uniqueRolesMap.values());
        List<Department> deptList = deptDAO.getAllDepartment();
        String type = request.getParameter("type");
        String empCode = request.getParameter("empCode");

        if ("edit".equalsIgnoreCase(type) && empCode != null) {
            Employee editEmp = empDAO.getEmployeeByEmpCode(empCode);
            request.setAttribute("editEmp", editEmp);
        }
        request.setAttribute("totalResults", totalResults);
        request.setAttribute("searchkey", searchkey);
        request.setAttribute("roleId", roleId);
        request.setAttribute("deptId", deptId);
        request.setAttribute("status", status);
        ses.setAttribute("empList", empList);
        ses.setAttribute("roleList", uniqueRoles);
        ses.setAttribute("deptList", deptList);
        //Comment de merge
        request.getRequestDispatcher("Views/employeeList.jsp").forward(request, response);
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
        List<Employee> empList = empDAO.getAllEmployees();
        request.getSession().setAttribute("empList", empList);
        request.getRequestDispatcher("Views/employeeList.jsp").forward(request, response);

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
