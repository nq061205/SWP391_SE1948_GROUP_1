package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.RecruitmentPostDAO;
import jakarta.servlet.http.HttpSession;
import model.RecruitmentPost;

public class JobSiteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession ses = request.getSession();
        String s = (String)ses.getAttribute("successMessage");
        if(s != null){
            request.setAttribute("successMessage", s);
            ses.removeAttribute("successMessage");
        }
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

            // Get filter parameters
            String keyword = request.getParameter("keyword");
            String depIdStr = request.getParameter("depId");
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");
            String sortOrder = request.getParameter("sort");

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

            System.out.println("Uploaded posts before filter: " + uploadedPosts.size());

            // Keep original list for counting by department
            List<RecruitmentPost> allUploadedPosts = new ArrayList<>(uploadedPosts);

            // Apply filters
            uploadedPosts = filterPosts(uploadedPosts, keyword, depIdStr, fromDateStr, toDateStr, sortOrder);
            System.out.println("Uploaded posts after filter: " + uploadedPosts.size());


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

            // Get departments for filter dropdown
            List<model.Department> departments = dao.getDepartments();
            
            // Count posts by department from all uploaded posts
            java.util.Map<String, Integer> deptCountMap = new java.util.HashMap<>();
            for (RecruitmentPost post : allUploadedPosts) {
                if (post.getDepartment() != null) {
                    String deptId = post.getDepartment().getDepId();
                    deptCountMap.put(deptId, deptCountMap.getOrDefault(deptId, 0) + 1);
                }
            }

            request.setAttribute("posts", posts);
            request.setAttribute("departments", departments);
            request.setAttribute("deptCountMap", deptCountMap);
            request.setAttribute("allUploadedCount", allUploadedPosts.size());
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

    private List<RecruitmentPost> filterPosts(List<RecruitmentPost> posts, String keyword, 
            String depIdStr, String fromDateStr, String toDateStr, String sortOrder) {
        List<RecruitmentPost> filtered = new ArrayList<>(posts);

        // Filter by keyword (search in title)
        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchTerm = keyword.trim().toLowerCase();
            filtered.removeIf(post -> 
                post.getTitle() == null || !post.getTitle().toLowerCase().contains(searchTerm)
            );
        }

        // Filter by department
        if (depIdStr != null && !depIdStr.trim().isEmpty()) {
            String depId = depIdStr.trim();
            filtered.removeIf(post -> 
                post.getDepartment() == null || !depId.equals(post.getDepartment().getDepId())
            );
        }

        // Filter by date range (using approvedAt)
        if (fromDateStr != null && !fromDateStr.trim().isEmpty() && 
            toDateStr != null && !toDateStr.trim().isEmpty()) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                final java.util.Date fromDate = sdf.parse(fromDateStr);
                java.util.Date toDateTemp = sdf.parse(toDateStr);
                
                // Set toDate to end of day
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(toDateTemp);
                cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
                cal.set(java.util.Calendar.MINUTE, 59);
                cal.set(java.util.Calendar.SECOND, 59);
                final java.util.Date toDate = cal.getTime();

                filtered.removeIf(post -> {
                    if (post.getApprovedAt() == null) return true;
                    java.util.Date postDate = new java.util.Date(post.getApprovedAt().getTime());
                    return postDate.before(fromDate) || postDate.after(toDate);
                });
            } catch (Exception e) {
                System.err.println("Error parsing date range: " + e.getMessage());
            }
        }

        // Sort by date
        if ("asc".equals(sortOrder)) {
            filtered.sort((p1, p2) -> {
                if (p1.getApprovedAt() == null && p2.getApprovedAt() == null) return 0;
                if (p1.getApprovedAt() == null) return 1;
                if (p2.getApprovedAt() == null) return -1;
                return p1.getApprovedAt().compareTo(p2.getApprovedAt());
            });
        } else {
            // Default: desc (newest first)
            filtered.sort((p1, p2) -> {
                if (p1.getApprovedAt() == null && p2.getApprovedAt() == null) return 0;
                if (p1.getApprovedAt() == null) return 1;
                if (p2.getApprovedAt() == null) return -1;
                return p2.getApprovedAt().compareTo(p1.getApprovedAt());
            });
        }

        return filtered;
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
