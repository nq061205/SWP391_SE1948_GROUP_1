package controller;

import api.EmailUtil;
import dal.CandidateDAO;
import dal.EmployeeDAO;
import dal.InterviewDAO;
import dal.RecruitmentPostDAO;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import model.RecruitmentPost;
import model.Candidate;
import model.Employee;
import model.Interview;
import java.sql.Date;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

public class scheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        List<RecruitmentPost> posts = rpDAO.getApprovedPosts();
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        request.setAttribute("postList", posts);
        request.getRequestDispatcher("Views/scheduleInterview.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        CandidateDAO cDAO = new CandidateDAO();
        EmployeeDAO eDAO = new EmployeeDAO();
        InterviewDAO iDAO = new InterviewDAO();
        String postIdStr = request.getParameter("postId");
        String[] selectedIds = request.getParameterValues("candidateIds");
        String dateStr = request.getParameter("date");
        String timeStr = request.getParameter("time");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        Employee user = (Employee) session.getAttribute("user");
        if (dateStr != null && !dateStr.isEmpty() && timeStr != null && !timeStr.isEmpty()) {
            date = LocalDate.parse(dateStr);
            time = LocalTime.parse(timeStr);
            if (time.isBefore(LocalTime.now()) && date.equals(LocalDate.now())) {
                request.setAttribute("errorMessage", "Please select correct time after present time");
            }
        }
        List<RecruitmentPost> posts = rpDAO.getApprovedPosts();
        request.setAttribute("postList", posts);

        if (postIdStr != null && !postIdStr.isEmpty()) {
            try {
                int postId = Integer.parseInt(postIdStr);
                List<Candidate> allCandidates = cDAO.getAllCandidate("approve");
                List<Candidate> candidateList = getAvailableCandidatesByPost(allCandidates, postId,iDAO.getAllInterviews());
                request.setAttribute("candidateList", candidateList);
                request.setAttribute("selectedPostId", postId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        List<Interview> listInterview = new ArrayList<>();

        if (selectedIds != null && selectedIds.length != 0 && postIdStr != null && !postIdStr.isEmpty()) {
            try {
                for (String selectedId : selectedIds) {
                    int postId = Integer.parseInt(postIdStr);
                    Candidate candidate = cDAO.getCandidateById(Integer.parseInt(selectedId));
                    String deptId = rpDAO.getPostById(postId).getDepartment().getDepId();
                    Interview i = new Interview();
                    i.setCandidate(candidate);
                    i.setCreatedBy(user);
                    i.setDate(Date.valueOf(date));
                    i.setInterviewedBy(eDAO.getManagerByDepartment(deptId));
                    i.setResult("Pending");
                    i.setTime(Time.valueOf(time));
                    EmailUtil.sendEmail(candidate.getEmail(), "Invitation to Interview ", "Congratulation" + candidate.getName()+" has been invited to our interview at " + dateStr+" " + timeStr);
                    iDAO.insertToInterview(i);
                     
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (MessagingException ex) {
                Logger.getLogger(scheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else{
            request.setAttribute("errorMessage", "Please choose candidate to create interview schedule");
            
        }
        int postId = Integer.parseInt(postIdStr);
                List<Candidate> allCandidates = cDAO.getAllCandidate("approve");
                List<Candidate> candidateList = getAvailableCandidatesByPost(allCandidates, postId,iDAO.getAllInterviews());
                request.setAttribute("candidateList", candidateList);
                request.setAttribute("selectedPostId", postId);
        request.getRequestDispatcher("Views/scheduleInterview.jsp").forward(request, response);
    }

     private List<Candidate> getAvailableCandidatesByPost(List<Candidate> all, int postId, List<Interview> interviewList) {
        List<Candidate> result = new ArrayList<>();
        Set<Integer> interviewedIds = new HashSet<>();
        for (Interview i : interviewList) {
            if (i.getCandidate() != null) {
                interviewedIds.add(i.getCandidate().getCandidateId());
            }
        }

        for (Candidate c : all) {
            if (c.getPost() != null && c.getPost().getPostId() == postId &&
                !interviewedIds.contains(c.getCandidateId())) {
                result.add(c);
            }
        }
        return result;
    }
}
