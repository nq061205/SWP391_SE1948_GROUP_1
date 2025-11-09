
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.hr;

import dal.AttendanceRawDAO;
import dal.DeptDAO;
import helper.AttendanceService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.AttendanceRaw;
import model.Department;
import model.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author admin
 */
@MultipartConfig(
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
        AttendanceRawDAO rawDAO = new AttendanceRawDAO();
        AttendanceService attendanceService = new AttendanceService();
        String action = request.getParameter("action");
        if ("cancel".equals(action)) {
            request.getSession().removeAttribute("importList");
            request.setAttribute("success", "Import cancelled.");
            loadAttendanceData(request, response);
            return;
        }
        if ("confirm".equals(action)) {
            List<AttendanceRaw> list = (List<AttendanceRaw>) request.getSession().getAttribute("importList");
            if (list != null && !list.isEmpty()) {
                try {
                    rawDAO.insertRawBatch(list);
                    attendanceService.processDailyAttendance(list);
                    request.getSession().removeAttribute("importList");
                    request.setAttribute("success", "Successfully imported " + list.size() + " records!");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Error importing: " + e.getMessage());
                }
            }
            loadAttendanceData(request, response);
            return;
        }

        //Process import file
        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "No file uploaded.");
            loadAttendanceData(request, response);
            return;
        }
        List<AttendanceRaw> list = new ArrayList<>();
        List<String> errorRows = new ArrayList<>();
        int successCount = 0;
        int errorCount = 0;
        try (InputStream inputStream = filePart.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                // --- Column 0: Employee ID ---
                int empId = 0;
                Cell empCell = row.getCell(0);
                if (empCell != null && empCell.getCellType() != CellType.BLANK) {
                    try {
                        if (empCell.getCellType() == CellType.NUMERIC) {
                            empId = (int) empCell.getNumericCellValue();
                        } else if (empCell.getCellType() == CellType.STRING) {
                            String text = empCell.getStringCellValue().trim();
                            if (!text.isEmpty()) {
                                empId = Integer.parseInt(text);
                            }
                        }
                    } catch (NumberFormatException e) {
                        errorRows.add("Row " + (i + 1) + ": Invalid Employee ID");
                        errorCount++;
                        continue;
                    }
                }

                // --- Column 1: Date (yyyy-MM-dd) ---
                java.sql.Date sqlDate = null;
                Cell dateCell = row.getCell(1);

                if (dateCell == null || dateCell.getCellType() == CellType.BLANK) {
                    errorRows.add("Row " + (i + 1) + ": Date is empty");
                    errorCount++;
                    continue;
                }
                try {
                    if (dateCell.getCellType() == CellType.NUMERIC) {
                        java.util.Date d = dateCell.getDateCellValue();
                        if (d != null) {
                            sqlDate = new java.sql.Date(d.getTime());
                        }
                    } else if (dateCell.getCellType() == CellType.STRING) {
                        String text = dateCell.getStringCellValue().trim();
                        if (!text.isEmpty()) {
                            try {
                                java.util.Date d = java.sql.Date.valueOf(text);
                                sqlDate = new java.sql.Date(d.getTime());
                            } catch (IllegalArgumentException e) {
                                errorRows.add("Row " + (i + 1) + ": Invalid date format '" + text + "' (must be yyyy-MM-dd)");
                                errorCount++;
                                continue;
                            }
                        }
                    }
                } catch (Exception e) {
                    errorRows.add("Row " + (i + 1) + ": Error parsing date - " + e.getMessage());
                    errorCount++;
                    continue;
                }
                if (sqlDate == null) {
                    errorRows.add("Row " + (i + 1) + ": Date is empty or invalid");
                    errorCount++;
                    continue;
                }

                // --- Column 2: Check Time (HH:mm:ss) ---
                java.sql.Time sqlTime = null;
                Cell timeCell = row.getCell(2);

                if (timeCell == null || timeCell.getCellType() == CellType.BLANK) {
                    errorRows.add("Row " + (i + 1) + ": Time is empty");
                    errorCount++;
                    continue;
                }
                try {
                    if (timeCell.getCellType() == CellType.STRING) {
                        String text = timeCell.getStringCellValue().trim();
                        if (!text.isEmpty()) {
                            try {
                                sqlTime = java.sql.Time.valueOf(text);
                            } catch (IllegalArgumentException e) {
                                errorRows.add("Row " + (i + 1) + ": Invalid time format '" + text + "' (must be HH:mm:ss)");
                                errorCount++;
                                continue;
                            }
                        }
                    } else if (timeCell.getCellType() == CellType.NUMERIC) {
                        java.util.Date d = timeCell.getDateCellValue();
                        if (d != null) {
                            sqlTime = new java.sql.Time(d.getTime());
                        }
                    }
                } catch (Exception e) {
                    errorRows.add("Row " + (i + 1) + ": Error parsing time - " + e.getMessage());
                    errorCount++;
                    continue;
                }
                if (sqlTime == null) {
                    errorRows.add("Row " + (i + 1) + ": Time is empty or invalid");
                    errorCount++;
                    continue;
                }

                // --- Column 3: Check Type (IN/OUT) ---
                String checkType = "";
                Cell checkTypeCell = row.getCell(3);
                if (checkTypeCell != null && checkTypeCell.getCellType() != CellType.BLANK) {
                    checkType = checkTypeCell.getStringCellValue().trim().toUpperCase();
                }
                if (!checkType.equals("IN") && !checkType.equals("OUT")) {
                    errorRows.add("Row " + (i + 1) + ": Invalid check type '" + checkType + "' (must be IN or OUT)");
                    errorCount++;
                    continue;
                }

                if (empId <= 0) {
                    errorRows.add("Row " + (i + 1) + ": Invalid or missing Employee ID");
                    errorCount++;
                    continue;
                }

                Employee e = new Employee();
                e.setEmpId(empId);
                list.add(new AttendanceRaw(e, sqlDate, sqlTime, checkType));
                successCount++;
            }

            if (list.isEmpty()) {
                request.setAttribute("error", "No valid records found in file. " + errorCount + " rows had errors.");
                request.setAttribute("errorDetails", errorRows);
                loadAttendanceData(request, response);
                return;
            }

            //Check duplicates
            List<AttendanceRaw> duplicates = rawDAO.findDuplicates(list);
            if (!duplicates.isEmpty()) {
                request.setAttribute("error", "Duplicate records detected! Cannot upload.");
                request.setAttribute("duplicateCount", duplicates.size());
                request.setAttribute("totalCount", list.size());
                request.setAttribute("duplicateRecords", duplicates);
                loadAttendanceData(request, response);
                return;
            }

            request.getSession().setAttribute("importList", list);
            List<AttendanceRaw> previewList = list.size() > 10 ? list.subList(0, 10) : list;
            request.setAttribute("preview", previewList);

            if (errorCount > 0) {
                request.setAttribute("errorDetails", errorRows);
                request.setAttribute("errorCount", errorCount);
                request.setAttribute("success", "File uploaded successfully! " + successCount + " valid records found. (" + errorCount + " rows with errors were skipped)");
            } else {
                request.setAttribute("success", "File uploaded successfully! " + successCount + " valid records found. Ready to import.");
            }
            loadAttendanceData(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error reading file: " + ex.getMessage());
            loadAttendanceData(request, response);
        }
    }

    private void loadAttendanceData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        String search = request.getParameter("search");
        String date = request.getParameter("date");  
        String filterType = request.getParameter("filterType");
        String department = request.getParameter("department");

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

        if (date == null || date.trim().isEmpty()) {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            date = yesterday.toString();
        } else {
            try {
                LocalDate.parse(date);
            } catch (Exception e) {
                LocalDate yesterday = LocalDate.now().minusDays(1);
                date = yesterday.toString();
            }
        }

        search = (search != null && !search.trim().isEmpty()) ? search.trim() : null;
        filterType = (filterType != null && !filterType.trim().isEmpty()) ? filterType.trim() : null;
        department = (department != null && !department.trim().isEmpty()) ? department.trim() : null;

        AttendanceRawDAO rawDAO = new AttendanceRawDAO();
        DeptDAO deptDAO = new DeptDAO();

        try {
            List<Department> departments = deptDAO.getAllDepartment();
            long totalRecords = rawDAO.countRawRecordsByDate(search, date, filterType, department);
            int totalPages = (totalRecords > 0) ? (int) Math.ceil((double) totalRecords / pageSize) : 1;

            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }

            int offset = (page - 1) * pageSize;
            List<AttendanceRaw> rawList = rawDAO.getRawRecordsByDate(offset, pageSize, search, date, filterType, department);

            request.setAttribute("rawList", rawList);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("search", search != null ? search : "");
            request.setAttribute("date", date);
            request.setAttribute("filterType", filterType != null ? filterType : "");
            request.setAttribute("department", department != null ? department : "");
            request.setAttribute("departments", departments);

            request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading data: " + e.getMessage());
            try {
                request.getRequestDispatcher("Views/HR/rawAttendance.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
