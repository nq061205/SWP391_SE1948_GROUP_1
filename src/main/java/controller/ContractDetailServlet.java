/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ContractDAO;
import dal.EmployeeDAO;
import dal.RolePermissionDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import model.Contract;
import model.Employee;

/**
 *
 * @author Admin
 */
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024,
        maxFileSize = 20 * 1024 * 1024,
        maxRequestSize = 50 * 1024 * 1024
)
public class ContractDetailServlet extends HttpServlet {

    private static final long MAX_AVATAR_SIZE = 2L * 1024 * 1024;
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/jpg", "image/pjpeg", "image/png", "image/gif", "image/webp", "application/pdf"
    );

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
        HttpSession ses = request.getSession();
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        Employee user = (Employee) ses.getAttribute("user");
        if (user == null || !rperDAO.hasPermission(user.getRole().getRoleId(), 4)) {
            response.sendRedirect("login");
            return;
        }
        ContractDAO conDAO = new ContractDAO();
        String empIdStr = (String) request.getAttribute("empId");
        int empId=0;
        try {
            empId=Integer.parseInt(empIdStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String tab = (String) request.getAttribute("tab");
        String option = (String) request.getAttribute("option");
        List<String> typeList = conDAO.getAllType();
        Contract con = conDAO.getContractByEmployeeId(empId);

        if ("edit".equalsIgnoreCase(option)) {
            request.setAttribute("typeList", typeList);
        }
        request.setAttribute("option", option);
        request.setAttribute("tab", tab);
        request.setAttribute("contract", con);
        request.setAttribute("empCode", empId);
        request.getRequestDispatcher("Views/employeedetails.jsp").forward(request, response);
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
        ContractDAO conDAO = new ContractDAO();
        EmployeeDAO empDAO = new EmployeeDAO();

        String type = request.getParameter("type");
        String startDateStr = request.getParameter("start");
        String endDateStr = request.getParameter("end");
        String tab = request.getParameter("tab");
        String option = request.getParameter("option");
        String empIdStr = request.getParameter("empId");
        int empId=0;
         try {
            empId=Integer.parseInt(empIdStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Part filePart = null;
        try {
            filePart = request.getPart("contractFile");
        } catch (Exception e) {
        }

        Date startDate = null, endDate = null;
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = Date.valueOf(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = Date.valueOf(endDateStr);
        }

        Contract con = conDAO.getContractByEmployeeId(empId);
        Employee emp = empDAO.getEmployeeByEmpId(empId);
        String fileUrl = con != null ? con.getContractImg() : "";

        boolean hasError = false;

        String errImg = validateAvatarPart(filePart);
        String errDate = validateDate(startDate, endDate);

        if (errImg != null) {
            request.setAttribute("avatarErr", errImg);
            hasError = true;
        }
        if (errDate != null) {
            request.setAttribute("DateErr", errDate);
            hasError = true;
        }
        if (hasError) {
            request.setAttribute("fileUrl", fileUrl);
            request.setAttribute("type", type);
            request.setAttribute("tab", tab);
            request.setAttribute("contract", con);
            request.setAttribute("option", "edit");
            request.setAttribute("empId", empId);
            request.setAttribute("typeList", conDAO.getAllType());
            request.getRequestDispatcher("Views/employeedetails.jsp").forward(request, response);
            return;
        }
        if ("save".equalsIgnoreCase(option)) {
            con.setType(type);
            con.setStartDate(startDate);
            con.setEndDate(endDate);
            if (filePart != null && filePart.getSize() > 0) {
                fileUrl = saveAvatar(filePart, getServletContext(), emp.getEmpCode());
                con.setContractImg(fileUrl);
            }

            conDAO.updateContract(con);
            request.setAttribute("fileUrl", fileUrl);
            request.setAttribute("type", type);
            request.setAttribute("tab", tab);
            request.setAttribute("contract", con);
            request.setAttribute("empId", empId);
            request.getRequestDispatcher("Views/employeedetails.jsp").forward(request, response);
        }
    }

    private String validateDate(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            return "Start date cannot after end date!";
        }
        return null;
    }

    private String validateAvatarPart(Part filePart) throws IOException {
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        if (filePart.getSize() > MAX_AVATAR_SIZE) {
            return "Avatar must be ≤ 2MB";
        }
        String ct = lower(filePart.getContentType());
        if (!ALLOWED_CONTENT_TYPES.contains(ct)) {
            return "Only JPG/PNG/GIF/WEBP/PDF are allowed";
        }
        return null;
    }

    private String lower(String s) {
        return s == null ? "" : s.toLowerCase();
    }

    private String saveAvatar(Part filePart, ServletContext ctx, String empCode) throws IOException {
        String ct = lower(filePart.getContentType());
        String ext;
        if (ct.contains("png")) {
            ext = ".png";
        } else if (ct.contains("gif")) {
            ext = ".gif";
        } else if (ct.contains("webp")) {
            ext = ".webp";
        } else if (ct.contains("pdf")) {
            ext = ".pdf";
        } else {
            ext = ".jpg";
        }

        String safeName = "ht_" + empCode + ext;

        String uploadPath = ctx.getRealPath("/images/contract");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File[] oldFiles = uploadDir.listFiles((dir, name)
                -> name.toLowerCase().startsWith(("ht_" + empCode).toLowerCase() + "."));
        if (oldFiles != null) {
            for (File f : oldFiles) {
                try {
                    if (f.isFile()) {
                        f.delete();
                    }
                } catch (Exception ignored) {
                }
            }
        }
        filePart.write(uploadPath + File.separator + safeName);
        return "images/contract/" + safeName;
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
