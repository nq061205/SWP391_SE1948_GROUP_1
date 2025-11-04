<%-- 
    Document   : InterviewList
    Modified on: Nov 4, 2025
    Author     : Hoàng Duy
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Interview Management</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Base CSS -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <style>
            .nav-tabs .nav-link.active {
                background-color: #007bff !important;
                color: #fff !important;
            }
            .filter-group {
                display: flex;
                align-items: center;
                gap: 15px;
                flex-wrap: wrap;
            }
            select.form-control {
                max-width: 300px;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <!-- Header + Navbar -->
        <jsp:include page="CommonItems/Header/dashboardHeader.jsp" />
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">

                <!-- Breadcrumb -->
                <div class="db-breadcrumb mb-3">
                    <h4 class="breadcrumb-title">Interview Management</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li>Interview</li>
                        <li>List</li>
                    </ul>
                </div>

                <!-- ✅ Widget Box -->
                <div class="widget-box">
                    <div class="wc-title">
                        <h4><i class="fa fa-calendar"></i> Interview List</h4>
                        <span class="badge badge-primary">
                            Total: ${sessionScope.interviewListFull != null ? sessionScope.interviewListFull.size() : 0}
                        </span>
                    </div>

                    <div class="widget-inner">

                        <!-- ✅ Search + Filter + Create Button -->
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <form action="${pageContext.request.contextPath}/interviewlist" method="get" class="filter-group">
                                <!-- Filter by Recruitment Post -->
                                <div>
                                    <label for="postFilter" class="form-label fw-bold me-2">Filter by Recruitment Post:</label>
                                    <select name="postId" id="postFilter" class="form-control" onchange="this.form.submit()">
                                        <option value="">All Posts</option>
                                        <c:forEach var="rp" items="${requestScope.postList}">
                                            <option value="${rp.postId}" ${param.postId eq rp.postId ? 'selected' : ''}>
                                                ${rp.title}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Search -->
                                <div class="input-group">
                                    <span class="input-group-text bg-white border-end-0">
                                        <i class="fa fa-search text-muted"></i>
                                    </span>
                                    <input type="text" name="keyword" class="form-control border-start-0"
                                           placeholder="Search by candidate name or email..."
                                           value="${param.keyword}">
                                    <div class="input-group-append">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fa fa-search me-1"></i> Search
                                        </button>
                                    </div>
                                </div>
                            </form>

                            <!-- Create Interview Schedule -->
                            <a href="${pageContext.request.contextPath}/schedule" class="btn btn-success">
                                <i class="fa fa-calendar-plus me-1"></i> Create Interview Schedule
                            </a>
                        </div>

                        <!-- ✅ Interview Table -->
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover align-middle text-center">
                                <thead class="thead-dark">
                                    <tr>
                                        <th>#</th>
                                        <th>Candidate</th>
                                        <th>Recruitment Post</th>
                                        <th>Interview Date</th>
                                        <th>Interview Time</th>
                                        <th>Interviewer</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="iv" items="${requestScope.interviewList}" varStatus="st">
                                        <tr>
                                            <td>${(sessionScope.pages - 1) * 5 + st.index + 1}</td>
                                            <td class="text-start">
                                                <strong class="text-primary">${iv.candidate.name}</strong><br>
                                                <small class="text-muted">${iv.candidate.email}</small>
                                            </td>
                                            <td>${iv.candidate.post.title}</td>
                                            <td><fmt:formatDate value="${iv.date}" pattern="MMM dd, yyyy" /></td>
                                            <td><fmt:formatDate value="${iv.time}" pattern="hh:mm a" /></td>
                                            <td>${iv.interviewedBy.fullname}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/interviewdetail?id=${iv.interviewId}"
                                                   class="btn btn-sm btn-info">
                                                    <i class="fa fa-eye"></i> View
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                        <c:if test="${empty requestScope.interviewList}">
                                        <tr>
                                            <td colspan="7" class="text-muted py-3">
                                                <i class="fa fa-inbox fa-2x mb-2"></i><br>
                                                No interview records found.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>

                            <!-- ✅ Pagination -->
                            <div class="d-flex justify-content-between align-items-center mt-4">
                                <div class="text-muted">
                                    Page <strong>${sessionScope.pages}</strong> of <strong>${sessionScope.total}</strong>
                                </div>
                                <nav aria-label="Interview pagination">
                                    <ul class="pagination mb-0">
                                        <li class="page-item ${sessionScope.pages == 1 ? 'disabled' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/interviewlist?page=${sessionScope.pages - 1}&postId=${param.postId}">
                                                <i class="fa fa-chevron-left"></i> Previous
                                            </a>
                                        </li>

                                        <li class="page-item active">
                                            <a class="page-link">${sessionScope.pages}</a>
                                        </li>

                                        <li class="page-item ${sessionScope.pages >= sessionScope.total ? 'disabled' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/interviewlist?page=${sessionScope.pages + 1}&postId=${param.postId}">
                                                Next <i class="fa fa-chevron-right"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </main>

        <!-- JS -->
        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
    </body>
</html>
