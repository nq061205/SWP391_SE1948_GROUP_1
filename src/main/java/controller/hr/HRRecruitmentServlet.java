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
            case "send":
                sendPost(request, response);
                break;
            default:
                showApprovedPostsList(request, response);
                break;
        }
    }
    
    private void showApprovedPostsList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
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
            
            String pageStr = request.getParameter("page");
            String pageSizeStr = request.getParameter("pageSize");
            String searchKeyword = request.getParameter("search");
            
            String notifPageStr = request.getParameter("notifPage");
            String notifPageSizeStr = request.getParameter("notifPageSize");
            String notifSearchKeyword = request.getParameter("notifSearch");
            String notifStatusFilter = request.getParameter("notifStatus");
            
            int currentPage = 1;
            int pageSize = 10;
            int notifCurrentPage = 1;
            int notifPageSize = 5;
            
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
                    if (pageSize > 100) pageSize = 100;
                } catch (NumberFormatException e) {
                    pageSize = 10;
                }
            }
            
            if (notifPageStr != null && !notifPageStr.trim().isEmpty()) {
                try {
                    notifCurrentPage = Integer.parseInt(notifPageStr);
                    if (notifCurrentPage < 1) notifCurrentPage = 1;
                } catch (NumberFormatException e) {
                    notifCurrentPage = 1;
                }
            }
            
            if (notifPageSizeStr != null && !notifPageSizeStr.trim().isEmpty()) {
                try {
                    notifPageSize = Integer.parseInt(notifPageSizeStr);
                    if (notifPageSize < 5) notifPageSize = 5;
                    if (notifPageSize > 50) notifPageSize = 50;
                } catch (NumberFormatException e) {
                    notifPageSize = 5;
                }
            }
            
            List<RecruitmentPost> allApprovedPosts = recruitmentPostDAO.getApprovedPosts();
            List<RecruitmentPost> allPendingAndRejectedPosts = recruitmentPostDAO.getPendingAndRejectedPosts();
            List<Department> departments = recruitmentPostDAO.getDepartments();
            
            List<RecruitmentPost> filteredApprovedPosts = allApprovedPosts;
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                filteredApprovedPosts = new java.util.ArrayList<>();
                String keyword = searchKeyword.trim().toLowerCase();
                for (RecruitmentPost post : allApprovedPosts) {
                    if (post.getTitle().toLowerCase().contains(keyword) ||
                        (post.getDepartment() != null && post.getDepartment().getDepName().toLowerCase().contains(keyword))) {
                        filteredApprovedPosts.add(post);
                    }
                }
            }
            
            List<RecruitmentPost> filteredPendingAndRejected = allPendingAndRejectedPosts;
            if (notifSearchKeyword != null && !notifSearchKeyword.trim().isEmpty()) {
                filteredPendingAndRejected = new java.util.ArrayList<>();
                String keyword = notifSearchKeyword.trim().toLowerCase();
                for (RecruitmentPost post : allPendingAndRejectedPosts) {
                    if (post.getTitle().toLowerCase().contains(keyword) ||
                        (post.getDepartment() != null && post.getDepartment().getDepName().toLowerCase().contains(keyword))) {
                        filteredPendingAndRejected.add(post);
                    }
                }
            }
            
            // Apply status filter
            if (notifStatusFilter != null && !notifStatusFilter.trim().isEmpty()) {
                List<RecruitmentPost> statusFiltered = new java.util.ArrayList<>();
                for (RecruitmentPost post : filteredPendingAndRejected) {
                    if (post.getStatus().equalsIgnoreCase(notifStatusFilter.trim())) {
                        statusFiltered.add(post);
                    }
                }
                filteredPendingAndRejected = statusFiltered;
            }
            
            int totalPosts = (filteredApprovedPosts != null) ? filteredApprovedPosts.size() : 0;
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
            if (totalPages < 1) totalPages = 1;
            if (currentPage > totalPages) currentPage = totalPages;
            
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalPosts);
            List<RecruitmentPost> approvedPosts = new java.util.ArrayList<>();
            if (filteredApprovedPosts != null && !filteredApprovedPosts.isEmpty()) {
                approvedPosts = filteredApprovedPosts.subList(startIndex, endIndex);
            }
            
            int totalNotifPosts = (filteredPendingAndRejected != null) ? filteredPendingAndRejected.size() : 0;
            int totalNotifPages = (int) Math.ceil((double) totalNotifPosts / notifPageSize);
            if (totalNotifPages < 1) totalNotifPages = 1;
            if (notifCurrentPage > totalNotifPages) notifCurrentPage = totalNotifPages;
            
            int notifStartIndex = (notifCurrentPage - 1) * notifPageSize;
            int notifEndIndex = Math.min(notifStartIndex + notifPageSize, totalNotifPosts);
            
            List<RecruitmentPost> pendingAndRejectedPosts = new java.util.ArrayList<>();
            if (filteredPendingAndRejected != null && !filteredPendingAndRejected.isEmpty()) {
                pendingAndRejectedPosts = filteredPendingAndRejected.subList(notifStartIndex, notifEndIndex);
            }
            
            boolean hasApprovedPosts = (approvedPosts != null && !approvedPosts.isEmpty());
            boolean hasPendingOrRejected = (totalNotifPosts > 0);
            boolean hasDepartments = (departments != null && !departments.isEmpty());
            
            request.setAttribute("approvedPosts", approvedPosts);
            request.setAttribute("pendingAndRejectedPosts", pendingAndRejectedPosts);
            request.setAttribute("departments", departments);
            request.setAttribute("totalPosts", totalPosts);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("searchKeyword", searchKeyword != null ? searchKeyword : "");
            request.setAttribute("totalNotifPosts", totalNotifPosts);
            request.setAttribute("notifCurrentPage", notifCurrentPage);
            request.setAttribute("notifTotalPages", totalNotifPages);
            request.setAttribute("notifPageSize", notifPageSize);
            request.setAttribute("notifSearchKeyword", notifSearchKeyword != null ? notifSearchKeyword : "");
            request.setAttribute("notifStatusFilter", notifStatusFilter != null ? notifStatusFilter : "");
            request.setAttribute("hasApprovedPosts", hasApprovedPosts);
            request.setAttribute("hasPendingOrRejected", hasPendingOrRejected);
            request.setAttribute("hasDepartments", hasDepartments);
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
            
            if (depId == null || depId.trim().isEmpty() || depId.equals("")) {
                validationErrors.append("Please select a department. ");
            }
            
            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/hrrecruitment");
                return;
            }
            
            int createdBy = 1;
            boolean success = recruitmentPostDAO.createPost(title.trim(), content.trim(), depId.trim(), createdBy);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post created successfully! Status is now 'New'.");
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
                
                if (post != null && ("Rejected".equals(post.getStatus()) || "New".equals(post.getStatus()))) {
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
                    request.setAttribute("pageTitle", "Edit Post");
                    request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Post not found or cannot be edited.");
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
            
            if (depId == null || depId.trim().isEmpty() || depId.equals("")) {
                validationErrors.append("Please select a department. ");
            }
            
            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/hrrecruitment");
                return;
            }
            
            int postId = Integer.parseInt(postIdStr.trim());
            boolean success = recruitmentPostDAO.updatePost(postId, title.trim(), content.trim(), depId.trim());
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post updated successfully! Status is now 'New'.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update post. Please ensure the post status is 'New' or 'Rejected'.");
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

    private void sendPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            
            if (postIdStr == null || postIdStr.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Post ID is required.");
                response.sendRedirect(request.getContextPath() + "/hrrecruitment");
                return;
            }
            
            int postId = Integer.parseInt(postIdStr.trim());
            boolean success = recruitmentPostDAO.sendPost(postId);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post sent successfully! Status changed to 'Waiting' for approval.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to send post. Please ensure the post status is 'New'.");
            }
            
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
        } catch (Exception e) {
            System.err.println("Error in sendPost: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to send post. Please try again.");
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
