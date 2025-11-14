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
        <title>Department List</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- All PLUGINS CSS ============================================= -->
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
        </style> 

    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <c:url var="baseUrl" value="departmentlist">
                    <c:if test="${not empty param.deptId}">
                        <c:param name="deptId" value="${param.deptId}" />
                    </c:if>
                    <c:if test="${not empty searchkey}">
                        <c:param name="searchkey" value="${searchkey}" />
                    </c:if>
                </c:url>
                <c:set var="urlPrefix" value="${baseUrl}${fn:contains(baseUrl, '?') ? '&' : '?'}" />
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Department Listing</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/departmentlist">Department list</a></li>
                    </ul>
                </div>

                <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addModal">
                    + Add Department
                </button>
                <div class="filter-row mb-3" style="margin: 0 1%; display:flex; align-items:center; gap:35px;">
                    <form action="${pageContext.request.contextPath}/departmentlist" method="get"
                          class="d-flex align-items-center gap-2 flex-grow-1">
                        <input type="hidden" name="page" value="${param.page}">
                        <c:if test="${not empty sortBy}">
                            <input type="hidden" name="sortBy" value="${sortBy}">
                            <input type="hidden" name="order" value="${order}">
                        </c:if>
                        <c:if test="${not empty param.deptId}">
                            <input type="hidden" name="deptId" value="${param.deptId}" />
                        </c:if>
                        <input type="text" name="searchkey" class="form-control filter-h"
                               placeholder="Search by id or name"
                               value="${searchkey}">
                        <button type="submit" class="btn btn-primary">
                            <i class="fa fa-search me-1"></i> Search
                        </button>
                    </form>
                    <form action="${pageContext.request.contextPath}/departmentlist" method="get"
                          class="d-flex align-items-center gap-2" style="min-width:250px;gap: 25px">
                        <c:if test="${not empty searchkey}">
                            <input type="hidden" name="searchkey" value="${searchkey}">
                            <input type="hidden" name="totalSearchResults" value="${totalSearchResults}">
                        </c:if>
                        <c:if test="${not empty sortBy}">
                            <input type="hidden" name="sortBy" value="${sortBy}">
                            <input type="hidden" name="order" value="${order}">
                        </c:if>
                        <div style="min-width:400px;">
                            <select name="deptId" class="form-control filter-h" style="height:45px; font-size:1.1rem;">
                                <option value="">-- All --</option>
                                <c:forEach var="dl" items="${deptNameList}">
                                    <option value="${dl.depId}" <c:if test="${param.deptId == dl.depId}">selected</c:if>>
                                        ${dl.depName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div style="display:flex;gap: 30px">
                            <button type="submit" class="btn btn-outline-secondary filter-h">
                                <i class="fa fa-filter"></i> Apply
                            </button>
                            <a href="${pageContext.request.contextPath}/departmentlist"
                               class="btn btn-warning filter-h">
                                <i class="fa fa-times"></i> Clear
                            </a>
                        </div>
                    </form>

                </div>
                <div class="modal fade" id="addModal">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <form action="departmentlist" method="post">
                                <div class="modal-header">
                                    <h5 class="modal-title">Add New Department</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal">X</button>
                                </div>

                                <div class="modal-body">
                                    <label class="form-label mt-2">Department Name</label>
                                    <input type="text" name="deptName" class="form-control" required maxlength="30">

                                    <label class="form-label mt-2">Description</label>
                                    <textarea name="description" class="form-control" required maxlength="200"></textarea>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    <button type="submit" name="action" value="add" class="btn btn-success">Add</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
                <div class="table-responsive" style="overflow-x:auto;">
                    <table class="table table-striped table-bordered table-hover align-middle text-center" 
                           style="table-layout: fixed; width: 100%; border-collapse: collapse;">
                        <c:set var="order" value="${order != null ? order : 'asc'}" />
                        <c:set var="nextOrder" value="${order == 'asc' ? 'desc' : 'asc'}" />
                        <thead class="thead-dark">
                            <tr style="text-align: center">
                                <th>No</th>
                                <th>
                                    <a class="sort" href="${urlPrefix}sortBy=dep_id&order=${nextOrder}&page=${page}">
                                        Department ID <i class="fa fa-sort"></i>
                                    </a>
                                </th>
                                <th>
                                    <a class="sort" href="${urlPrefix}sortBy=dep_name&order=${nextOrder}&page=${page}">
                                        Department Name <i class="fa fa-sort"></i>
                                    </a>
                                </th>
                                <th>Description</th>
                                <th>Action</th>
                            </tr>                                 
                        </thead>
                        <tbody>
                            <c:forEach var="el" items="${sessionScope.deptList}" varStatus ="loop">
                                <tr style="text-align:center">
                                    <td style="overflow-wrap: break-word;">${loop.index+1}</td>
                                    <td style="overflow-wrap: break-word;">${el.depId}</td>
                                    <td style="overflow-wrap: break-word;">${el.depName}</td>
                                    <td style="overflow-wrap: break-word;">${el.description}</td>                                           
                                    <td>
                                        <div style="display: flex;gap: 30px">
                                            <a style="width: 50%" href="${pageContext.request.contextPath}/updatedepartment?depId=${el.depId}" class="btn btn-sm btn-primary">Edit</a>
                                            <a style="width:50%" href="${pageContext.request.contextPath}/departmentdetail?deptId=${el.depId}" class="btn btn-sm btn-primary">View</a>
                                        </div>
                                    </td>                                                   
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:url var="baseUrlWithSort" value="departmentlist">
                    <c:if test="${not empty deptId}">
                        <c:forEach var="d" items="${deptId}">
                            <c:param name="deptId" value="${d}" />
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty searchkey}">
                        <c:param name="searchkey" value="${searchkey}" />
                    </c:if>
                    <c:if test="${not empty sortBy}">
                        <input type="hidden" name="sortBy" value="${sortBy}">
                        <input type="hidden" name="order" value="${order}">
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
        </main>
        <c:if test="${not empty errorMessage}">
            <script>
                window.onload = function () {
                    var myModal = new bootstrap.Modal(document.getElementById('addModal'));
                    myModal.show();
                };
            </script>
        </c:if>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

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

        </style>
    </body>
</html>

