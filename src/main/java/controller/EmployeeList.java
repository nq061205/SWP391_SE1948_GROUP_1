/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.EmployeeDAO;
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
import java.util.List;

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
        List<Employee> empList = new ArrayList<>();
        empList = empDAO.getAllEmployees();
        String type = request.getParameter("type");
        String empCode = request.getParameter("empCode");

        if ("edit".equalsIgnoreCase(type) && empCode != null) {
            Employee editEmp = empDAO.getEmployeeByEmpCode(empCode);
            request.setAttribute("editEmp", editEmp);
        } else if ("delete".equalsIgnoreCase(type) && empCode != null) {
            empDAO.deleteEmployee(empCode);
        }


        ses.setAttribute("empList", empList);
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
        if ("save".equalsIgnoreCase(action)) {
            String empCode = request.getParameter("empCode");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
            String dobStr = request.getParameter("dob");
            LocalDate localDate = LocalDate.parse(dobStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String phone = request.getParameter("phone");
            String positionTittle = request.getParameter("positionTitle");
            int dependantCount = Integer.parseInt(request.getParameter("dependantCount"));

            Employee emp = empDAO.getEmployeeByEmpCode(empCode);
            if (emp != null) {
                emp.setFullname(fullname);
                emp.setEmail(email);
                emp.setGender(gender);
                emp.setDob(java.sql.Date.valueOf(localDate));
                emp.setPhone(phone);
                emp.setPositionTitle(positionTittle);
                emp.setDependantCount(dependantCount);
                empDAO.updateEmployee(emp); // update vào DB
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            String empCode = request.getParameter("empCode");
            empDAO.deleteEmployee(empCode);
        }
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
