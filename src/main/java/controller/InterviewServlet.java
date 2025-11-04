/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CandidateDAO;
import dal.EmployeeDAO;
import dal.InterviewDAO;
import dal.RecruitmentPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Candidate;
import model.Employee;
import model.Interview;
import model.RecruitmentPost;

/**
 *
 * @author hgduy
 */
public class InterviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        InterviewDAO iDAO = new InterviewDAO();
        List<RecruitmentPost> postList = rpDAO.getAllPosts();
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        Employee user = (Employee) session.getAttribute("user");
        request.setAttribute("postList", getPostByDepartment(rpDAO.getAllPosts(), user.getDept().getDepId()));
        request.setAttribute("interviewList", getInterviewByDepartment(iDAO.getAllInterviews(), user.getEmpId()));
        System.out.println(getInterviewByDepartment(iDAO.getAllInterviews(), user.getEmpId()));
        request.getRequestDispatcher("Views/InterviewList.jsp").forward(request, response);
    }

    public List<RecruitmentPost> getPostByDepartment(List<RecruitmentPost> allPost, String deptId) {
        List<RecruitmentPost> result = new ArrayList<>();
        for (RecruitmentPost recruitmentPost : allPost) {
            if ("Approved".equals(recruitmentPost.getStatus()) && recruitmentPost.getDepartment().getDepId().equals(deptId)) {
                result.add(recruitmentPost);
            }
        }
        return result;
    }

    public List<Interview> getInterviewByDepartment(List<Interview> allInterview, int empId) {
        List<Interview> result = new ArrayList<>();
        for (Interview candidate : allInterview) {
            if (candidate.getInterviewedBy().getEmpId() == empId && candidate.getResult().equals("Pending")) {
                result.add(candidate);
            }
        }
        return result;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
