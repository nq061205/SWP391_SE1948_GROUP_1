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
import java.util.ArrayList;
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
        String page = request.getParameter("page");
        String tab = (request.getParameter("tab") != null) ? request.getParameter("tab") : "pending";
        int pageNum = 1;
        if (page != null) {
            pageNum = Integer.parseInt(page);
        }
        HttpSession session = request.getSession();

        List<Candidate> fullList = (List<Candidate>) session.getAttribute("candidateListFull");
        if ("approve".equals(tab)) {
            fullList = cDAO.getAllCandidate("approve");
        } else if ("reject".equals(tab)) {
            fullList = cDAO.getAllCandidate("reject");
        } else {
            fullList = cDAO.getAllCandidate("pending");
        }
        session.setAttribute("tab", tab);
        session.setAttribute("candidateListFull", fullList);
        List<Candidate> candidateList = (List<Candidate>) session.getAttribute("candidateList");
        if (session.getAttribute("candidateList") == null
                || (type == null || (!type.equals("name") && !type.equals("appliedat")))) {

            session.setAttribute("candidateList", cDAO.getCandidateByPage(fullList, pageNum, 5));
            session.setAttribute("pages", pageNum);

            int totalPage = (int) Math.ceil((double) fullList.size() / 5);
            session.setAttribute("total", totalPage);
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
        String tab = request.getParameter("tab");
        if (tab == null) {
            tab = (String) ses.getAttribute("tab"); 
        }
        List<Candidate> searchResult;
        if ("approve".equals(tab)) {
            searchResult = cDAO.getAllCandidateByKeyWord(keyword, "approve");
        } else if ("reject".equals(tab)) {
            searchResult = cDAO.getAllCandidateByKeyWord(keyword, "reject");
        } else {
            searchResult = cDAO.getAllCandidateByKeyWord(keyword, "pending");
        }

        int totalPage = (int) Math.ceil((double) searchResult.size() / 5);
        List<Candidate> pagedList = cDAO.getCandidateByPage(searchResult, 1, 5);

        ses.setAttribute("candidateListFull", searchResult);
        ses.setAttribute("candidateList", pagedList);
        ses.setAttribute("pages", 1);
        ses.setAttribute("total", totalPage);
        ses.setAttribute("keyword", keyword);
        ses.setAttribute("tab", tab);

        request.getRequestDispatcher("Views/candidatelist.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
