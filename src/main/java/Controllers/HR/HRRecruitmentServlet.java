


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.HR;

import DAL.RecruitmentPostDAO;
import Models.RecruitmentPost;
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
            // Get approved posts from database
            List<RecruitmentPost> approvedPosts = recruitmentPostDAO.getApprovedPosts();
            
            // Set attributes for JSP
            request.setAttribute("approvedPosts", approvedPosts);
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
