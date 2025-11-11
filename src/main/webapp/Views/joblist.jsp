<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />
        <meta name="description" content="Human Tech - Jobs Search" />
        <meta property="og:title" content="Jobs Search - Human Tech" />
        <meta property="og:description" content="Find your dream job at Human Tech" />
        <meta name="format-detection" content="telephone=no">

        <link rel="icon" href="${pageContext.request.contextPath}/assets1/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets1/images/favicon.png" />

        <title>Jobs Search - Human Tech</title>

        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/assets.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/typography.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/shortcodes/shortcodes.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/color/color-1.css">

        <style>
            body, html {
                height: 100%;
                margin: 0;
            }
            .page-wraper {
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }
            .page-content {
                flex: 1;
            }
            .job-card {
                background: #fff;
                border: 1px solid #e6e6e6;
                border-radius: 8px;
                padding: 25px;
                margin-bottom: 20px;
                transition: all 0.3s ease;
                position: relative;
            }
            .job-card:hover {
                box-shadow: 0 5px 20px rgba(0,0,0,0.1);
                transform: translateY(-2px);
            }
            .job-title {
                font-size: 20px;
                font-weight: 600;
                color: #333;
                margin-bottom: 15px;
                cursor: pointer;
                text-decoration: none;
                display: block;
                transition: color 0.3s ease;
            }
            .job-title:hover {
                color: #f7b205;
                text-decoration: none;
            }
            .job-meta {
                display: flex;
                gap: 20px;
                flex-wrap: wrap;
                margin-bottom: 15px;
                font-size: 14px;
                color: #666;
            }
            .job-meta-item {
                display: flex;
                align-items: center;
                gap: 8px;
            }
            .job-meta-item i {
                color: #f7b205;
                font-size: 16px;
            }
            .job-description {
                color: #666;
                line-height: 1.6;
                margin-bottom: 20px;
                max-height: 80px;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
            }
            .job-footer {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-top: 20px;
                border-top: 1px solid #e6e6e6;
            }
            .apply-btn {
                background: #f7b205;
                color: #000;
                padding: 10px 30px;
                border-radius: 5px;
                font-weight: 600;
                text-decoration: none;
                transition: all 0.3s ease;
                display: inline-block;
                border: none;
            }
            .apply-btn:hover {
                background: #4c1864;
                color: #fff;
                text-decoration: none;
                transform: translateY(-2px);
            }
            .search-section {
                background: url('${pageContext.request.contextPath}/assets1/images/banner/banner1.jpg') no-repeat center center;
                background-size: cover;
                position: relative;
                padding: 150px 0 60px 0;
                margin-bottom: 50px;
                margin-top: 0;
            }
            .search-section::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 0, 0, 0.4);
                z-index: 1;
            }
            .search-section .container {
                position: relative;
                z-index: 2;
            }
            .search-box {
                background: #fff;
                border-radius: 10px;
                padding: 30px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            }
            .search-title {
                color: #fff;
                font-size: 36px;
                font-weight: 700;
                margin-bottom: 30px;
                text-align: center;
            }
            .no-jobs {
                text-align: center;
                padding: 60px 20px;
                background: #f8f9fa;
                border-radius: 10px;
                margin-top: 30px;
            }
            .no-jobs i {
                font-size: 64px;
                color: #ccc;
                margin-bottom: 20px;
            }
            .sidebar-filter {
                background: #fff;
                border: 1px solid #e6e6e6;
                border-radius: 8px;
                padding: 25px;
                margin-bottom: 20px;
            }
            .filter-title {
                font-size: 18px;
                font-weight: 600;
                color: #333;
                margin-bottom: 15px;
                padding-bottom: 10px;
                border-bottom: 2px solid #f7b205;
            }
            .filter-item {
                padding: 8px 0;
                display: flex;
                justify-content: space-between;
                align-items: center;
                cursor: pointer;
                color: #666;
                transition: all 0.3s ease;
            }
            .filter-item:hover {
                color: #f7b205;
                padding-left: 5px;
            }
            .filter-count {
                background: #f5f5f5;
                padding: 2px 10px;
                border-radius: 12px;
                font-size: 12px;
                color: #666;
            }
            .pagination-wrapper {
                margin-top: 40px;
                display: flex;
                justify-content: center;
            }
            .pagination {
                display: flex;
                list-style: none;
                padding: 0;
                gap: 5px;
            }
            .pagination li {
                display: inline-block;
            }
            .pagination a,
            .pagination span {
                display: block;
                padding: 10px 15px;
                border: 1px solid #e6e6e6;
                border-radius: 5px;
                color: #666;
                text-decoration: none;
                transition: all 0.3s ease;
                background: #fff;
            }
            .pagination a:hover {
                background: #f7b205;
                color: #000;
                border-color: #f7b205;
            }
            .pagination .active span {
                background: #f7b205;
                color: #000;
                border-color: #f7b205;
                font-weight: 600;
            }
            .pagination .disabled span {
                background: #f5f5f5;
                color: #ccc;
                cursor: not-allowed;
            }
        </style>
    </head>

    <body id="bg">
        <div class="page-wraper">
            <%@ include file="CommonItems/Header/homepageHeader.jsp" %>

            <div class="page-content bg-white">
                <div class="search-section">
                    <div class="container">
                        <h1 class="search-title">Jobs Search</h1>
                        <div class="search-box">
                            <form action="${pageContext.request.contextPath}/jobsite" method="get">
                                <div class="row">
                                    <div class="col-lg-3 col-md-6 mb-3 mb-lg-0">
                                        <div class="form-group mb-0">
                                            <input type="text" name="keyword" class="form-control"
                                                   placeholder="Job title, keywords..."
                                                   value="${param.keyword}"
                                                   style="height: 50px; border-radius: 5px;">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-6 mb-3 mb-lg-0">
                                        <div class="form-group mb-0">
                                            <input type="date" name="fromDate" class="form-control"
                                                   placeholder="From Date"
                                                   value="${param.fromDate}"
                                                   style="height: 50px; border-radius: 5px;">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-6 mb-3 mb-lg-0">
                                        <div class="form-group mb-0">
                                            <input type="date" name="toDate" class="form-control"
                                                   placeholder="To Date"
                                                   value="${param.toDate}"
                                                   style="height: 50px; border-radius: 5px;">
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-md-6 mb-3 mb-lg-0">
                                        <div class="form-group mb-0">
                                            <select name="sort" class="form-control"
                                                    style="height: 50px; border-radius: 5px;">
                                                <option value="desc" ${param.sort == 'desc' ? 'selected' : ''}>Newest First</option>
                                                <option value="asc" ${param.sort == 'asc' ? 'selected' : ''}>Oldest First</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-1 col-md-6">
                                        <button type="submit" class="btn btn-block"
                                                style="background: #f7b205; color: #000; height: 50px; font-weight: 600; border-radius: 5px;">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="content-block">
                    <div class="section-full browse-job content-inner-2">
                        <div class="container">
                            <div class="row">
                                <div class="col-xl-3 col-lg-4 col-md-4 col-sm-12 m-b30">
                                    <div class="sidebar-filter">
                                        <h5 class="filter-title">Departments</h5>

                                        <a href="${pageContext.request.contextPath}/jobsite" 
                                           class="filter-item" style="text-decoration: none; color: inherit; display: block; cursor: pointer;">
                                            <span style="${empty param.depId ? 'font-weight: bold; color: #f7b205;' : ''}">All Departments</span>
                                            <span class="filter-count">${allUploadedCount}</span>
                                        </a>

                                        <c:if test="${not empty departments}">
                                            <c:forEach items="${departments}" var="dept">
                                                <c:set var="count" value="${deptCountMap[dept.depId]}" />
                                                <c:if test="${count != null && count > 0}">
                                                    <a href="${pageContext.request.contextPath}/jobsite?depId=${dept.depId}<c:if test="${not empty param.keyword}">&keyword=${param.keyword}</c:if><c:if test="${not empty param.fromDate}">&fromDate=${param.fromDate}</c:if><c:if test="${not empty param.toDate}">&toDate=${param.toDate}</c:if><c:if test="${not empty param.sort}">&sort=${param.sort}</c:if>" 
                                                       class="filter-item" style="text-decoration: none; color: inherit; display: block; cursor: pointer;">
                                                        <span style="${param.depId == dept.depId ? 'font-weight: bold; color: #f7b205;' : ''}">${dept.depName}</span>
                                                        <span class="filter-count">${count}</span>
                                                    </a>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="col-xl-9 col-lg-8 col-md-8 col-sm-12">
                                    <div class="job-bx-title clearfix">
                                        <h5 class="font-weight-700 pull-left text-uppercase" style="margin-bottom: 30px;">
                                            <c:choose>
                                                <c:when test="${totalPosts == 0}">0 Jobs Found</c:when>
                                                <c:when test="${totalPosts == 1}">1 Job Found</c:when>
                                                <c:otherwise>${totalPosts} Jobs Found</c:otherwise>
                                            </c:choose>
                                        </h5>
                                    </div>

                                    <c:choose>
                                        <c:when test="${not empty posts}">
                                            <c:forEach items="${posts}" var="post">
                                                <div class="job-card">
                                                    <a href="${pageContext.request.contextPath}/jobsite?action=view&postId=${post.postId}"
                                                       class="job-title">
                                                        ${post.title}
                                                    </a>

                                                    <div class="job-meta">
                                                        <c:if test="${post.department != null}">
                                                            <div class="job-meta-item">
                                                                <i class="fa fa-building-o"></i>
                                                                <span>${post.department.depName}</span>
                                                            </div>
                                                        </c:if>
                                                        <div class="job-meta-item">
                                                            <i class="fa fa-map-marker"></i>
                                                            <span>Ha Noi, Viet Nam</span>
                                                        </div>
                                                        <c:if test="${post.approvedAt != null}">
                                                            <div class="job-meta-item">
                                                                <i class="fa fa-calendar"></i>
                                                                <span>
                                                                    <fmt:formatDate value="${post.approvedAt}" pattern="MMM dd, yyyy" />
                                                                </span>
                                                            </div>
                                                        </c:if>
                                                    </div>

                                                    <div class="job-description">
                                                        ${post.content}
                                                    </div>

                                                    <div class="job-footer">
                                                        <div>
                                                            <span style="color: #666; font-size: 14px;">
                                                                <i class="fa fa-clock-o" style="margin-right: 5px;"></i>
                                                                Posted <fmt:formatDate value="${post.approvedAt}" pattern="MMM dd, yyyy" />
                                                            </span>
                                                        </div>
                                                        <a href="${pageContext.request.contextPath}/jobsite?action=view&postId=${post.postId}"
                                                           class="apply-btn">
                                                            View Details
                                                        </a>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="no-jobs">
                                                <i class="fa fa-briefcase"></i>
                                                <h4 style="color: #333; font-weight: 600;">No Jobs Found</h4>
                                                <p style="color: #666;">There are currently no job openings available. Please check back later.</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:if test="${totalPages > 1}">
                                        <div class="pagination-wrapper">
                                            <ul class="pagination">
                                                <li class="${currentPage == 1 ? 'disabled' : ''}">
                                                    <c:choose>
                                                        <c:when test="${currentPage == 1}">
                                                            <span>&laquo; Previous</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/jobsite?page=${currentPage - 1}&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                &laquo; Previous
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>

                                                <c:choose>
                                                    <c:when test="${totalPages <= 7}">
                                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                                            <li class="${i == currentPage ? 'active' : ''}">
                                                                <c:choose>
                                                                    <c:when test="${i == currentPage}">
                                                                        <span>${i}</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a href="${pageContext.request.contextPath}/jobsite?page=${i}&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                            ${i}
                                                                        </a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </li>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${currentPage <= 4}">
                                                                <c:forEach begin="1" end="5" var="i">
                                                                    <li class="${i == currentPage ? 'active' : ''}">
                                                                        <c:choose>
                                                                            <c:when test="${i == currentPage}">
                                                                                <span>${i}</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <a href="${pageContext.request.contextPath}/jobsite?page=${i}&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                                    ${i}
                                                                                </a>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </li>
                                                                </c:forEach>
                                                                <li class="disabled"><span>...</span></li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/jobsite?page=${totalPages}&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                        ${totalPages}
                                                                    </a>
                                                                </li>
                                                            </c:when>
                                                            <c:when test="${currentPage >= totalPages - 3}">
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/jobsite?page=1&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                        1
                                                                    </a>
                                                                </li>
                                                                <li class="disabled"><span>...</span></li>
                                                                <c:forEach begin="${totalPages - 4}" end="${totalPages}" var="i">
                                                                    <li class="${i == currentPage ? 'active' : ''}">
                                                                        <c:choose>
                                                                            <c:when test="${i == currentPage}">
                                                                                <span>${i}</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <a href="${pageContext.request.contextPath}/jobsite?page=${i}&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                                    ${i}
                                                                                </a>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </li>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/jobsite?page=1&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                        1
                                                                    </a>
                                                                </li>
                                                                <li class="disabled"><span>...</span></li>
                                                                <c:forEach begin="${currentPage - 1}" end="${currentPage + 1}" var="i">
                                                                    <li class="${i == currentPage ? 'active' : ''}">
                                                                        <c:choose>
                                                                            <c:when test="${i == currentPage}">
                                                                                <span>${i}</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <a href="${pageContext.request.contextPath}/jobsite?page=${i}&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                                    ${i}
                                                                                </a>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </li>
                                                                </c:forEach>
                                                                <li class="disabled"><span>...</span></li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/jobsite?page=${totalPages}&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                        ${totalPages}
                                                                    </a>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>

                                                <li class="${currentPage == totalPages ? 'disabled' : ''}">
                                                    <c:choose>
                                                        <c:when test="${currentPage == totalPages}">
                                                            <span>Next &raquo;</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/jobsite?page=${currentPage + 1}&pageSize=${pageSize}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}${not empty param.fromDate ? '&fromDate='.concat(param.fromDate) : ''}${not empty param.toDate ? '&toDate='.concat(param.toDate) : ''}${not empty param.sort ? '&sort='.concat(param.sort) : ''}">
                                                                Next &raquo;
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>
                                            </ul>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="CommonItems/Footer/homepageFooter.jsp" %>
        </div>

        <script src="${pageContext.request.contextPath}/assets1/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/counter/waypoints-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/counter/counterup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/masonry/masonry-3.1.4.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/masonry/masonry.filter.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/dz.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/dz.ajax.js"></script>
    </body>
</html>
