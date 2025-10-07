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

/**
 *
 * @author Dat Tran
 */
public class HRRecruitmentServlet extends HttpServlet {

    private RecruitmentPostDAO recruitmentPostDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        recruitmentPostDAO = new RecruitmentPostDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // default action
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
    
    /**
     * Show approved recruitment posts list
     */
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
            
            // Get approved posts from database
            List<RecruitmentPost> approvedPosts = recruitmentPostDAO.getApprovedPosts();
            
            // Get pending and rejected posts for notification table
            List<RecruitmentPost> pendingAndRejectedPosts = recruitmentPostDAO.getPendingAndRejectedPosts();
            
            // Get departments for dropdown
            List<Department> departments = recruitmentPostDAO.getDepartments();
            System.out.println("Servlet - departments size: " + departments.size());
            
            // Set attributes for JSP
            request.setAttribute("approvedPosts", approvedPosts);
            request.setAttribute("pendingAndRejectedPosts", pendingAndRejectedPosts);
            request.setAttribute("departments", departments);
            request.setAttribute("totalPosts", approvedPosts.size());
            
            // Set current page info
            request.setAttribute("currentPage", "Recruitment Management");
            request.setAttribute("pageTitle", "Approved Posts List");
            
            // Forward to JSP
            request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in showApprovedPostsList: " + e.getMessage());
            e.printStackTrace();
            
            // Set error message
            request.setAttribute("errorMessage", "Unable to load recruitment posts. Please try again.");
            request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);
        }
    }
    
    /**
     * View recruitment post detail
     */
    private void viewPostDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            if (postIdStr != null && !postIdStr.trim().isEmpty()) {
                int postId = Integer.parseInt(postIdStr);
                
                // Get post detail from database
                RecruitmentPost post = recruitmentPostDAO.getPostById(postId);
                
                if (post != null) {
                    request.setAttribute("post", post);
                    request.setAttribute("currentPage", "Recruitment Management");
                    request.setAttribute("pageTitle", "Post Detail");
                    
                    // Forward to detail JSP
                    request.getRequestDispatcher("/Views/HR/recruitmentPostDetail.jsp").forward(request, response);
                } else {
                    // Post not found
                    request.setAttribute("errorMessage", "Recruitment post not found.");
                    showApprovedPostsList(request, response);
                }
            } else {
                // Invalid post ID
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
    
    /**
     * Create new recruitment post
     */
    private void createPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String depId = request.getParameter("depId");
            
            // Validate input
            if (title == null || title.trim().isEmpty() ||
                content == null || content.trim().isEmpty() ||
                depId == null || depId.trim().isEmpty()) {
                
                request.getSession().setAttribute("errorMessage", "All fields are required.");
                response.sendRedirect(request.getContextPath() + "/hrrecruitment");
                return;
            }
            
            // Default values as specified (created_by = 1, approved_by = 2)
            int createdBy = 1;
            int approvedBy = 2;
            
            // Create post
            boolean success = recruitmentPostDAO.createPost(title, content, depId, createdBy, approvedBy);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post created successfully! It's now pending approval.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to create post. Please try again.");
            }
            
            // Redirect to list (without editPost)
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
            
        } catch (Exception e) {
            System.err.println("Error in createPost: " + e.getMessage());
            e.printStackTrace();
            
            request.getSession().setAttribute("errorMessage", "Unable to create post. Please try again.");
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
        }
    }
    
    /**
     * Edit post (show edit form for rejected posts)
     */
    private void editPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            if (postIdStr != null && !postIdStr.trim().isEmpty()) {
                int postId = Integer.parseInt(postIdStr);
                
                // Get post detail from database
                RecruitmentPost post = recruitmentPostDAO.getPostById(postId);
                
                if (post != null && "Rejected".equals(post.getStatus())) {
                    // Get departments for dropdown
                    List<Department> departments = recruitmentPostDAO.getDepartments();
                    
                    // Get approved posts for the main table
                    List<RecruitmentPost> approvedPosts = recruitmentPostDAO.getApprovedPosts();
                    
                    // Get pending and rejected posts for notification table
                    List<RecruitmentPost> pendingAndRejectedPosts = recruitmentPostDAO.getPendingAndRejectedPosts();
                    
                    request.setAttribute("editPost", post);
                    request.setAttribute("departments", departments);
                    request.setAttribute("approvedPosts", approvedPosts);
                    request.setAttribute("pendingAndRejectedPosts", pendingAndRejectedPosts);
                    request.setAttribute("totalPosts", approvedPosts.size());
                    request.setAttribute("currentPage", "Recruitment Management");
                    request.setAttribute("pageTitle", "Edit Rejected Post");
                    
                    // Forward to the same JSP with edit form visible
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
    
    /**
     * Update rejected post (change status back to pending)
     */
    private void updatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String depId = request.getParameter("depId");
            
            // Validate input
            if (postIdStr == null || postIdStr.trim().isEmpty() ||
                title == null || title.trim().isEmpty() ||
                content == null || content.trim().isEmpty() ||
                depId == null || depId.trim().isEmpty()) {
                
                request.getSession().setAttribute("errorMessage", "All fields are required.");
                response.sendRedirect(request.getContextPath() + "/hrrecruitment");
                return;
            }
            
            int postId = Integer.parseInt(postIdStr);
            
            // Update post
            boolean success = recruitmentPostDAO.updatePost(postId, title, content, depId);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Post updated successfully! Status changed to pending.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update post. Please try again.");
            }
            
            // Redirect to list (without editPost)
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "HR Recruitment Management Servlet";
    }// </editor-fold>

}
