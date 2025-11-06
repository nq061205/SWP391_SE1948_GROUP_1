/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DependantDAO;
import dal.EmployeeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import model.Dependant;
import model.Employee;

/**
 *
 * @author Admin
 */
public class DependantDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet DependantDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DependantDetailServlet at " + request.getContextPath() + "</h1>");
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
        DependantDAO depenDAO = new DependantDAO();
        String empIdStr = request.getParameter("empId");
        String tab = request.getParameter("tab");
        String searchKey = request.getParameter("searchkey");
        String relationship = request.getParameter("relationship");
        String gender = request.getParameter("gender");
        int empId = 0;
        try {
            empId = Integer.parseInt(empIdStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int page = 1;
        int pageSize = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            page = 1;
        }
        int totalRecords = depenDAO.countFilteredDependantsByEmpId(empId, searchKey, relationship, gender);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        List<Dependant> dependantList = depenDAO.getFilteredDependantsByEmpId(empId, searchKey, relationship, gender, page, pageSize);
        List<String> relationList = depenDAO.getRelationshipListEnumValues();

        request.setAttribute("relationList", relationList);
        request.setAttribute("dependantList", dependantList);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchkey", searchKey);

        request.setAttribute("tab", tab);
        request.setAttribute("empId", empId);
        request.getRequestDispatcher("Views/employeedetails.jsp").forward(request, response);
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
        DependantDAO depDAO = new DependantDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        String name = request.getParameter("name");
        String relationship = request.getParameter("relationship");
        String dobStr = request.getParameter("dob");
        String genderStr = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String empIdStr = request.getParameter("empId");
        String tab = request.getParameter("tab");

        boolean hasError = false;
        String nameErr = null, dobErr = null, phoneErr = null;

        Date dob = null;
        Boolean gender = null;
        int empId = 0;

        try {
            empId = Integer.parseInt(empIdStr);
        } catch (Exception e) {
            hasError = true;
        }

        try {
            dob = Date.valueOf(dobStr);
        } catch (Exception e) {
            dobErr = "Invalid date format!";
            hasError = true;
        }

        try {
            gender = Boolean.parseBoolean(genderStr);
        } catch (Exception e) {
            gender = null;
        }

        if (dob != null) {
            LocalDate today = LocalDate.now();
            LocalDate dobLocal = dob.toLocalDate();
            if (dobLocal.isAfter(today)) {
                dobErr = "Date of birth must not be after today!";
                hasError = true;
            } else {
                int age = Period.between(dobLocal, today).getYears();
                if ((relationship.equalsIgnoreCase("Spouse") || relationship.equalsIgnoreCase("Parent"))
                        && age < 18) {
                    dobErr = "A Spouse or Parent must be at least 18 years old!";
                    hasError = true;
                }
            }
        }
        List<Dependant> dependantList = depDAO.getAllDependantByEmpId(empId);
        List<String> relationList = depDAO.getRelationshipListEnumValues();

        if (hasError) {
            request.setAttribute("DobErr", dobErr);
            request.setAttribute("dependantList", dependantList);
            request.setAttribute("relationList", relationList);
            request.setAttribute("name", name);
            request.setAttribute("relationship", relationship);
            request.setAttribute("gender", gender);
            request.setAttribute("phone", phone);
            request.setAttribute("dob", dob);
            request.setAttribute("empId", empId);
            request.setAttribute("tab", tab);
            request.setAttribute("showModal", true);
            request.getRequestDispatcher("Views/employeedetails.jsp").forward(request, response);
            return;
        }
        Dependant dep = new Dependant();
        dep.setName(name);
        dep.setRelationship(relationship);
        dep.setDob(dob);
        dep.setGender(gender);
        dep.setPhone(phone);
        Employee emp = empDAO.getEmployeeByEmpId(empId);
        dep.setEmployee(emp);

        depDAO.createDependant(dep);
        response.sendRedirect("employeedetail?empId=" + empId + "&tab=" + tab);
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
