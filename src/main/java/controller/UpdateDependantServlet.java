/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DependantDAO;
import dal.EmployeeDAO;
import dal.RolePermissionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
public class UpdateDependantServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateDependantServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateDependantServlet at " + request.getContextPath() + "</h1>");
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
        if(user == null){
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 4)) {
            ses.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        DependantDAO depenDAO = new DependantDAO();
        String empIdStr = request.getParameter("empId");
        int empId = 0;
        try {
            empId = Integer.parseInt(empIdStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dependant dependant = depenDAO.getDependantByEmpId(empId);
        List<String> relationList = depenDAO.getRelationshipListEnumValues();

        request.setAttribute("relationList", relationList);
        request.setAttribute("empId", empId);
        ses.setAttribute("dependant", dependant);
        request.getRequestDispatcher("Views/updatedependant.jsp").forward(request, response);
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
        DependantDAO depenDAO = new DependantDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        String name = request.getParameter("name");
        String relationship = request.getParameter("relationship");
        String dobStr = request.getParameter("dob");
        String genderStr = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String button = request.getParameter("button");
        String empIdStr = request.getParameter("empId");
        Date dob = null;
        int empId = 0;
        boolean gender = true;
        try {
            dob = Date.valueOf(dobStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            gender = Boolean.parseBoolean("genderStr");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            empId = Integer.parseInt(empIdStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean hasError = false;
        LocalDate today = LocalDate.now();
        LocalDate dobLocal = dob.toLocalDate();
        if (dobLocal.isAfter(today)) {
            request.setAttribute("DobErr", "Date of birth must not be after today!");
            hasError = true;
        } else {
            int age = Period.between(dobLocal, today).getYears();
            if ((relationship.equalsIgnoreCase("Spouse") || relationship.equalsIgnoreCase("Parent"))
                    && age < 18) {
                request.setAttribute("DobErr", "Parent or spouse must be at least 18 year olds!");
                hasError = true;
            }
        }

        if (!phone.matches("0\\d{9}")) {
            request.setAttribute("PhoneErr", "Phone number must start with 0 and have exactly 10 digits!");
            hasError = true;
        }
        Dependant dependant = depenDAO.getDependantByEmpId(empId);
        List<String> relationList = depenDAO.getRelationshipListEnumValues();
        if (hasError) {
            request.setAttribute("empId", empId);
            request.setAttribute("relationList", relationList);
            ses.setAttribute("dependant", dependant);
            request.getRequestDispatcher("Views/updatedependant.jsp").forward(request, response);
            return;
        }
        if ("save".equalsIgnoreCase(button)) {
            dependant.setName(name);
            dependant.setRelationship(relationship);
            dependant.setDob(dob);
            dependant.setGender(gender);
            dependant.setPhone(phone);
            Employee emp = empDAO.getEmployeeByEmpId(empId);
            dependant.setEmployee(emp);

            depenDAO.updateDependant(dependant);
            request.setAttribute("empId", empId);
            request.setAttribute("message", "Updated succesfully!");
            request.setAttribute("relationList", relationList);
            ses.setAttribute("dependant", dependant);
            request.getRequestDispatcher("Views/updatedependant.jsp").forward(request, response);
        }

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
