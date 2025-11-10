/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PayrollDAO;
import dal.SalaryDAO;
import jakarta.mail.Session;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import model.Employee;
import model.Payroll;
import model.Salary;

/**
 *
 * @author Lenovo
 */
public class PayrollReportDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String monthParam = request.getParameter("month");
        String yearParam = request.getParameter("year");

        int month;
        int year;

        if (monthParam == null || monthParam.isEmpty()) {
            month = LocalDate.now().getMonthValue();
        } else {
            month = Integer.parseInt(monthParam);
        }

        if (yearParam == null || yearParam.isEmpty()) {
            year = LocalDate.now().getYear();
        } else {
            year = Integer.parseInt(yearParam);
        }

        request.setAttribute("month", month);
        request.setAttribute("year", year);

        PayrollDAO payrollDAO = new PayrollDAO();
        SalaryDAO salaryDAO = new SalaryDAO();
        Payroll payroll = payrollDAO.getPayrollDeatailByTime(user.getEmpId(), month, year);
        Salary salary = salaryDAO.getSalaryDetailByTime(user.getEmpId(), month, year);
        request.setAttribute("payroll", payroll);
        request.setAttribute("salary", salary);
        request.getRequestDispatcher("Views/payrollreportdetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
