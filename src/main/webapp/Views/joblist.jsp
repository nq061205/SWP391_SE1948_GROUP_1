<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />
        <meta name="description" content="Human Tech - Jobs Search" />
        <meta property="og:title" content="Jobs Search - Human Tech" />
        <meta property="og:description" content="Find your dream job at Human Tech" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets1/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets1/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Jobs Search - Human Tech</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/assets.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
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
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                padding: 150px 0 60px 0;
                margin-bottom: 50px;
                margin-top: 0;
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
        </style>
    </head>

    <body id="bg">
        <div class="page-wraper">
            <!-- Header -->
            <%@ include file="CommonItems/Header/homepageHeader.jsp" %>

            <div class="page-content bg-white">
                <!-- Search Section -->
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

                <!-- Jobs List Section -->
                <div class="content-block">
                    <div class="section-full browse-job content-inner-2">
                        <div class="container">
                            <div class="row">
                                <!-- Sidebar -->
                                <div class="col-xl-3 col-lg-4 col-md-4 col-sm-12 m-b30">
                                    <!-- Department Filter -->
                                    <div class="sidebar-filter">
                                        <h5 class="filter-title">Specialisms</h5>
                                        
                                        <!-- All Departments -->
                                        <div class="filter-item">
                                            <span>All Departments</span>
                                            <span class="filter-count">${posts.size()}</span>
                                        </div>
                                        
                                        <!-- Dynamic Department List -->
                                        <c:set var="departmentMap" value="${{}}" />
                                        <c:forEach items="${posts}" var="post">
                                            <c:if test="${post.department != null}">
                                                <c:set var="deptId" value="${post.department.depId}" />
                                                <c:set var="deptName" value="${post.department.depName}" />
                                                
                                                <!-- Count posts per department -->
                                                <c:set var="found" value="false" />
                                                <c:forEach items="${departmentMap}" var="entry">
                                                    <c:if test="${entry.key == deptId}">
                                                        <c:set var="found" value="true" />
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                        
                                        <!-- Display unique departments -->
                                        <c:set var="processedDepts" value="" />
                                        <c:forEach items="${posts}" var="post">
                                            <c:if test="${post.department != null}">
                                                <c:set var="deptId" value="${post.department.depId}" />
                                                <c:if test="${!processedDepts.contains(deptId)}">
                                                    <c:set var="deptCount" value="0" />
                                                    <c:forEach items="${posts}" var="p">
                                                        <c:if test="${p.department != null && p.department.depId == deptId}">
                                                            <c:set var="deptCount" value="${deptCount + 1}" />
                                                        </c:if>
                                                    </c:forEach>
                                                    
                                                    <div class="filter-item">
                                                        <span>${post.department.depName}</span>
                                                        <span class="filter-count">${deptCount}</span>
                                                    </div>
                                                    
                                                    <c:set var="processedDepts" value="${processedDepts}${deptId}," />
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>

                                <!-- Jobs List -->
                                <div class="col-xl-9 col-lg-8 col-md-8 col-sm-12">
                                    <div class="job-bx-title clearfix">
                                        <h5 class="font-weight-700 pull-left text-uppercase" style="margin-bottom: 30px;">
                                            <c:choose>
                                                <c:when test="${posts.size() == 0}">0 Jobs Found</c:when>
                                                <c:when test="${posts.size() == 1}">1 Job Found</c:when>
                                                <c:otherwise>${posts.size()} Jobs Found</c:otherwise>
                                            </c:choose>
                                        </h5>
                                    </div>

                                    <c:choose>
                                        <c:when test="${not empty posts}">
                                            <c:forEach items="${posts}" var="post">
                                                <div class="job-card">
                                                    <!-- Job Title -->
                                                    <a href="${pageContext.request.contextPath}/jobsite?action=view&postId=${post.postId}" 
                                                       class="job-title">
                                                        ${post.title}
                                                    </a>

                                                    <!-- Job Meta Info -->
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

                                                    <!-- Job Description Preview -->
                                                    <div class="job-description">
                                                        ${post.content}
                                                    </div>

                                                    <!-- Job Footer -->
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
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Footer -->
            <%@ include file="CommonItems/Footer/homepageFooter.jsp" %>
        </div>

        <!-- External JavaScripts -->
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
