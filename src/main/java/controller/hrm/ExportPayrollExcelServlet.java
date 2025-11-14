/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hrm;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.*;
import helper.PayrollService;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import model.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author admin
 */
public class ExportPayrollExcelServlet extends HttpServlet {

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
            out.println("<title>Servlet ExportPayrollExcelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportPayrollExcelServlet at " + request.getContextPath() + "</h1>");
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

        String monthParam = request.getParameter("month");
        String yearParam = request.getParameter("year");
        String department = request.getParameter("department");
        String search = request.getParameter("search");

        LocalDate now = LocalDate.now();
        int month = (monthParam != null && !monthParam.isEmpty())
                ? Integer.parseInt(monthParam)
                : now.getMonthValue();
        int year = (yearParam != null && !yearParam.isEmpty())
                ? Integer.parseInt(yearParam)
                : now.getYear();

        EmployeeDAO employeeDAO = new EmployeeDAO();
        PayrollDAO payrollDAO = new PayrollDAO();
        PayrollService payrollService = new PayrollService();

        List<Employee> employees = employeeDAO.getEmployees(0, Integer.MAX_VALUE, search, department);
        List<Integer> empIds = employees.stream().map(Employee::getEmpId).collect(Collectors.toList());
        List<Payroll> payrollList = payrollDAO.getPayrollByEmpIds(empIds, month, year);

        int standardWorkDays = payrollService.getStandardWorkDays(month, year);

