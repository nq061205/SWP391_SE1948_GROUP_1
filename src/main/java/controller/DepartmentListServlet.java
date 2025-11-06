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
import java.util.List;
import model.Department;
import model.Employee;

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
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        Employee user = (Employee) ses.getAttribute("user");

        if (user == null || !rperDAO.hasPermission(user.getRole().getRoleId(), 3)) {
            response.sendRedirect("login");
            return;
        }
        String searchKey = request.getParameter("searchkey");
        String deptId = request.getParameter("deptId");
        int page = 1;
        int pageSize = 5;
        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            page = Integer.parseInt(pageParam);
        }
        DeptDAO deptDAO = new DeptDAO();
        int totalResults = deptDAO.countDepartmentsByFilter(deptId, searchKey);
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);
        List<Department> deptList;
        deptList = deptDAO.getDepartmentsByFilter(deptId, searchKey, page, pageSize);
        List<Department> deptNameList = deptDAO.getAllDepartment();
        String type = request.getParameter("type");
        String depId = request.getParameter("depId");

        if ("edit".equalsIgnoreCase(type) && depId != null) {
            Department editDept = deptDAO.getDepartmentByDepartmentId(depId);
            request.setAttribute("editDept", editDept);
        }
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchkey", searchKey);
        request.setAttribute("deptNameList", deptNameList);
        request.setAttribute("page", page);
        ses.setAttribute("deptList", deptList);
        request.getRequestDispatcher("Views/departmentlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses = request.getSession();
        String action = request.getParameter("action");
        String addDepId = request.getParameter("deptID");
        String addDepName = request.getParameter("deptName");
        String addDescription = request.getParameter("description");
        String searchKey = request.getParameter("searchkey");
        String deptId = request.getParameter("deptId");
        int page = 1;
        int pageSize = 5;
        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            page = Integer.parseInt(pageParam);
        }
        DeptDAO depDAO = new DeptDAO();
        List<Department> deptNameList = depDAO.getAllDepartment();
        int totalResults = depDAO.countDepartmentsByFilter(deptId, searchKey);
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);
        if ("add".equalsIgnoreCase(action)) {
            if (depDAO.existsById(addDepId.trim().toUpperCase())) {
                request.setAttribute("errorMessage", "Department ID already exists!");
            } else {
                Department dept = new Department();
                dept.setDepId(addDepId.trim().toUpperCase());
                dept.setDepName(addDepName != null ? addDepName.trim() : "");
                dept.setDescription(addDescription != null ? addDescription.trim() : "");
                depDAO.createDepartment(dept);
                response.sendRedirect("departmentlist");
                return;
            }
        }

        List<Department> departmentList = depDAO.getDepartmentsByFilter(deptId, searchKey, page, pageSize);
        request.setAttribute("deptNameList", deptNameList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchkey", searchKey);
        request.setAttribute("page", page);
        ses.setAttribute("deptList", departmentList);

        request.getRequestDispatcher("Views/departmentlist.jsp").forward(request, response);

    }

}
