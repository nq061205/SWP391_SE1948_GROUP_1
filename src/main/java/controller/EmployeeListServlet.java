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
        int totalPages = 0;
        int totalResults = 0;
        Integer oldPosCount = (Integer) ses.getAttribute("oldPosCount");
        String oldAgeRange = (String) ses.getAttribute("oldAgeRange");
        Boolean oldGender = (Boolean) ses.getAttribute("oldGender");
        int newPosCount = (positionTitle == null ? 0 : positionTitle.length);
        Boolean newGender = gender;
        String newAgeRange = ageRange;
        String currentPageStr = request.getParameter("page");
        boolean isFilterChanged
                = (oldPosCount == null ? newPosCount != 0 : !oldPosCount.equals(newPosCount))
                || (oldAgeRange == null ? newAgeRange != null : !oldAgeRange.equals(newAgeRange))
                || (oldGender == null ? newGender != null : !oldGender.equals(newGender));
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
        List<String> positionList = empDAO.getAllPosition();
        List<Employee> empList = empDAO.manageEmployeeForHR(searchkey, currentPage, quantityOfPage, gender, positionTitle, ageRange, sortBy, order);
        if (searchkey != null && !searchkey.trim().isEmpty()) {
            totalResults = empDAO.countSearchRecordOfEmployee(searchkey);
            totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        } else if (gender != null || (positionTitle != null) || (ageRange != null)) {
            totalResults = empDAO.countFilterEmployee(gender, positionTitle, ageRange);
            totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        } else if (sortBy != null) {
            totalResults = empDAO.countAllRecordOfEmployee();
            totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        } else {
            totalResults = empDAO.countAllRecordOfEmployee();
            totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        }

        if ("edit".equalsIgnoreCase(type) && empCode != null) {
            Employee editEmp = empDAO.getEmployeeByEmpCode(empCode);
            request.setAttribute("editEmp", editEmp);
        }
        if (empList == null || empList.isEmpty()) {
            request.setAttribute("message", "No results found!");
        }
        request.setAttribute("searchkey", searchkey);
        request.setAttribute("gender", gender);
        request.setAttribute("ageRange", ageRange);
        request.setAttribute("positionTitle", positionTitle);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("order", order);
        request.setAttribute("totalSearchResults", totalResults);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", currentPage);

        ses.setAttribute("oldPosCount", newPosCount);
        ses.setAttribute("oldAgeRange", newAgeRange);
        ses.setAttribute("oldGender", newGender);
        ses.setAttribute("empList", empList);
        ses.setAttribute("positionList", positionList);
        empDAO.close();
        request.getRequestDispatcher("Views/employeelist.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession ses = request.getSession();
        String action = request.getParameter("action");
        EmployeeDAO empDAO = new EmployeeDAO();

        int quantityOfPage = 5;
        int currentPage = 1;
        String currentPageStr = request.getParameter("page");
        if (currentPageStr != null && !currentPageStr.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(currentPageStr);
            } catch (NumberFormatException ignored) {
            }
        }

        String searchkey = request.getParameter("searchkey");
        String ageRange = request.getParameter("ageRange");
        String genderStr = request.getParameter("gender");
        Boolean gender = (genderStr == null || genderStr.trim().isEmpty()) ? null : Boolean.parseBoolean(genderStr);
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        String[] positionTitleArray = request.getParameterValues("positionTitle");

        if ("save".equalsIgnoreCase(action)) {

            String empCode = request.getParameter("empCode");
            Employee emp = empDAO.getEmployeeByEmpCode(empCode);

            String dependant_count = request.getParameter("dependantCount");
            String dobStr = request.getParameter("dob");
            String email = request.getParameter("email");
            String positionTitle = request.getParameter("editPositionTitle");

            boolean hasError = false;
            int dependantcount = 0;
            Date dob = null;

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
                List<Employee> empList = empDAO.manageEmployeeForHR(searchkey, currentPage, quantityOfPage, gender, positionTitleArray, ageRange, sortBy, order);
                int totalResults = empDAO.countAllRecordOfEmployee();
                int totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);

                request.setAttribute("editEmp", emp);
                request.setAttribute("empList", empList);
                request.setAttribute("page", currentPage);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("searchkey", searchkey);
                request.setAttribute("gender", gender);
                request.setAttribute("ageRange", ageRange);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("order", order);
                request.setAttribute("positionTitle", positionTitleArray);

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

            StringBuilder redirectUrl = new StringBuilder("employeelistservlet?page=" + currentPage);
            if (searchkey != null) {
                redirectUrl.append("&searchkey=").append(searchkey);
            }
            if (gender != null) {
                redirectUrl.append("&gender=").append(gender);
            }
            if (ageRange != null) {
                redirectUrl.append("&ageRange=").append(ageRange);
            }
            if (sortBy != null) {
                redirectUrl.append("&sortBy=").append(sortBy);
            }
            if (order != null) {
                redirectUrl.append("&order=").append(order);
            }
            if (positionTitleArray != null) {
                for (String pos : positionTitleArray) {
                    redirectUrl.append("&positionTitle=").append(pos);
                }
            }

            empDAO.close();
            response.sendRedirect(redirectUrl.toString());
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
