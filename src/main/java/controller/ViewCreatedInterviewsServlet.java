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

        // ✅ Kiểm tra đăng nhập
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        InterviewDAO iDAO = new InterviewDAO();
        List<Interview> interviewList = iDAO.getInterviewsCreatedBy(user.getEmpId());

        // ✅ Map lưu trạng thái edit được hay không
        Map<Integer, Boolean> editableMap = new HashMap<>();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        for (Interview iv : interviewList) {
            boolean canEdit = false;

            try {
                // Nếu ngày phỏng vấn không null
                if (iv.getDate() != null) {
                    LocalDate interviewDate = iv.getDate().toLocalDate();
                    long daysBetween = ChronoUnit.DAYS.between(today, interviewDate);
                    canEdit = daysBetween >= 2;
                }
            } catch (Exception e) {
                // Ghi log nếu có lỗi parsing
                e.printStackTrace();
                canEdit = false;
            }

            editableMap.put(iv.getInterviewId(), canEdit);
        }

        // ✅ Gửi dữ liệu sang JSP
        request.setAttribute("interviewList", interviewList);
        request.setAttribute("editableMap", editableMap);

        // ✅ Forward tới JSP hiển thị
        request.getRequestDispatcher("Views/viewcreatedinterviews.jsp").forward(request, response);
    }
}
