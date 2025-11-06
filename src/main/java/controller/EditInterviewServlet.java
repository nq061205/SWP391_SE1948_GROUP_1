package controller;

import api.EmailUtil;
import dal.InterviewDAO;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Interview;

public class EditInterviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("viewcreatedinterview");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            InterviewDAO dao = new InterviewDAO();
            Interview iv = dao.getInterviewById(id);

            if (iv == null) {
                response.sendRedirect("viewcreatedinterview?error=notfound");
                return;
            }

            request.setAttribute("interview", iv);
            request.getRequestDispatcher("Views/editinterview.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("viewcreatedinterview");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("interviewId"));
            String dateStr = request.getParameter("date");
            String timeStr = request.getParameter("time");

            LocalDate selectedDate = LocalDate.parse(dateStr);
            LocalTime selectedTime = LocalTime.parse(timeStr);

            LocalDate minDate = LocalDate.now().plusDays(3);
            if (selectedDate.isBefore(minDate)) {
                setError(request, response, id,
                        "Interview date must be at least 3 days from today.");
                return;
            }

            LocalTime start = LocalTime.of(8, 0);
            LocalTime end = LocalTime.of(17, 30);
            if (selectedTime.isBefore(start) || selectedTime.isAfter(end)) {
                setError(request, response, id,
                        "Interview time must be between 08:00 and 17:30.");
                return;
            }

            Date date = Date.valueOf(selectedDate);
            Time time = Time.valueOf(selectedTime);

            InterviewDAO dao = new InterviewDAO();
            boolean updated = dao.updateInterviewDateTime(id, date, time);

            if (updated) {
                Interview iv = dao.getInterviewById(id);
                if (iv != null && iv.getCandidate() != null) {
                    String to = iv.getCandidate().getEmail();
                    String subject = "Interview Schedule Updated";
                    String content = String.format(
                            "Dear %s,\n\nYour interview schedule has been updated.\n\n" +
                            "Position: %s\nNew Date: %s\nNew Time: %s\n\n" +
                            "Please make sure to attend on time.\n\nBest regards,\nHR Department",
                            iv.getCandidate().getName(),
                            iv.getCandidate().getPost().getTitle(),
                            selectedDate,
                            selectedTime
                    );

                    try {
                        EmailUtil.sendEmail(to, subject, content);
                    } catch (MessagingException ex) {
                        Logger.getLogger(EditInterviewServlet.class.getName())
                              .log(Level.SEVERE, "Failed to send email", ex);
                    }
                }

                response.sendRedirect("viewcreatedinterview?success=1");
            } else {
                response.sendRedirect("viewcreatedinterview?error=notfound");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("viewcreatedinterview?error=1");
        }
    }

    private void setError(HttpServletRequest request, HttpServletResponse response,
                          int interviewId, String message)
            throws ServletException, IOException {
        InterviewDAO dao = new InterviewDAO();
        Interview iv = dao.getInterviewById(interviewId);
        request.setAttribute("interview", iv);
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("Views/editinterview.jsp").forward(request, response);
    }
}