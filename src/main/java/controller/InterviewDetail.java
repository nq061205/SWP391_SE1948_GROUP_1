package controller;

import api.EmailUtil;
import dal.InterviewDAO;
import dal.RolePermissionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Employee;
import model.Interview;

public class InterviewDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 6)) {
            session.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }

        InterviewDAO iDAO = new InterviewDAO();
        String idStr = request.getParameter("id");
        String action = request.getParameter("action");

        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("interview");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Interview interview = iDAO.getInterviewById(id);

            if (interview == null) {
                response.sendRedirect("interview");
                return;
            }

            if (action != null) {
                Interview nextInterview = getNextInterview(id, getAllInterviewOfEmp(iDAO.getAllInterviews(), user.getEmpId()));
                if(!interview.getResult().equals("Pending")){
                response.sendRedirect("Views/error-404.jsp");
                return;
            }
                if (action.equals("approve")) {
                    iDAO.updateInterviewResult(id, "Pass");
                    String subject = "Interview Result – Human Tech Group";

                    String content
                            = "Dear " + interview.getCandidate().getName() + ",\n\n"
                            + "We are pleased to inform you that you have successfully passed the interview for the position: "
                            + interview.getCandidate().getPost().getTitle() + ".\n\n"
                            + "Our recruitment team will contact you shortly regarding the next steps in the hiring process.\n\n"
                            + "If you have any questions or need additional assistance, please feel free to reply to this email.\n\n"
                            + "Congratulations, and welcome to the next stage!\n\n"
                            + "Best regards,\n"
                            + "Human Tech Recruitment Team\n"
                            + "Human Tech Group";

                    EmailUtil.sendEmail(interview.getCandidate().getEmail(), subject, content);
                } else if (action.equals("reject")) {
                    iDAO.updateInterviewResult(id, "Fail");
                    String subject = "Interview Result – Human Tech Group";
                    String content
                            = "Dear " + interview.getCandidate().getName() + ",\n\n"
                            + "Thank you for taking the time to participate in the interview for the position: "
                            + interview.getCandidate().getPost().getTitle() + ".\n\n"
                            + "After careful consideration, we regret to inform you that you were not selected to move forward in the hiring process.\n\n"
                            + "We truly appreciate your interest in joining Human Tech Group. "
                            + "Please feel free to apply again in future opportunities that match your qualifications.\n\n"
                            + "We wish you the best of luck in your career journey.\n\n"
                            + "Best regards,\n"
                            + "Human Tech Recruitment Team\n"
                            + "Human Tech Group";

                    EmailUtil.sendEmail(interview.getCandidate().getEmail(), subject, content);
                }

                if (nextInterview != null) {
                    response.sendRedirect("interviewdetail?id=" + nextInterview.getInterviewId());
                } else {
                    response.sendRedirect("interview");
                }
                return;
            }
            request.setAttribute("interview", interview);
            request.getRequestDispatcher("Views/interviewdetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    public Interview getNextInterview(int interviewId, List<Interview> interviews) {
        for (int i = 0; i < interviews.size(); i++) {
            if (interviews.get(i).getInterviewId() == interviewId) {
                if (i + 1 < interviews.size()) {
                    return interviews.get(i + 1);
                }
                break;
            }
        }
        return null;
    }

    public List<Interview> getAllInterviewOfEmp(List<Interview> allInterview, int empId) {
        List<Interview> result = new ArrayList<>();
        for (Interview i : allInterview) {
            if (i.getInterviewedBy() != null
                    && i.getInterviewedBy().getEmpId() == empId
                    && "Pending".equalsIgnoreCase(i.getResult())) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
