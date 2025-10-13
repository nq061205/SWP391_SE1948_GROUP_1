/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hr;

import dal.AttendanceRawDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.AttendanceRaw;

/**
 *
 * @author admin
 */
public class RawAttendanceServlet extends HttpServlet {

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
            out.println("<title>Servlet RawAttendanceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RawAttendanceServlet at " + request.getContextPath() + "</h1>");
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
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        String search = request.getParameter("search");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String filterType = request.getParameter("filterType");

        int page = 1;
        int pageSize = 10;
        boolean pageSizeChanged = false;

        try {
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
            if (pageSizeParam != null && !pageSizeParam.trim().isEmpty()) {
                int newPageSize = Integer.parseInt(pageSizeParam);
                if (newPageSize != pageSize) {
                    pageSizeChanged = true;
                    pageSize = newPageSize;
                    page = 1; 
                } else {
                    pageSize = newPageSize;
                }
            }
        } catch (NumberFormatException e) {
            page = 1;
            pageSize = 10;
        }

        if (page < 1) {
            page = 1;
        }
        if (pageSize < 1 || pageSize > 100) {
            pageSize = 10;
        }

        search = (search != null && !search.trim().isEmpty()) ? search.trim() : null;
        fromDate = (fromDate != null && !fromDate.trim().isEmpty()) ? fromDate.trim() : null;
        toDate = (toDate != null && !toDate.trim().isEmpty()) ? toDate.trim() : null;
        filterType = (filterType != null && !filterType.trim().isEmpty()) ? filterType.trim() : null;

        AttendanceRawDAO rawDAO = new AttendanceRawDAO();
        try {
            long totalRecords = rawDAO.countRawRecords(search, fromDate, toDate, filterType);
            int totalPages = (totalRecords > 0) ? (int) Math.ceil((double) totalRecords / pageSize) : 1;

            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }

            int offset = (page - 1) * pageSize;

            List<AttendanceRaw> rawList = rawDAO.getRawRecords(offset, pageSize, search, fromDate, toDate, filterType);

            request.setAttribute("rawList", rawList);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("search", search != null ? search : "");
            request.setAttribute("fromDate", fromDate != null ? fromDate : "");
            request.setAttribute("toDate", toDate != null ? toDate : "");
            request.setAttribute("filterType", filterType != null ? filterType : "");

            request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        doGet(request, response);
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
