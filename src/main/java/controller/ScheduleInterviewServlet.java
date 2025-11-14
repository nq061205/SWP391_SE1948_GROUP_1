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
import java.time.DayOfWeek;
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

        String postIdStr = request.getParameter("postId");
        String action = request.getParameter("action");
        String[] selectedIds = request.getParameterValues("candidateIds");
        String dateStr = request.getParameter("date");
        String timeStr = request.getParameter("time");
        String interviewer = request.getParameter("interviewer");

        List<RecruitmentPost> postList = rpDAO.getUploadedPosts();
        request.setAttribute("postList", postList);

        if (action == null) {

            if (postIdStr != null && !"all".equals(postIdStr) && !postIdStr.isEmpty()) {
                int postId = Integer.parseInt(postIdStr);

                List<Candidate> allCandidates = cDAO.getAllCandidate("approve");
                List<Candidate> candidateList
                        = getAvailableCandidatesByPost(allCandidates, postId, iDAO.getAllInterviews());

                String deptId = rpDAO.getPostById(postId).getDepartment().getDepId();
                List<Employee> interviewerList = getEmployeeByDept(eDAO.getAllEmployees(), deptId);

                request.setAttribute("candidatesList", candidateList);
                request.setAttribute("employeeInterview", interviewerList);
                request.setAttribute("selectedPostId", postId);
            }

            request.setAttribute("selectedCandidatesData", selectedIds);
            request.setAttribute("selectedInterviewer", interviewer);
            request.setAttribute("selectedDate", dateStr);
            request.setAttribute("selectedTime", timeStr);

            request.getRequestDispatcher("Views/scheduleInterview.jsp").forward(request, response);
            return;
        }

        try {

            if (postIdStr == null || postIdStr.isEmpty() || "all".equals(postIdStr)) {
                request.setAttribute("errorMessage", "Please select a recruitment post first.");
            } else if (selectedIds == null || selectedIds.length == 0) {
                request.setAttribute("errorMessage", "Please choose candidate(s) to create interview schedule.");
            } else if (dateStr == null || timeStr == null || dateStr.isEmpty() || timeStr.isEmpty()) {
                request.setAttribute("errorMessage", "Please select date and time for interview.");
            } else {

                LocalDate date = LocalDate.parse(dateStr);
                LocalTime time = LocalTime.parse(timeStr);

                if (date.getDayOfWeek().getValue() >= 6) {
                    request.setAttribute("errorMessage", "Please select from Monday to Friday.");
                } else if (date.isBefore(LocalDate.now().plusDays(2))
                        || (date.equals(LocalDate.now()) && time.isBefore(LocalTime.now()))) {
                    request.setAttribute("errorMessage", "Please select a valid future date and time.");
                } else {
                    int postId = Integer.parseInt(postIdStr);
                    int interviewerId = Integer.parseInt(interviewer);
                    Employee interviewerBy = eDAO.getEmployeeByEmpId(interviewerId);

                    for (String cid : selectedIds) {
                        Candidate c = cDAO.getCandidateById(Integer.parseInt(cid));

                        Interview i = new Interview();
                        i.setCandidate(c);
                        i.setCreatedBy(user);
                        i.setInterviewedBy(interviewerBy);
                        i.setDate(Date.valueOf(date));
                        i.setTime(Time.valueOf(time));
                        i.setResult("Pending");

                        iDAO.insertToInterview(i);

                        try {
                            String subject = "Interview Invitation – " + c.getPost().getTitle();

                            String message
                                    = "Dear " + c.getName() + ",\n\n"
                                    + "We are pleased to inform you that you have been shortlisted for an interview "
                                    + "for the position: **" + c.getPost().getTitle() + "**.\n\n"
                                    + "Please find the interview details below:\n"
                                    + "• Date: " + dateStr + "\n"
                                    + "• Time: " + timeStr + "\n"
                                    + "• Interviewer: " + interviewerBy.getFullname()+ "\n\n"
                                    + "If you are unable to attend at the scheduled time, kindly contact us as soon as possible "
                                    + "so we can assist with rescheduling.\n\n"
                                    + "We look forward to meeting you.\n\n"
                                    + "Best regards,\n"
                                    + "HR Department\n"
                                    + "Human Tech";

                            EmailUtil.sendEmail(c.getEmail(), subject, message);
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
            List<Candidate> candidateList
                    = getAvailableCandidatesByPost(allCandidates, postId, iDAO.getAllInterviews());

            String deptId = rpDAO.getPostById(postId).getDepartment().getDepId();
            List<Employee> interviewerList = getEmployeeByDept(eDAO.getAllEmployees(), deptId);

            request.setAttribute("candidatesList", candidateList);
            request.setAttribute("employeeInterview", interviewerList);
            request.setAttribute("selectedPostId", postId);
        }

        request.setAttribute("selectedCandidatesData", selectedIds);
        request.setAttribute("selectedInterviewer", interviewer);
        request.setAttribute("selectedDate", dateStr);
        request.setAttribute("selectedTime", timeStr);

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
