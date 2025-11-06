/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CandidateDAO;
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
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        Employee user = (Employee) ses.getAttribute("user");
        if (user == null || !rperDAO.hasPermission(user.getRole().getRoleId(), 4)) {
            response.sendRedirect("login");
            return;
        }
        EmployeeDAO empDAO = new EmployeeDAO();
        String searchkey = request.getParameter("searchkey");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        String type = request.getParameter("type");
        String empCode = request.getParameter("empCode");

        String ageRange = request.getParameter("ageRange");
        String genderStr = request.getParameter("gender");
        if ("All".equalsIgnoreCase(ageRange)) {
            ageRange = null;
        }
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
        String oldSearchKey = (String) ses.getAttribute("oldSearchKey");
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

        List<String> positionList = empDAO.getAllPosition();

        if (searchkey != null && !searchkey.trim().isEmpty()) {
            totalResults = empDAO.countSearchAndFilterEmployee(searchkey, gender, positionTitle, ageRange);
        } else if (gender != null || (positionTitle != null) || (ageRange != null)) {
            totalResults = empDAO.countSearchAndFilterEmployee(searchkey, gender, positionTitle, ageRange);
        } else if (sortBy != null) {
            totalResults = empDAO.countAllRecordOfEmployee();
        } else {
            totalResults = empDAO.countAllRecordOfEmployee();
        }
        totalPages = (int) Math.ceil((double) totalResults / quantityOfPage);
        List<Employee> empList = empDAO.manageEmployeeForHR(searchkey, currentPage, quantityOfPage, gender, positionTitle, ageRange, sortBy, order);

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
        ses.setAttribute("oldSearchKey", searchkey);
        ses.setAttribute("oldAgeRange", newAgeRange);
        ses.setAttribute("oldGender", newGender);
        ses.setAttribute("empList", empList);
        ses.setAttribute("positionList", positionList);
        request.getRequestDispatcher("Views/employeelist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Views/employeelist.jsp").forward(request, response);
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
