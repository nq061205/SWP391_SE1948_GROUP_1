package controller.hr;

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

public class HRRecruitmentServlet extends HttpServlet {

    private RecruitmentPostDAO recruitmentPostDAO;


    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_NOTIF_PAGE_SIZE = 5;
    private static final int MIN_PAGE_SIZE = 5;
    private static final int MAX_PAGE_SIZE = 100;
    private static final int MAX_NOTIF_PAGE_SIZE = 50;

    @Override
    public void init() throws ServletException {
        super.init();
        recruitmentPostDAO = new RecruitmentPostDAO();
    }


    private int parsePageNumber(String pageStr, int defaultPage) {
        if (pageStr == null || pageStr.trim().isEmpty()) {
            return defaultPage;
        }
        try {
            int page = Integer.parseInt(pageStr.trim());
            return (page < 1) ? 1 : page;
        } catch (NumberFormatException e) {
            return defaultPage;
        }
    }


    private int parsePageSize(String pageSizeStr, int defaultSize, int minSize, int maxSize) {
        if (pageSizeStr == null || pageSizeStr.trim().isEmpty()) {
            return defaultSize;
        }
        try {
            int size = Integer.parseInt(pageSizeStr.trim());
            if (size < minSize) return minSize;
            if (size > maxSize) return maxSize;
            return size;
        } catch (NumberFormatException e) {
            return defaultSize;
        }
    }


    private List<RecruitmentPost> filterBySearch(List<RecruitmentPost> posts, String keyword) {
        if (keyword == null || keyword.trim().isEmpty() || posts == null) {
            return posts;
        }

        List<RecruitmentPost> filtered = new ArrayList<>();
        String searchTerm = keyword.trim().toLowerCase();

        for (RecruitmentPost post : posts) {
            boolean matchesTitle = post.getTitle() != null &&
                                  post.getTitle().toLowerCase().contains(searchTerm);
            boolean matchesDept = post.getDepartment() != null &&
                                 post.getDepartment().getDepName() != null &&
                                 post.getDepartment().getDepName().toLowerCase().contains(searchTerm);

            if (matchesTitle || matchesDept) {
                filtered.add(post);
            }
        }
        return filtered;
    }


    private List<RecruitmentPost> filterByDateRange(List<RecruitmentPost> posts,
                                                    String fromDateStr,
                                                    String toDateStr,
                                                    boolean useApprovedDate) {
        if (fromDateStr == null || fromDateStr.trim().isEmpty() ||
            toDateStr == null || toDateStr.trim().isEmpty() || posts == null) {
            return posts;
        }

        List<RecruitmentPost> filtered = new ArrayList<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(fromDateStr.trim());
            Date endDate = sdf.parse(toDateStr.trim());


            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            endDate = cal.getTime();

            for (RecruitmentPost post : posts) {
                Timestamp timestamp = useApprovedDate ? post.getApprovedAt() : post.getCreatedAt();

                if (timestamp != null) {
                    Date postDate = new Date(timestamp.getTime());
                    if (!postDate.before(startDate) && !postDate.after(endDate)) {
                        filtered.add(post);
                    }
                }
            }
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return posts;
        }

