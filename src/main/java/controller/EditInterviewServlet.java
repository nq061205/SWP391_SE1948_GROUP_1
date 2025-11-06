package controller;

import dal.InterviewDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import model.Interview;

public class EditInterviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("viewcreatedinterviews");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            InterviewDAO dao = new InterviewDAO();
            Interview iv = dao.getInterviewById(id);

            if (iv == null) {
                response.sendRedirect("viewcreatedinterviews?error=notfound");
                return;
            }

            request.setAttribute("interview", iv);
            request.getRequestDispatcher("Views/editinterview.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("viewcreatedinterviews");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("interviewId"));
            Date date = Date.valueOf(request.getParameter("date"));
            Time time = Time.valueOf(request.getParameter("time"));

            InterviewDAO dao = new InterviewDAO();
            dao.updateInterviewDateTime(id, date, time);

            response.sendRedirect("viewcreatedinterviews?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("viewcreatedinterviews?error=1");
        }
    }
}
