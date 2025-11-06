package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.RecruitmentPostDAO;
import model.RecruitmentPost;

public class JobSiteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("view".equals(action)) {
            viewPostDetail(request, response);
        } else {
            showJobList(request, response);
        }
    }

    private void showJobList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("=== JobSiteServlet START ===");

        try {
            RecruitmentPostDAO dao = new RecruitmentPostDAO();
            System.out.println("DAO created");


            String pageStr = request.getParameter("page");
            String pageSizeStr = request.getParameter("pageSize");

            int currentPage = 1;
            int pageSize = 9;

            if (pageStr != null && !pageStr.trim().isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageStr);
                    if (currentPage < 1) currentPage = 1;
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }

            if (pageSizeStr != null && !pageSizeStr.trim().isEmpty()) {
                try {
                    pageSize = Integer.parseInt(pageSizeStr);
                    if (pageSize < 5) pageSize = 5;
                    if (pageSize > 50) pageSize = 50;
                } catch (NumberFormatException e) {
                    pageSize = 9;
                }
            }


            List<RecruitmentPost> allPosts = dao.getAllPosts();
            System.out.println("All posts retrieved: " + (allPosts != null ? allPosts.size() : "null"));

            List<RecruitmentPost> uploadedPosts = new ArrayList<>();

            if (allPosts != null) {
                for (RecruitmentPost post : allPosts) {
                    if ("Uploaded".equals(post.getStatus())) {
                        uploadedPosts.add(post);
                    }
                }
            }

            System.out.println("Uploaded posts: " + uploadedPosts.size());


            int totalPosts = uploadedPosts.size();
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
            if (totalPages == 0) totalPages = 1;
            if (currentPage > totalPages) currentPage = totalPages;


            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalPosts);

            List<RecruitmentPost> posts = new ArrayList<>();
            if (startIndex < totalPosts) {
                posts = uploadedPosts.subList(startIndex, endIndex);
            }


            request.setAttribute("posts", posts);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPosts", totalPosts);

            System.out.println("Forwarding to joblist.jsp");
            request.getRequestDispatcher("Views/joblist.jsp").forward(request, response);
            System.out.println("=== JobSiteServlet END ===");

        } catch (Exception e) {
            System.err.println("Error in JobSiteServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("posts", new ArrayList<>());
            request.setAttribute("currentPage", 1);
            request.setAttribute("totalPages", 1);
            request.setAttribute("pageSize", 9);
            request.setAttribute("totalPosts", 0);
            request.getRequestDispatcher("Views/joblist.jsp").forward(request, response);
        }
    }

    private void viewPostDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            if (postIdStr != null && !postIdStr.trim().isEmpty()) {
                int postId = Integer.parseInt(postIdStr.trim());
                RecruitmentPostDAO dao = new RecruitmentPostDAO();
                RecruitmentPost post = dao.getPostById(postId);

                if (post != null && "Uploaded".equals(post.getStatus())) {
                    request.setAttribute("post", post);
                    request.getRequestDispatcher("Views/uploadedPost.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/jobsite");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/jobsite");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/jobsite");
        } catch (Exception e) {
            System.err.println("Error in viewPostDetail: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/jobsite");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
