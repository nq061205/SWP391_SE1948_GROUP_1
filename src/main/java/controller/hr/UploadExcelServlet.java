/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hr;

import model.Employee;
import model.AttendanceRaw;
import dal.AttendanceRawDAO;
import dal.DailyAttendanceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import model.DailyAttendance;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author admin
 */
@MultipartConfig
public class UploadExcelServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadExcelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadExcelServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("cancel".equals(action)) {
            request.setAttribute("success", "Import cancelled.");
            request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);
            return;
        }
        if ("confirm".equals(action)) {
            List<AttendanceRaw> list = (List<AttendanceRaw>) request.getSession().getAttribute("importList");
            if (list != null) {
                new AttendanceRawDAO().insertRawBatch(list);
                request.getSession().removeAttribute("importList");
                request.setAttribute("success", "Successfully imported " + list.size() + " records!");
            }
            request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);
            return;
        }

        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "No file uploaded.");
            request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);
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
            request.setAttribute("success", "File uploaded successfully! Review before import.");
            request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error reading file: " + ex.getMessage());
            request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
}
