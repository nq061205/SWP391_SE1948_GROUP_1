package controller;

import dal.InterviewDAO;
import dal.RecruitmentPostDAO;
import dal.RolePermissionDAO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import model.Employee;
import model.Interview;
import model.RecruitmentPost;

public class InterviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InterviewDAO iDAO = new InterviewDAO();
        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        RolePermissionDAO rperDAO = new RolePermissionDAO();
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        Employee user = (Employee) session.getAttribute("user");
        if(user == null){
            response.sendRedirect("login");
            return;
        }
        if (!rperDAO.hasPermission(user.getRole().getRoleId(), 6)) {
            session.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }

        String postIdParam = request.getParameter("postId");
        String keyword = request.getParameter("keyword");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        String type = request.getParameter("type");   
        String order = request.getParameter("order"); 
        String pageParam = request.getParameter("page");

        if (type == null) type = "name";
        if (order == null) order = "asc";

        int pageNum = 1, pageSize = 5;
        if (pageParam != null) {
            try {
                pageNum = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageNum = 1;
            }
        }

        List<Interview> fullList = getAllInterviewOfEmp(iDAO.getAllInterviews(), user.getEmpId());

        if (postIdParam != null && !"all".equals(postIdParam)) {
            try {
                int postId = Integer.parseInt(postIdParam);
                fullList = getAllInterviewOfEmpAndFilterPost(fullList, postId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (dateFrom != null && !dateFrom.isEmpty() && dateTo != null && !dateTo.isEmpty()) {
            try {
                LocalDate dFrom = LocalDate.parse(dateFrom);
                LocalDate dTo = LocalDate.parse(dateTo);
                fullList = getAllInterviewOfEmpAndFilterDate(fullList, Date.valueOf(dFrom), Date.valueOf(dTo), keyword);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            fullList = getAllInterviewByKeyword(fullList, keyword);
        }

        sortInterviewList(fullList, type, order);

        int totalPage = (int) Math.ceil((double) fullList.size() / pageSize);
        if (pageNum > totalPage && totalPage > 0) pageNum = totalPage;
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, fullList.size());
        List<Interview> pagedList = (start < end) ? fullList.subList(start, end) : new ArrayList<>();

        session.setAttribute("interviewListFull", fullList);
        session.setAttribute("interviewList", pagedList);
        session.setAttribute("pages", pageNum);
        session.setAttribute("total", totalPage);

        request.setAttribute("postList", getPostByDepartment(rpDAO.getAllPosts(), user.getDept().getDepId()));
        request.setAttribute("postId", postIdParam);
        request.setAttribute("keyword", keyword);
        request.setAttribute("dateFrom", dateFrom);
        request.setAttribute("dateTo", dateTo);
        request.setAttribute("type", type);
        request.setAttribute("order", order);

        request.getRequestDispatcher("Views/InterviewList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InterviewDAO iDAO = new InterviewDAO();
        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");

        String submit = request.getParameter("action");
        String keyword = request.getParameter("keyword");
        String postIdParam = request.getParameter("postId");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");

        List<Interview> searchResult = getAllInterviewOfEmp(iDAO.getAllInterviews(), user.getEmpId());

        if (postIdParam != null && !"all".equals(postIdParam)) {
            int postId = Integer.parseInt(postIdParam);
            searchResult = getAllInterviewOfEmpAndFilterPost(searchResult, postId);
        }

        if ((dateFrom != null && !dateFrom.isEmpty()) && (dateTo != null && !dateTo.isEmpty()) && submit != null) {
            try {
                LocalDate dFrom = LocalDate.parse(dateFrom);
                LocalDate dTo = LocalDate.parse(dateTo);
                if (dFrom.isAfter(dTo)) {
                    request.setAttribute("errorDateMessage", "Please choose date from before date to");
                } else {
                    searchResult = getAllInterviewOfEmpAndFilterDate(searchResult, Date.valueOf(dFrom), Date.valueOf(dTo), keyword);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (keyword != null && !keyword.isEmpty()) {
            searchResult = getAllInterviewByKeyword(searchResult, keyword);
        } else if ((dateFrom != null && dateTo.isEmpty()) || (dateFrom.isEmpty() && dateTo != null)) {
            if (submit != null)
                request.setAttribute("errorDateMessage", "Please choose both date from and date to");
        }

        int pageNum = 1, pageSize = 5;
        int totalPage = (int) Math.ceil((double) searchResult.size() / pageSize);
        List<Interview> pagedList = searchResult.size() > pageSize
                ? searchResult.subList(0, pageSize)
                : searchResult;

        session.setAttribute("interviewListFull", searchResult);
        session.setAttribute("interviewList", pagedList);
        session.setAttribute("pages", pageNum);
        session.setAttribute("total", totalPage);
        request.setAttribute("postId", postIdParam);
        request.setAttribute("dateFrom", dateFrom);
        request.setAttribute("dateTo", dateTo);
        request.setAttribute("keyword", keyword);
        request.setAttribute("postList", getPostByDepartment(rpDAO.getAllPosts(), user.getDept().getDepId()));

        request.getRequestDispatcher("Views/InterviewList.jsp").forward(request, response);
    }

    private void sortInterviewList(List<Interview> list, String type, String order) {
        Comparator<Interview> comparator;
        if ("date".equalsIgnoreCase(type)) {
            comparator = Comparator.comparing(Interview::getDate,
                    Comparator.nullsLast(Comparator.naturalOrder()));
        } else {
            comparator = Comparator.comparing(i -> i.getCandidate().getName().toLowerCase());
        }
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        list.sort(comparator);
    }

    public List<RecruitmentPost> getPostByDepartment(List<RecruitmentPost> allPost, String deptId) {
        List<RecruitmentPost> result = new ArrayList<>();
        for (RecruitmentPost rp : allPost) {
            if ("Approved".equals(rp.getStatus()) && rp.getDepartment().getDepId().equals(deptId)) {
                result.add(rp);
            }
        }
        return result;
    }

    public List<Interview> getAllInterviewOfEmp(List<Interview> allInterview, int empId) {
        List<Interview> result = new ArrayList<>();
        for (Interview i : allInterview) {
            if (i.getInterviewedBy().getEmpId() == empId && "Pending".equals(i.getResult())) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Interview> getAllInterviewOfEmpAndFilterPost(List<Interview> allInterview, int postId) {
        List<Interview> result = new ArrayList<>();
        for (Interview i : allInterview) {
            if (i.getCandidate().getPost().getPostId() == postId) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Interview> getAllInterviewOfEmpAndFilterDate(List<Interview> allInterview, Date dateF, Date dateT, String keyword) {
        List<Interview> result = new ArrayList<>();
        for (Interview i : allInterview) {
            Date d = i.getDate();
            if (d != null && (d.equals(dateF) || d.after(dateF)) && (d.equals(dateT) || d.before(dateT))) {
                if (keyword == null || keyword.trim().isEmpty()) {
                    result.add(i);
                } else {
                    String k = keyword.toLowerCase();
                    String name = i.getCandidate().getName().toLowerCase();
                    String email = i.getCandidate().getEmail().toLowerCase();
                    if (name.contains(k) || email.contains(k)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }

    public List<Interview> getAllInterviewByKeyword(List<Interview> allInterview, String keyword) {
        List<Interview> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) return allInterview;
        String lower = keyword.toLowerCase();
        for (Interview i : allInterview) {
            String name = i.getCandidate().getName().toLowerCase();
            String email = i.getCandidate().getEmail().toLowerCase();
            if (name.contains(lower) || email.contains(lower)) {
                result.add(i);
            }
        }
        return result;
    }
}
