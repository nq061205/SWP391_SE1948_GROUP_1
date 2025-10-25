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
        <title>Account List</title>

        <!-- MOBILE SPECIFIC ============================================= -->
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
                    <h4 class="breadcrumb-title">Account Listing</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/Admin/adminDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/accountlist">Accountlist</a></li>
                    </ul>
                </div>
                <c:url var="baseUrl" value="accountlist">
                    <c:if test="${status != null}">
                        <c:param name="status" value="${status}" />
                    </c:if>
                    <c:if test="${not empty deptId}">
                        <c:forEach var="d" items="${deptId}">
                            <c:param name="deptId" value="${d}" />
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty roleId}">
                        <c:forEach var="r" items="${roleId}">
                            <c:param name="roleId" value="${r}" />
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty searchkey}">
                        <c:param name="searchkey" value="${searchkey}" />
                    </c:if>
                </c:url>
                <c:set var="urlPrefix" value="${baseUrl}${fn:contains(baseUrl, '?') ? '&' : '?'}" />
                <div class="row align-items-stretch"> <!-- thêm align-items-stretch -->
                    <!-- SEARCH -->
                    <div class="col-md-4">
                        <form action="${pageContext.request.contextPath}/accountlist" method="get"
                              class="d-flex align-items-center justify-content-between h-100">
                            <input type="hidden" name="page" value="${page}">
                            <c:if test="${status != null}">
                                <input type="hidden" name="status" value="${status}">
                            </c:if>
                            <c:if test="${not empty deptId}">
                                <c:forEach var="d" items="${deptId}">
                                    <input type="hidden" name="deptId" value="${d}">
                                </c:forEach>
                            </c:if>
                            <c:if test="${not empty roleId}">
                                <c:forEach var="r" items="${roleId}">
                                    <input type="hidden" name="roleId" value="${r}">
                                </c:forEach>
                            </c:if>
                            <c:if test="${not empty sortBy}">
                                <input type="hidden" name="sortBy" value="${sortBy}">
                                <input type="hidden" name="order" value="${order}">
                            </c:if>

                            <div class="input-group w-100">
                                <span class="input-group-text bg-white border-end-0">
                                    <i class="fa fa-search text-muted"></i>
                                </span>
                                <input type="text" name="searchkey" class="form-control border-start-0"
                                       placeholder="Search by code or name"
                                       value="${param.keyword}">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-search me-1"></i> Search
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- FILTER -->
                    <div class="col-md-8 custom-col">
                        <form action="${pageContext.request.contextPath}/accountlist" method="get"
                              class="d-flex align-items-center justify-content-between flex-nowrap gap-3 h-100">

                            <input type="hidden" name="page" value="${page}">
                            <c:if test="${not empty searchkey}">
                                <input type="hidden" name="searchkey" value="${searchkey}">
                                <input type="hidden" name="totalSearchResults" value="${totalSearchResults}">
                            </c:if>
                            <c:if test="${not empty sortBy}">
                                <input type="hidden" name="sortBy" value="${sortBy}">
                                <input type="hidden" name="order" value="${order}">
                            </c:if>

                            <select name="status" class="form-select flex-fill" style="min-width:150px;">
                                <option value="All" ${status == null || status == 'All' ? 'selected' : ''}>All</option>
                                <option value="true" ${status == 'true' ? 'selected' : ''}>Active</option>
                                <option value="false" ${status == 'false' ? 'selected' : ''}>Inactive</option>
                            </select>

                            <select name="deptId" class="form-select flex-fill" style="min-width:150px;">
                                <option value="">-- All Departments --</option>
                                <c:forEach var="dep" items="${sessionScope.deptList}">
                                    <option value="${dep.depId}"
                                            <c:if test="${not empty deptId}">
                                                <c:forEach var="selectedId" items="${deptId}">
                                                    <c:if test="${selectedId eq dep.depId}">selected</c:if>
                                                </c:forEach>
                                            </c:if>>
                                        ${dep.depName}
                                    </option>
                                </c:forEach>
                            </select>

                            <select name="roleId" class="form-select flex-fill" style="min-width:150px;">
                                <option value="">-- All Roles --</option>
                                <c:forEach var="rl" items="${sessionScope.roleList}">
                                    <option value="${rl.roleId}"
                                            <c:if test="${not empty roleId}">
                                                <c:forEach var="selectedId" items="${roleId}">
                                                    <c:if test="${selectedId eq rl.roleId}">selected</c:if>
                                                </c:forEach>
                                            </c:if>>
                                        ${rl.roleName}
                                    </option>
                                </c:forEach>
                            </select>

                            <div class="d-flex align-items-center gap-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-filter"></i> Apply
                                </button>
                                <a href="${pageContext.request.contextPath}/accountlist" class="btn btn-secondary">
                                    <i class="fa fa-times"></i> Clear
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
                <div>
                    <c:if test="${not empty searchkey}">
                        <p>Found <strong>${totalSearchResults}</strong> products with search key is <strong>${searchkey}</strong></p>  
                    </c:if>
                </div>
                <div class="table-responsive" style="overflow-x:auto;">
                    <table class="table table-striped table-bordered table-hover align-middle text-center" 
                           style="table-layout: fixed; width: 100%; border-collapse: collapse;">
                        <c:set var="order" value="${order != null ? order : 'asc'}" />
                        <c:set var="nextOrder" value="${order == 'asc' ? 'desc' : 'asc'}" />

                        <thead class="thead-dark" >
                            <tr>
                                <th style="width: 50px;">STT</th>
                                <th style="cursor:pointer;width: 120px">
                                    <a class="sort" href="${urlPrefix}sortBy=emp_code&order=${nextOrder}&page=${page}">
                                        Emp Code <i class="fa fa-sort"></i>
                                    </a>
                                </th>
                                <th style="cursor:pointer;width: 120px">
                                    <a class="sort" href="${urlPrefix}sortBy=fullname&order=${nextOrder}&page=${page}">
                                        Full Name<i class="fa fa-sort"></i>
                                    </a>
                                </th>
                                <th style="cursor:pointer;width: 140px">
                                    <a class="sort" href="${urlPrefix}sortBy=email&order=${nextOrder}&page=${page}">
                                        Email<i class="fa fa-sort"></i>
                                    </a>
                                </th>
                                <th style="cursor:pointer;width: 100px">Image</th> 
                                <th style="cursor:pointer;width: 140px">
                                    <a class="sort" href="${urlPrefix}sortBy=dep_id&order=${nextOrder}&page=${page}">
                                        Department<i class="fa fa-sort"></i>
                                    </a>
                                </th>
                                <th style="width: 85px">Role</th> 
                                <th style="width: 70px">Status</th>
                                <th style="width: 70px">Edit</th>
                                <th style="width: 90px">Action</th>
                            </tr>                                 
                        </thead>
                        <tbody>
                            <c:forEach var="el" items="${sessionScope.empList}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${el.empCode}</td>
                                    <td>${el.fullname}</td>
                                    <td style="overflow-wrap: break-word;">${el.email}</td>
                                    <td>
                                        <img src="${el.image}" alt="Employee Image"
                                             style="width:60px; height:60px; object-fit:cover; border-radius:5px;">
                                    </td>
                                    <td style="overflow-wrap: break-word;">${el.dept.depName}</td>
                                    <td style="overflow-wrap: break-word;">${el.role.roleName}</td>
                                    <td>${el.status ? 'Active' : 'Inactive'}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/updateaccount?empCode=${el.empCode}"
                                           class="btn btn-sm btn-primary">
                                            Edit
                                        </a>
                                    </td>
                                    <td>
                                        <form action="accountlist" method="post">
                                            <input type="hidden" name="action" value="toggle">
                                            <input type="hidden" name="empCode" value="${el.empCode}">
                                            <input type="hidden" name="newstatus" value="${!el.status}">
                                            <input type="hidden" name="page" value="${page}">

                                            <c:if test="${not empty searchkey}">
                                                <input type="hidden" name="searchkey" value="${searchkey}">
                                            </c:if>
                                            <c:if test="${status != null}">
                                                <input type="hidden" name="status" value="${status}">
                                            </c:if>
                                            <c:if test="${not empty deptId}">
                                                <c:forEach var="d" items="${deptId}">
                                                    <input type="hidden" name="deptId" value="${d}">
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${not empty roleId}">
                                                <c:forEach var="r" items="${roleId}">
                                                    <input type="hidden" name="roleId" value="${r}">
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${not empty sortBy}">
                                                <input type="hidden" name="sortBy" value="${sortBy}">
                                                <input type="hidden" name="order" value="${order}">
                                            </c:if>

                                            <button style="width: 90px; text-align:center;"
                                                    type="submit"
                                                    class="btn ${el.status ? 'btn-danger' : 'btn-success'}">
                                                ${el.status ? 'Deactive' : 'Active'}
                                            </button>
                                        </form>
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
                <c:url var="baseUrlWithSort" value="accountlist">
                    <c:if test="${status != null}">
                        <c:param name="status" value="${status}" />
                    </c:if>
                    <c:if test="${not empty deptId}">
                        <c:forEach var="d" items="${deptId}">
                            <c:param name="deptId" value="${d}" />
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty roleId}">
                        <c:forEach var="r" items="${roleId}">
                            <c:param name="roleId" value="${r}" />
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
