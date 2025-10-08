<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Candidate Management</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- CSS libraries -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <!-- Custom CSS -->

    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/adminNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">

                <!-- Breadcrumb -->
                <div class="db-breadcrumb mb-3">
                    <h4 class="breadcrumb-title">Candidate Listing</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/candidatelist"><i class="fa fa-home"></i> Candidate List</a></li>
                    </ul>
                </div>

                <!-- 🔍 Search Bar -->
                <div class="search-bar" style="margin-bottom:  20px">
                    <form action="${pageContext.request.contextPath}/candidatelist" method="get" class="d-flex align-items-center justify-content-between search-form">
                        <div class="input-group search-input-group">
                            <span class="input-group-text bg-white border-end-0">
                                <i class="fa fa-search text-muted"></i>
                            </span>
                            <input type="text" name="keyword" class="form-control border-start-0" placeholder="Search by name, email, or phone..." value="${param.keyword}">
                        </div>
                        <button type="submit" class="btn btn-primary ms-2">
                            <i class="fa fa-search me-1"></i> Search
                        </button>
                    </form>
                </div>

                <!-- Table -->
                <div class="table-container">
                    <table class="table table-bordered table-hover align-middle text-center">
                        <thead class="table-primary">
                            <tr>
                                <th>#</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Applied At</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="el" items="${sessionScope.candidateList}" varStatus="st">
                                <tr onclick="window.location.href='${pageContext.request.contextPath}/candidatedetail?id=${el.candidateId}'"
                                    style="cursor:pointer;">
                                    <td>${st.index + 1}</td>
                                    <td>${el.name}</td>
                                    <td>${el.email}</td>
                                    <td>${el.phone}</td>
                                    <td>${el.appliedAt}</td>

                                </tr>
                            </c:forEach>

                            <c:if test="${empty sessionScope.candidateList}">
                                <tr>
                                    <td colspan="6" class="text-muted">No candidates found.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>

        <!-- JS -->
        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
