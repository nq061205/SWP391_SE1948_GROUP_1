/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import api.EmailUtil;
import dal.CandidateDAO;
import dal.InterviewDAO;
import dal.RolePermissionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Candidate;
import model.Employee;

/**
 *
 * @author hgduy
 */
public class CandidateActionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        CandidateDAO cDAO = new CandidateDAO();
        InterviewDAO iDAO = new InterviewDAO();
        HttpSession ses = request.getSession();
        Employee user = (Employee) ses.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 2)) {
            ses.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (id == null || action == null) {
            response.sendRedirect("candidatelist");
            return;
        }
        try {
            int candidateid = Integer.parseInt(id);
            Candidate thisCandidate = cDAO.getCandidateById(candidateid);
            Candidate candidate = getNextCandidate(candidateid, cDAO.getAllCandidate("pending"));
            if (thisCandidate.getResult() != null) {
                response.sendRedirect("Views/error-404.jsp");
                return;
            }
            if ("approve".equals(action)) {

                String subject = "CV Screening Result – Human Tech Group";
                String content
                        = "Dear " + thisCandidate.getName() + ",\n\n"
                        + "We are pleased to inform you that your CV has successfully passed our screening stage. "
                        + "You have been shortlisted for the next round of the recruitment process.\n\n"
                        + "Our HR team will soon contact you with details regarding the interview schedule. "
                        + "Please keep an eye on your inbox for further updates.\n\n"
                        + "Congratulations, and we look forward to meeting you in the upcoming round.\n\n"
                        + "Best regards,\n"
                        + "Human Tech Recruitment Team\n"
                        + "Human Tech Group";

                EmailUtil.sendEmail(thisCandidate.getEmail(), subject, content);
                cDAO.updateResultCandidate(1, candidateid);

            } else {

                String subject = "CV Screening Result – Human Tech Group";
                String content
                        = "Dear " + thisCandidate.getName() + ",\n\n"
                        + "Thank you for your interest in joining Human Tech Group. "
                        + "After careful consideration, we regret to inform you that your profile does not match "
                        + "the requirements for the current position.\n\n"
                        + "We truly appreciate the time and effort you invested in your application. "
                        + "Please feel free to apply again in future openings that better align with your qualifications.\n\n"
                        + "We wish you all the best in your future career endeavors.\n\n"
                        + "Best regards,\n"
                        + "Human Tech Recruitment Team\n"
                        + "Human Tech Group";

                EmailUtil.sendEmail(thisCandidate.getEmail(), subject, content);
                cDAO.updateResultCandidate(0, candidateid);
            }
            if (candidate == null) {
                response.sendRedirect("candidatelist");
            }
            response.sendRedirect("candidatedetail?id=" + candidate.getCandidateId());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Candidate getNextCandidate(int canId, List<Candidate> candidates) {
        for (int i = 0; i < candidates.size(); i++) {
            if (candidates.get(i).getCandidateId() == canId) {
                if (i + 1 < candidates.size()) {
                    return candidates.get(i + 1);
                } else {
                    return null;
                }
            }
        }
        return null;
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
