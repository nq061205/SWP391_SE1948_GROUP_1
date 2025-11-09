
<%-- 
    Document   : dashboard
    Created on : Oct 4, 2025, 4:14:15 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
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
        <meta property="og:description" content="Human Tech" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="../error-404.html" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Human Tech - Raw Attendance</title>

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

        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap4.min.css">
        <style>
            .pagination .page-link {
                color: #007bff;
                border: 1px solid #dee2e6;
            }

            .pagination .page-item.active .page-link {
                background-color: #007bff;
                border-color: #007bff;
                color: white;
            }

            .pagination .page-item.disabled .page-link {
                color: #6c757d;
                background-color: #fff;
                border-color: #dee2e6;
            }

            .pagination .page-link:hover {
                color: #0056b3;
                text-decoration: none;
                background-color: #e9ecef;
                border-color: #dee2e6;
            }

            .bootstrap-select .dropdown-toggle {
                color: #000000 !important;
            }

            .bootstrap-select .dropdown-toggle .filter-option {
                color: #000000 !important;
            }

            .bootstrap-select .dropdown-menu li a {
                color: #000000 !important;
            }

            .bootstrap-select .dropdown-menu li a:hover {
                background-color: #007bff !important;
                color: #ffffff !important;
            }

            .bootstrap-select .dropdown-toggle .filter-option-inner-inner {
                color: #000000 !important;
            }/*

            #processingModal .modal-content {
                border-radius: 15px;
                box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            }

            #processingModal .spinner-border {
                border-width: 0.4rem;
            }

            #processingModal .progress {
                border-radius: 10px;
            }

            #processingModal .modal-body {
                padding: 3rem 2rem;
            }*/
        </style>

    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="../CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="../CommonItems/Navbar/empNavbar.jsp" %>
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Raw Attendance</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="hr-dashboard"><i class="fa fa-home"></i>Home</a></li>
                        <li>Attendance Management</li>
                        <li>Raw Attendance</li>
                    </ul>
                </div>
                <!-- Display Success Message if any -->
                <c:if test="${not empty success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <strong>Success!</strong> ${success}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <!-- Display Error Message if any -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error!</strong> ${error}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <!-- Upload Section -->
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-upload"></i> Import Raw Attendance Records</h4>
                            </div>
                            <div class="widget-inner">
                                <!-- Download Sample Button -->
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group text-center mb-4">
                                            <form action="download-template" method="get" class="d-inline">
                                                <button type="submit" class="btn btn-outline-secondary">
                                                    <i class="fa fa-download"></i> Download Sample Template
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>

                                <!-- Upload Form -->
                                <form action="raw-attendance" method="post" enctype="multipart/form-data">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <label for="file">Select Excel File <span class="text-danger">*</span></label>
                                                <input type="file" name="file" accept=".xlsx" class="form-control" required>
                                                <small class="form-text text-muted">Only .xlsx files are supported</small>
                                            </div>
                                        </div>
                                        <div class="col-md-4 d-flex align-items-center">
                                            <div class="form-group w-100">
                                                <button type="submit" class="btn btn-primary w-100">
                                                    <i class="fa fa-upload"></i> Upload & Preview
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Error Section: Duplicate Records Detected -->
                <c:if test="${not empty duplicateRecords}">
                    <div class="row">
                        <div class="col-lg-12 m-b30">
                            <div class="widget-box">
                                <div class="wc-title">
                                    <h4><i class="fa fa-ban"></i> Upload Failed - Duplicate Records Found</h4>
                                </div>
                                <div class="widget-inner">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <strong><i class="fa fa-exclamation-circle"></i> Cannot Upload File!</strong>
                                        <br>Found <strong>${duplicateCount}</strong> duplicate record(s) out of <strong>${totalCount}</strong> total records.
                                        <br><br>
                                        <strong>Action Required:</strong>
                                        <ol>
                                            <li>Please check your Excel file</li>
                                            <li>Remove or modify the duplicate records below</li>
                                            <li>Upload the corrected file</li>
                                        </ol>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>

                                    <!-- Duplicate Records Table -->
                                    <h5 class="mt-4 mb-3"><i class="fa fa-list"></i> Duplicate Records:</h5>
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered table-danger">
                                            <thead class="table-danger">
                                                <tr>
                                                    <th width="50">No.</th>
                                                    <th width="150">Employee ID</th>
                                                    <th width="150">Date</th>
                                                    <th width="150">Check Time</th>
                                                    <th width="100">Check Type</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="row" items="${duplicateRecords}" varStatus="status">
                                                    <c:if test="${status.index < 20}">
                                                        <tr>
                                                            <td class="text-center">
                                                                <span class="badge badge-danger">${status.index + 1}</span>
                                                            </td>
                                                            <td><strong>${row.emp.empId}</strong></td>
                                                            <td><strong>${row.date}</strong></td>
                                                            <td><strong>${row.checkTime}</strong></td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${row.checkType == 'IN'}">
                                                                        <span class="badge badge-success">IN</span>
                                                                    </c:when>
                                                                    <c:when test="${row.checkType == 'OUT'}">
                                                                        <span class="badge badge-danger">OUT</span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>

                                                <c:if test="${fn:length(duplicateRecords) > 20}">
                                                    <tr>
                                                        <td colspan="5" class="text-center text-muted">
                                                            ... and ${fn:length(duplicateRecords) - 20} other duplicates ...
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty preview}">
                    <div class="row">
                        <div class="col-lg-12 m-b30">
                            <div class="widget-box">
                                <div class="wc-title">
                                    <h4><i class="fa fa-eye"></i> Preview Records</h4>
                                    <span class="badge badge-info">Showing first ${preview.size()} records</span>
                                </div>
                                <div class="widget-inner">
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered">
                                            <thead class="thead-dark">
                                                <tr>
                                                    <th width="50">No.</th>
                                                    <th width="200">Employee ID</th>
                                                    <th width="200">Date</th>
                                                    <th>Check Time</th>
                                                    <th width="200">Check Type</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="row" items="${preview}" varStatus="status">
                                                    <tr>
                                                        <td class="text-center">
                                                            <span class="badge badge-secondary">${status.index + 1}</span>
                                                        </td>
                                                        <td>${row.emp.empId}</td>
                                                        <td>${row.date}</td>
                                                        <td>${row.checkTime}</td>
                                                        <td class="text-center">
                                                            <c:choose>
                                                                <c:when test="${row.checkType == 'IN'}">
                                                                    <span class="badge badge-success">IN</span>
                                                                </c:when>
                                                                <c:when test="${row.checkType == 'OUT'}">
                                                                    <span class="badge badge-danger">OUT</span>
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>

                                    <!-- Action Buttons -->
                                    <div class="row">
                                        <div class="col-md-12 text-center">
                                            <form action="raw-attendance" method="post" class="d-inline mr-2">
                                                <input type="hidden" name="action" value="confirm">
                                                <button type="submit" class="btn btn-success btn-lg">
                                                    <i class="fa fa-check-circle"></i> Import
                                                </button>
                                            </form>
                                            <form action="raw-attendance" method="post" class="d-inline">
                                                <input type="hidden" name="action" value="cancel">
                                                <button type="submit" class="btn btn-secondary btn-lg">
                                                    <i class="fa fa-times-circle"></i> Cancel
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>

                <!-- Error Details Section -->
                <c:if test="${not empty errorDetails}">
                    <div class="row">
                        <div class="col-lg-12 m-b30">
                            <div class="widget-box">
                                <div class="wc-title">
                                    <h4><i class="fa fa-exclamation"></i> Validation Errors</h4>
                                </div>
                                <div class="widget-inner">
                                    <div class="alert alert-danger">
                                        <ul class="mb-0">
                                            <c:forEach var="error" items="${errorDetails}">
                                                <li>${error}</li>
                                                </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>

                <!-- Raw Attendance List -->
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-list"></i> Raw Attendance Records</h4>
                                <span class="badge badge-primary">Total: ${totalRecords} records</span>
                            </div>
                            <div class="widget-inner">
                                <!-- Filter Form -->
                                <form method="get" action="raw-attendance" class="mb-4" id="filterForm">
                                    <div class="row g-3 align-items-end">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="date"><i class="fa fa-calendar"></i> Select Date</label>
                                                <input type="date" name="date" value="${date}" 
                                                       class="form-control form-control-lg" id="date" 
                                                       onchange="resetPageAndSubmit()" required>
                                            </div>
                                        </div>
                                        <!-- Department Filter -->
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label for="department"><i class="fa fa-building"></i> Department</label>
                                                <select name="department" class="form-control" id="department" 
                                                        onchange="resetPageAndSubmit()">

                                                    <option value="">All Departments</option>
                                                    <c:forEach var="dept" items="${departments}">
                                                        <option value="${dept.depId}" ${department == dept.depId ? 'selected' : ''}>
                                                            ${dept.depName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- Check Type Filter -->
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label for="filterType"><i class="fa fa-filter"></i> Check Type</label>
                                                <select name="filterType" class="form-control" id="filterType" 
                                                        onchange="resetPageAndSubmit()">
                                                    <option value="">All Types</option>
                                                    <option value="IN" ${filterType == 'IN' ? 'selected' : ''}>Check IN</option>
                                                    <option value="OUT" ${filterType == 'OUT' ? 'selected' : ''}>Check OUT</option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- Search Box -->
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="search"><i class="fa fa-search"></i> Search Employee</label>
                                                <input type="text" name="search" value="${search}" 
                                                       class="form-control" id="search"
                                                       placeholder="Code or Name..."
                                                       onchange="resetPageAndSubmit()">
                                            </div>
                                        </div>

                                        <!-- Reset Button -->
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <a href="raw-attendance" class="btn btn-secondary btn-block">
                                                    <i class="fa fa-refresh"></i> Reset
                                                </a>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Page Size Selection -->
                                    <div class="row mt-3">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="pageSize">Records per page</label>
                                                <select name="pageSize" class="form-control" id="pageSize" 
                                                         onchange="resetPageAndSubmit()"">
                                                    <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                                    <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                                                    <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                                                    <option value="100" ${pageSize == 100 ? 'selected' : ''}>100</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-9 text-right">
                                            <p class="text-muted mt-4">
                                                Showing ${(currentPage - 1) * pageSize + 1} to 
                                                ${currentPage * pageSize > totalRecords ? totalRecords : currentPage * pageSize} 
                                                of ${totalRecords} entries
                                            </p>
                                        </div>
                                    </div>

                                    <input type="hidden" name="page" value="${currentPage}" id="pageInput">
                                </form>

                                <!-- Results Table -->
                                <c:choose>
                                    <c:when test="${not empty rawList}">
                                        <div class="table-responsive">
                                            <table class="table table-striped table-bordered table-hover">
                                                <thead class="thead-dark">
                                                    <tr>
                                                        <th width="50" class="text-center">#</th>
                                                        <th width="120">Employee Code</th>
                                                        <th width="200">Full Name</th>
                                                        <th width="150">Department</th>
                                                        <th width="100">Check Time</th>
                                                        <th width="100" class="text-center">Type</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="record" items="${rawList}" varStatus="status">
                                                        <tr>
                                                            <td class="text-center">
                                                                <span class="badge badge-secondary">
                                                                    ${(currentPage - 1) * pageSize + status.index + 1}
                                                                </span>
                                                            </td>
                                                            <td>
                                                                <strong class="text-primary">${record.emp.empCode}</strong>
                                                            </td>
                                                            <td>
                                                                <span class="font-weight-bold">${record.emp.fullname}</span>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty record.emp.dept}">
                                                                        <span class="badge badge-info">${record.emp.dept.depName}</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">-</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <span class="text-primary font-weight-bold">${record.checkTime}</span>
                                                            </td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${record.checkType == 'IN'}">
                                                                        <span class="badge badge-success badge-lg">
                                                                            <i class="fa fa-sign-in"></i> IN
                                                                        </span>
                                                                    </c:when>
                                                                    <c:when test="${record.checkType == 'OUT'}">
                                                                        <span class="badge badge-danger badge-lg">
                                                                            <i class="fa fa-sign-out"></i> OUT
                                                                        </span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>

                                        <!-- Pagination Controls -->
                                        <c:if test="${totalPages > 1}">
                                            <nav aria-label="Page navigation">
                                                <ul class="pagination justify-content-center">
                                                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                                        <c:url var="prevUrl" value="raw-attendance">
                                                            <c:param name="page" value="${currentPage - 1}"/>
                                                            <c:param name="pageSize" value="${pageSize}"/>
                                                            <c:param name="date" value="${date}"/>
                                                            <c:if test="${not empty search}"><c:param name="search" value="${search}"/></c:if>
                                                            <c:if test="${not empty filterType}"><c:param name="filterType" value="${filterType}"/></c:if>
                                                            <c:if test="${not empty department}"><c:param name="department" value="${department}"/></c:if>
                                                        </c:url>
                                                        <a class="page-link" href="${currentPage > 1 ? prevUrl : '#'}">
                                                            <i class="fa fa-chevron-left"></i> Previous
                                                        </a>
                                                    </li>

                                                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                                        <c:if test="${pageNum == 1 || pageNum == totalPages || (pageNum >= currentPage - 2 && pageNum <= currentPage + 2)}">
                                                            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                                                <c:url var="pageUrl" value="raw-attendance">
                                                                    <c:param name="page" value="${pageNum}"/>
                                                                    <c:param name="pageSize" value="${pageSize}"/>
                                                                    <c:param name="date" value="${date}"/>
                                                                    <c:if test="${not empty search}"><c:param name="search" value="${search}"/></c:if>
                                                                    <c:if test="${not empty filterType}"><c:param name="filterType" value="${filterType}"/></c:if>
                                                                    <c:if test="${not empty department}"><c:param name="department" value="${department}"/></c:if>
                                                                </c:url>
                                                                <a class="page-link" href="${pageUrl}">${pageNum}</a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(pageNum == currentPage - 3 && pageNum > 1) || (pageNum == currentPage + 3 && pageNum < totalPages)}">
                                                            <li class="page-item disabled"><span class="page-link">...</span></li>
                                                            </c:if>
                                                        </c:forEach>

                                                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                                        <c:url var="nextUrl" value="raw-attendance">
                                                            <c:param name="page" value="${currentPage + 1}"/>
                                                            <c:param name="pageSize" value="${pageSize}"/>
                                                            <c:param name="date" value="${date}"/>
                                                            <c:if test="${not empty search}"><c:param name="search" value="${search}"/></c:if>
                                                            <c:if test="${not empty filterType}"><c:param name="filterType" value="${filterType}"/></c:if>
                                                            <c:if test="${not empty department}"><c:param name="department" value="${department}"/></c:if>
                                                        </c:url>
                                                        <a class="page-link" href="${currentPage < totalPages ? nextUrl : '#'}">
                                                            Next <i class="fa fa-chevron-right"></i>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </nav>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center py-5">
                                            <div class="mb-3">
                                                <i class="fa fa-inbox fa-3x text-muted"></i>
                                            </div>
                                            <h5 class="text-muted">No Attendance Records Found</h5>
                                            <p class="text-muted">No attendance records for ${date}</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Loading Modal -->
                <div class="modal fade" id="processingModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-body text-center py-5">
                                <div class="spinner-border text-primary mb-4" role="status" style="width: 4rem; height: 4rem;">
                                    <span class="sr-only">Processing...</span>
                                </div>
                                <h3 class="mb-3"><i class="fa fa-cog fa-spin"></i> Processing Import</h3>

                                <!-- THÊM ID ĐỂ UPDATE MESSAGES -->
                                <p class="text-muted mb-2 lead" id="processingMessage" style="min-height: 30px;">
                                    <strong>Preparing to import...</strong>
                                </p>

                                <div class="alert alert-warning mt-4 mb-0">
                                    <i class="fa fa-exclamation-triangle"></i>
                                    <strong>Please do not close this window or navigate away</strong>
                                </div>

                                <div class="progress mt-4" style="height: 30px;">
                                    <div class="progress-bar progress-bar-striped progress-bar-animated bg-success" 
                                         role="progressbar" style="width: 100%">
                                        <strong>Processing...</strong>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </main>
    </body>

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
    <script src="${pageContext.request.contextPath}/assets2/vendors/calendar/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.js"></script>
    <script src="${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js"></script>
    <script>
                                                            let isProcessing = false;

                                                            $(document).ready(function () {
                                                                $('form').on('submit', function (e) {
                                                                    const action = $(this).find('input[name="action"]').val();

                                                                    if (action === 'confirm') {
                                                                        e.preventDefault();

                                                                        const form = this;

                                                                        // Show loading modal
                                                                        $('#processingModal').modal({
                                                                            backdrop: 'static',
                                                                            keyboard: false
                                                                        });

                                                                        isProcessing = true;

                                                                        // Disable all buttons
                                                                        $('button, a.btn').prop('disabled', true).addClass('disabled');

                                                                        // Animate processing messages
                                                                        const messages = [
                                                                            '<i class="fa fa-check text-success"></i> Validating data...',
                                                                            '<i class="fa fa-database text-primary"></i> Importing raw attendance records...',
                                                                            '<i class="fa fa-users text-info"></i> Loading employee information...',
                                                                            '<i class="fa fa-calculator text-warning"></i> Calculating work hours...',
                                                                            '<i class="fa fa-clock-o text-primary"></i> Processing overtime requests...',
                                                                            '<i class="fa fa-check-circle text-success"></i> Finalizing daily attendance...'
                                                                        ];

                                                                        const messageElement = $('#processingMessage');
                                                                        let step = 0;

                                                                        // Update message every 500ms
                                                                        const messageInterval = setInterval(function () {
                                                                            if (step < messages.length) {
                                                                                messageElement.fadeOut(200, function () {
                                                                                    $(this).html(messages[step]).fadeIn(200);
                                                                                });
                                                                                step++;
                                                                            } else {
                                                                                clearInterval(messageInterval);
                                                                            }
                                                                        }, 800);

                                                                        // DELAY SUBMIT ĐỂ MODAL HIỂN THỊ ÍT NHẤT 3 GIÂY
                                                                        setTimeout(function () {
                                                                            form.submit();
                                                                        }, 3000); // 3 seconds - đủ thời gian để user đọc messages
                                                                    }
                                                                });
                                                            });

                                                            function resetPageAndSubmit(formId = 'filterForm') {
                                                                document.getElementById('pageInput').value = '1';
                                                                document.getElementById(formId).submit();
                                                            }
    </script>



</html>
