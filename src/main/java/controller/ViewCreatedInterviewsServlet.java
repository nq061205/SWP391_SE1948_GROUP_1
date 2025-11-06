package controller;

import dal.InterviewDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import model.Employee;
import model.Interview;

public class ViewCreatedInterviewsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        InterviewDAO iDAO = new InterviewDAO();
        List<Interview> interviewList = iDAO.getInterviewsCreatedBy(user.getEmpId());
        Map<Integer, Boolean> editableMap = new HashMap<>();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        for (Interview iv : interviewList) {
            boolean canEdit = false;

            try {
                if (iv.getDate() != null) {
                    LocalDate interviewDate = iv.getDate().toLocalDate();
                    long daysBetween = ChronoUnit.DAYS.between(today, interviewDate);
                    canEdit = daysBetween >= 2;
                }
            } catch (Exception e) {
                e.printStackTrace();
                canEdit = false;
            }

            editableMap.put(iv.getInterviewId(), canEdit);
        }

        request.setAttribute("interviewList", interviewList);
        request.setAttribute("editableMap", editableMap);
        request.getRequestDispatcher("Views/viewcreatedinterviews.jsp").forward(request, response);
    }
}
