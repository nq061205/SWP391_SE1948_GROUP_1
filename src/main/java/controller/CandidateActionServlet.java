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
        if (user == null || !rperDAO.hasPermission(user.getRole().getRoleId(), 2)) {
            response.sendRedirect("login");
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
            if ("approve".equals(action)) {
                EmailUtil.sendEmail(thisCandidate.getEmail(), "CV result notification", "Congratuation " + thisCandidate.getName() + " has pass our cv stage, please go to interview tommorow");
                cDAO.updateResultCandidate(1, candidateid);
            } else {
                EmailUtil.sendEmail(thisCandidate.getEmail(), "CV result notification", "You have not met the requirements of our Human Tech group, sorry " + thisCandidate.getName());
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
