/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CandidateDAO;
import dal.RecruitmentPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import model.Candidate;

/**
 *
 * @author hgduy
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ApplyJobServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("postId", request.getParameter("postId"));
        request.getRequestDispatcher("Views/applyjob.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CandidateDAO cDAO = new CandidateDAO();
        RecruitmentPostDAO rDAO = new RecruitmentPostDAO();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String postId = request.getParameter("postId");

        Part filePart = request.getPart("cvFile");

        try {
            int post = Integer.parseInt(postId);
            String cvRelativePath = "";
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("/images/cv");

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                filePart.write(uploadPath + File.separator + fileName);
                cvRelativePath = "images/cv/" + fileName;
            }
            Candidate candidate = new Candidate();
            candidate.setCv(cvRelativePath);
            candidate.setName(name);
            candidate.setEmail(email);
            candidate.setPhone(phone);
            candidate.setPost(rDAO.getPostById(post));

            if (cDAO.insertCandidate(candidate)) {
                request.setAttribute("successMessage", "Your application has been submitted successfully!");
            } else {
                request.setAttribute("errorMessage", "Apply failed. Please try again.");
            }
            request.getRequestDispatcher("Views/applyjob.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e);
            request.getRequestDispatcher("Views/applyjob.jsp").forward(request, response);
        }

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
