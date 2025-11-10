/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Employee;

/**
 *
 * @author Admin
 */
public class UpdateEmployeeServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateEmployeeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateEmployeeServlet at " + request.getContextPath() + "</h1>");
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
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 4)) {
            ses.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        String empCode = request.getParameter("empCode");
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee emp = empDAO.getEmployeeByEmpCode(empCode);
        boolean hasDeptManager = empDAO.hasDeptManager(emp.getDept().getDepId());

        boolean hasHRManager = empDAO.hasHRManager(emp.getDept().getDepId());
        List<String> posList = empDAO.getAllPosition();
        List<Map<String, String>> posOptions = new ArrayList<>();
        for (String pos : posList) {
            Map<String, String> option = new HashMap<>();
            option.put("value", pos);
            if (pos.equals(emp.getPositionTitle())) {
                option.put("selected", "true");
            } else {
                option.put("selected", "false");
            }

            boolean isDisabled = false;
            boolean notAllowed = false;

            if (emp.getDept().getDepName().equals("HR") && pos.equals("Dept Manager")) {
                isDisabled = true;
                notAllowed = true;
            } else if (emp.getDept().getDepName().equals("HR") && pos.equals("HR Manager") && hasHRManager) {
                isDisabled = true;
                notAllowed = false;
            } else if (!emp.getDept().getDepName().equals("HR") && pos.equals("HR Manager")) {
                isDisabled = true;
                notAllowed = true;
            } else if (!emp.getDept().getDepName().equals("HR") && pos.equals("Dept Manager") && hasDeptManager) {
                isDisabled = true;
                notAllowed = false;
            }

            option.put("disabled", String.valueOf(isDisabled));
            if (notAllowed) {
                option.put("label", "(Not Allowed)");
            } else if (isDisabled) {
                option.put("label", "(Already Assigned)");
            } else {
                option.put("label", "");
            }

            posOptions.add(option);
        }

        request.setAttribute("posList", posList);
        request.setAttribute("posOptions", posOptions);
        ses.setAttribute("emp", emp);
        request.getRequestDispatcher("Views/updateemployee.jsp").forward(request, response);
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
        EmployeeDAO empDAO = new EmployeeDAO();
        String empCode = request.getParameter("empCode");
        String email = request.getParameter("email");
        String dobStr = request.getParameter("dob");
        boolean validate = false;
        Date dob = null;
        if (dobStr != null && !dobStr.isBlank()) {
            try {
                dob = Date.valueOf(dobStr);
                LocalDate today = LocalDate.now();
                if (dob.after(Date.valueOf(LocalDate.now()))) {
                    request.setAttribute("dobErr", "Date of birth cannot be in the future");
                    validate = true;
                } else {
                    LocalDate dobLocal = dob.toLocalDate();
                    LocalDate minAgeDate = today.minusYears(18);
                    if (dobLocal.isAfter(minAgeDate)) {
                        request.setAttribute("dobErr", "Employee must be at least 18 years old");
                        validate = true;
                    }
                }

            } catch (IllegalArgumentException e) {
            }
        }
        String positionTitle = request.getParameter("positionTitle");
        String button = request.getParameter("button");
        Employee emp = empDAO.getEmployeeByEmpCode(empCode);
        if (email.length() > 100) {
            request.setAttribute("EmailErr", "Email have the max length is 100!");
            validate = true;
        }
        if ("save".equalsIgnoreCase(button) && validate == false) {
            emp.setEmail(email);
            emp.setDob(dob);
            emp.setPositionTitle(positionTitle);
            empDAO.updateEmployee(emp);
            request.setAttribute("message", "Update successfully!");
        }
        List<String> posList = empDAO.getAllPosition();

        request.setAttribute("posList", posList);
        ses.setAttribute("emp", emp);
        request.getRequestDispatcher("Views/updateemployee.jsp").forward(request, response);

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
