/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hrm;

import dal.ChartDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import model.Payroll;

/**
 *
 * @author BUI TUAN DAT
 */
public class HRMDashboardServlet extends HttpServlet {

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
            out.println("<title>Servlet HRMDashboardServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HRMDashboardServlet at " + request.getContextPath() + "</h1>");
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
    String yearStr = request.getParameter("year");
    int year = yearStr != null ? Integer.parseInt(yearStr) : 2025;
    String mAbsence = request.getParameter("monthAbsence");
    String mAttendance = request.getParameter("monthAttendance");

    int monthAbsence = mAbsence != null ? Integer.parseInt(mAbsence) : 11;
    int monthAttendance = mAttendance != null ? Integer.parseInt(mAttendance) : 11;

    ChartDAO dao = new ChartDAO();
    Map<String, Double> salaryByMonth = dao.getSalaryPaidByMonth(year);

    List<Payroll> topAbsences = dao.getTopAbsenceEmployees(monthAbsence, year, 5);
    List<Payroll> topAttendance = dao.getTopAttendanceEmployees(monthAttendance, year, 5);

    request.setAttribute("salaryData", salaryByMonth);
    request.setAttribute("selectedYear", year);
    request.setAttribute("selectedMonthAbsence", monthAbsence);
    request.setAttribute("selectedMonthAttendance", monthAttendance);
    request.setAttribute("topAbsences", topAbsences);
    request.setAttribute("topAttendance", topAttendance);
    request.getRequestDispatcher("/Views/HRM/hrmDashboard.jsp").forward(request, response);
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
        processRequest(request, response);
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
