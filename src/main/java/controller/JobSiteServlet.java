// ...existing code...
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
            
            // Get only Uploaded posts (posts that are published)
            List<RecruitmentPost> allPosts = dao.getAllPosts();
            System.out.println("All posts retrieved: " + (allPosts != null ? allPosts.size() : "null"));
            
            List<RecruitmentPost> posts = new ArrayList<>();
            
            if (allPosts != null) {
                for (RecruitmentPost post : allPosts) {
                    if ("Uploaded".equals(post.getStatus())) {
                        posts.add(post);
                    }
                }
            }
            
            System.out.println("Uploaded posts: " + posts.size());
            request.setAttribute("posts", posts);
            
            System.out.println("Forwarding to joblist.jsp");
            request.getRequestDispatcher("Views/joblist.jsp").forward(request, response);
            System.out.println("=== JobSiteServlet END ===");
            
        } catch (Exception e) {
            System.err.println("Error in JobSiteServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("posts", new ArrayList<>());
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
