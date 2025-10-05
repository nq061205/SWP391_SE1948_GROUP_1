///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package Controllers.HR;
//
//import jakarta.mail.Part;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.InputStream;
//
///**
// *
// * @author admin
// */
//public class UploadExcelServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet UploadExcelServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet UploadExcelServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        Part filePart = request.getPart("file");
//        InputStream inputStream = filePart.getInputStream();
//
//        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
//            Sheet sheet = workbook.getSheetAt(0);
//
//            Connection conn = DBUtils.getConnection(); // Tự viết hàm kết nối DB
//            String sql = "INSERT INTO attendance (employee_id, date, time_in, time_out) VALUES (?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ dòng tiêu đề
//                Row row = sheet.getRow(i);
//                if (row == null) {
//                    continue;
//                }
//
//                String empId = row.getCell(0).getStringCellValue();
//                Date date = row.getCell(1).getDateCellValue();
//                String timeIn = row.getCell(2).getStringCellValue();
//                String timeOut = row.getCell(3).getStringCellValue();
//
//                ps.setString(1, empId);
//                ps.setDate(2, new java.sql.Date(date.getTime()));
//                ps.setString(3, timeIn);
//                ps.setString(4, timeOut);
//                ps.addBatch();
//            }
//
//            ps.executeBatch();
//            conn.close();
//            response.getWriter().println("Import thành công!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("Lỗi khi import file: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
