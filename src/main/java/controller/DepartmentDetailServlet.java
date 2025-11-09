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
import java.util.List;
import model.Department;
import model.Employee;

/**
 *
 * @author Admin
 */
public class DepartmentDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet DepartmentDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DepartmentDetailServlet at " + request.getContextPath() + "</h1>");
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
        DeptDAO depDAO = new DeptDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        String searchkey = request.getParameter("searchkey");
        String genderStr = request.getParameter("gender");
        Boolean gender = null;
        if ("All".equalsIgnoreCase(genderStr)) {
            gender = null;
        } else if (genderStr != null && !genderStr.trim().isEmpty()) {
            gender = Boolean.parseBoolean(genderStr);
        }
        String[] positionTitle = request.getParameterValues("positionTitle");
        int quantityOfPage = 5;
        int currentPage = 1;
        int totalPages;
        int totalResults;
        String deptID = request.getParameter("deptId");
        Department dept = depDAO.getDepartmentByDepartmentId(deptID);
        int empCount = depDAO.countEmployeeInDepartment(deptID);
        Employee manager = empDAO.getManagerByDepartment(deptID);
        String oldSearchKey = (String) ses.getAttribute("oldSearchKey");
        Integer oldPosCount = (Integer) ses.getAttribute("oldPosCount");
        Boolean oldGender = (Boolean) ses.getAttribute("oldGender");
        int newPosCount = (positionTitle == null ? 0 : positionTitle.length);
        Boolean newGender = gender;
        String currentPageStr = request.getParameter("page");
        boolean isFilterChanged
                = (oldPosCount == null ? newPosCount != 0 : !oldPosCount.equals(newPosCount))
                || (oldGender == null ? newGender != null : !oldGender.equals(newGender))
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
        if (positionTitle != null && positionTitle[0].isEmpty()) {
            positionTitle = null;
        }
        List<String> positionList = empDAO.getPositionByDepartment(deptID);

        if (searchkey != null && !searchkey.trim().isEmpty()) {
            totalResults = empDAO.countSearchAndFilterEmployeeByDepartment(deptID, searchkey, gender, positionTitle);
        } else if (gender != null || (positionTitle != null)) {
            totalResults = empDAO.countSearchAndFilterEmployeeByDepartment(deptID, searchkey, gender, positionTitle);
        } else {
            totalResults = empCount;
        }
        totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);

        List<Employee> empList = empDAO.getEmployeesByDepartment(deptID, searchkey, gender, positionTitle, currentPage, quantityOfPage);
        if (empList == null || empList.isEmpty()) {
            request.setAttribute("message", "No results found!");
        }
        request.setAttribute("searchkey", searchkey);
        request.setAttribute("gender", gender);
        request.setAttribute("positionTitle", positionTitle);
        request.setAttribute("totalSearchResults", totalResults);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", currentPage);
        request.setAttribute("empList", empList);
        request.setAttribute("manager", manager);
        request.setAttribute("employeeCount", empCount);
        request.setAttribute("dept", dept);
        request.setAttribute("deptId", deptID);

        ses.setAttribute("oldPosCount", newPosCount);
        ses.setAttribute("oldSearchKey", searchkey);
        ses.setAttribute("oldGender", newGender);
        ses.setAttribute("positionList", positionList);
        request.getRequestDispatcher("Views/departmentdetail.jsp").forward(request, response);
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
