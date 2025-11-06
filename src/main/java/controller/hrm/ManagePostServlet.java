package controller.hrm;

import dal.RecruitmentPostDAO;
import model.Department;
import model.RecruitmentPost;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ManagePostServlet extends HttpServlet {

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
                showManagePostList(request, response);
                break;
            case "view":
                viewPostDetail(request, response);
                break;
            case "upload":
                uploadPost(request, response);
                break;
            case "delete":
                deletePost(request, response);
                break;
            case "takedown":
                takeDownPost(request, response);
                break;
            default:
                showManagePostList(request, response);
                break;
        }
    }

    private void showManagePostList(HttpServletRequest request, HttpServletResponse response)
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

            int currentPage = 1;
            int pageSize = 10;

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
                    pageSize = 10;
                }
            }


            String searchKeyword = request.getParameter("search");
            String statusFilter = request.getParameter("status");
            String depIdFilter = request.getParameter("depId");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");


            List<RecruitmentPost> allPosts = recruitmentPostDAO.getApprovedUploadedDeletedPosts();
            List<Department> departments = recruitmentPostDAO.getDepartments();


            List<RecruitmentPost> filteredPosts = filterPosts(allPosts, searchKeyword, statusFilter, depIdFilter, fromDate, toDate);


            int totalPosts = filteredPosts != null ? filteredPosts.size() : 0;
            int totalPages = Math.max(1, (int) Math.ceil((double) totalPosts / pageSize));
            if (currentPage > totalPages) currentPage = totalPages;

            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalPosts);
            List<RecruitmentPost> posts = (filteredPosts != null && !filteredPosts.isEmpty())
                ? filteredPosts.subList(startIndex, endIndex)
                : new ArrayList<>();


            int approvedCount = 0;
            int uploadedCount = 0;
            int deletedCount = 0;
            if (allPosts != null) {
                for (RecruitmentPost post : allPosts) {
                    if ("Approved".equals(post.getStatus())) {
                        approvedCount++;
                    } else if ("Uploaded".equals(post.getStatus())) {
                        uploadedCount++;
                    } else if ("Deleted".equals(post.getStatus())) {
                        deletedCount++;
                    }
                }
            }


            request.setAttribute("posts", posts);
            request.setAttribute("departments", departments);
            request.setAttribute("totalPosts", totalPosts);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("searchKeyword", searchKeyword != null ? searchKeyword : "");
            request.setAttribute("statusFilter", statusFilter != null ? statusFilter : "");
            request.setAttribute("depIdFilter", depIdFilter != null ? depIdFilter : "");
            request.setAttribute("fromDate", fromDate != null ? fromDate : "");
            request.setAttribute("toDate", toDate != null ? toDate : "");
            request.setAttribute("approvedCount", approvedCount);
            request.setAttribute("uploadedCount", uploadedCount);
            request.setAttribute("deletedCount", deletedCount);
            request.setAttribute("hasPosts", totalPosts > 0);
            request.setAttribute("hasDepartments", departments != null && !departments.isEmpty());


            String baseUrl = request.getContextPath() + "/managepost?pageSize=" + pageSize;


            StringBuilder searchClearUrl = new StringBuilder(baseUrl);
            if (statusFilter != null && !statusFilter.isEmpty()) {
                searchClearUrl.append("&status=").append(statusFilter);
            }
            if (depIdFilter != null && !depIdFilter.isEmpty()) {
                searchClearUrl.append("&depId=").append(depIdFilter);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                searchClearUrl.append("&fromDate=").append(fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                searchClearUrl.append("&toDate=").append(toDate);
            }
            request.setAttribute("searchClearUrl", searchClearUrl.toString());


            StringBuilder dateClearUrl = new StringBuilder(baseUrl);
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                dateClearUrl.append("&search=").append(searchKeyword);
            }
            if (statusFilter != null && !statusFilter.isEmpty()) {
                dateClearUrl.append("&status=").append(statusFilter);
            }
            if (depIdFilter != null && !depIdFilter.isEmpty()) {
                dateClearUrl.append("&depId=").append(depIdFilter);
            }
            request.setAttribute("dateClearUrl", dateClearUrl.toString());


            StringBuilder paginationUrl = new StringBuilder(baseUrl);
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                paginationUrl.append("&search=").append(searchKeyword);
            }
            if (statusFilter != null && !statusFilter.isEmpty()) {
                paginationUrl.append("&status=").append(statusFilter);
            }
            if (depIdFilter != null && !depIdFilter.isEmpty()) {
                paginationUrl.append("&depId=").append(depIdFilter);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                paginationUrl.append("&fromDate=").append(fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                paginationUrl.append("&toDate=").append(toDate);
            }
            request.setAttribute("paginationBaseUrl", paginationUrl.toString());

            request.setAttribute("pageName", "Manage Posts");
            request.setAttribute("pageTitle", "Manage Recruitment Posts");

            request.getRequestDispatcher("/Views/HRM/ManagePost.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error in showManagePostList: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load recruitment posts. Please try again.");
            request.getRequestDispatcher("/Views/HRM/ManagePost.jsp").forward(request, response);
        }
    }


    private List<RecruitmentPost> filterPosts(List<RecruitmentPost> posts, String searchKeyword,
                                              String statusFilter, String depIdFilter, String fromDate, String toDate) {
        if (posts == null) {
            return new ArrayList<>();
        }

        List<RecruitmentPost> filtered = new ArrayList<>(posts);


        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            String keyword = searchKeyword.trim().toLowerCase();
            filtered = filtered.stream()
                .filter(post ->
                    post.getTitle().toLowerCase().contains(keyword) ||
                    (post.getDepartment() != null && post.getDepartment().getDepName().toLowerCase().contains(keyword))
                )
                .collect(java.util.stream.Collectors.toList());
        }


        if (statusFilter != null && !statusFilter.trim().isEmpty() && !"All".equalsIgnoreCase(statusFilter)) {
            String status = statusFilter.trim();
            filtered = filtered.stream()
                .filter(post -> status.equalsIgnoreCase(post.getStatus()))
                .collect(java.util.stream.Collectors.toList());
        }


        if (depIdFilter != null && !depIdFilter.trim().isEmpty()) {
            String depId = depIdFilter.trim();
            filtered = filtered.stream()
                .filter(post -> post.getDepartment() != null && depId.equals(post.getDepartment().getDepId()))
                .collect(java.util.stream.Collectors.toList());
        }


        if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = sdf.parse(fromDate.trim());
                Date endDate = sdf.parse(toDate.trim());


                Calendar cal = Calendar.getInstance();
                cal.setTime(endDate);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                endDate = cal.getTime();

                final Date finalStartDate = startDate;
                final Date finalEndDate = endDate;

                filtered = filtered.stream()
                    .filter(post -> {
                        Timestamp timestamp = post.getApprovedAt();
                        if (timestamp == null) return false;
                        Date postDate = new Date(timestamp.getTime());
                        return !postDate.before(finalStartDate) && !postDate.after(finalEndDate);
                    })
                    .collect(java.util.stream.Collectors.toList());
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
        }

        return filtered;
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
                    boolean isApproved = "Approved".equals(post.getStatus());
                    boolean isUploaded = "Uploaded".equals(post.getStatus());
                    boolean isDeleted = "Deleted".equals(post.getStatus());

                    request.setAttribute("post", post);
                    request.setAttribute("hasPost", hasPost);
                    request.setAttribute("hasContent", hasContent);
                    request.setAttribute("hasDepartment", hasDepartment);
                    request.setAttribute("hasCreatedBy", hasCreatedBy);
                    request.setAttribute("hasApprovedBy", hasApprovedBy);
                    request.setAttribute("hasCreatedAt", hasCreatedAt);
                    request.setAttribute("hasApprovedAt", hasApprovedAt);
                    request.setAttribute("hasUpdatedAt", hasUpdatedAt);
                    request.setAttribute("isApproved", isApproved);
                    request.setAttribute("isUploaded", isUploaded);
                    request.setAttribute("isDeleted", isDeleted);

                    request.getRequestDispatcher("/Views/HRM/PostReviewDetail.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("errorMessage", "Post not found.");
                    response.sendRedirect(request.getContextPath() + "/managepost");
                }
            } else {
                request.getSession().setAttribute("errorMessage", "Invalid post ID.");
                response.sendRedirect(request.getContextPath() + "/managepost");
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/managepost");
        } catch (Exception e) {
            System.err.println("Error in viewPostDetail: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to load post detail. Please try again.");
            response.sendRedirect(request.getContextPath() + "/managepost");
        }
    }

    private void uploadPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            StringBuilder validationErrors = new StringBuilder();

            if (postIdStr == null || postIdStr.trim().isEmpty()) {
                validationErrors.append("Post ID is required. ");
            }

            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            int postId = Integer.parseInt(postIdStr.trim());
            RecruitmentPost post = recruitmentPostDAO.getPostById(postId);

            if (post == null) {
                request.getSession().setAttribute("errorMessage", "Post not found.");
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            if (!"Approved".equals(post.getStatus())) {
                request.getSession().setAttribute("errorMessage", "Only approved posts can be uploaded.");
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            boolean success = recruitmentPostDAO.uploadPost(postId);

            if (success) {
                request.getSession().setAttribute("successMessage", "Post '" + post.getTitle() + "' uploaded successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to upload post. Please try again.");
            }

            response.sendRedirect(request.getContextPath() + "/managepost");

        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/managepost");
        } catch (Exception e) {
            System.err.println("Error in uploadPost: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to upload post. Please try again.");
            response.sendRedirect(request.getContextPath() + "/managepost");
        }
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            StringBuilder validationErrors = new StringBuilder();

            if (postIdStr == null || postIdStr.trim().isEmpty()) {
                validationErrors.append("Post ID is required. ");
            }

            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            int postId = Integer.parseInt(postIdStr.trim());
            RecruitmentPost post = recruitmentPostDAO.getPostById(postId);

            if (post == null) {
                request.getSession().setAttribute("errorMessage", "Post not found.");
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            if (!"Approved".equals(post.getStatus())) {
                request.getSession().setAttribute("errorMessage", "Only approved posts can be deleted.");
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            boolean success = recruitmentPostDAO.deletePost(postId);

            if (success) {
                request.getSession().setAttribute("successMessage", "Post '" + post.getTitle() + "' deleted successfully.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to delete post. Please try again.");
            }

            response.sendRedirect(request.getContextPath() + "/managepost");

        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/managepost");
        } catch (Exception e) {
            System.err.println("Error in deletePost: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to delete post. Please try again.");
            response.sendRedirect(request.getContextPath() + "/managepost");
        }
    }

    private void takeDownPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String postIdStr = request.getParameter("postId");
            StringBuilder validationErrors = new StringBuilder();

            if (postIdStr == null || postIdStr.trim().isEmpty()) {
                validationErrors.append("Post ID is required. ");
            }

            if (validationErrors.length() > 0) {
                request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            int postId = Integer.parseInt(postIdStr.trim());
            RecruitmentPost post = recruitmentPostDAO.getPostById(postId);

            if (post == null) {
                request.getSession().setAttribute("errorMessage", "Post not found.");
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            if (!"Uploaded".equals(post.getStatus())) {
                request.getSession().setAttribute("errorMessage", "Only uploaded posts can be taken down.");
                response.sendRedirect(request.getContextPath() + "/managepost");
                return;
            }

            boolean success = recruitmentPostDAO.takeDownPost(postId);

            if (success) {
                request.getSession().setAttribute("successMessage", "Post '" + post.getTitle() + "' taken down successfully.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to take down post. Please try again.");
            }

            response.sendRedirect(request.getContextPath() + "/managepost");

        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/managepost");
        } catch (Exception e) {
            System.err.println("Error in takeDownPost: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to take down post. Please try again.");
            response.sendRedirect(request.getContextPath() + "/managepost");
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
        return "HRM Manager Manage Posts Servlet";
    }

}
