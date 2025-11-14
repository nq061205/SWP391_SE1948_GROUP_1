package controller;

import api.EmailUtil;
import dal.InterviewDAO;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Interview;

public class EditInterviewServlet extends HttpServlet {

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
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 5)) {
            session.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }

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
            DayOfWeek weekday = selectedDate.getDayOfWeek();
            LocalDate minDate = LocalDate.now().plusDays(2);
            if (selectedDate.isBefore(minDate)) {
                setError(request, response, id,
                        "You can only edit the schedule when it has more than 2 days left.");
                return;
            }
            if (weekday.getValue() == 6 || weekday.getValue() == 7) {
                setError(request, response, id,
                        "Please choose from monday to friday.");
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
                    String subject = "Updated Interview Schedule – " + iv.getCandidate().getPost().getTitle();
                    String content
                            = "Dear " + iv.getCandidate().getName() + ",\n\n"
                            + "We would like to inform you that the interview schedule for your application "
                            + "has been updated. Please refer to the details below:\n\n"
                            + "• Position: " + iv.getCandidate().getPost().getTitle() + "\n"
                            + "• Updated Date: " + selectedDate + "\n"
                            + "• Updated Time: " + selectedTime + "\n\n"
                            + "Kindly make sure to attend the interview on time. If you are unable to join at the scheduled time, "
                            + "please reply to this email so we can assist you with rescheduling.\n\n"
                            + "If you have any questions, feel free to reach out to our HR department.\n\n"
                            + "Best regards,\n"
                            + "HR Department\n"
                            + "Human Tech";
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
