/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DeptDAO;
import dal.EmployeeDAO;
import dal.RoleDAO;
import dal.RolePermissionDAO;
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
import model.Department;
import model.Employee;
import model.Role;

/**
 *
 * @author Admin
 */
public class UpdateAccountServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateAccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateAccountServlet at " + request.getContextPath() + "</h1>");
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
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        Employee user = (Employee) ses.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 1)) {
            ses.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        String empCode = request.getParameter("empCode");
        EmployeeDAO empDAO = new EmployeeDAO();
        DeptDAO depDAO = new DeptDAO();
        RoleDAO rDAO = new RoleDAO();
        DeptDAO dDAO = new DeptDAO();
        String deptID = request.getParameter("deptId");
        String roleIDStr = request.getParameter("roleId");
        Department dept = dDAO.getDepartmentByDepartmentId(deptID);
        int roleID = 0;
        if (roleIDStr != null) {
            roleID = Integer.parseInt(roleIDStr);
        }
        List<Department> departments = dDAO.getAllDepartment();
        List<Role> roleList = rDAO.getAllRoles();
        Map<String, Role> uniqueRolesMap = new LinkedHashMap<>();
        for (Role r : roleList) {
            uniqueRolesMap.putIfAbsent(r.getRoleName(), r);
        }

        List<Role> uniqueRoles = new ArrayList<>(uniqueRolesMap.values());
        List<String> managerDepIds = depDAO.getDepartmentsHavingManager();

        Role role = rDAO.getRoleByRoleId(roleID);
        Employee emp = empDAO.getEmployeeByEmpCode(empCode);
        ses.setAttribute("departments", departments);
        ses.setAttribute("roles", uniqueRoles);
        ses.setAttribute("managerDepIds", managerDepIds);
        ses.setAttribute("emp", emp);
        ses.setAttribute("dept", dept);
        ses.setAttribute("role", role);
        request.getRequestDispatcher("Views/updateAccount.jsp").forward(request, response);
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
        String button = request.getParameter("button");
        String empCode = request.getParameter("empCode");
        String email = request.getParameter("email");
        String deptID = request.getParameter("deptId");
        String roleIdStr = request.getParameter("roleId");
        EmployeeDAO dao = new EmployeeDAO();
        RoleDAO rDAO = new RoleDAO();
        DeptDAO dDAO = new DeptDAO();
        int roleID = 0;
        try {
            roleID = Integer.parseInt(roleIdStr);
        } catch (NumberFormatException e) {

        }
        Employee editEmp = dao.getEmployeeByEmpCode(empCode);
        boolean hasErr = false;
        if (email.length() > 100) {
            request.setAttribute("EmailErr", "Email have the max length is 100");
            hasErr = true;

        }
        if (!email.equals(editEmp.getEmail()) && dao.getEmployeeByEmail(email) != null) {
            request.setAttribute("EmailErr", "Email has been existed");
            hasErr = true;
        }
        if ("save".equalsIgnoreCase(button) && !hasErr) {
            editEmp.setEmail(email);
            Department dept = dDAO.getDepartmentByDepartmentId(deptID);
            editEmp.setDept(dept);
            Role role = rDAO.getRoleByRoleId(roleID);
            editEmp.setRole(role);
            dao.updateEmployee(editEmp);
            request.setAttribute("message", "Update successfully!");
        }
        List<Department> departments = dDAO.getAllDepartment();
        List<Role> roleList = rDAO.getAllRoles();
        Map<String, Role> uniqueRolesMap = new LinkedHashMap<>();
        for (Role r : roleList) {
            uniqueRolesMap.putIfAbsent(r.getRoleName(), r);
        }

        List<Role> uniqueRoles = new ArrayList<>(uniqueRolesMap.values());

        request.setAttribute("departments", departments);
        request.setAttribute("roles", uniqueRoles);
        ses.setAttribute("emp", editEmp);
        request.getRequestDispatcher("Views/updateAccount.jsp").forward(request, response);
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
