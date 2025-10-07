package controller;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package Controllers;
//
//import DAL.*;
//import Models.*;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.sql.Date;
//import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Lenovo
// */
//public class ComposeApplicationServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String type = request.getParameter("type");
//        request.setAttribute("type", type);
//        request.getRequestDispatcher("Views/composeapplication.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        EmployeeDAO empDAO = new EmployeeDAO();
//        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
//        OTRequestDAO OTDAO = new OTRequestDAO();
//        String type = request.getParameter("type");
//        String email = request.getParameter("email");
//        try {
//            if (empDAO.getEmployeeByEmail(email) == null) {
//                request.setAttribute("message", "Email is not available");
//            } else {
//                if ("LEAVE".equalsIgnoreCase(type)) {
//                    Employee emp = empDAO.getEmployeeByEmail(email);
//                    leaveDAO.composeLeaveRequest(
//                            emp.getEmpId(),
//                            request.getParameter("type_leave"),
//                            request.getParameter("content"),
//                            Date.valueOf(request.getParameter("startdate")),
//                            Date.valueOf(request.getParameter("enddate")),
//                            empDAO.getEmployeeByEmail(email).getEmpId()
//                    );
//                } else if ("OT".equalsIgnoreCase(type)) {
//
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ComposeApplicationServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if ("leave".equalsIgnoreCase(type)) {
//            String type_leave = request.getParameter("type_application");
//            Date startDate = Date.valueOf(request.getParameter("startdate"));
//            Date endDate = Date.valueOf(request.getParameter("enddate"));
//            String content = request.getParameter("content");
//            request.setAttribute("type_leave", type_leave);
//            request.setAttribute("startdate", startDate);
//            request.setAttribute("endDate", endDate);
//            request.setAttribute("content", content);
//            request.getRequestDispatcher("compose?type=LEAVE").forward(request, response);
//        } else if ("ot".equalsIgnoreCase(type)) {
//            Date date = Date.valueOf(request.getParameter("date"));
//            double othour = Integer.parseInt(request.getParameter("othour"));
//            request.setAttribute("date", date);
//            request.setAttribute("othour", othour);
//            request.getRequestDispatcher("compose?type=OT").forward(request, response);
//        }
//
//    }
//}
