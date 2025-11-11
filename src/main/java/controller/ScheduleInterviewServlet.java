package controller;

import api.EmailUtil;
import dal.CandidateDAO;
import dal.EmployeeDAO;
import dal.InterviewDAO;
import dal.RecruitmentPostDAO;
import dal.RolePermissionDAO;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RecruitmentPost;
import model.Candidate;
import model.Employee;
import model.Interview;

public class ScheduleInterviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        EmployeeDAO eDAO = new EmployeeDAO();
        RolePermissionDAO rperDAO = new RolePermissionDAO();

        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 5)) {
            session.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        List<RecruitmentPost> posts = rpDAO.getUploadedPosts();
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
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        String postIdStr = request.getParameter("postId");
        String action = request.getParameter("action");
        String[] selectedIds = request.getParameterValues("candidateIds");
        String dateStr = request.getParameter("date");
        String timeStr = request.getParameter("time");
        String interviewer = request.getParameter("interviewer");
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 5)) {
            session.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        List<RecruitmentPost> posts = rpDAO.getUploadedPosts();
        request.setAttribute("postList", posts);
        if (action == null) {
            if (postIdStr != null && !postIdStr.equals("all") && !postIdStr.isEmpty()) {
                try {
                    int postId = Integer.parseInt(postIdStr);
                    List<Candidate> allCandidates = cDAO.getAllCandidate("approve");
                    System.out.println(allCandidates);
                    List<Candidate> candidateList = getAvailableCandidatesByPost(allCandidates, postId, iDAO.getAllInterviews());
                    System.out.println(candidateList);
                    String deptId = rpDAO.getPostById(postId).getDepartment().getDepId();
                    List<Employee> interviewerList = getEmployeeByDept(eDAO.getAllEmployees(), deptId);

                    request.setAttribute("candidatesList", candidateList);
                    request.setAttribute("employeeInterview", interviewerList);
                    request.setAttribute("selectedPostId", postId);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            request.getRequestDispatcher("Views/scheduleInterview.jsp").forward(request, response);
            return;
        }

        try {
            if (postIdStr == null || postIdStr.isEmpty() || "all".equals(postIdStr)) {
                request.setAttribute("errorMessage", "Please select a recruitment post first.");
            } else if (selectedIds == null || selectedIds.length == 0) {
                request.setAttribute("errorMessage", "Please choose candidate(s) to create interview schedule.");
            } else if (dateStr == null || dateStr.isEmpty() || timeStr == null || timeStr.isEmpty()) {
                request.setAttribute("errorMessage", "Please select date and time for interview.");
            } else {
                LocalDate date = LocalDate.parse(dateStr);
                LocalTime time = LocalTime.parse(timeStr);

                if (date.isBefore(LocalDate.now())
                        || (date.equals(LocalDate.now()) && time.isBefore(LocalTime.now()))) {
                    request.setAttribute("errorMessage", "Please select a valid future date and time.");
                } else {
                    int postId = Integer.parseInt(postIdStr);
                    String deptId = rpDAO.getPostById(postId).getDepartment().getDepId();
                    int interviewerId = Integer.parseInt(interviewer);
                    Employee interviewerBy = eDAO.getEmployeeByEmpId(interviewerId);

                    for (String selectedId : selectedIds) {
                        Candidate candidate = cDAO.getCandidateById(Integer.parseInt(selectedId));
                        Interview i = new Interview();
                        i.setCandidate(candidate);
                        i.setCreatedBy(user);
                        i.setInterviewedBy(interviewerBy);
                        i.setDate(Date.valueOf(date));
                        i.setTime(Time.valueOf(time));
                        i.setResult("Pending");
                        iDAO.insertToInterview(i);

                        try {
                            EmailUtil.sendEmail(
                                    candidate.getEmail(),
                                    "Invitation to Interview",
                                    "Dear " + candidate.getName()
                                    + ",\n\nCongratulations! You are invited to an interview for the position "
                                    + candidate.getPost().getTitle() + ".\n\n"
                                    + "Date: " + dateStr + "\nTime: " + timeStr
                                    + "\n\nPlease arrive 10 minutes early.\n\nBest regards,\nHR Department"
                            );
                        } catch (MessagingException ex) {
                            Logger.getLogger(ScheduleInterviewServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    request.setAttribute("successMessage", "Interview schedule(s) created successfully!");
                }
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Error: " + ex.getMessage());
            ex.printStackTrace();
        }

        if (postIdStr != null && !postIdStr.isEmpty() && !"all".equals(postIdStr)) {
            int postId = Integer.parseInt(postIdStr);
            List<Candidate> allCandidates = cDAO.getAllCandidate("approve");
            List<Candidate> candidateList = getAvailableCandidatesByPost(allCandidates, postId, iDAO.getAllInterviews());
            String deptId = rpDAO.getPostById(postId).getDepartment().getDepId();
            List<Employee> interviewerList = getEmployeeByDept(eDAO.getAllEmployees(), deptId);

            request.setAttribute("candidatesList", candidateList);
            request.setAttribute("employeeInterview", interviewerList);
            request.setAttribute("selectedPostId", postId);
        }

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
            if (c.getPost() != null
                    && c.getPost().getPostId() == postId
                    && !interviewedIds.contains(c.getCandidateId())) {
                result.add(c);
            }
        }
        return result;
    }

    private List<Employee> getEmployeeByDept(List<Employee> empList, String deptId) {
        List<Employee> eList = new ArrayList<>();
        for (Employee employee : empList) {
            if (employee.getDept() != null && deptId.equals(employee.getDept().getDepId())) {
                eList.add(employee);
            }
        }
        return eList;
    }
}
