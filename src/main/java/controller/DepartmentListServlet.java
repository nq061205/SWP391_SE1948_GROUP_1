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
import model.Department;
import model.Role;

/**
 *
 * @author Admin
 */
public class DepartmentListServlet extends HttpServlet {

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
            out.println("<title>Servlet DepartmentListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DepartmentListServlet at " + request.getContextPath() + "</h1>");
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
        List<Department> deptList;
        deptList = deptDAO.getAllDepartment();
        String type = request.getParameter("type");
        String depId = request.getParameter("depId");

        if ("edit".equalsIgnoreCase(type) && depId != null) {
            Department editDept = deptDAO.getDepartmentByDepartmentId(depId);
            request.setAttribute("editDept", editDept);
        }
        ses.setAttribute("deptList", deptList);
        //Comment de merge
        request.getRequestDispatcher("Views/departmentlist.jsp").forward(request, response);
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
        String depId = request.getParameter("depId");
        DeptDAO depDAO = new DeptDAO();
        if ("save".equalsIgnoreCase(action)) {
            String depName = request.getParameter("depName");
            String description = request.getParameter("description");
            Department dept = depDAO.getDepartmentByDepartmentId(depId);
            if (dept != null) {
                dept.setDepName(depName);
                dept.setDescription(description);
                depDAO.updateDepartment(dept);
            }
        }
        List<Department> departmentList = depDAO.getAllDepartment();
        request.getSession().setAttribute("deptList", departmentList);
        request.getRequestDispatcher("Views/departmentlist.jsp").forward(request, response);

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
