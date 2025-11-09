    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import model.Employee;
import model.LeaveRequest;
import model.OTRequest;
import dal.EmployeeDAO;
import dal.LeaveRequestDAO;
import dal.OTRequestDAO;
import dal.RolePermissionDAO;
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
public class ApplicationDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 7)) {
            session.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        OTRequestDAO OTDAO = new OTRequestDAO();
        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        String leaveId = request.getParameter("leaveId");
        String OTId = request.getParameter("otId");
        if(OTId != null && !OTId.isEmpty()){
            OTRequest ot = OTDAO.getOTRequestByOTId(Integer.parseInt(OTId));
            request.setAttribute("type", "ot");
            request.setAttribute("ot", ot);
            
        }else if(leaveId != null && !leaveId.isEmpty()){
            LeaveRequest leave = leaveDAO.getLeaveRequestByLeaveId(Integer.parseInt(leaveId));
            request.setAttribute("type", "leave");
            request.setAttribute("leave", leave);
        }
        request.getRequestDispatcher("Views/applicationdetail.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }
}
