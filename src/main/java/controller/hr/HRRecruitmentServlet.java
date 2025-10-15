package controller.hr;

import dal.RecruitmentPostDAO;
import model.Department;
import model.RecruitmentPost;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HRRecruitmentServlet extends HttpServlet {

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
                showApprovedPostsList(request, response);
                break;
            case "view":
                viewPostDetail(request, response);
                break;
            case "create":
                createPost(request, response);
                break;
            case "edit":
                editPost(request, response);
                break;
            case "update":
                updatePost(request, response);
                break;
            default:
                showApprovedPostsList(request, response);
                break;
        }
    }
    
    private void showApprovedPostsList(HttpServletRequest request, HttpServletResponse response)
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
            
            List<RecruitmentPost> approvedPosts = recruitmentPostDAO.getApprovedPosts();
            List<RecruitmentPost> pendingAndRejectedPosts = recruitmentPostDAO.getPendingAndRejectedPosts();
            List<Department> departments = recruitmentPostDAO.getDepartments();
            System.out.println("Servlet - departments size: " + departments.size());
            
            int totalPosts = (approvedPosts != null) ? approvedPosts.size() : 0;
            
            boolean hasApprovedPosts = (approvedPosts != null && !approvedPosts.isEmpty());
            boolean hasPendingOrRejected = (pendingAndRejectedPosts != null && !pendingAndRejectedPosts.isEmpty());
            boolean hasDepartments = (departments != null && !departments.isEmpty());
            
            request.setAttribute("approvedPosts", approvedPosts);
            request.setAttribute("pendingAndRejectedPosts", pendingAndRejectedPosts);
            request.setAttribute("departments", departments);
            request.setAttribute("totalPosts", totalPosts);
            request.setAttribute("hasApprovedPosts", hasApprovedPosts);
            request.setAttribute("hasPendingOrRejected", hasPendingOrRejected);
            request.setAttribute("hasDepartments", hasDepartments);
            request.setAttribute("currentPage", "Recruitment Management");
            request.setAttribute("pageTitle", "Approved Posts List");
            
            request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in showApprovedPostsList: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load recruitment posts. Please try again.");
            request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);
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
                    
                    request.setAttribute("post", post);
                    request.setAttribute("hasPost", hasPost);
                    request.setAttribute("hasContent", hasContent);
                    request.setAttribute("hasDepartment", hasDepartment);
                    request.setAttribute("hasCreatedBy", hasCreatedBy);
                    request.setAttribute("hasApprovedBy", hasApprovedBy);
                    request.setAttribute("hasCreatedAt", hasCreatedAt);
                    request.setAttribute("hasApprovedAt", hasApprovedAt);
                    request.setAttribute("hasUpdatedAt", hasUpdatedAt);
                    request.setAttribute("currentPage", "Recruitment Management");
                    request.setAttribute("pageTitle", "Post Detail");
                    request.getRequestDispatcher("/Views/HR/recruitmentPostDetail.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Recruitment post not found.");
                    showApprovedPostsList(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Invalid post ID.");
                showApprovedPostsList(request, response);
            }
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.setAttribute("errorMessage", "Invalid post ID format.");
            showApprovedPostsList(request, response);
        } catch (Exception e) {
            System.err.println("Error in viewPostDetail: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load post detail. Please try again.");
            showApprovedPostsList(request, response);
        }
    }
    
    private void createPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String depId = request.getParameter("depId");
            StringBuilder validationErrors = new StringBuilder();
            
            if (title == null || title.trim().isEmpty()) {
                validationErrors.append("Title is required. ");
            } else if (title.trim().length() > 255) {
                validationErrors.append("Title must not exceed 255 characters. ");
            }
            
            if (content == null || content.trim().isEmpty()) {
                validationErrors.append("Job description is required. ");
            }
            
            if (depId == null || depId.trim().isEmpty()) {
                validationErrors.append("Department is required. ");
            }
            
            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/hrrecruitment");
                return;
            }
            
            int createdBy = 1;
            int approvedBy = 2;
            boolean success = recruitmentPostDAO.createPost(title.trim(), content.trim(), depId.trim(), createdBy, approvedBy);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post created successfully! It's now pending approval.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to create post. Please try again.");
            }
            
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
            
        } catch (Exception e) {
            System.err.println("Error in createPost: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to create post. Please try again.");
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
        }
    }
    
    private void editPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            if (postIdStr != null && !postIdStr.trim().isEmpty()) {
                int postId = Integer.parseInt(postIdStr.trim());
                RecruitmentPost post = recruitmentPostDAO.getPostById(postId);
                
                if (post != null && "Rejected".equals(post.getStatus())) {
                    List<Department> departments = recruitmentPostDAO.getDepartments();
                    List<RecruitmentPost> approvedPosts = recruitmentPostDAO.getApprovedPosts();
                    List<RecruitmentPost> pendingAndRejectedPosts = recruitmentPostDAO.getPendingAndRejectedPosts();
                    
                    int totalPosts = (approvedPosts != null) ? approvedPosts.size() : 0;
                    boolean hasApprovedPosts = (approvedPosts != null && !approvedPosts.isEmpty());
                    boolean hasPendingOrRejected = (pendingAndRejectedPosts != null && !pendingAndRejectedPosts.isEmpty());
                    boolean hasDepartments = (departments != null && !departments.isEmpty());
                    
                    request.setAttribute("editPost", post);
                    request.setAttribute("departments", departments);
                    request.setAttribute("approvedPosts", approvedPosts);
                    request.setAttribute("pendingAndRejectedPosts", pendingAndRejectedPosts);
                    request.setAttribute("totalPosts", totalPosts);
                    request.setAttribute("hasApprovedPosts", hasApprovedPosts);
                    request.setAttribute("hasPendingOrRejected", hasPendingOrRejected);
                    request.setAttribute("hasDepartments", hasDepartments);
                    request.setAttribute("currentPage", "Recruitment Management");
                    request.setAttribute("pageTitle", "Edit Rejected Post");
                    request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Post not found or not in rejected status.");
                    showApprovedPostsList(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Invalid post ID.");
                showApprovedPostsList(request, response);
            }
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.setAttribute("errorMessage", "Invalid post ID format.");
            showApprovedPostsList(request, response);
        } catch (Exception e) {
            System.err.println("Error in editPost: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load post for editing. Please try again.");
            showApprovedPostsList(request, response);
        }
    }
    
    private void updatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String depId = request.getParameter("depId");
            StringBuilder validationErrors = new StringBuilder();
            
            if (postIdStr == null || postIdStr.trim().isEmpty()) {
                validationErrors.append("Post ID is required. ");
            }
            
            if (title == null || title.trim().isEmpty()) {
                validationErrors.append("Title is required. ");
            } else if (title.trim().length() > 255) {
                validationErrors.append("Title must not exceed 255 characters. ");
            }
            
            if (content == null || content.trim().isEmpty()) {
                validationErrors.append("Job description is required. ");
            }
            
            if (depId == null || depId.trim().isEmpty()) {
                validationErrors.append("Department is required. ");
            }
            
            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/hrrecruitment");
                return;
            }
            
            int postId = Integer.parseInt(postIdStr.trim());
            boolean success = recruitmentPostDAO.updatePost(postId, title.trim(), content.trim(), depId.trim());
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post updated successfully! Status changed to pending.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update post. Please try again.");
            }
            
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
        } catch (Exception e) {
            System.err.println("Error in updatePost: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to update post. Please try again.");
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
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
        return "HR Recruitment Management Servlet";
    }

}
