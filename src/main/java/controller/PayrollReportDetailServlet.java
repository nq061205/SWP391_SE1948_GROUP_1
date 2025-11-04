/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.PayrollDAO;
import jakarta.mail.Session;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Employee;
import model.Payroll;

/**
 *
 * @author Lenovo
 */
public class PayrollReportDetailServlet extends HttpServlet {
   
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee emp = (Employee)session.getAttribute("user");
        if(emp == null){
            request.getRequestDispatcher("Views/login.jsp");
            return;
        }
        PayrollDAO payrollDAO = new PayrollDAO();
        Payroll payroll = payrollDAO.getPayrollDeatailByPayrollId(1);
        request.setAttribute("payroll", payroll);
        request.getRequestDispatcher("Views/payrollreportdetail.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }
}