        return filtered;
    }


    private List<RecruitmentPost> filterByStatus(List<RecruitmentPost> posts, String status) {
        if (status == null || status.trim().isEmpty() || posts == null) {
            return posts;
        }

        List<RecruitmentPost> filtered = new ArrayList<>();
        String statusFilter = status.trim();

        for (RecruitmentPost post : posts) {
            if (statusFilter.equalsIgnoreCase(post.getStatus())) {
                filtered.add(post);
            }
        }
        return filtered;
    }


    private List<RecruitmentPost> filterByDepartment(List<RecruitmentPost> posts, String depId) {
        if (depId == null || depId.trim().isEmpty() || posts == null) {
            return posts;
        }

        List<RecruitmentPost> filtered = new ArrayList<>();
        String departmentId = depId.trim();

        for (RecruitmentPost post : posts) {
            if (post.getDepartment() != null && departmentId.equals(post.getDepartment().getDepId())) {
                filtered.add(post);
            }
        }
        return filtered;
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

            transferSessionMessage(request, "successMessage");
            transferSessionMessage(request, "errorMessage");


            int currentPage = parsePageNumber(request.getParameter("page"), 1);
            int pageSize = parsePageSize(request.getParameter("pageSize"), DEFAULT_PAGE_SIZE, MIN_PAGE_SIZE, MAX_PAGE_SIZE);
            int notifCurrentPage = parsePageNumber(request.getParameter("notifPage"), 1);
            int notifPageSize = parsePageSize(request.getParameter("notifPageSize"), DEFAULT_NOTIF_PAGE_SIZE, MIN_PAGE_SIZE, MAX_NOTIF_PAGE_SIZE);


            String searchKeyword = request.getParameter("search");
            String notifSearchKeyword = request.getParameter("notifSearch");
            String notifStatusFilter = request.getParameter("notifStatus");
            String depIdFilter = request.getParameter("depId");
            String notifDepIdFilter = request.getParameter("notifDepId");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String notifFromDate = request.getParameter("notifFromDate");
            String notifToDate = request.getParameter("notifToDate");


            List<RecruitmentPost> allApprovedPosts = recruitmentPostDAO.getApprovedPosts();
            List<RecruitmentPost> allPendingAndRejectedPosts = recruitmentPostDAO.getPendingAndRejectedPosts();
            List<Department> departments = recruitmentPostDAO.getDepartments();


            List<RecruitmentPost> filteredApprovedPosts = filterBySearch(allApprovedPosts, searchKeyword);
            filteredApprovedPosts = filterByDepartment(filteredApprovedPosts, depIdFilter);
            filteredApprovedPosts = filterByDateRange(filteredApprovedPosts, fromDate, toDate, true);


            List<RecruitmentPost> filteredPendingAndRejected = filterBySearch(allPendingAndRejectedPosts, notifSearchKeyword);
            filteredPendingAndRejected = filterByDepartment(filteredPendingAndRejected, notifDepIdFilter);
            filteredPendingAndRejected = filterByStatus(filteredPendingAndRejected, notifStatusFilter);
            filteredPendingAndRejected = filterByDateRange(filteredPendingAndRejected, notifFromDate, notifToDate, false);


            int totalPosts = (filteredApprovedPosts != null) ? filteredApprovedPosts.size() : 0;
            int totalPages = Math.max(1, (int) Math.ceil((double) totalPosts / pageSize));
            currentPage = Math.min(currentPage, totalPages);

            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalPosts);
            List<RecruitmentPost> approvedPosts = (filteredApprovedPosts != null && !filteredApprovedPosts.isEmpty())
                ? filteredApprovedPosts.subList(startIndex, endIndex)
                : new ArrayList<>();


            int totalNotifPosts = (filteredPendingAndRejected != null) ? filteredPendingAndRejected.size() : 0;
            int totalNotifPages = Math.max(1, (int) Math.ceil((double) totalNotifPosts / notifPageSize));
            notifCurrentPage = Math.min(notifCurrentPage, totalNotifPages);

            int notifStartIndex = (notifCurrentPage - 1) * notifPageSize;
            int notifEndIndex = Math.min(notifStartIndex + notifPageSize, totalNotifPosts);
            List<RecruitmentPost> pendingAndRejectedPosts = (filteredPendingAndRejected != null && !filteredPendingAndRejected.isEmpty())
                ? filteredPendingAndRejected.subList(notifStartIndex, notifEndIndex)
                : new ArrayList<>();


            setListAttributes(request, approvedPosts, pendingAndRejectedPosts, departments);
            setPaginationAttributes(request, currentPage, totalPages, pageSize, totalPosts,
                                   notifCurrentPage, totalNotifPages, notifPageSize, totalNotifPosts);
            setFilterAttributes(request, searchKeyword, notifSearchKeyword, notifStatusFilter,
                              depIdFilter, notifDepIdFilter, fromDate, toDate, notifFromDate, notifToDate);


            int notifStartDisplay = totalNotifPosts > 0 ? (notifCurrentPage - 1) * notifPageSize + 1 : 0;
            int notifEndDisplay = (notifCurrentPage - 1) * notifPageSize + pendingAndRejectedPosts.size();
            int approvedStartDisplay = totalPosts > 0 ? (currentPage - 1) * pageSize + 1 : 0;
            int approvedEndDisplay = (currentPage - 1) * pageSize + approvedPosts.size();

            request.setAttribute("notifStartDisplay", notifStartDisplay);
            request.setAttribute("notifEndDisplay", notifEndDisplay);
            request.setAttribute("approvedStartDisplay", approvedStartDisplay);
            request.setAttribute("approvedEndDisplay", approvedEndDisplay);
            request.setAttribute("notifStartIndex", (notifCurrentPage - 1) * notifPageSize);
            request.setAttribute("approvedStartIndex", (currentPage - 1) * pageSize);

            request.setAttribute("hasApprovedPosts", !approvedPosts.isEmpty());
            request.setAttribute("hasPendingOrRejected", totalNotifPosts > 0);
            request.setAttribute("hasDepartments", departments != null && !departments.isEmpty());
            request.setAttribute("pageTitle", "Approved Posts List");

            request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error in showApprovedPostsList: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load recruitment posts. Please try again.");
            request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);
        }
    }


    private void transferSessionMessage(HttpServletRequest request, String attributeName) {
        String message = (String) request.getSession().getAttribute(attributeName);
        if (message != null) {
            request.setAttribute(attributeName, message);
            request.getSession().removeAttribute(attributeName);
        }
    }


    private void setListAttributes(HttpServletRequest request,
                                   List<RecruitmentPost> approvedPosts,
                                   List<RecruitmentPost> pendingPosts,
                                   List<Department> departments) {
        request.setAttribute("approvedPosts", approvedPosts);
        request.setAttribute("pendingAndRejectedPosts", pendingPosts);
        request.setAttribute("departments", departments);
    }


    private void setPaginationAttributes(HttpServletRequest request,
                                        int currentPage, int totalPages, int pageSize, int totalPosts,
                                        int notifCurrentPage, int notifTotalPages, int notifPageSize, int totalNotifPosts) {
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPosts", totalPosts);
        request.setAttribute("notifCurrentPage", notifCurrentPage);
        request.setAttribute("notifTotalPages", notifTotalPages);
        request.setAttribute("notifPageSize", notifPageSize);
        request.setAttribute("totalNotifPosts", totalNotifPosts);
    }


    private void setFilterAttributes(HttpServletRequest request,
                                     String searchKeyword, String notifSearchKeyword, String notifStatusFilter,
                                     String depIdFilter, String notifDepIdFilter,
                                     String fromDate, String toDate, String notifFromDate, String notifToDate) {
        request.setAttribute("searchKeyword", searchKeyword != null ? searchKeyword : "");
        request.setAttribute("notifSearchKeyword", notifSearchKeyword != null ? notifSearchKeyword : "");
        request.setAttribute("notifStatusFilter", notifStatusFilter != null ? notifStatusFilter : "");
        request.setAttribute("depIdFilter", depIdFilter != null ? depIdFilter : "");
        request.setAttribute("notifDepIdFilter", notifDepIdFilter != null ? notifDepIdFilter : "");
        request.setAttribute("fromDate", fromDate != null ? fromDate : "");
        request.setAttribute("toDate", toDate != null ? toDate : "");
        request.setAttribute("notifFromDate", notifFromDate != null ? notifFromDate : "");
        request.setAttribute("notifToDate", notifToDate != null ? notifToDate : "");


        String contextPath = request.getContextPath();
        request.setAttribute("baseUrl", contextPath + "/hrrecruitment?action=list");
        request.setAttribute("notifParams", buildNotifParams(notifSearchKeyword, notifStatusFilter, notifDepIdFilter, notifFromDate, notifToDate, request.getAttribute("notifPageSize")));
        request.setAttribute("approvedParams", buildApprovedParams(searchKeyword, depIdFilter, fromDate, toDate, request.getAttribute("pageSize"), request.getAttribute("currentPage")));
        request.setAttribute("approvedPostParams", buildApprovedPostParams(searchKeyword, depIdFilter, fromDate, toDate, request.getAttribute("pageSize")));
        request.setAttribute("notifPostParams", buildNotifPostParams(notifSearchKeyword, notifDepIdFilter, request.getAttribute("notifPageSize"), request.getAttribute("notifCurrentPage")));


        request.setAttribute("notifSearchClearUrl", contextPath + "/hrrecruitment?action=list&" + buildNotifParams("", notifStatusFilter, notifDepIdFilter, notifFromDate, notifToDate, request.getAttribute("notifPageSize")) + buildApprovedParams(searchKeyword, depIdFilter, fromDate, toDate, request.getAttribute("pageSize"), request.getAttribute("currentPage")));
        request.setAttribute("notifDateClearUrl", contextPath + "/hrrecruitment?action=list&notifPageSize=" + request.getAttribute("notifPageSize") + (notifSearchKeyword != null && !notifSearchKeyword.isEmpty() ? "&notifSearch=" + notifSearchKeyword : "") + (notifStatusFilter != null && !notifStatusFilter.isEmpty() ? "&notifStatus=" + notifStatusFilter : "") + (notifDepIdFilter != null && !notifDepIdFilter.isEmpty() ? "&notifDepId=" + notifDepIdFilter : "") + buildApprovedParams(searchKeyword, depIdFilter, fromDate, toDate, request.getAttribute("pageSize"), request.getAttribute("currentPage")));
        request.setAttribute("approvedSearchClearUrl", contextPath + "/hrrecruitment?action=list&pageSize=" + request.getAttribute("pageSize") + (depIdFilter != null && !depIdFilter.isEmpty() ? "&depId=" + depIdFilter : "") + buildNotifPostParams(notifSearchKeyword, notifDepIdFilter, request.getAttribute("notifPageSize"), request.getAttribute("notifCurrentPage")) + (fromDate != null && !fromDate.isEmpty() ? "&fromDate=" + fromDate : "") + (toDate != null && !toDate.isEmpty() ? "&toDate=" + toDate : ""));
        request.setAttribute("approvedDateClearUrl", contextPath + "/hrrecruitment?action=list&pageSize=" + request.getAttribute("pageSize") + (searchKeyword != null && !searchKeyword.isEmpty() ? "&search=" + searchKeyword : "") + (depIdFilter != null && !depIdFilter.isEmpty() ? "&depId=" + depIdFilter : "") + buildNotifPostParams(notifSearchKeyword, notifDepIdFilter, request.getAttribute("notifPageSize"), request.getAttribute("notifCurrentPage")));
    }


    private String buildNotifParams(String notifSearchKeyword, String notifStatusFilter, String notifDepIdFilter,
                                    String notifFromDate, String notifToDate, Object notifPageSize) {
        StringBuilder params = new StringBuilder();
        params.append("notifPageSize=").append(notifPageSize);

        if (notifSearchKeyword != null && !notifSearchKeyword.isEmpty()) {
            params.append("&notifSearch=").append(notifSearchKeyword);
        }
        if (notifStatusFilter != null && !notifStatusFilter.isEmpty()) {
            params.append("&notifStatus=").append(notifStatusFilter);
        }
        if (notifDepIdFilter != null && !notifDepIdFilter.isEmpty()) {
            params.append("&notifDepId=").append(notifDepIdFilter);
        }
        if (notifFromDate != null && !notifFromDate.isEmpty()) {
            params.append("&notifFromDate=").append(notifFromDate);
        }
        if (notifToDate != null && !notifToDate.isEmpty()) {
            params.append("&notifToDate=").append(notifToDate);
        }

        return params.toString();
    }


    private String buildApprovedParams(String searchKeyword, String depIdFilter, String fromDate, String toDate,
                                       Object pageSize, Object currentPage) {
        StringBuilder params = new StringBuilder();

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            params.append("&search=").append(searchKeyword);
        }
        if (depIdFilter != null && !depIdFilter.isEmpty()) {
            params.append("&depId=").append(depIdFilter);
        }
        if (pageSize != null) {
            params.append("&pageSize=").append(pageSize);
        }
        if (currentPage != null) {
            params.append("&page=").append(currentPage);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            params.append("&fromDate=").append(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            params.append("&toDate=").append(toDate);
        }

        return params.toString();
    }


    private String buildApprovedPostParams(String searchKeyword, String depIdFilter, String fromDate, String toDate, Object pageSize) {
        StringBuilder params = new StringBuilder();

        if (pageSize != null) {
            params.append("pageSize=").append(pageSize);
        }
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            params.append("&search=").append(searchKeyword);
        }
        if (depIdFilter != null && !depIdFilter.isEmpty()) {
            params.append("&depId=").append(depIdFilter);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            params.append("&fromDate=").append(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            params.append("&toDate=").append(toDate);
        }

        return params.toString();
    }


    private String buildNotifPostParams(String notifSearchKeyword, String notifDepIdFilter, Object notifPageSize, Object notifCurrentPage) {
        StringBuilder params = new StringBuilder();

        if (notifSearchKeyword != null && !notifSearchKeyword.isEmpty()) {
            params.append("&notifSearch=").append(notifSearchKeyword);
        }
        if (notifDepIdFilter != null && !notifDepIdFilter.isEmpty()) {
            params.append("&notifDepId=").append(notifDepIdFilter);
        }
        if (notifPageSize != null) {
            params.append("&notifPageSize=").append(notifPageSize);
        }
        if (notifCurrentPage != null) {
            params.append("&notifPage=").append(notifCurrentPage);
        }

        return params.toString();
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
                    int totalNotifPosts = (pendingAndRejectedPosts != null) ? pendingAndRejectedPosts.size() : 0;
                    boolean hasApprovedPosts = (approvedPosts != null && !approvedPosts.isEmpty());
                    boolean hasPendingOrRejected = (pendingAndRejectedPosts != null && !pendingAndRejectedPosts.isEmpty());
                    boolean hasDepartments = (departments != null && !departments.isEmpty());


                    request.setAttribute("editPost", post);
                    request.setAttribute("departments", departments);
                    request.setAttribute("approvedPosts", approvedPosts != null ? approvedPosts : new java.util.ArrayList<>());
                    request.setAttribute("pendingAndRejectedPosts", pendingAndRejectedPosts != null ? pendingAndRejectedPosts : new java.util.ArrayList<>());
                    request.setAttribute("totalPosts", totalPosts);
                    request.setAttribute("totalNotifPosts", totalNotifPosts);
                    request.setAttribute("currentPage", 1);
                    request.setAttribute("totalPages", 1);
                    request.setAttribute("pageSize", 10);
                    request.setAttribute("notifCurrentPage", 1);
                    request.setAttribute("notifTotalPages", 1);
                    request.setAttribute("notifPageSize", 5);
                    request.setAttribute("searchKeyword", "");
                    request.setAttribute("notifSearchKeyword", "");
                    request.setAttribute("notifStatusFilter", "");
                    request.setAttribute("notifFromDate", "");
                    request.setAttribute("notifToDate", "");
                    request.setAttribute("fromDate", "");
                    request.setAttribute("toDate", "");
                    request.setAttribute("hasApprovedPosts", hasApprovedPosts);
                    request.setAttribute("hasPendingOrRejected", hasPendingOrRejected);
                    request.setAttribute("hasDepartments", hasDepartments);
                    request.setAttribute("pageTitle", "Edit Post");
                    request.getRequestDispatcher("/Views/HR/recruitmentManagement.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("errorMessage", "Post not found or cannot be edited.");
                    response.sendRedirect(request.getContextPath() + "/hrrecruitment");
                }
            } else {
                request.getSession().setAttribute("errorMessage", "Invalid post ID.");
                response.sendRedirect(request.getContextPath() + "/hrrecruitment");
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid post ID format: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", "Invalid post ID format.");
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
        } catch (Exception e) {
            System.err.println("Error in editPost: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unable to load post for editing. Please try again.");
            response.sendRedirect(request.getContextPath() + "/hrrecruitment");
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
