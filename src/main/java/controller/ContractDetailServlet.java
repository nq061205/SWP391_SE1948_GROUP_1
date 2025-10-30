/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ContractDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import model.Contract;

/**
 *
 * @author Admin
 */
public class ContractDetailServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ContractDetailServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ContractDetailServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession ses = request.getSession();
        ContractDAO conDAO = new ContractDAO();
        String empCode = (String) request.getAttribute("empCode");
        String tab = (String) request.getAttribute("tab");
        String option = (String) request.getAttribute("option");
        List<String> typeList = conDAO.getAllType();
        Contract con = conDAO.getContractByEmployeeCode(empCode);
        
        if ("edit".equalsIgnoreCase(option)) {
            request.setAttribute("typeList", typeList);
        }
        request.setAttribute("option", option);
        request.setAttribute("tab", tab);
        request.setAttribute("contract", con);
        request.setAttribute("empCode", empCode);
        request.getRequestDispatcher("Views/employeedetails.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ContractDAO conDAO = new ContractDAO();
        String type= request.getParameter("type");
        String startDateStr = request.getParameter("start");
        String endDateStr = request.getParameter("end");
        Date startDate = null;
        String tab = request.getParameter("tab");
        String option = request.getParameter("option");
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = Date.valueOf(startDateStr);
        }
        Date endDate = null;
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = Date.valueOf(endDateStr);
        }
        String empCode = request.getParameter("empCode");
        Contract con = conDAO.getContractByEmployeeCode(empCode);
        if ("save".equalsIgnoreCase(option)) {
             con.setType(type);
             con.setStartDate(startDate);
             con.setEndDate(endDate);
             conDAO.updateContract(con);
        }
        request.setAttribute("type", type);
        request.setAttribute("tab", tab);
        request.setAttribute("contract", con);
        request.setAttribute("empCode", empCode);
        request.getRequestDispatcher("Views/employeedetails.jsp").forward(request, response);
        
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
