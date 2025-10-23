package controller;

import api.EmailUtil;
import dal.CandidateDAO;
import dal.RecruitmentPostDAO;
import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Candidate;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
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

        String name = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        if(EmailUtil.isValidEmail(email) == false){
            request.setAttribute("errorMessage", "Email does not exist");
            return;
        }
        String phone = request.getParameter("phone").trim();
        String postId = request.getParameter("postId");
        request.setAttribute("postId", postId);
        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        Part filePart = request.getPart("cvFile");
        if (filePart != null && filePart.getSubmittedFileName() != null) {
            request.setAttribute("fileName", Paths.get(filePart.getSubmittedFileName()).getFileName().toString());
        }
        long fileSize = filePart.getSize();
        long maxFileSize = 5 * 1024 * 1024;

        if (fileSize > maxFileSize) {
            request.setAttribute("errorMessage", "CV file is too large! Maximum size allowed is 5 MB.");
            request.getRequestDispatcher("Views/applyjob.jsp").forward(request, response);
            return;
        }

        try {

            if (!name.matches("^[\\p{L} ]+$")) {
                request.setAttribute("errorMessage", "Invalid name! Name cannot contain numbers or special characters.");
                request.getRequestDispatcher("Views/applyjob.jsp").forward(request, response);
                return;
            }

            if (!phone.matches("^0\\d{9}$")) {
                request.setAttribute("errorMessage", "Invalid phone number! It must start with 0 and contain 10 digits.");
                request.getRequestDispatcher("Views/applyjob.jsp").forward(request, response);
                return;
            }

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
                request.setAttribute("errorMessage", "Apply failed, Email has been approve in system. Please try again.");
            }

            request.getRequestDispatcher("Views/applyjob.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("Views/applyjob.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "ApplyJobServlet handles candidate job application with validation.";
    }
}