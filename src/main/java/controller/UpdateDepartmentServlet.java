/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DeptDAO;
import dal.EmployeeDAO;
import dal.RolePermissionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Department;
import model.Employee;

/**
 *
 * @author Admin
 */
public class UpdateDepartmentServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateDepartmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateDepartmentServlet at " + request.getContextPath() + "</h1>");
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
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 3)) {
            ses.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        DeptDAO dDAO = new DeptDAO();
        String depID = request.getParameter("depId");
        Department dept = dDAO.getDepartmentByDepartmentId(depID);

        ses.setAttribute("dep", dept);
        request.getRequestDispatcher("Views/updatedepartment.jsp").forward(request, response);
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
        DeptDAO dDAO = new DeptDAO();
        String deptID = request.getParameter("depId");
        String depName = request.getParameter("depName");
        String description = request.getParameter("description");
        String button = request.getParameter("button");
        Department dept = dDAO.getDepartmentByDepartmentId(deptID);

        boolean hasErr = false;
        if (depName.length() > 100) {
            request.setAttribute("NameErr", "Name have the max length is 100");
            hasErr = true;
        }
        if (dDAO.existsByDepName(depName) && !depName.equals(dept.getDepName())) {
            request.setAttribute("NameErr", "DeptName have been existed!");
            hasErr = true;
        }

        if (description.length() > 255) {
            request.setAttribute("descripErr", "Description have the max length is 255");
            hasErr = true;
        }

        if ("save".equalsIgnoreCase(button) && !hasErr) {
            dept.setDepId(deptID);
            dept.setDepName(depName);
            dept.setDescription(description);
            dDAO.updateDepartment(dept);
            request.setAttribute("message", "Update successfully!");
        }
        ses.setAttribute("dep", dept);
        request.getRequestDispatcher("Views/updatedepartment.jsp").forward(request, response);
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
