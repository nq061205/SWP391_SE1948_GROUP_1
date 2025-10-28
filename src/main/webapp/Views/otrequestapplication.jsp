
<%-- 
    Document   : listapplication
    Created on : Oct 5, 2025, 10:46:57 PM
    Author     : Lenovo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Overtime Application</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- HEADER + NAVBAR -->
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>
        <input type="hidden" name="typeApplication" value="otrequest" />
        
        <main class="ttr-wrapper">
            <div class="filter-row mb-3">
                            <form action="${pageContext.request.contextPath}/application" method="get"
                                  class="d-flex align-items-center flex-nowrap w-100" style="gap:12px;">
                                <input type="hidden" name="typeapplication" value="LEAVE"/>

                                <select name="status" class="form-control filter-h" style="width:160px;" onchange="this.form.submit()">
                                    <option value="">All Status</option>
                                    <option value="Pending"  ${param.status == 'Pending'  ? 'selected' : ''}>Pending</option>
                                    <option value="Approved" ${param.status == 'Approved' ? 'selected' : ''}>Approved</option>
                                    <option value="Rejected" ${param.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                </select>

                                <select name="type" class="form-control filter-h" style="width:170px;" onchange="this.form.submit()">
                                    <option value="">All Type</option>
                                    <option value="Annual Leave" ${param.type == 'Annual Leave' ? 'selected' : ''}>Annual Leave</option>
                                    <option value="Sick"    ${param.type == 'Sick'    ? 'selected' : ''}>Sick</option>
                                    <option value="Unpaid"  ${param.type == 'Unpaid'  ? 'selected' : ''}>Unpaid</option>
                                    <option value="Maternity" ${param.type == 'Maternity' ? 'selected' : ''}>Maternity</option>
                                    <option value="Other"   ${param.type == 'Other'   ? 'selected' : ''}>Other</option>
                                </select>

                                <input type="date" name="startDate" value="${param.startDate}"
                                       class="form-control filter-h" style="width:170px;">
                                <span class="sep">to</span>
                                <input type="date" name="endDate" value="${param.endDate}"
                                       class="form-control filter-h" style="width:170px;">

                                <button type="submit" class="btn btn-outline-secondary filter-h">Apply</button>
                                <a href="${pageContext.request.contextPath}/application?typeapplication=LEAVE"
                                   class="btn btn-warning filter-h">Clear</a>
                            </form>
                        </div>
            <div class="table-responsive" style="overflow-x:auto;">
                <table class="table table-striped table-bordered table-hover align-middle text-center" 
                       style="table-layout: fixed; width: 100%; border-collapse: collapse;">
                    <thead class="thead-dark" >
                        <tr>
                            <th style="width: 50px;">
                                No.
                            </th>
                            <th style="width: 120px">
                                Receiver
                            </th>
                            <th style="width: 120px">
                                Email
                            </th>
                            <th style="width:80px">
                                Status
                            </th>
                            <th style="width:80px">
                                Overtime hours
                            </th>
                            <th style="width:80px">
                                Day Overtime
                            </th>
                            <th style="width:120px">
                                Day Created
                            </th>
                            <th style="cursor:pointer;width: 100px">
                                Action

                            </th>
                        </tr>                                 
                    </thead>
                    <tbody>
                        <c:forEach var="list" items="${listapplication}" varStatus ="loop">
                            <tr>
                                <td>${(page - 1) * 10 +loop.index+1}</td>
                                <td>${list.approvedBy.fullname}</td>
                                <td>${list.approvedBy.email}</td>
                                <td
                                    style="font-weight: bold;
                                    color:
                                    <c:choose>
                                        <c:when test="${list.status eq 'Approved'}">green</c:when>
                                        <c:when test="${list.status eq 'Rejected'}">red</c:when>
                                        <c:otherwise>goldenrod</c:otherwise>
                                    </c:choose>;
                                    "
                                    >${list.status}</td>
                                <td>${list.otHours}</td>
                                <td>${list.date}</td>
                                <td>${list.createdAt}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not list.status.equalsIgnoreCase('Pending')}">
                                            <a href="${pageContext.request.contextPath}/detail?otId=${list.otId}" class="icon-circle" title="More detail">
                                                <i class="fa fa-info"></i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/editapplication?type=OT&id=${list.otId}" class="icon-circle" data-toggle="tooltip" title="Edit">
                                                <i class="fa fa-pencil"></i>
                                            </a>   
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${not empty message}">
                            <tr>
                                <td colspan="10" style="text-align:center; color:red; font-weight:bold;">
                                    No results found!
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
            <c:url var="baseUrl" value="/application">
                <c:param name="typeapplication" value="OT"/>
                <c:if test="${not empty param.search}">
                    <c:param name="search" value="${param.search}"/>
                </c:if>
                <c:if test="${not empty param.status}">
                    <c:param name="status" value="${param.status}"/>
                </c:if>
                <c:if test="${not empty param.startDate}">
                    <c:param name="startDate" value="${param.startDate}"/>
                </c:if>
                <c:if test="${not empty param.endDate}">
                    <c:param name="endDate" value="${param.endDate}"/>
                </c:if>
            </c:url>
            <nav class="mt-3">
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
                <ul class="pagination justify-content-center">
                    <li class="page-item ${page <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${baseUrl}&page=${page-1}">Prev</a>
                    </li>

                    <c:forEach var="p" begin="${startPage}" end="${endPage}">
                        <li class="page-item ${p == page ? 'active' : ''}">
                            <a class="page-link" href="${baseUrl}&page=${p}">${p}</a>
                        </li>
                    </c:forEach>

                    <li class="page-item ${page >= totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${baseUrl}&page=${page+1}">Next</a>
                    </li>
                </ul>
            </nav>
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
        <script>
                                                $(document).ready(function () {
                                                    $('[data-toggle="tooltip"]').tooltip();
                                                });
        </script>
        <style>
            .icon-circle {
                display: inline-flex;
                justify-content: center;
                align-items: center;
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background-color: #e0e0e0;
                color: #333;
                text-decoration: none;
                transition: 0.3s;
                border: none;
                background: none;
                padding: 0;
                cursor: pointer;
                color: inherit;
            }

            .icon-circle:hover {
                background-color: #d5d5d5;
                color: #000;
            }
        </style>
    </body>
</html>
