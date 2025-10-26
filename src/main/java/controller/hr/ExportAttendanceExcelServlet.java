package controller.hr;

import dal.DailyAttendanceDAO;
import dal.EmployeeDAO;
import model.Employee;
import model.DailyAttendance;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportAttendanceExcelServlet extends HttpServlet {

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
   
            if (employees == null || employees.isEmpty()) {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html><html><body>");
                out.println("<h2>Error: No employees found</h2>");
                out.println("<p>Month: " + selectedMonth + ", Year: " + selectedYear + "</p>");
                out.println("<p>Department: " + (department == null || department.isEmpty() ? "All" : department) + "</p>");
                out.println("<p>Search: " + (search == null || search.isEmpty() ? "None" : search) + "</p>");
                out.println("<a href='javascript:history.back()'>Go Back</a>");
                out.println("</body></html>");
                return;
            }
            
            List<Integer> empIds = employees.stream()
                    .filter(e -> e != null)
                    .map(e -> e.getEmpId())
                    .collect(Collectors.toList());
            
            dailyDAO = new DailyAttendanceDAO();
            List<DailyAttendance> dailyList = dailyDAO.getAttendanceByEmpIds(empIds, selectedMonth, selectedYear);
            
            Map<Integer, Map<Integer, DailyAttendance>> attendanceByDay = new HashMap<>();
            if (dailyList != null) {
                for (DailyAttendance att : dailyList) {
                    if (att != null && att.getEmployee() != null && att.getDate() != null) {
                        try {
                            int empId = att.getEmployee().getEmpId();
                            LocalDate localDate = att.getDate().toLocalDate();
                            int dayOfMonth = localDate.getDayOfMonth();
                            attendanceByDay.computeIfAbsent(empId, k -> new HashMap<>()).put(dayOfMonth, att);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            exportToExcel(response, employees, attendanceByDay, selectedMonth, selectedYear);
            return;

        } catch (Exception ex) {
            ex.printStackTrace();
            
            try {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html><html><body>");
                out.println("<h2>Error Exporting Excel</h2>");
                out.println("<p><strong>Message:</strong> " + ex.getMessage() + "</p>");
                out.println("<h3>Stack Trace:</h3><pre>");
                ex.printStackTrace(out);
                out.println("</pre>");
                out.println("<a href='javascript:history.back()'>Go Back</a>");
                out.println("</body></html>");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            if (employeeDAO != null) {
                try { employeeDAO.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if (dailyDAO != null) {
                try { dailyDAO.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    private void exportToExcel(HttpServletResponse response,
            List<Employee> employees,
            Map<Integer, Map<Integer, DailyAttendance>> attendanceByDay,
            int month, int year) throws IOException {
        response.reset();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"attendance_" + year + "_" + month + ".xlsx\"");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        Workbook workbook = null;
        OutputStream out = null;

        try {
            workbook = new XSSFWorkbook();
            out = response.getOutputStream();

            Sheet sheet = workbook.createSheet("Attendance " + month + "-" + year);
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();

            // ===== STYLES =====
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dayHeaderStyle = workbook.createCellStyle();
            dayHeaderStyle.cloneStyleFrom(headerStyle);
            dayHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            Font dayFont = workbook.createFont();
            dayFont.setBold(true);
            dayFont.setFontHeightInPoints((short) 10);
            dayHeaderStyle.setFont(dayFont);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            CellStyle weekendStyle = workbook.createCellStyle();
            weekendStyle.cloneStyleFrom(dataStyle);
            weekendStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            weekendStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle presentStyle = workbook.createCellStyle();
            presentStyle.cloneStyleFrom(dataStyle);
            presentStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            presentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle absentStyle = workbook.createCellStyle();
            absentStyle.cloneStyleFrom(dataStyle);
            absentStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());
            absentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle leaveStyle = workbook.createCellStyle();
            leaveStyle.cloneStyleFrom(dataStyle);
            leaveStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            leaveStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // ===== HEADER ROW 1 =====
            Row headerRow1 = sheet.createRow(0);
            headerRow1.setHeightInPoints(25);

            String[] headers1 = {"Code", "Employee", "Department"};
            for (int i = 0; i < headers1.length; i++) {
                Cell cell = headerRow1.createCell(i);
                cell.setCellValue(headers1[i]);
                cell.setCellStyle(headerStyle);
            }

            Cell monthYearCell = headerRow1.createCell(3);
            monthYearCell.setCellValue("Month " + month + "/" + year);
            monthYearCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 3 + daysInMonth - 1));

            Cell totalDaysCell = headerRow1.createCell(3 + daysInMonth);
            totalDaysCell.setCellValue("Total Days");
            totalDaysCell.setCellStyle(headerStyle);

            Cell totalOTCell = headerRow1.createCell(4 + daysInMonth);
            totalOTCell.setCellValue("Total OT");
            totalOTCell.setCellStyle(headerStyle);

            // ===== HEADER ROW 2 =====
            Row headerRow2 = sheet.createRow(1);
            headerRow2.setHeightInPoints(20);

            for (int i = 0; i < 3; i++) {
                Cell cell = headerRow2.createCell(i);
                cell.setCellValue("");
                cell.setCellStyle(headerStyle);
            }

            for (int day = 1; day <= daysInMonth; day++) {
                Cell cell = headerRow2.createCell(2 + day);
                cell.setCellValue(day);
                cell.setCellStyle(dayHeaderStyle);
            }

            for (int i = 0; i < 2; i++) {
                Cell cell = headerRow2.createCell(3 + daysInMonth + i);
                cell.setCellValue("");
                cell.setCellStyle(headerStyle);
            }

            // ===== DATA ROWS =====
            int rowNum = 2;
            for (Employee emp : employees) {
                if (emp == null) {
                    continue;
                }

                Row row = sheet.createRow(rowNum++);
                row.setHeightInPoints(18);

                // Employee Code
                Cell codeCell = row.createCell(0);
                codeCell.setCellValue(emp.getEmpCode() != null ? emp.getEmpCode() : "");
                codeCell.setCellStyle(dataStyle);

                // Employee Name
                Cell nameCell = row.createCell(1);
                nameCell.setCellValue(emp.getFullname() != null ? emp.getFullname() : "");
                nameCell.setCellStyle(dataStyle);

                // Department
                Cell deptCell = row.createCell(2);
                String deptName = "";
                if (emp.getDept() != null && emp.getDept().getDepName() != null) {
                    deptName = emp.getDept().getDepName();
                }
                deptCell.setCellValue(deptName);
                deptCell.setCellStyle(dataStyle);

                // Attendance data
                Map<Integer, DailyAttendance> empAttendance = attendanceByDay.get(emp.getEmpId());
                double totalWorkDays = 0;
                double totalOT = 0;

                for (int day = 1; day <= daysInMonth; day++) {
                    Cell cell = row.createCell(2 + day);

                    LocalDate date = LocalDate.of(year, month, day);
                    boolean isWeekend = date.getDayOfWeek().getValue() >= 6;

                    if (isWeekend) {
                        cell.setCellValue("-");
                        cell.setCellStyle(weekendStyle);
                    } else if (empAttendance != null && empAttendance.containsKey(day)) {
                        DailyAttendance att = empAttendance.get(day);
                        double workDay = att.getWorkDay();

                        String value = (workDay % 1 == 0)
                                ? String.valueOf((int) workDay)
                                : String.format("%.1f", workDay);

                        if (att.getOtHours() > 0) {
                            value += "T";
                        }
                        cell.setCellValue(value);

                        CellStyle style = dataStyle;
                        if ("Present".equals(att.getStatus())) {
                            style = presentStyle;
                        } else if ("Absent".equals(att.getStatus())) {
                            style = absentStyle;
                        } else if ("Leave".equals(att.getStatus())) {
                            style = leaveStyle;
                        }
                        cell.setCellStyle(style);

                        totalWorkDays += workDay;
                        totalOT += att.getOtHours();
                    } else {
                        cell.setCellValue("");
                        cell.setCellStyle(dataStyle);
                    }
                }

                // Totals
                Cell totalDaysCellData = row.createCell(3 + daysInMonth);
                totalDaysCellData.setCellValue(totalWorkDays);
                CellStyle totalStyle = workbook.createCellStyle();
                totalStyle.cloneStyleFrom(dataStyle);
                Font boldFont = workbook.createFont();
                boldFont.setBold(true);
                totalStyle.setFont(boldFont);
                totalDaysCellData.setCellStyle(totalStyle);

                Cell totalOTCellData = row.createCell(4 + daysInMonth);
                totalOTCellData.setCellValue(totalOT);
                totalOTCellData.setCellStyle(totalStyle);
            }

            // ===== COLUMN WIDTHS =====
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 6000);
            sheet.setColumnWidth(2, 4000);

            for (int i = 3; i < 3 + daysInMonth; i++) {
                sheet.setColumnWidth(i, 1200);
            }

            sheet.setColumnWidth(3 + daysInMonth, 3000);
            sheet.setColumnWidth(4 + daysInMonth, 3000);

            sheet.createFreezePane(3, 2);

            // Write and flush
            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error generating Excel file: " + e.getMessage(), e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                    System.out.println("Workbook closed");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Export Attendance to Excel Servlet";
    }
}
