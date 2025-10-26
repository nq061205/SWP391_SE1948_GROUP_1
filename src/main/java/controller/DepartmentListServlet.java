/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DeptDAO;
import dal.EmployeeDAO;
import dal.RoleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Department;

/**
 *
 * @author Admin
 */
public class DepartmentListServlet extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses = request.getSession();
        DeptDAO deptDAO = new DeptDAO();
        List<Department> deptList;
        deptList = deptDAO.getAllDepartment();
        String type = request.getParameter("type");
        String depId = request.getParameter("depId");

        if ("edit".equalsIgnoreCase(type) && depId != null) {
            Department editDept = deptDAO.getDepartmentByDepartmentId(depId);
            request.setAttribute("editDept", editDept);
        }
        ses.setAttribute("deptList", deptList);
        request.getRequestDispatcher("Views/departmentlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses = request.getSession();
        String action = request.getParameter("action");
        String editDepId = request.getParameter("depId");
        String addDepId = request.getParameter("deptID");
        String addDepName = request.getParameter("deptName");
        String addDescription = request.getParameter("description");
        DeptDAO depDAO = new DeptDAO();
        if ("save".equalsIgnoreCase(action)) {
            String depName = request.getParameter("depName");
            String description = request.getParameter("description");
            Department dept = depDAO.getDepartmentByDepartmentId(editDepId);
            if (dept != null) {
                dept.setDepName(depName);
                dept.setDescription(description);
                depDAO.updateDepartment(dept);
            }
        } else if ("add".equalsIgnoreCase(action)) {
            Department dept = new Department();
            dept.setDepId(addDepId.toUpperCase());
            dept.setDepName(addDepName);
            dept.setDescription(addDescription);
            depDAO.createDepartment(dept);
        }
        List<Department> departmentList = depDAO.getAllDepartment();
        ses.setAttribute("deptList", departmentList);
        request.getRequestDispatcher("Views/departmentlist.jsp").forward(request, response);

    }

}
