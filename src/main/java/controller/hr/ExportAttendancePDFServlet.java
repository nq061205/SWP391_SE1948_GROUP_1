/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hr;

import dal.DailyAttendanceDAO;
import dal.EmployeeDAO;
import java.io.IOException;
import java.io.OutputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import model.Employee;
import model.DailyAttendance;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.colors.ColorConstants;
import java.io.PrintWriter;


/**
 *
 * @author admin
 */
public class ExportAttendancePDFServlet extends HttpServlet {

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
            out.println("<title>Servlet ExportAttendancePDFServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportAttendancePDFServlet at " + request.getContextPath() + "</h1>");
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
        EmployeeDAO employeeDAO = null;
        DailyAttendanceDAO dailyDAO = null;

        try {
            String monthParam = request.getParameter("month");
            String yearParam = request.getParameter("year");
            String department = request.getParameter("department");
            String search = request.getParameter("search");

            LocalDate now = LocalDate.now();
            int selectedMonth = (monthParam != null && !monthParam.isEmpty())
                    ? Integer.parseInt(monthParam) : now.getMonthValue();
            int selectedYear = (yearParam != null && !yearParam.isEmpty())
                    ? Integer.parseInt(yearParam) : now.getYear();

            employeeDAO = new EmployeeDAO();
            List<Employee> employees = employeeDAO.getEmployees(0, Integer.MAX_VALUE, search, department);
            List<Integer> empIds = employees.stream().map(e -> e.getEmpId()).collect(Collectors.toList());

            dailyDAO = new DailyAttendanceDAO();
            List<DailyAttendance> dailyList = dailyDAO.getAttendanceByEmpIds(empIds, selectedMonth, selectedYear);

            Map<Integer, Map<Integer, DailyAttendance>> attendanceByDay = new HashMap<>();
            for (DailyAttendance att : dailyList) {
                int empId = att.getEmployee().getEmpId();
                LocalDate localDate = att.getDate().toLocalDate();
                int dayOfMonth = localDate.getDayOfMonth();
                attendanceByDay.computeIfAbsent(empId, k -> new HashMap<>()).put(dayOfMonth, att);
            }

            exportToPDF(response, employees, attendanceByDay, selectedMonth, selectedYear);

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error exporting PDF: " + ex.getMessage());
        } finally {
            if (employeeDAO != null) {
                try {
                    employeeDAO.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (dailyDAO != null) {
                try {
                    dailyDAO.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void exportToPDF(HttpServletResponse response,
            List<Employee> employees,
            Map<Integer, Map<Integer, DailyAttendance>> attendanceByDay,
            int month, int year) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=attendance_" + year + "_" + month + ".pdf");

        try (OutputStream out = response.getOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4.rotate());

            Document document = new Document(pdfDoc);
            document.setMargins(20, 20, 20, 20);

            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();

            // ===== TITLE =====
            Paragraph title = new Paragraph("Daily Attendance Report - " + month + "/" + year)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(16)
                    .setBold()
                    .setMarginBottom(10);
            document.add(title);

            // ===== CREATE TABLE =====
            int numColumns = 3 + daysInMonth + 2; 
            Table table = new Table(numColumns);
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFontSize(8);

            // Colors
            Color headerBg = new DeviceRgb(0, 51, 102);
            Color headerText = ColorConstants.WHITE;
            Color dayHeaderBg = new DeviceRgb(200, 200, 200);
            Color weekendBg = new DeviceRgb(220, 220, 220);
            Color presentBg = new DeviceRgb(144, 238, 144); // Light green
            Color absentBg = new DeviceRgb(255, 182, 193); // Light pink
            Color leaveBg = new DeviceRgb(173, 216, 230); // Light blue

            // ===== HEADER ROW 1 (Main headers) =====
            Cell codeHeader = new Cell(2, 1).add(new Paragraph("Code"))
                    .setBackgroundColor(headerBg)
                    .setFontColor(headerText)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setFontSize(9);
            table.addHeaderCell(codeHeader);

            Cell nameHeader = new Cell(2, 1).add(new Paragraph("Employee"))
                    .setBackgroundColor(headerBg)
                    .setFontColor(headerText)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setFontSize(9);
            table.addHeaderCell(nameHeader);

            Cell deptHeader = new Cell(2, 1).add(new Paragraph("Dept"))
                    .setBackgroundColor(headerBg)
                    .setFontColor(headerText)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setFontSize(9);
            table.addHeaderCell(deptHeader);

            Cell monthHeader = new Cell(1, daysInMonth)
                    .add(new Paragraph("Month " + month + "/" + year))
                    .setBackgroundColor(headerBg)
                    .setFontColor(headerText)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(9);
            table.addHeaderCell(monthHeader);

            Cell totalDaysHeader = new Cell(2, 1).add(new Paragraph("Days"))
                    .setBackgroundColor(headerBg)
                    .setFontColor(headerText)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setFontSize(9);
            table.addHeaderCell(totalDaysHeader);

            Cell totalOTHeader = new Cell(2, 1).add(new Paragraph("OT"))
                    .setBackgroundColor(headerBg)
                    .setFontColor(headerText)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setFontSize(9);
            table.addHeaderCell(totalOTHeader);

            // ===== HEADER ROW 2 (Day numbers) =====
            for (int day = 1; day <= daysInMonth; day++) {
                Cell dayCell = new Cell().add(new Paragraph(String.valueOf(day)))
                        .setBackgroundColor(dayHeaderBg)
                        .setFontSize(7)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(2);
                table.addHeaderCell(dayCell);
            }

            // ===== DATA ROWS =====
            for (Employee emp : employees) {
                // Employee Code
                Cell codeCell = new Cell().add(new Paragraph(emp.getEmpCode()))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(7)
                        .setPadding(3);
                table.addCell(codeCell);

                // Employee Name
                Cell nameCell = new Cell().add(new Paragraph(emp.getFullname()))
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontSize(7)
                        .setPadding(3);
                table.addCell(nameCell);

                // Department
                Cell deptCell = new Cell().add(new Paragraph(
                        emp.getDept() != null ? emp.getDept().getDepName() : ""))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(7)
                        .setPadding(3);
                table.addCell(deptCell);

                // Attendance data for each day
                Map<Integer, DailyAttendance> empAttendance = attendanceByDay.get(emp.getEmpId());
                double totalWorkDays = 0;
                double totalOT = 0;

                for (int day = 1; day <= daysInMonth; day++) {
                    LocalDate date = LocalDate.of(year, month, day);
                    boolean isWeekend = date.getDayOfWeek().getValue() >= 6; 

                    Cell dayCell = new Cell();
                    dayCell.setTextAlignment(TextAlignment.CENTER);
                    dayCell.setFontSize(7);
                    dayCell.setPadding(2);

                    if (isWeekend) {
                        // Weekend cell
                        dayCell.add(new Paragraph("-"));
                        dayCell.setBackgroundColor(weekendBg);
                    } else if (empAttendance != null && empAttendance.containsKey(day)) {
                        // Has attendance data
                        DailyAttendance att = empAttendance.get(day);
                        double workDay = att.getWorkDay();

                        String value = (workDay % 1 == 0)
                                ? String.valueOf((int) workDay)
                                : String.format("%.1f", workDay);

                        if (att.getOtHours() > 0) {
                            value += "T";
                        }

                        dayCell.add(new Paragraph(value));

                        // Background color by status
                        String status = att.getStatus();
                        if ("Present".equals(status)) {
                            dayCell.setBackgroundColor(presentBg);
                        } else if ("Absent".equals(status)) {
                            dayCell.setBackgroundColor(absentBg);
                        } else if ("Leave".equals(status)) {
                            dayCell.setBackgroundColor(leaveBg);
                        }

                        totalWorkDays += workDay;
                        totalOT += att.getOtHours();
                    } else {
                        dayCell.add(new Paragraph(""));
                    }

                    table.addCell(dayCell);
                }

                // Total Work Days
                Cell totalDaysCell = new Cell()
                        .add(new Paragraph(String.format("%.1f", totalWorkDays)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(7)
                        .setBold()
                        .setPadding(3);
                table.addCell(totalDaysCell);

                // Total OT Hours
                Cell totalOTCell = new Cell()
                        .add(new Paragraph(String.format("%.1f", totalOT)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(7)
                        .setBold()
                        .setPadding(3);
                table.addCell(totalOTCell);
            }

            document.add(table);

            // ===== FOOTER (optional) =====
            Paragraph footer = new Paragraph("Generated on: " + LocalDate.now().toString())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(8)
                    .setMarginTop(10);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error generating PDF file", e);
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
