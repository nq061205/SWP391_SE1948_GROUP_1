/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CandidateDAO;
import dal.DeptDAO;
import dal.EmployeeDAO;
import dal.InterviewDAO;
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
import model.Candidate;
import model.Department;
import model.Employee;
import model.Interview;
import model.Role;

/**
 *
 * @author Admin
 */
public class CreateAccountServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateAccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateAccountServlet at " + request.getContextPath() + "</h1>");
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
        if (user == null || !rperDAO.hasPermission(user.getRole().getRoleId(), 1)) {
            response.sendRedirect("login");
            return;
        }
        InterviewDAO interDAO = new InterviewDAO();
        DeptDAO deptDAO = new DeptDAO();
        RoleDAO rDAO = new RoleDAO();
        CandidateDAO canDAO = new CandidateDAO();

        List<Role> roleList = rDAO.getAllRoles();
        Map<String, Role> uniqueRolesMap = new LinkedHashMap<>();
        for (Role r : roleList) {
            uniqueRolesMap.putIfAbsent(r.getRoleName(), r);
        }

        List<Role> uniqueRoles = new ArrayList<>(uniqueRolesMap.values());

        List<Interview> interList = interDAO.getAllPassNotInEmployee("Pass");

        ses.setAttribute("roleList", uniqueRoles);
        ses.setAttribute("deptList", deptDAO.getAllDepartment());
        request.setAttribute("passedList", interList);
        request.getRequestDispatcher("Views/createaccount.jsp").forward(request, response);
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
        InterviewDAO interDAO = new InterviewDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        DeptDAO depDAO = new DeptDAO();
        RoleDAO roleDAO = new RoleDAO();
        String addCanName = request.getParameter("canName");
        String addEmail = request.getParameter("email");
        String addPhone = request.getParameter("phone");
        String addDepartmentId = request.getParameter("deptId");
        String addRoleId = request.getParameter("roleId");
        String action = request.getParameter("action");
        int roleId = 0;
        try {
            roleId = Integer.parseInt(addRoleId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("add".equalsIgnoreCase(action)) {
            Employee emp = new Employee();
            emp.setEmpCode(empDAO.generateUserName());
            emp.setFullname(addCanName);
            emp.setEmail(addEmail);
            emp.setPhone(addPhone);

            Department dept = depDAO.getDepartmentByDepartmentId(addDepartmentId);
            emp.setDept(dept);

            Role role = roleDAO.getRoleByRoleId(roleId);
            emp.setRole(role);
            emp.setPassword(empDAO.generatePassword());
            emp.setGender(true);
            emp.setStatus(true);
            empDAO.createEmployee(emp);
        }
        List<Interview> interList = interDAO.getAllPassNotInEmployee("Pass");
        request.setAttribute("passedList", interList);
        request.getRequestDispatcher("Views/createaccount.jsp").forward(request, response);

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
