package controller.hrm;

import dal.RecruitmentPostDAO;
import model.RecruitmentPost;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HRManagerRecruitmentServlet extends HttpServlet {

    private RecruitmentPostDAO recruitmentPostDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        recruitmentPostDAO = new RecruitmentPostDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                showPostReviewList(request, response);
                break;
            case "view":
                viewPostDetail(request, response);
                break;
            case "approve":
                approvePost(request, response);
                break;
            case "reject":
                rejectPost(request, response);
                break;
            default:
                showPostReviewList(request, response);
                break;
        }
    }
    
    private void showPostReviewList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Check for messages from session
            String successMessage = (String) request.getSession().getAttribute("successMessage");
            String errorMessage = (String) request.getSession().getAttribute("errorMessage");
            
            if (successMessage != null) {
                request.setAttribute("successMessage", successMessage);
                request.getSession().removeAttribute("successMessage");
            }
            
            if (errorMessage != null) {
                request.setAttribute("errorMessage", errorMessage);
                request.getSession().removeAttribute("errorMessage");
            }
            
            List<RecruitmentPost> pendingAndRejectedPosts = recruitmentPostDAO.getPendingAndRejectedPosts();
            boolean hasPendingOrRejected = (pendingAndRejectedPosts != null && !pendingAndRejectedPosts.isEmpty());
            int totalPosts = (pendingAndRejectedPosts != null) ? pendingAndRejectedPosts.size() : 0;
            int pendingCount = 0;
            int rejectedCount = 0;
            
            if (pendingAndRejectedPosts != null) {
                for (RecruitmentPost post : pendingAndRejectedPosts) {
                    if ("Pending".equals(post.getStatus())) {
                        pendingCount++;
                    } else if ("Rejected".equals(post.getStatus())) {
                        rejectedCount++;
                    }
                }
            }
            
            request.setAttribute("pendingAndRejectedPosts", pendingAndRejectedPosts);
            request.setAttribute("hasPendingOrRejected", hasPendingOrRejected);
            request.setAttribute("totalPosts", totalPosts);
            request.setAttribute("pendingCount", pendingCount);
            request.setAttribute("rejectedCount", rejectedCount);
            request.setAttribute("currentPage", "Post Review");
            request.setAttribute("pageTitle", "Review Recruitment Posts");
            request.getRequestDispatcher("/Views/HRM/PostReview.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in showPostReviewList: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load recruitment posts. Please try again.");
            request.getRequestDispatcher("/Views/HRM/PostReview.jsp").forward(request, response);
        }
    }
    
    private void viewPostDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            if (postIdStr != null && !postIdStr.trim().isEmpty()) {
                int postId = Integer.parseInt(postIdStr.trim());
                RecruitmentPost post = recruitmentPostDAO.getPostById(postId);
                
                if (post != null) {
                    boolean hasPost = true;
                    boolean hasContent = (post.getContent() != null && !post.getContent().trim().isEmpty());
                    boolean hasDepartment = (post.getDepartment() != null);
                    boolean hasCreatedBy = (post.getCreatedBy() != null);
                    boolean hasApprovedBy = (post.getApprovedBy() != null);
                    boolean hasCreatedAt = (post.getCreatedAt() != null);
                    boolean hasApprovedAt = (post.getApprovedAt() != null);
                    boolean hasUpdatedAt = (post.getUpdatedAt() != null);
                    boolean isPending = "Pending".equals(post.getStatus());
                    boolean isRejected = "Rejected".equals(post.getStatus());
                    
                    request.setAttribute("post", post);
                    request.setAttribute("hasPost", hasPost);
                    request.setAttribute("hasContent", hasContent);
                    request.setAttribute("hasDepartment", hasDepartment);
                    request.setAttribute("hasCreatedBy", hasCreatedBy);
                    request.setAttribute("hasApprovedBy", hasApprovedBy);
                    request.setAttribute("hasCreatedAt", hasCreatedAt);
                    request.setAttribute("hasApprovedAt", hasApprovedAt);
                    request.setAttribute("hasUpdatedAt", hasUpdatedAt);
                    request.setAttribute("isPending", isPending);
                    request.setAttribute("isRejected", isRejected);
                    request.setAttribute("currentPage", "Post Review");
                    request.setAttribute("pageTitle", "Post Detail");
                    request.getRequestDispatcher("/Views/HRM/PostReviewDetail.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("errorMessage", "Recruitment post not found.");
                    response.sendRedirect(request.getContextPath() + "/postreview");
                }
            } else {
                request.getSession().setAttribute("errorMessage", "Invalid post ID.");
                response.sendRedirect(request.getContextPath() + "/postreview");
            }
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/postreview");
        } catch (Exception e) {
            System.err.println("Error in viewPostDetail: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to load post detail. Please try again.");
            response.sendRedirect(request.getContextPath() + "/postreview");
        }
    }
    
    private void approvePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            String note = request.getParameter("note");
            StringBuilder validationErrors = new StringBuilder();
            
            if (postIdStr == null || postIdStr.trim().isEmpty()) {
                validationErrors.append("Post ID is required. ");
            }
            
            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/postreview");
                return;
            }
            
            int postId = Integer.parseInt(postIdStr.trim());
            RecruitmentPost post = recruitmentPostDAO.getPostById(postId);
            
            if (post == null) {
                request.getSession().setAttribute("errorMessage", "Post not found.");
                response.sendRedirect(request.getContextPath() + "/postreview");
                return;
            }
            
            if (!"Pending".equals(post.getStatus())) {
                request.getSession().setAttribute("errorMessage", "Only pending posts can be approved.");
                response.sendRedirect(request.getContextPath() + "/postreview");
                return;
            }
            
            int approvedBy = 2;
            boolean success = recruitmentPostDAO.approvePost(postId, approvedBy, note != null ? note.trim() : null);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post '" + post.getTitle() + "' approved successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to approve post. Please try again.");
            }
            
            response.sendRedirect(request.getContextPath() + "/postreview");
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/postreview");
        } catch (Exception e) {
            System.err.println("Error in approvePost: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to approve post. Please try again.");
            response.sendRedirect(request.getContextPath() + "/postreview");
        }
    }
    
    private void rejectPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            StringBuilder validationErrors = new StringBuilder();
            
            if (postIdStr == null || postIdStr.trim().isEmpty()) {
                validationErrors.append("Post ID is required. ");
            }
            
            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/postreview");
                return;
            }
            
            int postId = Integer.parseInt(postIdStr.trim());
            RecruitmentPost post = recruitmentPostDAO.getPostById(postId);
            
            if (post == null) {
                request.getSession().setAttribute("errorMessage", "Post not found.");
                response.sendRedirect(request.getContextPath() + "/postreview");
                return;
            }
            
            if (!"Pending".equals(post.getStatus())) {
                request.getSession().setAttribute("errorMessage", "Only pending posts can be rejected.");
                response.sendRedirect(request.getContextPath() + "/postreview");
                return;
            }
            
            int rejectedBy = 2;
            boolean success = recruitmentPostDAO.rejectPost(postId, rejectedBy);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post '" + post.getTitle() + "' rejected successfully.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to reject post. Please try again.");
            }
            
            response.sendRedirect(request.getContextPath() + "/postreview");
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/postreview");
        } catch (Exception e) {
            System.err.println("Error in rejectPost: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to reject post. Please try again.");
            response.sendRedirect(request.getContextPath() + "/postreview");
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
        return "HRM Manager Recruitment Post Review Servlet";
    }

}
