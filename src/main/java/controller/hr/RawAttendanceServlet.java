
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hr;

import dal.AttendanceRawDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import model.AttendanceRaw;
import model.Employee;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author admin
 */
@MultipartConfig( // THÊM NÀY
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
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
        loadAttendanceData(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("cancel".equals(action)) {
            request.getSession().removeAttribute("importList");
            request.setAttribute("success", "Import cancelled.");
            loadAttendanceData(request, response);  // GỌI METHOD CHUNG
            return;
        }

        if ("confirm".equals(action)) {
            List<AttendanceRaw> list = (List<AttendanceRaw>) request.getSession().getAttribute("importList");
            if (list != null && !list.isEmpty()) {
                AttendanceRawDAO dao = null;
                try {
                    dao = new AttendanceRawDAO();
                    dao.insertRawBatch(list);
                    request.getSession().removeAttribute("importList");
                    request.setAttribute("success", "Successfully imported " + list.size() + " records!");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Error importing: " + e.getMessage());
                } finally {
                    if (dao != null) {
                        dao.close();
                    }
                }
            }
            loadAttendanceData(request, response);  // GỌI METHOD CHUNG
            return;
        }

        // Handle file upload
        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "No file uploaded.");
            loadAttendanceData(request, response);  // GỌI METHOD CHUNG
            return;
        }

        List<AttendanceRaw> list = new ArrayList<>();

        try (InputStream inputStream = filePart.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                int empId = (int) row.getCell(0).getNumericCellValue();
                java.util.Date utilDate = row.getCell(1).getDateCellValue();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                java.sql.Time sqlTime;
                if (row.getCell(2).getCellType() == CellType.STRING) {
                    sqlTime = java.sql.Time.valueOf(row.getCell(2).getStringCellValue());
                } else {
                    java.util.Date d = row.getCell(2).getDateCellValue();
                    sqlTime = new java.sql.Time(d.getTime());
                }

                String checkType = row.getCell(3).getStringCellValue();

                Employee e = new Employee();
                e.setEmpId(empId);
                list.add(new AttendanceRaw(e, sqlDate, sqlTime, checkType));
            }

            request.getSession().setAttribute("importList", list);
            List<AttendanceRaw> previewList = list.size() > 10 ? list.subList(0, 10) : list;
            request.setAttribute("preview", previewList);
            request.setAttribute("success", "File uploaded successfully! Review " + list.size() + " records before import.");

            loadAttendanceData(request, response);  // GỌI METHOD CHUNG

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error reading file: " + ex.getMessage());
            loadAttendanceData(request, response);  // GỌI METHOD CHUNG
        }
    }

    /**
     * Method chung để load attendance data và forward đến JSP
     */
    private void loadAttendanceData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        String search = request.getParameter("search");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String filterType = request.getParameter("filterType");

        int page = 1;
        int pageSize = 10;

        try {
            if (pageSizeParam != null && !pageSizeParam.trim().isEmpty()) {
                pageSize = Integer.parseInt(pageSizeParam);
            }
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
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

        AttendanceRawDAO rawDAO = null;
        try {
            rawDAO = new AttendanceRawDAO();

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
            request.setAttribute("error", "Error loading data: " + e.getMessage());
            request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);
        } finally {
            if (rawDAO != null) {
                rawDAO.close();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