        // Tạo workbook trước
        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("Payroll_" + month + "_" + year);

            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);
            CellStyle totalStyle = createTotalStyle(workbook);

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "Emp Code", "Name", "Position", "Department", "Work Day", "OT Hours",
                "Regular Salary", "OT Earning", "Allowance", "Insurance Base",
                "SI", "HI", "UI", "Taxable Income", "Tax", "Net Salary"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Variables for totals
            double sumRegularSalary = 0, sumOTEarning = 0, sumAllowance = 0;
            double sumInsuranceBase = 0, sumSI = 0, sumHI = 0, sumUI = 0;
            double sumTaxableIncome = 0, sumTax = 0, sumNetSalary = 0;
            double sumWorkDay = 0, sumOTHours = 0;

            // Add data rows
            int rowNum = 1;
            for (Payroll p : payrollList) {
                Row row = sheet.createRow(rowNum++);
                Employee e = p.getEmployee();

                double allowance = p.getInsuranceBase() - p.getRegularSalary();
                double netSalary = (p.getRegularSalary() + p.getOtEarning() + allowance)
                        - (p.getSi() + p.getHi() + p.getUi()) - p.getTax();

                // Accumulate totals
                sumWorkDay += p.getTotalWorkDay();
                sumOTHours += p.getTotalOTHours();
                sumRegularSalary += p.getRegularSalary();
                sumOTEarning += p.getOtEarning();
                sumAllowance += allowance;
                sumInsuranceBase += p.getInsuranceBase();
                sumSI += p.getSi();
                sumHI += p.getHi();
                sumUI += p.getUi();
                sumTaxableIncome += p.getTaxIncome();
                sumTax += p.getTax();
                sumNetSalary += netSalary;

                // Emp Code
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(e.getEmpCode());
                cell0.setCellStyle(dataStyle);

                // Name
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(e.getFullname());
                cell1.setCellStyle(dataStyle);

                // Position
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(e.getPositionTitle());
                cell2.setCellStyle(dataStyle);

                // Department
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(e.getDept().getDepName());
                cell3.setCellStyle(dataStyle);

                // Work Day
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(p.getTotalWorkDay());
                cell4.setCellStyle(dataStyle);

                // OT Hours
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(p.getTotalOTHours());
                cell5.setCellStyle(dataStyle);

                // Regular Salary
                Cell cell6 = row.createCell(6);
                cell6.setCellValue(p.getRegularSalary());
                cell6.setCellStyle(currencyStyle);

                // OT Earning
                Cell cell7 = row.createCell(7);
                cell7.setCellValue(p.getOtEarning());
                cell7.setCellStyle(currencyStyle);

                // Allowance
                Cell cell8 = row.createCell(8);
                cell8.setCellValue(allowance);
                cell8.setCellStyle(currencyStyle);

                // Insurance Base
                Cell cell9 = row.createCell(9);
                cell9.setCellValue(p.getInsuranceBase());
                cell9.setCellStyle(currencyStyle);

                // SI
                Cell cell10 = row.createCell(10);
                cell10.setCellValue(p.getSi());
                cell10.setCellStyle(currencyStyle);

                // HI
                Cell cell11 = row.createCell(11);
                cell11.setCellValue(p.getHi());
                cell11.setCellStyle(currencyStyle);

                // UI
                Cell cell12 = row.createCell(12);
                cell12.setCellValue(p.getUi());
                cell12.setCellStyle(currencyStyle);

                // Taxable Income
                Cell cell13 = row.createCell(13);
                cell13.setCellValue(p.getTaxIncome());
                cell13.setCellStyle(currencyStyle);

                // Tax
                Cell cell14 = row.createCell(14);
                cell14.setCellValue(p.getTax());
                cell14.setCellStyle(currencyStyle);

                // Net Salary
                Cell cell15 = row.createCell(15);
                cell15.setCellValue(netSalary);
                cell15.setCellStyle(currencyStyle);
            }

            // Add total row
            Row totalRow = sheet.createRow(rowNum);

            Cell totalLabelCell = totalRow.createCell(0);
            totalLabelCell.setCellValue("TOTAL");
            totalLabelCell.setCellStyle(totalStyle);

            totalRow.createCell(1).setCellStyle(totalStyle);
            totalRow.createCell(2).setCellStyle(totalStyle);
            totalRow.createCell(3).setCellStyle(totalStyle);

            Cell totalWorkDayCell = totalRow.createCell(4);
            totalWorkDayCell.setCellValue(sumWorkDay);
            totalWorkDayCell.setCellStyle(totalStyle);

            Cell totalOTHoursCell = totalRow.createCell(5);
            totalOTHoursCell.setCellValue(sumOTHours);
            totalOTHoursCell.setCellStyle(totalStyle);

            Cell totalRegularSalaryCell = totalRow.createCell(6);
            totalRegularSalaryCell.setCellValue(sumRegularSalary);
            totalRegularSalaryCell.setCellStyle(totalStyle);

            Cell totalOTEarningCell = totalRow.createCell(7);
            totalOTEarningCell.setCellValue(sumOTEarning);
            totalOTEarningCell.setCellStyle(totalStyle);

            Cell totalAllowanceCell = totalRow.createCell(8);
            totalAllowanceCell.setCellValue(sumAllowance);
            totalAllowanceCell.setCellStyle(totalStyle);

            Cell totalInsuranceBaseCell = totalRow.createCell(9);
            totalInsuranceBaseCell.setCellValue(sumInsuranceBase);
            totalInsuranceBaseCell.setCellStyle(totalStyle);

            Cell totalSICell = totalRow.createCell(10);
            totalSICell.setCellValue(sumSI);
            totalSICell.setCellStyle(totalStyle);

            Cell totalHICell = totalRow.createCell(11);
            totalHICell.setCellValue(sumHI);
            totalHICell.setCellStyle(totalStyle);

            Cell totalUICell = totalRow.createCell(12);
            totalUICell.setCellValue(sumUI);
            totalUICell.setCellStyle(totalStyle);

            Cell totalTaxableIncomeCell = totalRow.createCell(13);
            totalTaxableIncomeCell.setCellValue(sumTaxableIncome);
            totalTaxableIncomeCell.setCellStyle(totalStyle);

            Cell totalTaxCell = totalRow.createCell(14);
            totalTaxCell.setCellValue(sumTax);
            totalTaxCell.setCellStyle(totalStyle);

            Cell totalNetSalaryCell = totalRow.createCell(15);
            totalNetSalaryCell.setCellValue(sumNetSalary);
            totalNetSalaryCell.setCellStyle(totalStyle);

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 500);
            }

            // SAU KHI TẠO XONG WORKBOOK, MỚI SET RESPONSE
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Payroll_" + month + "_" + year + ".xlsx");

            // Write workbook to response
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error generating Excel file: " + e.getMessage());
        } finally {
            workbook.close();
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        return style;
    }

    private CellStyle createTotalStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        return style;
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
