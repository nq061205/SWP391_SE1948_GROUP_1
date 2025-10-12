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
        <title>Candidate Management - HRM System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Base CSS -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <!-- Header + Navbar -->
        <jsp:include page="CommonItems/Header/dashboardHeader.jsp" />
        <jsp:include page="CommonItems/Navbar/adminNavbar.jsp" />

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

                <!-- ✅ Search Bar -->
                <div class="widget-box">
                    <div class="wc-title">
                        <h4><i class="fa fa-users"></i> Candidate List</h4>
                        <span class="badge badge-primary">
                            Total: ${sessionScope.candidateList != null ? sessionScope.candidateList.size() : 0}
                        </span>
                    </div>

                    <div class="widget-inner">

                        <!-- Search Form (POST → Servlet) -->
                        <form action="${pageContext.request.contextPath}/candidatelist" method="post" 
                              class="d-flex align-items-center justify-content-between mb-4">
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

                        <!-- ✅ Table (click để sắp xếp qua Servlet) -->
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover align-middle text-center">
                                <thead class="thead-dark">
                                    <tr>
                                        <th>#</th>
                                        <!-- Gọi lại servlet /candidatelist?type=name để sắp xếp -->
                                        <th style="cursor:pointer;" 
                                            onclick="window.location = '${pageContext.request.contextPath}/candidatelist?type=name'">
                                            Full Name <i class="fa fa-sort"></i>
                                        </th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <!-- Gọi lại servlet /candidatelist?type=appliedat để sắp xếp -->
                                        <th style="cursor:pointer;"
                                            onclick="window.location = '${pageContext.request.contextPath}/candidatelist?type=appliedat'">
                                            Applied At <i class="fa fa-sort"></i>
                                        </th>
                                        <th>Action</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="el" items="${sessionScope.candidateList}" varStatus="st">
                                        <tr>
                                            <td>${st.index + 1}</td>
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

                                    <!-- Nếu rỗng -->
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
                            <!-- ✅ Pagination UI -->
                            <div class="d-flex justify-content-between align-items-center mt-4">

                                <!-- Hiển thị tổng số bản ghi (tuỳ chọn) -->
                                <div class="text-muted">
                                    Showing <strong>1</strong> to <strong>10</strong> of <strong>50</strong> entries
                                </div>

                                <!-- Thanh phân trang -->
                                <nav aria-label="Candidate pagination">
                                    <ul class="pagination mb-0">
                                        <li class="page-item ${sessionScope.pages == 1 ? 'disabled' : ''}">
                                            <c:choose>
                                                <c:when test="${sessionScope.pages > 1}">
                                                    <a class="page-link" 
                                                       href="${pageContext.request.contextPath}/candidatelist?page=${sessionScope.pages - 1}">
                                                        <i class="fa fa-chevron-left"></i> Previous
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="page-link" tabindex="-1">
                                                        <i class="fa fa-chevron-left"></i> Previous
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </li>

                                        <li class="page-item active">
                                            <a class="page-link">${sessionScope.pages}</a>
                                        </li>
                                        <li class="page-item ${sessionScope.pages >= sessionScope.total ? 'disabled' : ''}">
                                            <c:choose>
                                                <c:when test="${sessionScope.pages < sessionScope.total}">
                                                    <a class="page-link" 
                                                       href="${pageContext.request.contextPath}/candidatelist?page=${sessionScope.pages + 1}">
                                                        Next <i class="fa fa-chevron-right"></i>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="page-link" tabindex="-1">
                                                        Next <i class="fa fa-chevron-right"></i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
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
    </body>
</html>
