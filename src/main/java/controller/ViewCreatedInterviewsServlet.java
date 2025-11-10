package controller;

import dal.InterviewDAO;
import dal.RolePermissionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import model.Employee;
import model.Interview;

public class ViewCreatedInterviewsServlet extends HttpServlet {

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

        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        String search = request.getParameter("search");
        String pageParam = request.getParameter("page");

        if (sortField == null || sortField.isEmpty()) {
            sortField = "date";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "asc";
        }
        if (search == null) {
            search = "";
        }
        
        int currentPage = 1;
        int recordsPerPage = 5; 
        try {
            if (pageParam != null) {
                currentPage = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            currentPage = 1;
        }

        InterviewDAO iDAO = new InterviewDAO();
        List<Interview> allInterviews = iDAO.getInterviewsCreatedBy(user.getEmpId());
        
        int totalRecordsUnfiltered = allInterviews.size();

        List<Interview> filteredList;
        if (!search.trim().isEmpty()) {
            String searchLower = search.trim().toLowerCase();
            filteredList = allInterviews.stream()
                .filter(iv -> iv.getCandidate().getName().toLowerCase().contains(searchLower))
                .collect(Collectors.toList());
        } else {
            filteredList = allInterviews;
        }

        Comparator<Interview> comparator;
        if ("name".equals(sortField)) {
            comparator = Comparator.comparing(
                    iv -> iv.getCandidate().getName(),
                    String.CASE_INSENSITIVE_ORDER
            );
        } else {
            comparator = Comparator.comparing(
                    Interview::getDate,
                    Comparator.nullsLast(Comparator.naturalOrder())
            );
        }

        if ("desc".equals(sortOrder)) {
            comparator = comparator.reversed();
        }
        
        filteredList.sort(comparator);

        int totalRecords = filteredList.size();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        if (currentPage > totalPages && totalPages > 0) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, totalRecords);

        List<Interview> interviewListForPage;
        if (start > end) {
            interviewListForPage = new ArrayList<>();
        } else {
            interviewListForPage = filteredList.subList(start, end);
        }
        Map<Integer, Boolean> editableMap = new HashMap<>();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        for (Interview iv : interviewListForPage) {
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

        request.setAttribute("interviewList", interviewListForPage);
        request.setAttribute("editableMap", editableMap);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("search", search); 
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages); 
        request.setAttribute("totalRecordsUnfiltered", totalRecordsUnfiltered);

        request.getRequestDispatcher("Views/viewcreatedinterviews.jsp").forward(request, response);
    }
}