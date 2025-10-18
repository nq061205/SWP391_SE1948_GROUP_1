/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CandidateDAO;
import dal.EmployeeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Candidate;
import model.Employee;

/**
 *
 * @author Admin
 */
public class EmployeeListServlet extends HttpServlet {

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
            out.println("<title>Servlet EmployeeListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeListServlet at " + request.getContextPath() + "</h1>");
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
        String searchkey = request.getParameter("searchkey");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        String type = request.getParameter("type");
        String empCode = request.getParameter("empCode");
        String ageRange = request.getParameter("ageRange");
        String genderStr = request.getParameter("gender");
        CandidateDAO canDAO = new CandidateDAO();
        List<Integer> idList = canDAO.getAllPassCandidateID("Pass");
        List<Candidate> canList = canDAO.getAllPassedCandidates(idList);
        for (int i = 0; i < canList.size(); i++) {
            if (!empDAO.existsByEmail(canList.get(i).getEmail())) {
                Candidate can = canList.get(i);
                String username = empDAO.generateUserName();
                String password = empDAO.generatePassword();
                String email = can.getEmail();
                String fullname = can.getName();
                String phone = can.getPhone();
                empDAO.createEmployee(username, password, fullname, email, true, phone);
            }
        }
        Boolean gender = null;
        if (genderStr != null && !genderStr.trim().isEmpty()) {
            gender = Boolean.parseBoolean(genderStr);
        }
        String[] positionTitle = request.getParameterValues("positionTitle");
        int quantityOfPage = 5;
        int currentPage = 1;
        String currentPageStr = request.getParameter("page");
        if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(currentPageStr);
            } catch (NumberFormatException e) {
            }
        }
        int totalSearchResults = 0;
        int totalFilterResults = 0;
        int totalPages = 0;
        int totalResults = 0;
        List<String> positionList = empDAO.getAllPosition();
        List<Employee> empList = empDAO.manageEmployeeForHR(searchkey, currentPage, quantityOfPage, gender, positionTitle, ageRange, sortBy, order);
        if (searchkey != null && !searchkey.trim().isEmpty()) {
            totalSearchResults = empDAO.countSearchRecordOfEmployee(searchkey);
            totalPages = (int) Math.ceil((double) totalSearchResults / quantityOfPage);
            currentPage=1;
        } else if (gender != null || (positionTitle != null) || (ageRange != null)) {
            totalFilterResults = empList.size();
            totalPages = (int) Math.ceil((double) totalFilterResults / quantityOfPage);
            currentPage=1;
        } else if (sortBy != null) {
            totalResults = empDAO.countAllRecordOfEmployee();
            totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
            currentPage=1;
        } else {
            totalResults = empDAO.countAllRecordOfEmployee();
            totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        }

        if ("edit".equalsIgnoreCase(type) && empCode != null) {
            Employee editEmp = empDAO.getEmployeeByEmpCode(empCode);
            request.setAttribute("editEmp", editEmp);
        }
        request.setAttribute("searchkey", searchkey);
        request.setAttribute("gender", gender);
        request.setAttribute("ageRange", ageRange);
        request.setAttribute("positionTitle", positionTitle);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("totalResults", totalResults);
        request.setAttribute("totalFilterResults", totalFilterResults);
        request.setAttribute("totalSearchResults", totalSearchResults);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", currentPage);
        ses.setAttribute("empList", empList);
        ses.setAttribute("positionList", positionList);
        empDAO.close();
        request.getRequestDispatcher("Views/employeelist.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        EmployeeDAO empDAO = new EmployeeDAO();
        String empCode = request.getParameter("empCode");
        String dobStr = request.getParameter("dob");
        String dependant_count = request.getParameter("dependantCount");

        if ("save".equalsIgnoreCase(action)) {

            Employee emp = empDAO.getEmployeeByEmpCode(empCode);

            int currentPage = 1;
            int quantityOfPage = 5;
            String currentPageStr = request.getParameter("page");
            if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
                try {
                    currentPage = Integer.parseInt(currentPageStr);
                } catch (NumberFormatException e) {
                }
            }
            String searchkey = request.getParameter("searchkey");
            String ageRange = request.getParameter("ageRange");
            String genderStr = request.getParameter("gender");
            Boolean gender = null;
            if (genderStr != null && !genderStr.trim().isEmpty()) {
                gender = Boolean.parseBoolean(genderStr);
            }
            String sortBy = request.getParameter("sortBy");
            String order = request.getParameter("order");
            String[] positionTitleArray = request.getParameterValues("positionTitle");

            int dependantcount = 0;
            Date dob = null;
            String email = request.getParameter("email");
            String positionTitle = request.getParameter("positionTitle");
            boolean hasError = false;

            try {
                dependantcount = Integer.parseInt(dependant_count);
                if (dependantcount < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                hasError = true;
                request.setAttribute("dependantError", "You must input a positive number!");
            }
            if (email == null || email.trim().isEmpty()) {
                hasError = true;
                request.setAttribute("emailError", "Email cannot be empty!");
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                hasError = true;
                request.setAttribute("emailError", "Invalid email format!");
            }

            if (dobStr == null || dobStr.trim().isEmpty()) {
                hasError = true;
                request.setAttribute("dobError", "Date of birth cannot be empty!");
            } else {
                try {
                    dob = Date.valueOf(dobStr);
                } catch (Exception e) {
                    hasError = true;
                    request.setAttribute("dobError", "Invalid date format!");
                }
            }
            if (positionTitle == null || positionTitle.trim().isEmpty()) {
                hasError = true;
                request.setAttribute("posError", "Position cannot be empty!");
            }

            if (hasError) {
                int totalResults = empDAO.countAllRecordOfEmployee();
                int totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
                List<Employee> empList = empDAO.manageEmployeeForHR(searchkey, currentPage, quantityOfPage, gender, positionTitleArray, ageRange, sortBy, order);
                request.setAttribute("editEmp", emp);
                request.setAttribute("page", currentPage);
                request.setAttribute("empList", empList);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("Views/employeelist.jsp").forward(request, response);
                return;
            }

            if (emp != null) {
                emp.setEmail(email);
                emp.setDob(dob);
                emp.setPositionTitle(positionTitle);
                emp.setDependantCount(dependantcount);
                empDAO.updateEmployee(emp);
            }
            List<Employee> empList = empDAO.manageEmployeeForHR(searchkey, currentPage, quantityOfPage, gender, positionTitleArray, ageRange, sortBy, order);
            ses.setAttribute("empList", empList);
            int totalResults = empDAO.countAllRecordOfEmployee();
            int totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
            request.setAttribute("page", currentPage);
            request.setAttribute("totalPages", totalPages);
            empDAO.close();
            response.sendRedirect("employeelistservlet?page=" + currentPage);
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
