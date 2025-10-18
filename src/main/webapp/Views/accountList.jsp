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
        <meta name="description" content="EduChamp : Education HTML Template" />

        <!-- OG -->
        <meta property="og:title" content="EduChamp : Education HTML Template" />
        <meta property="og:description" content="Profile" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>EduChamp : Education HTML Template</title>

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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- HEADER + NAVBAR -->
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/adminNavbar.jsp" %>
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


                <div class="row">
                    <div class="col-md-2">
                        <form action="${pageContext.request.contextPath}/accountlist" method="get" class=" mb-3">
                            <div class="mb-3">                                                    
                                <input type="hidden" name="page" value="${page}">
                                <c:if test="${not empty searchkey}">
                                    <input type="hidden" name="searchkey" value="${searchkey}">
                                    <input type="hidden" name="totalSearchResults" value="${totalSearchResults}">
                                </c:if>
                                <c:if test="${not empty sortBy}">
                                    <input type="hidden" name="sortBy" value="${sortBy}">
                                    <input type="hidden" name="order" value="${order}">
                                </c:if>      
                                <label class="form-label"><strong>Status:</strong></label>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="status" value="true" 
                                           ${status == 'true' ? 'checked' : ''} id="statusActive" onclick="this.form.submit()">
                                    <label class="form-check-label" for="statusActive">Active</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="status" value="false" 
                                           ${status == 'false' ? 'checked' : ''} id="statusInactive" onclick="this.form.submit()">
                                    <label class="form-check-label" for="statusInactive">Inactive</label>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Department:</strong></label>
                                <c:forEach var="dep" items="${sessionScope.deptList}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="deptId" value="${dep.depId}" 
                                               <c:choose>
                                                   <c:when test="${not empty deptId}">
                                                       <c:forEach var="selectedId" items="${deptId}">
                                                           <c:if test="${selectedId eq dep.depId}">checked</c:if>
                                                       </c:forEach>
                                                   </c:when>
                                               </c:choose> onclick="this.form.submit()">
                                        <label class="form-check-label" for="dep${dep.depId}">${dep.depName}</label>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="mb-3">
                                <label class="form-label"><strong>Role:</strong></label>
                                <c:forEach var="rl" items="${sessionScope.roleList}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="roleId" value="${rl.roleId}" 
                                               <c:choose>
                                                   <c:when test="${not empty roleId}">
                                                       <c:forEach var="selectedId" items="${roleId}">
                                                           <c:if test="${selectedId eq rl.roleId}">checked</c:if>
                                                       </c:forEach>
                                                   </c:when>
                                               </c:choose> id="rl${rl.roleId}" onclick="this.form.submit()">
                                        <label class="form-check-label" for="rl${rl.roleId}">${rl.roleName}</label>
                                    </div>
                                </c:forEach>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-10">
                        <div>
                            <form action="${pageContext.request.contextPath}/accountlist" method="get" class="d-flex align-items-center justify-content-between mb-3">
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
                                <input type="text" name="searchkey" class="form-control border-start-0" placeholder="Search by code or name" value="${searchkey}">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </form>

                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <c:if test="${not empty searchkey}">
                                    <p>Found <strong>${totalSearchResults}</strong> products with search key is <strong>${searchkey}</strong></p>  
                                </c:if>
                            </div>

                            <div class="col-md-8" style="display: flex; align-items: center; gap: 15px;">
                                <p style="margin: 1%;">Sort by:</p>
                                <div style="display: flex; gap: 20px;">
                                    <c:set var="urlPrefix" value="${baseUrl}${fn:contains(baseUrl, '?') ? '&' : '?'}" />
                                    Code:
                                    <div style="display: flex; flex-direction: column; line-height: 1.2;">
                                        <a href="${urlPrefix}sortBy=emp_code&order=asc&page=${page}" class="sort-link">ASC</a>
                                        <a href="${urlPrefix}sortBy=emp_code&order=desc&page=${page}" class="sort-link">DESC</a>
                                    </div>
                                    Name:
                                    <div style="display: flex; flex-direction: column; line-height: 1.2;">
                                        <a href="${urlPrefix}sortBy=fullname&order=asc&page=${page}" class="sort-link">ASC</a>
                                        <a href="${urlPrefix}sortBy=fullname&order=desc&page=${page}" class="sort-link">DESC</a>
                                    </div>
                                    Email:
                                    <div style="display: flex; flex-direction: column; line-height: 1.2;">
                                        <a href="${urlPrefix}sortBy=email&order=asc&page=${page}" class="sort-link">ASC</a>
                                        <a href="${urlPrefix}sortBy=email&order=desc&page=${page}" class="sort-link">DESC</a>
                                    </div>
                                    Department:
                                    <div style="display: flex; flex-direction: column; line-height: 1.2;">
                                        <a href="${urlPrefix}sortBy=dep_id&order=asc&page=${page}" class="sort-link">ASC</a>
                                        <a href="${urlPrefix}sortBy=dep_id&order=desc&page=${page}" class="sort-link">DESC</a>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover align-middle text-center">
                                <thead class="thead-dark" >
                                    <tr>
                                        <th>ID</th>
                                        <th>Employee Code</th>
                                        <th>Full Name</th>
                                        <th>Email</th>
                                        <th>Image</th>
                                        <th>Department</th>
                                        <th>Role</th>
                                        <th>Status</th>
                                        <th>Edit</th>
                                        <th>Action</th>
                                    </tr>                                 
                                </thead>
                                <tbody>

                                    <c:forEach var="el" items="${sessionScope.empList}" varStatus ="loop">
                                        <tr>
                                            <c:choose>
                                                <c:when test="${editEmp != null && editEmp.empCode eq el.empCode}">
                                            <form action="${pageContext.request.contextPath}/accountlist" method="post">
                                                <input type="hidden" name="page" value="${page}">
                                                <td>${loop.index+1}</td>
                                                <td><input type="hidden" name="empCode" value="${el.empCode}" />${el.empCode}</td>
                                                <td>${el.fullname}</td>
                                                <td>
                                                    <input type="text" name="email" value="${el.email}" />
                                                    <span style="color:red;">${emailError}</span>
                                                </td>
                                                <td>${el.image}</td>
                                                <td>
                                                    ${el.dept.depName}
                                                </td>
                                                <td>
                                                    <select name="roleId">
                                                        <c:forEach var="r" items="${sessionScope.roleList}">
                                                            <option value="${r.roleId}">${r.roleName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>${el.status ?'Active' :'Inactive'}</td>
                                                <td>
                                                    <button type="submit" name="action" value="save" class="btn btn-success btn-sm">Save</button>
                                                    <a href="${pageContext.request.contextPath}/accountlist" class="btn btn-secondary btn-sm">Cancel</a>
                                                </td>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${loop.index+1}</td>
                                            <td>${el.empCode}</td>
                                            <td>${el.fullname}</td>
                                            <td>${el.email}</td>
                                            <td>${el.image}</td>
                                            <td>${el.dept.depName}</td>
                                            <td>${el.role.roleName}</td>
                                            <td>${el.status ? 'Active' :'Inactive'}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/accountlist?type=edit&empCode=${el.empCode}&page=${page}" class="btn btn-sm btn-primary">Edit</a>
                                            </td>
                                            <td>
                                                <form action="accountlist" method="post">
                                                    <input type="hidden" name="action" value="toggle">
                                                    <input type="hidden" name="empCode" value="${el.empCode}">
                                                    <input type="hidden" name="newstatus" value="${!el.status}">
                                                    <input type="hidden" name="page" value="${page}">
                                                    <button type="submit"
                                                            class="btn ${el.status ? 'btn-danger' : 'btn-success'}">
                                                        ${el.status ? 'Deactivate' : 'Activate'}
                                                    </button>
                                                </form>
                                            </td>                                            
                                        </c:otherwise>
                                    </c:choose>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <c:set var="maxPagesToShow" value="5" />
                        <c:set var="halfPagesToShow" value="${(maxPagesToShow-1) / 2}" />

                        <c:set var="startPage" value="${page - halfPagesToShow}" />
                        <c:set var="endPage" value="${page + halfPagesToShow}" />

                        <c:if test="${startPage < 1}"><c:set var="startPage" value="1" /></c:if>
                        <c:if test="${endPage > totalPages}"><c:set var="endPage" value="${totalPages}" /></c:if>

                            <div class="pagination">

                            <c:if test="${page > 1}">
                                <a href="${urlPrefix}page=${page - 1}<c:if test='${not empty sortBy}'>&sortBy=${sortBy}&order=${order}</c:if>">&laquo;Prev</a>
                            </c:if>

                            <c:if test="${startPage > 1}">
                                <a href="${urlPrefix}page=1<c:if test='${not empty sortBy}'>&sortBy=${sortBy}&order=${order}</c:if>">1</a>
                                    <span>...</span>
                            </c:if>

                            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                <c:choose>
                                    <c:when test="${i == page}">
                                        <span class="current">${i}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${urlPrefix}page=${i}<c:if test='${not empty sortBy}'>&sortBy=${sortBy}&order=${order}</c:if>">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${endPage < totalPages}">
                                <span>...</span>
                                <a href="${urlPrefix}page=${totalPages}<c:if test='${not empty sortBy}'>&sortBy=${sortBy}&order=${order}</c:if>">${totalPages}</a>
                            </c:if>
                            <c:if test="${page < totalPages}">
                                <a href="${urlPrefix}page=${page + 1}<c:if test='${not empty sortBy}'>&sortBy=${sortBy}&order=${order}</c:if>">Next &raquo;</a>
                            </c:if>
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
        <script src='${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js'></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
        <script src='${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js'></script>
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
                border: none;           /* bỏ viền mặc định */
                background: none;       /* bỏ nền mặc định */
                padding: 0;             /* bỏ khoảng cách bên trong */
                cursor: pointer;        /* hiện con trỏ tay khi hover */
                color: inherit;
            }
            .pagination a {
                padding: 5px 10px;
                margin: 2px;
                border: 1px solid #ccc;
                text-decoration: none;
                color: #333;
            }
            .pagination a:hover {
                background-color: #eee;
            }
            .pagination span.current {
                padding: 5px 10px;
                margin: 2px;
                font-weight: bold;
                background-color: #333;
                color: white;
            }

            .icon-circle:hover {
                background-color: #d5d5d5; /* xám đậm hơn khi hover */
                color: #000;
            }
            #candidateModal {
                display: none;
                position: relative; /* giữ vị trí ngay dưới button */
                margin-top: 15px; /* khoảng cách với nút Add Employee */
                z-index: 1;
            }

            #candidateModal .modal-content {
                background-color: #ffffff;
                border: 1px solid #ccc;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            }


            #candidateModal .close {
                color: #555;
                float: right;
                font-size: 20px;
                font-weight: bold;
                cursor: pointer;
            }

            #candidateModal .close:hover {
                color: #000;
            }

            #candidateModal table {
                margin-top: 10px;
            }

            #candidateModal table thead {
                background-color: #007bff;
                color: #fff;
            }

            #candidateModal table td,
            #candidateModal table th {
                text-align: center;
                vertical-align: middle;
            }
            .sort-link {
                display: inline-block;
                padding: 6px 12px;
                margin: 2px;
                background-color: #4CAF50; /* màu nền */
                color: white; /* màu chữ */
                text-decoration: none; /* bỏ gạch chân */
                border-radius: 5px; /* bo tròn góc */
                font-weight: bold;
                transition: background-color 0.3s, transform 0.2s;
            }

            .sort-link:hover {
                background-color: #45a049; /* màu khi hover */
                transform: scale(1.05); /* phóng to nhẹ khi hover */
            }
            .pagination {
                display: flex;
                gap: 8px;
                margin-top: 20px;
                justify-content: center;
                align-items: center;
                font-family: Arial, sans-serif;
            }

            .pagination a {
                display: inline-block;
                padding: 6px 12px;
                text-decoration: none;
                color: #007bff;
                border: 1px solid #dee2e6;
                border-radius: 5px;
                transition: 0.2s;
                font-size: 14px;
            }

            .pagination a:hover {
                background-color: #007bff;
                color: white;
                border-color: #007bff;
            }

            .pagination a.active {
                background-color: #007bff;
                color: white;
                border-color: #007bff;
                font-weight: bold;
                cursor: default;
            }

        </style>
    </body>
</html>
