/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.InterviewDAO;
import dal.RolePermissionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Employee;
import model.Interview;

/**
 *
 * @author hgduy
 */
public class InterviewDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession ses = request.getSession();
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        Employee user = (Employee) ses.getAttribute("user");
        if (user == null || !rperDAO.hasPermission(user.getRole().getRoleId(), 6)) {
            response.sendRedirect("login");
            return;
        }
        InterviewDAO iDAO = new InterviewDAO();
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            Interview interview = iDAO.getInterviewById(id);
            request.setAttribute("interview", interview);
        } catch (Exception e) {
        }
        request.getRequestDispatcher("Views/interviewdetail.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

  
}
