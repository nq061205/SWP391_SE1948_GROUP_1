package controller;

import dal.CandidateDAO;
import dal.RecruitmentPostDAO;
import java.io.IOException;
import java.util.List;
import model.RecruitmentPost;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Candidate;

public class scheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        CandidateDAO cDAO = new CandidateDAO();
        List<RecruitmentPost> posts = rpDAO.getApprovedPosts();
        request.setAttribute("postList", posts);
        request.getRequestDispatcher("Views/scheduleInterview.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // sẽ thêm xử lý sau
    }
}
