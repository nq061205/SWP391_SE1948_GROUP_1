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

        if (user == null || !rperDAO.hasPermission(user.getRole().getRoleId(), 6)) {
            response.sendRedirect("login");
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
                if (action.equals("approve")) {
                    iDAO.updateInterviewResult(id, "Pass");
                    EmailUtil.sendEmail(
                            interview.getCandidate().getEmail(),
                            "Interview Result",
                            "Congratulations! You have passed our interview."
                    );
                } else if (action.equals("reject")) {
                    iDAO.updateInterviewResult(id, "Fail");
                    EmailUtil.sendEmail(
                            interview.getCandidate().getEmail(),
                            "Interview Result",
                            "We’re sorry, you did not pass our interview this time."
                    );
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
