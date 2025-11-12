/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LeaveRequestDAO;
import dal.OTRequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
public class DeleteApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OTRequestDAO otRequestDAO = new OTRequestDAO();
        LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");
        HttpSession session = request.getSession();
        session.setAttribute("flashMessage", "Deleted Successfully!");
        switch (type) {
            case "OT":
                otRequestDAO.deleteOTRequest(id);
                response.sendRedirect("application?typeapplication=ot");
                break;
            case "LEAVE":
                leaveRequestDAO.deleteLeaveRequest(id);
                response.sendRedirect("application?typeapplication=leave");
                break;
        }
    }
}
