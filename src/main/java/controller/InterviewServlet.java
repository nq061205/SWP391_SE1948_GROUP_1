package controller;

import dal.InterviewDAO;
import dal.RecruitmentPostDAO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        Employee user = (Employee) session.getAttribute("user");

        String postIdParam = request.getParameter("postId");
        String pageParam = request.getParameter("page");
        String keyword = request.getParameter("keyword");


        int pageNum = 1;
        int pageSize = 5;
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

        session.setAttribute("interviewListFull", fullList);

        int totalPage = (int) Math.ceil((double) fullList.size() / pageSize);
        if (pageNum > totalPage && totalPage > 0) {
            pageNum = totalPage;
        }

        List<Interview> pagedList = new ArrayList<>();
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, fullList.size());
        if (start < end) {
            pagedList = fullList.subList(start, end);
        }

        session.setAttribute("interviewList", pagedList);
        session.setAttribute("pages", pageNum);
        session.setAttribute("total", totalPage);

        request.setAttribute("postId", postIdParam);

        request.setAttribute("keyword", keyword);
        request.setAttribute("postList",
                getPostByDepartment(rpDAO.getAllPosts(), user.getDept().getDepId()));
        request.getRequestDispatcher("Views/InterviewList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InterviewDAO iDAO = new InterviewDAO();
        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        HttpSession session = request.getSession();

        Employee user = (Employee) session.getAttribute("user");

        String keyword = request.getParameter("keyword");
        String postIdParam = request.getParameter("postId");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        System.out.println("abcddd");
        System.out.println(dateFrom);
        System.out.println(dateTo);

        List<Interview> searchResult = getAllInterviewOfEmp(iDAO.getAllInterviews(), user.getEmpId());

        if (postIdParam != null && !"all".equals(postIdParam)) {
            int postId = Integer.parseInt(postIdParam);
            System.out.println("post");
            searchResult = getAllInterviewOfEmpAndFilterPost(searchResult, postId);
        }

        if ((dateFrom != null && !dateFrom.isEmpty()) && (dateTo != null && !dateTo.isEmpty())) {
                try {
                    System.out.println("abababa");
                    LocalDate dFrom = LocalDate.parse(dateFrom);
                    LocalDate dTo = LocalDate.parse(dateTo);
                    if(dFrom.isAfter(dTo)){
                        request.setAttribute("errorDateMessage", "Please choose date from before date to");
                    }else{
                          searchResult = getAllInterviewOfEmpAndFilterDate(
                                searchResult, Date.valueOf(dFrom), Date.valueOf(dTo), keyword);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
        } else if (keyword != null && !keyword.isEmpty()) {
            searchResult = getAllInterviewByKeyword(searchResult, keyword);
        }
        else if((dateFrom != null && dateTo.isEmpty())|| (dateFrom.isEmpty() && dateTo != null)){
            System.out.println("abc");
            request.setAttribute("errorDateMessage", "Please choose both date from and date to");
        }

        int totalPage = (int) Math.ceil((double) searchResult.size() / 5);
        List<Interview> pagedList = searchResult.size() > 5 ? searchResult.subList(0, 5) : searchResult;

        session.setAttribute("interviewListFull", searchResult);
        session.setAttribute("interviewList", pagedList);
        session.setAttribute("pages", (int) session.getAttribute("pages"));
        session.setAttribute("total", totalPage);
        request.setAttribute("postId", postIdParam);
        request.setAttribute("dateFrom", dateFrom);
        request.setAttribute("dateTo", dateTo);
        request.setAttribute("keyword", keyword);
        request.setAttribute("postList",
                getPostByDepartment(rpDAO.getAllPosts(), user.getDept().getDepId()));

        request.getRequestDispatcher("Views/InterviewList.jsp").forward(request, response);
    }

    public List<RecruitmentPost> getPostByDepartment(List<RecruitmentPost> allPost, String deptId) {
        List<RecruitmentPost> result = new ArrayList<>();
        for (RecruitmentPost rp : allPost) {
            if ("Approved".equals(rp.getStatus())
                    && rp.getDepartment().getDepId().equals(deptId)) {
                result.add(rp);
            }
        }
        return result;
    }

    public List<Interview> getAllInterviewOfEmp(List<Interview> allInterview, int empId) {
        List<Interview> result = new ArrayList<>();
        for (Interview i : allInterview) {
            if (i.getInterviewedBy().getEmpId() == empId
                    && "Pending".equals(i.getResult())) {
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

    public List<Interview> getAllInterviewOfEmpAndFilterDate(
            List<Interview> allInterview, Date dateF, Date dateT, String keyword) {

        List<Interview> result = new ArrayList<>();
        for (Interview i : allInterview) {
            Date d = i.getDate();
            if (d != null && (d.equals(dateF) || d.after(dateF))
                    && (d.equals(dateT) || d.before(dateT))) {

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
        if (keyword == null || keyword.trim().isEmpty()) {
            return allInterview;
        }

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
