/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CandidateDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import model.Candidate;

/**
 *
 * @author hgduy
 */
public class CandidateListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CandidateDAO cDAO = new CandidateDAO();
        String type = request.getParameter("type");
        HttpSession session = request.getSession();
        List<Candidate> candidateList = (List<Candidate>) session.getAttribute("candidateList");
        if (session.getAttribute("candidateList") == null || (type == null || (!type.equals("name") && !type.equals("appliedat")))) {
            session.setAttribute("candidateList", cDAO.getAllCandidate());
            candidateList = (List<Candidate>) session.getAttribute("candidateList");
            session.setAttribute("direct", 1);
        } else {
            if (type != null) {
                Object directObj = session.getAttribute("direct");
                int direct = (directObj == null) ? 1 : (int) directObj;
                if (type.equals("name")) {
                    if (direct == 1) {
                        session.setAttribute("candidateList", sortByName(candidateList, direct));
                        session.setAttribute("direct", -1);
                    } else {
                        session.setAttribute("candidateList", sortByName(candidateList, direct));
                        session.setAttribute("direct", 1);
                    }

                } else {
                    if (direct == 1) {
                        session.setAttribute("candidateList", sortByApplied(candidateList, direct));
                        session.setAttribute("direct", -1);
                    } else {
                        session.setAttribute("candidateList", sortByApplied(candidateList, direct));
                        session.setAttribute("direct", 1);
                    }
                }
            }
        }

        request.getRequestDispatcher("Views/candidatelist.jsp").forward(request, response);
    }

    public List<Candidate> sortByName(List<Candidate> candidateList, int direct) {
        if (direct == 1) {
            Collections.sort(candidateList, Comparator.comparing(Candidate::getName));
        } else {
            Collections.sort(candidateList, Comparator.comparing(Candidate::getName).reversed());
        }
        return candidateList;
    }

    public List<Candidate> sortByApplied(List<Candidate> candidateList, int direct) {
        if (direct == 1) {
            Collections.sort(candidateList, Comparator.comparing(Candidate::getAppliedAt));
        } else {
            Collections.sort(candidateList, Comparator.comparing(Candidate::getAppliedAt).reversed());
        }
        return candidateList;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        CandidateDAO cDAO = new CandidateDAO();
        HttpSession ses = request.getSession();
        ses.setAttribute("candidateList", cDAO.getAllCandidateByKeyWord(keyword));
        request.getRequestDispatcher("Views/candidatelist.jsp").forward(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
