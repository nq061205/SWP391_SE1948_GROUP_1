<%-- 
    Document   : profile
    Created on : Oct 4, 2025, 9:18:37 PM
    Author     : Lenovo
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Department detail</title>

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
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- HEADER + NAVBAR -->
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/adminNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Department information</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>Department Detail</li>
                    </ul>
                </div>
                <a href="${pageContext.request.contextPath}/departmentlist" class="btn btn-secondary">← Back to list</a>
                <h4>Department Name: ${dept.depName}</h4>
                <p><strong>Total employees:</strong> ${employeeCount}</p>

                <p><strong>Manager:</strong>
                    <c:choose>
                        <c:when test="${manager != null}">
                            ${manager.fullname}
                        </c:when>
                        <c:otherwise>This department don't have a manager</c:otherwise>
                    </c:choose>
                </p>

                <h5>Employee list in this department:</h5>
                <div class="row align-items-stretch">

                    <div class="col-md-4">
                        <form action="${pageContext.request.contextPath}/departmentdetail" method="get"
                              class="d-flex align-items-center justify-content-between h-100">
                            <input type="hidden" name="page" value="${page}">
                            <input type="hidden" name="deptId" value="${deptId}">
                            <div class="input-group w-100">
                                <span class="input-group-text bg-white border-end-0">
                                    <i class="fa fa-search text-muted"></i>
                                </span>
                                <input type="text" name="searchkey" class="form-control border-start-0"
                                       placeholder="Search by name or email"
                                       value="${searchkey}">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-search me-1"></i> Search
                                </button>
                            </div>
                        </form>
                    </div>


                    <div class="col-md-8 custom-col">
                        <form action="${pageContext.request.contextPath}/departmentdetail" method="get"
                              class="d-flex align-items-center justify-content-between flex-nowrap gap-3 h-100">
                            <input type="hidden" name="page" value="${page}">
                            <input type="hidden" name="deptId" value="${deptId}">
                            <c:if test="${not empty searchkey}">
                                <input type="hidden" name="searchkey" value="${searchkey}">
                            </c:if>
                            <select name="gender" class="form-select flex-fill" style="min-width:150px;">
                                <option value="All" ${gender == null || gender == 'All' ? 'selected' : ''}>All Genders</option>
                                <option value="true" ${gender == 'true' ? 'selected' : ''}>Male</option>
                                <option value="false" ${gender == 'false' ? 'selected' : ''}>Female</option>
                            </select>
                            <select name="positionTitle" class="form-select flex-fill" style="min-width:150px;">
                                <option value="">-- All Positions --</option>
                                <c:forEach var="pl" items="${sessionScope.positionList}">
                                    <option value="${pl}" ${not empty positionTitle && positionTitle[0] == pl ? 'selected' : ''}>${pl}</option>
                                </c:forEach>
                            </select>
                            <div class="d-flex align-items-center gap-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-filter"></i> Apply
                                </button>
                                <a href="${pageContext.request.contextPath}/departmentdetail?deptId=${deptId}" class="btn btn-secondary">
                                    <i class="fa fa-times"></i> Clear
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
                <c:if test="${not empty searchkey}">
                    <p>Found <strong>${totalSearchResults}</strong> employee with search key is <strong>${searchkey}</strong></p>  
                </c:if>
                <div class="table-responsive" style="overflow-x:auto;"> 
                    <table class="table table-striped table-bordered table-hover align-middle text-center" 
                           style="table-layout: fixed; width: 100%; border-collapse: collapse;"> 
                        <c:set var="nextOrder" value="${order == 'asc' ? 'desc' : 'asc'}" /> 
                        <thead class="thead-dark"> 
                            <tr> 
                                <th>No</th>
                                <th>Employee Name</th>
                                <th>Gender</th>
                                <th>Position Title</th>
                                <th>Email</th>
                                <th>Phone</th>
                            </tr> 
                        </thead> 
                        <tbody> 
                            <c:forEach var="el" items="${empList}" varStatus ="loop">
                                <tr>
                                    <td>${loop.index+1}</td> 
                                    <td>${el.fullname}</td>
                                    <td>${el.gender ?'Male' :'Female'}</td>
                                    <td style="overflow-wrap: break-word;">${el.positionTitle}</td> 
                                    <td style="overflow-wrap: break-word;">${el.email}</td> 
                                    <td>${el.phone}</td>
                                </tr>
                            </c:forEach> 

                            <c:if test="${not empty message}"> 
                                <tr> 
                                    <td colspan="6" style="text-align:center; color:red; font-weight:bold;"> 
                                        No results found! 
                                    </td> 
                                </tr> 
                            </c:if>
                        </tbody>
                    </table>
                </div>
                <c:url var="baseUrlWithSort" value="departmentdetail">
                    <c:if test="${not empty deptId}">
                        <c:param name="deptId" value="${deptId}" />
                    </c:if>
                    <c:if test="${not empty gender}">
                        <c:param name="gender" value="${gender}" />
                    </c:if>
                    <c:if test="${not empty positionTitle}">
                        <c:forEach var="pt" items="${positionTitle}">
                            <c:param name="positionTitle" value="${pt}" />
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty searchkey}">
                        <c:param name="searchkey" value="${searchkey}" />
                    </c:if>
                </c:url>
                <c:set var="urlPrefixWithSort" value="${baseUrlWithSort}${fn:contains(baseUrlWithSort, '?') ? '&' : '?'}" />
                <nav class="mt-3">
                    <ul class="pagination justify-content-center">
                        <c:set var="startPage" value="${page - 1}" />
                        <c:set var="endPage" value="${page + 1}" />

                        <c:if test="${startPage < 1}">
                            <c:set var="endPage" value="${endPage + (1 - startPage)}" />
                            <c:set var="startPage" value="1" />
                        </c:if>

                        <c:if test="${endPage > totalPages}">
                            <c:set var="startPage" value="${startPage - (endPage - totalPages)}" />
                            <c:set var="endPage" value="${totalPages}" />
                        </c:if>

                        <c:if test="${startPage < 1}">
                            <c:set var="startPage" value="1" />
                        </c:if>
                        <li class="page-item ${page <= 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${urlPrefixWithSort}&page=${page-1}">Prev</a>
                        </li>

                        <c:forEach var="p" begin="${startPage}" end="${endPage}">
                            <li class="page-item ${p == page ? 'active' : ''}">
                                <a class="page-link" href="${urlPrefixWithSort}&page=${p}">${p}</a>
                            </li>
                        </c:forEach>

                        <li class="page-item ${page >= totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${urlPrefixWithSort}&page=${page+1}">Next</a>
                        </li>
                    </ul>
                </nav>
        </main>
        <!-- SCRIPT ZONE -->
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