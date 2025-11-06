<%-- 
    Document   : InterviewList
    Modified on: Nov 6, 2025
    Author     : Hoàng Duy
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>
         <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />

        <!-- DESCRIPTION -->
        <meta name="description" content="Human Tech" />

        <!-- OG -->
        <meta property="og:title" content="Human Tech" />
        <meta property="og:description" content="Profile" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Interview Management</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/assets2/js/html5shiv.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/respond.min.js"></script>
        <![endif]-->

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
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

                        <!-- ✅ Filter Form -->
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <form action="${pageContext.request.contextPath}/interview" method="post" class="filter-group">
                                <div>
                                    <label for="postFilter" class="form-label fw-bold me-2">Filter by Recruitment Post:</label>
                                    <select name="postId" id="postFilter" class="form-control" onchange="this.form.submit()">
                                        <option value="all" 
                                                <c:if test="${requestScope.postId eq 'all'}">selected</c:if>>All Posts</option>
                                        <c:forEach var="rp" items="${requestScope.postList}">
                                            <option value="${rp.postId}" 
                                                    <c:if test="${requestScope.postId ne 'all' and requestScope.postId == (rp.postId).toString()}">
                                                        selected
                                                    </c:if>>
                                                ${rp.title}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div>
                                    <label class="form-label fw-bold me-2">Date From</label>
                                    <input type="date" value="${requestScope.dateFrom}" id="dateFrom" name="dateFrom" class="form-control">
                                </div>

                                <div>
                                    <label class="form-label fw-bold me-2">Date To</label>
                                    <input type="date" value="${requestScope.dateTo}" id="dateTo" name="dateTo" class="form-control">
                                </div>

                                <div>
                                    <p style="color: red">${requestScope.errorDateMessage}</p>
                                </div>

                                <div class="input-group">
                                    <span class="input-group-text bg-white border-end-0">
                                        <i class="fa fa-search text-muted"></i>
                                    </span>
                                    <input type="text" name="keyword" class="form-control border-start-0"
                                           placeholder="Search by candidate name or email..."
                                           value="${requestScope.keyword}">
                                    <div class="input-group-append">
                                        <button type="submit" name="action" value="search" class="btn btn-primary">
                                            <i class="fa fa-search me-1"></i> Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <!-- ✅ Interview Table -->
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover align-middle text-center">
                                <<thead class="thead-dark">
                                    <tr>
                                        <th>#</th>

                                        <!-- ✅ Sort by Candidate -->
                                        <th style="cursor:pointer;"
                                            onclick="window.location = '${pageContext.request.contextPath}/interview?type=name&order=${requestScope.order == 'asc' && requestScope.type == 'name' ? 'desc' : 'asc'}&page=${sessionScope.pages}&postId=${requestScope.postId != null ? requestScope.postId : 'all'}&keyword=${fn:escapeXml(requestScope.keyword != null ? requestScope.keyword : '')}&dateFrom=${requestScope.dateFrom != null ? requestScope.dateFrom : ''}&dateTo=${requestScope.dateTo != null ? requestScope.dateTo : ''}'">
                                            Candidate
                                            <i class="fa
                                               <c:choose>
                                                   <c:when test="${requestScope.type == 'name' && requestScope.order == 'asc'}">fa-sort-up</c:when>
                                                   <c:when test="${requestScope.type == 'name' && requestScope.order == 'desc'}">fa-sort-down</c:when>
                                                   <c:otherwise>fa-sort</c:otherwise>
                                               </c:choose>"></i>
                                        </th>

                                        <th>Recruitment Post</th>

                                        <!-- ✅ Sort by Interview Date -->
                                        <th style="cursor:pointer;"
                                            onclick="window.location = '${pageContext.request.contextPath}/interview?type=date&order=${requestScope.order == 'asc' && requestScope.type == 'date' ? 'desc' : 'asc'}&page=${sessionScope.pages}&postId=${requestScope.postId != null ? requestScope.postId : 'all'}&keyword=${fn:escapeXml(requestScope.keyword != null ? requestScope.keyword : '')}&dateFrom=${requestScope.dateFrom != null ? requestScope.dateFrom : ''}&dateTo=${requestScope.dateTo != null ? requestScope.dateTo : ''}'">
                                            Interview Date
                                            <i class="fa
                                               <c:choose>
                                                   <c:when test="${requestScope.type == 'date' && requestScope.order == 'asc'}">fa-sort-up</c:when>
                                                   <c:when test="${requestScope.type == 'date' && requestScope.order == 'desc'}">fa-sort-down</c:when>
                                                   <c:otherwise>fa-sort</c:otherwise>
                                               </c:choose>"></i>
                                        </th>

                                        <th>Interview Time</th>
                                        <th>Interviewer</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>


                                <tbody>
                                    <c:forEach var="iv" items="${sessionScope.interviewList}" varStatus="st">
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

                                    <c:if test="${empty sessionScope.interviewList}">
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
                                               href="${pageContext.request.contextPath}/interview?page=${sessionScope.pages - 1}&type=${requestScope.type}&order=${requestScope.order}&postId=${requestScope.postId}&keyword=${requestScope.keyword}&dateFrom=${requestScope.dateFrom}&dateTo=${requestScope.dateTo}">
                                                <i class="fa fa-chevron-left"></i> Previous
                                            </a>
                                        </li>

                                        <li class="page-item active">
                                            <a class="page-link">${sessionScope.pages}</a>
                                        </li>

                                        <li class="page-item ${sessionScope.pages >= sessionScope.total ? 'disabled' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/interview?page=${sessionScope.pages + 1}&type=${requestScope.type}&order=${requestScope.order}&postId=${requestScope.postId}&keyword=${requestScope.keyword}&dateFrom=${requestScope.dateFrom}&dateTo=${requestScope.dateTo}">
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

        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/counter/waypoints-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/counter/counterup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/masonry/masonry.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/masonry/filter.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js"></script>
    </body>
</html>
