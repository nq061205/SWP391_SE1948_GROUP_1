<%-- 
    Document   : listapplication
    Created on : Oct 5, 2025, 10:46:57 PM
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
        <title>Employee List</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

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
            .sort {
                text-decoration: none;
                color: white;
            }
            .sort:hover {
                color: white;
                text-decoration: none;
            }
            .row > .col-md-8 {
                background: transparent !important;
                box-shadow: none !important;
                border: none !important;
                width: 65% !important;
                flex: 0 0 65% !important; /* bắt flex box theo 30% */
                max-width: 65% !important;
                height: 70px !important;
            }
            .custom-col form {
                align-items: center !important;
                gap: 20px !important; /* khoảng cách giữa các combo box */
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- HEADER + NAVBAR -->
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>
        <input type="hidden" name="typeApplication" value="leaverequest" />
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Employee Listing</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/employeelist">Employee list</a></li>
                    </ul>
                </div>
                <c:url var="baseUrl" value="employeelist">
                    <c:if test="${not empty ageRange}">
                        <c:param name="ageRange" value="${ageRange}" />
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
                <c:set var="urlPrefix" value="${baseUrl}${fn:contains(baseUrl, '?') ? '&' : '?'}" />


                <div class="row align-items-stretch">

                    <div class="col-md-4">
                        <form action="${pageContext.request.contextPath}/employeelist" method="get"
                              class="d-flex align-items-center justify-content-between h-100">
                            <input type="hidden" name="page" value="${page}">
                            <c:if test="${not empty sortBy}">
                                <input type="hidden" name="sortBy" value="${sortBy}">
                                <input type="hidden" name="order" value="${order}">
                            </c:if>
                            <div class="input-group w-100">
                                <input type="text" name="searchkey" class="form-control border-start-0"
                                       placeholder="Search by code or name"
                                       value="${searchkey}">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-search me-1"></i> Search
                                </button>
                            </div>
                        </form>
                    </div>


                    <div class="col-md-8 custom-col">
                        <form action="${pageContext.request.contextPath}/employeelist" method="get"
                              class="d-flex align-items-center justify-content-between flex-nowrap gap-3 h-100">

                            <input type="hidden" name="page" value="${page}">
                            <c:if test="${not empty searchkey}">
                                <input type="hidden" name="searchkey" value="${searchkey}">
                            </c:if>
                            <c:if test="${not empty sortBy}">
                                <input type="hidden" name="sortBy" value="${sortBy}">
                                <input type="hidden" name="order" value="${order}">
                            </c:if>

                            <!-- GENDER -->
                            <select name="gender" class="form-select flex-fill" style="min-width:150px;">
                                <option value="All" ${gender == null || gender == 'All' ? 'selected' : ''}>All Genders</option>
                                <option value="true" ${gender == 'true' ? 'selected' : ''}>Male</option>
                                <option value="false" ${gender == 'false' ? 'selected' : ''}>Female</option>
                            </select>

                            <!-- AGE RANGE -->
                            <select name="ageRange" class="form-select flex-fill" style="min-width:150px;">
                                <option value="All" ${ageRange == null || ageRange == 'All' ? 'selected' : ''}>All Ages</option>
                                <option value="under25" ${ageRange == 'under25' ? 'selected' : ''}>Under 25</option>
                                <option value="25to30" ${ageRange == '25to30' ? 'selected' : ''}>25 - 30</option>
                                <option value="31to40" ${ageRange == '31to40' ? 'selected' : ''}>31 - 40</option>
                                <option value="above40" ${ageRange == 'above40' ? 'selected' : ''}>Above 40</option>
                            </select>

                            <!-- POSITION -->
                            <select name="positionTitle" class="form-select flex-fill" style="min-width:150px;">
                                <option value="">-- All Positions --</option>
                                <c:forEach var="pl" items="${sessionScope.positionList}">
                                    <option value="${pl}" ${not empty positionTitle && positionTitle[0] == pl ? 'selected' : ''}>${pl}</option>
                                </c:forEach>
                            </select>

                            <!-- BUTTONS -->
                            <div class="d-flex align-items-center gap-2" style="display:flex;gap:10px">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-filter"></i> Apply
                                </button>
                                <a href="${pageContext.request.contextPath}/employeelist" class="btn btn-secondary">
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
                                <th style="width: 50px;">No</th> 
                                <th style="cursor:pointer;width: 120px"> 
                                    <a href="${urlPrefix}sortBy=emp_code&order=${nextOrder}&page=${page}" class="sort"> 
                                        Emp Code<i class="fa fa-sort"></i> 
                                    </a> 
                                </th> 
                                <th style="cursor:pointer;width: 120px"> 
                                    <a href="${urlPrefix}sortBy=fullname&order=${nextOrder}&page=${page}" class="sort"> 
                                        Full Name<i class="fa fa-sort"></i> 
                                    </a> 
                                </th> 
                                <th style="width: 100px">Email</th> 
                                <th style="width:80px">Gender</th> 
                                <th style="cursor:pointer;width: 80px"> 
                                    <a href="${urlPrefix}sortBy=dob&order=${nextOrder}&page=${page}" class="sort"> 
                                        Dob<i class="fa fa-sort"></i> 
                                    </a> 
                                </th> 
                                <th style="width:60px">Image</th> 
                                <th style="width:105px">Position Title</th> 
                                <th style="width:70px">
                                    <a href="${urlPrefix}sortBy=paid_leave_days&order=${nextOrder}&page=${page}" class="sort"> 
                                        Paid leaves day<i class="fa fa-sort"></i> 
                                    </a>
                                </th> 
                                <th style="width:180px">Action</th> 
                            </tr> 
                        </thead> 
                        <tbody> 
                            <c:forEach var="el" items="${sessionScope.empList}" varStatus ="loop">
                                <tr>
                                    <td>${loop.index+1}</td> 
                                    <td>${el.empCode}</td> 
                                    <td>${el.fullname}</td> 
                                    <td style="overflow-wrap: break-word;">${el.email}</td> 
                                    <td>${el.gender ?'Male' :'Female'}</td> 
                                    <td>${el.dob}</td> 
                                    <td> 
                                        <img style="width:65px" src="${el.image}" alt="Employee Image" 
                                             style="width:60px; height:60px; object-fit:cover; border-radius:6px;"> 
                                    </td> 
                                    <td style="overflow-wrap: break-word;">${el.positionTitle}</td> 
                                    <td>${el.paidLeaveDays}</td> 
                                    <td class="text-center" style="padding: 1%;">
                                        <div class="d-flex justify-content-center" style="gap:20px">
                                            <a href="${pageContext.request.contextPath}/updateemployee?empCode=${el.empCode}" 
                                               class="btn btn-sm btn-primary">Edit</a>
                                            <a href="${pageContext.request.contextPath}/employeedetail?empId=${el.empId}" 
                                               class="btn btn-sm btn-secondary" title="View Detail">View detail</a>
                                        </div>
                                    </td>

                                </tr>
                            </c:forEach> 

                            <c:if test="${not empty message}"> 
                                <tr> 
                                    <td colspan="11" style="text-align:center; color:red; font-weight:bold;"> 
                                        No results found! 
                                    </td> 
                                </tr> 
                            </c:if> 
                        </tbody> 
                    </table> 
                </div>


                <c:url var="baseUrlWithSort" value="employeelist">
                    <c:if test="${not empty ageRange}">
                        <c:param name="ageRange" value="${ageRange}" />
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
                    <c:if test="${not empty sortBy}">
                        <c:param name="sortBy" value="${sortBy}" />
                    </c:if>
                    <c:if test="${not empty order}">
                        <c:param name="order" value="${order}" />
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
<script src='${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js'></script>
<script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
<script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
<script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
<script src='${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js'></script>

</body>
</html>

