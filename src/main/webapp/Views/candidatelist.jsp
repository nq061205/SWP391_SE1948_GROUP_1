<%-- 
    Document   : candidatelist
    Created on : Oct 8, 2025
    Author     : Hoàng Duy
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Candidate Management</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Base CSS -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <style>
            .nav-tabs .nav-link.active {
                background-color: #007bff !important; /* xanh dương */
                color: #fff !important;
                border-color: #007bff #007bff #fff;
            }
            .nav-tabs .nav-link:hover {
                background-color: #e9f2ff;
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
                    <h4 class="breadcrumb-title">Candidate Management</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li>Candidate</li>
                        <li>List</li>
                    </ul>
                </div>

                <!-- ✅ Widget Box -->
                <div class="widget-box">
                    <div class="wc-title">
                        <h4><i class="fa fa-users"></i> Candidate List</h4>
                        <span class="badge badge-primary">
                            Total: ${sessionScope.candidateListFull != null ? sessionScope.candidateListFull.size() : 0}
                        </span>
                    </div>

                    <div class="widget-inner">

                        <!-- ✅ Search Form -->
                        <form action="${pageContext.request.contextPath}/candidatelist" method="post"
                              class="d-flex align-items-center justify-content-between mb-4">
                            <input type="hidden" name="tab" value="${sessionScope.tab}">
                            <div class="input-group" style="max-width: 400px;">
                                <span class="input-group-text bg-white border-end-0">
                                    <i class="fa fa-search text-muted"></i>
                                </span>
                                <input type="text" name="keyword" class="form-control border-start-0"
                                       placeholder="Search by name, email, or phone..."
                                       value="${param.keyword}">
                                <div class="input-group-append">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-search me-1"></i> Search
                                    </button>
                                </div>
                            </div>
                        </form>

                        <!-- ✅ Tabs: Pending / Approved / Rejected -->
                        <ul class="nav nav-tabs mb-4" id="candidateTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${sessionScope.tab eq 'pending' ? 'active bg-primary text-white' : ''}" 
                                   href="${pageContext.request.contextPath}/candidatelist?tab=pending" role="tab">
                                    <i class="fa fa-list"></i> Pending
                                </a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${sessionScope.tab eq 'approve' ? 'active bg-primary text-white' : ''}" 
                                   href="${pageContext.request.contextPath}/candidatelist?tab=approve" role="tab">
                                    <i class="fa fa-check-circle text-success"></i> Approved
                                </a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${sessionScope.tab eq 'reject' ? 'active bg-primary text-white' : ''}" 
                                   href="${pageContext.request.contextPath}/candidatelist?tab=reject" role="tab">
                                    <i class="fa fa-times-circle text-danger"></i> Rejected
                                </a>
                            </li>
                        </ul>

                        <!-- ✅ Candidate Table -->
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover align-middle text-center">
                                <thead class="thead-dark">
                                    <tr>
                                        <th>#</th>
                                        <th style="cursor:pointer;"
                                            onclick="window.location = '${pageContext.request.contextPath}/candidatelist?type=name&tab=${sessionScope.tab}'">
                                            Full Name <i class="fa fa-sort"></i>
                                        </th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th style="cursor:pointer;"
                                            onclick="window.location = '${pageContext.request.contextPath}/candidatelist?type=appliedat&tab=${sessionScope.tab}'">
                                            Applied At <i class="fa fa-sort"></i>
                                        </th>
                                        <th>Action</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="el" items="${sessionScope.candidateList}" varStatus="st">
                                        <tr>
                                            <td>${(sessionScope.pages - 1) * 5 + st.index + 1}</td>
                                            <td class="text-left">
                                                <strong class="text-primary">${el.name}</strong><br>
                                                <small class="text-muted">ID: ${el.candidateId}</small>
                                            </td>
                                            <td>${el.email}</td>
                                            <td>${el.phone}</td>
                                            <td>
                                                <fmt:formatDate value="${el.appliedAt}" pattern="MMM dd, yyyy HH:mm" />
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/candidatedetail?id=${el.candidateId}"
                                                   class="btn btn-sm btn-info" title="View Detail">
                                                    <i class="fa fa-eye"></i> View
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty sessionScope.candidateList}">
                                        <tr>
                                            <td colspan="6" class="text-muted text-center py-3">
                                                <i class="fa fa-inbox fa-2x mb-2"></i><br>
                                                No candidates found.
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
                                <nav aria-label="Candidate pagination">
                                    <ul class="pagination mb-0">
                                        <li class="page-item ${sessionScope.pages == 1 ? 'disabled' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/candidatelist?page=${sessionScope.pages - 1}&tab=${sessionScope.tab}">
                                                <i class="fa fa-chevron-left"></i> Previous
                                            </a>
                                        </li>

                                        <li class="page-item active">
                                            <a class="page-link">${sessionScope.pages}</a>
                                        </li>

                                        <li class="page-item ${sessionScope.pages >= sessionScope.total ? 'disabled' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/candidatelist?page=${sessionScope.pages + 1}&tab=${sessionScope.tab}">
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

        <!-- JS libraries -->
        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
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