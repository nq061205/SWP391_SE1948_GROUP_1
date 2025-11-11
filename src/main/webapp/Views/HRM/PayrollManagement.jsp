<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
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
        <title>My Profile</title>

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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/payroll-management-style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <<<<<<< HEAD
        =======
        <style>
            .bg-success-light {
                background-color: #d4edda !important;
            }
            .align-middle {
                vertical-align: middle !important;
            }
            .salary-report-table {
                font-size: 0.9rem;
            }
            .salary-report-table th {
                font-weight: 600;
                white-space: nowrap;
            }
            .salary-report-table td {
                vertical-align: middle;
            }
            /* Sticky columns */
            .sticky-table th:nth-child(1),
            .sticky-table td:nth-child(1) {
                position: sticky;
                left: 0;
                z-index: 5;
                box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            }

            .sticky-table thead th:nth-child(1) {
                z-index: 10;
                background-color: #343a40 !important; /* Đen */
            }

            .sticky-table tbody td:nth-child(1) {
                background-color: #fff;
            }

            .sticky-table th:nth-child(2),
            .sticky-table td:nth-child(2) {
                position: sticky;
                left: 100px;
                z-index: 5;
                box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            }

            .sticky-table thead th:nth-child(2) {
                z-index: 10;
                background-color: #343a40 !important; /* Đen */
            }

            .sticky-table tbody td:nth-child(2) {
                background-color: #fff;
            }

            /* Tất cả header đều đen */
            .sticky-table thead th {
                position: sticky;
                top: 0;
                z-index: 8;
                background-color: #343a40 !important; /* Đen */
                color: #fff !important; /* Chữ trắng */
            }

            /* Total row */
            .sticky-table .table-active td:nth-child(1),
            .sticky-table .table-active td:nth-child(2) {
                background-color: #e9ecef !important;
            }
        </style>

        >>>>>>> aa6d9bace69408ff6703f36ab3fba07df816e713
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="../CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="../CommonItems/Navbar/empNavbar.jsp" %>
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <!-- Breadcrumb -->
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">
                        <i class="fa fa-money"></i> Monthly Payroll Report
                    </h4>

                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>Payroll Management</li>
                        <li>Monthly Payroll Report</li>
                    </ul>
                </div>
                <c:if test="${isPayrollLocked}">
                    <span class="badge badge-danger ml-2">🔒 LOCKED</span>
                </c:if>
                <!-- Alert Messages -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show">
                        <strong>Success!</strong> ${successMessage}
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                    </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <strong>Error!</strong> ${errorMessage}
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                    </div>
                </c:if>

                <!-- Main Content -->
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <div class="float-right">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fa fa-download"></i> Export
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <a class="dropdown-item" href="#" onclick="exportPayroll('excel')">
                                                <i class="fa fa-file-excel-o text-success"></i> Export to Excel
                                            </a>
                                            <a class="dropdown-item" href="#" onclick="exportPayroll('pdf')">
                                                <i class="fa fa-file-pdf-o text-danger"></i> Export to PDF
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="widget-inner">
                                <!-- Filters -->
                                <form method="get" action="monthly-payroll" id="filterForm" class="mb-4">

                                    <div class="row">
                                        <!-- Month -->
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label for="selectedMonth">Month</label>
                                                <select name="month" id="selectedMonth" class="form-control" onchange="applyFilter()">
                                                    <option value="1"  ${selectedMonth == 1 ? 'selected' : ''}>January</option>
                                                    <option value="2"  ${selectedMonth == 2 ? 'selected' : ''}>February</option>
                                                    <option value="3"  ${selectedMonth == 3 ? 'selected' : ''}>March</option>
                                                    <option value="4"  ${selectedMonth == 4 ? 'selected' : ''}>April</option>
                                                    <option value="5"  ${selectedMonth == 5 ? 'selected' : ''}>May</option>
                                                    <option value="6"  ${selectedMonth == 6 ? 'selected' : ''}>June</option>
                                                    <option value="7"  ${selectedMonth == 7 ? 'selected' : ''}>July</option>
                                                    <option value="8"  ${selectedMonth == 8 ? 'selected' : ''}>August</option>
                                                    <option value="9"  ${selectedMonth == 9 ? 'selected' : ''}>September</option>
                                                    <option value="10" ${selectedMonth == 10 ? 'selected' : ''}>October</option>
                                                    <option value="11" ${selectedMonth == 11 ? 'selected' : ''}>November</option>
                                                    <option value="12" ${selectedMonth == 12 ? 'selected' : ''}>December</option>
                                                </select>
                                            </div>
                                        </div>

                                        <!-- Year -->
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label for="selectedYear">Year</label>
                                                <select name="year" id="selectedYear" class="form-control" onchange="applyFilter()">
                                                    <c:forEach var="y" begin="${startYear}" end="${endYear}">
                                                        <option value="${y}" ${selectedYear == y ? 'selected' : ''}>${y}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <!-- Calculate Button -->
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>&nbsp;</label>
                                                <button type="button" 
                                                        class="btn btn-primary btn-block" 
                                                        onclick="calculatePayroll()"
                                                        ${isPayrollLocked ? 'disabled title="Payroll is locked"' : ''}>
                                                    <i class="fa fa-calculator"></i> Calculate Payroll
                                                </button>

                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <!-- Department -->
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="departmentFilter">Department</label>
                                                <select name="department" id="departmentFilter" class="form-control" onchange="applyFilter()">
                                                    <option value="">All Departments</option>
                                                    <c:forEach var="dept" items="${departments}">
                                                        <option value="${dept.depId}" ${selectedDepartment == dept.depId ? 'selected' : ''}>${dept.depName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <!-- Search -->
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label for="searchInput">Search</label>
                                                <div class="input-group">
                                                    <input type="text" name="search" id="searchInput"
                                                           value="${search}" class="form-control"
                                                           placeholder="Code or Name...">
                                                    <div class="input-group-append">
                                                        <button type="submit" onclick="resetPageBeforeSubmit()" class="btn btn-outline-secondary">
                                                            <i class="fa fa-search"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label for="pageSize">Records per page</label>
                                                <select name="pageSize" id="pageSize" class="form-control" onchange="changePageSize(this.value)">
                                                    <option value="20" ${pageSize == 20 || empty pageSize ? 'selected' : ''}>20</option>
                                                    <option value="30" ${pageSize == 30 ? 'selected' : ''}>30</option>
                                                    <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-md-10 text-right">
                                            <label>&nbsp;</label>
                                            <div>
                                                <a href="monthly-payroll" class="btn btn-secondary">
                                                    <i class="fa fa-refresh"></i> Reset
                                                </a>
                                            </div>
                                        </div>
                                    </div>

                                    <input type="hidden" name="page" value="${currentPage}">
                                </form>

                                <!-- Info Row -->
                                <div class="row mb-2">
                                    <div class="col-md-6">
                                        <small class="text-muted">
                                            Showing ${(currentPage - 1) * pageSize + 1} to 
                                            ${currentPage * pageSize > totalRecords ? totalRecords : currentPage * pageSize} 
                                            of ${totalRecords} employees
                                        </small>
                                    </div>
                                    <div class="col-md-6 text-right">
                                        <small class="text-muted">
                                            <strong>${selectedMonth}/${selectedYear}</strong>
                                            <c:if test="${not empty selectedDepartment}"> | ${selectedDepartment}</c:if>
                                            </small>
                                        </div>
                                    </div>

                                    <!-- Payroll Table -->
                                <c:choose>
                                    <c:when test="${not empty payrollList}">
                                        <!-- Payroll Table -->
                                        <div class="table-responsive">
                                            <table class="table table-sm table-bordered table-hover text-nowrap sticky-table">
                                                <thead class="thead-dark">
                                                    <tr>
                                                        <th rowspan="2" class="text-center align-middle">Emp Code</th>
                                                        <th rowspan="2" class="text-center align-middle">Name</th>
                                                        <th rowspan="2" class="text-center align-middle">Position</th>
                                                        <th rowspan="2" class="text-center align-middle">Department</th>
                                                        <th rowspan="2" class="text-center align-middle">Work Day</th>
                                                        <th rowspan="2" class="text-center align-middle">OT Hours</th>
                                                        <th rowspan="2" class="text-center align-middle">Regular Salary</th>
                                                        <th rowspan="2" class="text-center align-middle">OT Earning</th>
                                                        <th rowspan="2" class="text-center align-middle">Allowance</th>
                                                        <th rowspan="2" class="text-center align-middle">Insurance Base</th>
                                                        <th colspan="3" class="text-center">Insurance</th>
                                                        <th rowspan="2" class="text-center align-middle">Taxable Income</th>
                                                        <th rowspan="2" class="text-center align-middle">Tax</th>
                                                        <th rowspan="2" class="text-center align-middle bg-success text-white">Net Salary</th>
                                                        <th rowspan="2" class="text-center align-middle">Action</th>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-center">SI</th>
                                                        <th class="text-center">HI</th>
                                                        <th class="text-center">UI</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:choose>
                                                        <c:when test="${not empty employees}">
                                                            <c:forEach items="${employees}" var="emp" varStatus="status">
                                                                <c:set var="payroll" value="${payrollMap[emp.empId]}" />
                                                                <c:choose>
                                                                    <c:when test="${payroll != null}">
                                                                        <c:set var="allowance" value="${payroll.insuranceBase - payroll.regularSalary}" />
                                                                        <c:set var="netSalary" value="${payroll.regularSalary + payroll.otEarning + allowance - payroll.si - payroll.hi - payroll.ui - payroll.tax}" />

                                                                        <tr>
                                                                            <td class="text-center font-weight-bold">${emp.empCode}</td>
                                                                            <td>${emp.fullname}</td>
                                                                            <td class="text-center"><small>${emp.positionTitle}</small></td>
                                                                            <td class="text-center"><small>${emp.dept.depName}</small></td>
                                                                            <td class="text-center">
                                                                                <span class="badge badge-info">${payroll.totalWorkDay}</span>
                                                                            </td>
                                                                            <td class="text-center">
                                                                                <c:choose>
                                                                                    <c:when test="${payroll.totalOTHours > 0}">
                                                                                        <span class="badge badge-warning">${payroll.totalOTHours}</span>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <span class="text-muted">-</span>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${payroll.regularSalary}" pattern="#,###" /></small></td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${payroll.otEarning}" pattern="#,###" /></small></td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${allowance}" pattern="#,###" /></small></td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${payroll.insuranceBase}" pattern="#,###" /></small></td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${payroll.si}" pattern="#,###" /></small></td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${payroll.hi}" pattern="#,###" /></small></td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${payroll.ui}" pattern="#,###" /></small></td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${payroll.taxIncome}" pattern="#,###" /></small></td>
                                                                            <td class="text-right"><small><fmt:formatNumber value="${payroll.tax}" pattern="#,###" /></small></td>
                                                                            <td class="text-right bg-success text-white font-weight-bold">
                                                                                <small><fmt:formatNumber value="${netSalary}" pattern="#,###" /></small>
                                                                            </td>
                                                                            <td class="text-center">
                                                                                <button class="btn btn-sm btn-primary" 
                                                                                        onclick="recalculatePayroll(${emp.empId}, ${selectedMonth}, ${selectedYear})"
                                                                                        ${isPayrollLocked ? 'disabled title="Payroll is locked"' : ''}>
                                                                                    <i class="fa fa-sync-alt"></i>
                                                                                </button>

                                                                            </td>
                                                                        </tr>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <tr>
                                                                            <td class="text-center font-weight-bold">${emp.empCode}</td>
                                                                            <td>${emp.fullname}</td>
                                                                            <td class="text-center"><small>${emp.positionTitle}</small></td>
                                                                            <td class="text-center"><small>${emp.dept.depName}</small></td>
                                                                            <td colspan="12" class="text-center text-muted">
                                                                                <em><small>No payroll data</small></em>
                                                                            </td>
                                                                            <td class="text-center">
                                                                                <button class="btn btn-sm btn-warning" 
                                                                                        onclick="recalculatePayroll(${emp.empId}, ${selectedMonth}, ${selectedYear})"
                                                                                        ${isPayrollLocked ? 'disabled title="Payroll is locked"' : ''}>
                                                                                    <i class="fa fa-calculator"></i>
                                                                                </button>

                                                                            </td>
                                                                        </tr>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>

                                                            <!-- Total Row -->
                                                            <tr class="table-active font-weight-bold">
                                                                <td class="text-right">TOTAL:</td>
                                                                <td></td>
                                                                <td class="text-center">-</td>
                                                                <td class="text-center">-</td>
                                                                <td class="text-center">-</td>
                                                                <td class="text-center">-</td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumRegularSalary}" pattern="#,###" /></td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumOTEarning}" pattern="#,###" /></td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumAllowance}" pattern="#,###" /></td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumRegularSalary + sumAllowance}" pattern="#,###" /></td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumSI}" pattern="#,###" /></td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumHI}" pattern="#,###" /></td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumUI}" pattern="#,###" /></td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumTaxableIncome}" pattern="#,###" /></td>
                                                                <td class="text-right"><fmt:formatNumber value="${sumTax}" pattern="#,###" /></td>
                                                                <td class="text-right bg-success text-white">
                                                                    <fmt:formatNumber value="${sumNetSalary}" pattern="#,###" />
                                                                </td>
                                                                <td></td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr>
                                                                <td colspan="17" class="text-center text-muted py-4">
                                                                    <i class="fa fa-inbox fa-2x mb-2 d-block"></i>
                                                                    <p class="mb-0">No employees found</p>
                                                                </td>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tbody>
                                            </table>
                                        </div>

                                        <!-- PAGINATION -->                
                                        <c:if test="${totalPages > 1}">
                                            <nav aria-label="Page navigation" class="mt-3">
                                                <ul class="pagination justify-content-center">
                                                    <!-- Previous -->
                                                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                                        <a class="page-link" href="?page=${currentPage - 1}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&pageSize=${pageSize}">
                                                            <i class="fa fa-chevron-left"></i> Previous
                                                        </a>
                                                    </li>

                                                    <!-- Page Numbers -->
                                                    <c:choose>
                                                        <c:when test="${totalPages <= 7}">
                                                            <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                                                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                                                    <a class="page-link" href="?page=${pageNum}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&pageSize=${pageSize}">
                                                                        ${pageNum}
                                                                    </a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <!-- First -->
                                                            <li class="page-item ${1 == currentPage ? 'active' : ''}">
                                                                <a class="page-link" href="?page=1&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&pageSize=${pageSize}">1</a>
                                                            </li>

                                                            <c:if test="${currentPage > 4}">
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                </c:if>

                                                            <!-- Middle -->
                                                            <c:forEach begin="${currentPage - 2 > 2 ? currentPage - 2 : 2}" 
                                                                       end="${currentPage + 2 < totalPages - 1 ? currentPage + 2 : totalPages - 1}" 
                                                                       var="pageNum">
                                                                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                                                    <a class="page-link" href="?page=${pageNum}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&pageSize=${pageSize}">
                                                                        ${pageNum}
                                                                    </a>
                                                                </li>
                                                            </c:forEach>

                                                            <c:if test="${currentPage < totalPages - 3}">
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                </c:if>

                                                            <!-- Last -->
                                                            <li class="page-item ${totalPages == currentPage ? 'active' : ''}">
                                                                <a class="page-link" href="?page=${totalPages}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&pageSize=${pageSize}">
                                                                    ${totalPages}
                                                                </a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <!-- Next -->
                                                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                                        <a class="page-link" href="?page=${currentPage + 1}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&pageSize=${pageSize}">
                                                            Next <i class="fa fa-chevron-right"></i>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </nav>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center py-5">
                                            <i class="fa fa-calendar-times-o fa-4x text-muted mb-3"></i>
                                            <h5 class="text-muted">No Salary Records Found</h5>
                                            <p class="text-muted">No records match your filter criteria.</p>
                                            <a href="monthly-payroll?month=${selectedMonth}&year=${selectedYear}" class="btn btn-primary">
                                                <i class="fa fa-refresh"></i> Reset Filters
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <!-- Lock Payroll Section -->
                                <div class="row mt-4">
                                    <div class="col-12">
                                        <c:choose>
                                            <c:when test="${isPayrollLocked}">
                                                <!-- Payroll đã LOCKED -->
                                                <div class="card border-success">
                                                    <div class="card-body bg-light">
                                                        <div class="row align-items-center">
                                                            <div class="col-md-8">
                                                                <h5 class="mb-2 text-success">
                                                                    <i class="fa fa-check-circle"></i> 
                                                                    Payroll for <strong>${selectedMonth}/${selectedYear}</strong> is LOCKED
                                                                </h5>
                                                                <p class="mb-0 text-muted">
                                                                    <small>
                                                                        <i class="fa fa-info-circle"></i> 
                                                                        This payroll has been locked and cannot be modified.
                                                                    </small>
                                                                </p>
                                                            </div>
                                                            <div class="col-md-4 text-right">
                                                                <button type="button" class="btn btn-success btn-lg" disabled>
                                                                    <i class="fa fa-lock"></i> Locked ✓
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="card border-danger">
                                                    <div class="card-body bg-light">
                                                        <div class="row align-items-center">
                                                            <div class="col-md-8">
                                                                <h5 class="mb-2">
                                                                    <i class="fa fa-lock text-danger"></i> 
                                                                    Lock Payroll for <strong class="text-primary">${selectedMonth}/${selectedYear}</strong>
                                                                </h5>
                                                                <p class="mb-0 text-muted">
                                                                    <small>
                                                                        <i class="fa fa-exclamation-triangle text-warning"></i> 
                                                                        <strong>Warning:</strong> After locking, no changes can be made to this month's payroll. This action is <strong>IRREVERSIBLE</strong>.
                                                                    </small>
                                                                </p>
                                                            </div>
                                                            <div class="col-md-4 text-right">
                                                                <button type="button" class="btn btn-danger btn-lg" onclick="lockPayroll()">
                                                                    <i class="fa fa-lock"></i> Lock Payroll
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>

        <script>
                                                                    var isProcessing = false;

                                                                    $(document).ready(function () {
                                                                        setTimeout(function () {
                                                                            $('.alert').fadeOut('slow');
                                                                        }, 5000);
                                                                    });

                                                                    function changePageSize(newPageSize) {
                                                                        if (isProcessing) {
                                                                            alert('Please wait until processing is complete');
                                                                            return;
                                                                        }
                                                                        const form = document.getElementById('filterForm');
                                                                        if (!form) {
                                                                            alert('Error: Form not found');
                                                                            return;
                                                                        }
                                                                        const pageInput = form.querySelector('input[name="page"]');
                                                                        if (pageInput) {
                                                                            pageInput.value = 1;
                                                                        }
                                                                        form.submit();
                                                                    }

                                                                    function resetPageBeforeSubmit() {
                                                                        const form = document.getElementById('filterForm');
                                                                        if (form) {
                                                                            form.querySelector('input[name="page"]').value = 1;
                                                                        }
                                                                    }

                                                                    function applyFilter() {
                                                                        const form = document.getElementById('filterForm');
                                                                        if (!form)
                                                                            return;
                                                                        form.querySelector('input[name="page"]').value = 1;
                                                                        form.submit();
                                                                    }

                                                                    function calculatePayroll() {
                                                                        if (isProcessing) {
                                                                            alert('⏳ Processing... Please wait!');
                                                                            return;
                                                                        }

                                                                        var month = $('#selectedMonth').val();
                                                                        var year = $('#selectedYear').val();

                                                                        if (confirm('💰 Calculate payroll for ' + month + '/' + year + '?\n\n' +
                                                                                'This will recalculate payroll for all employees.\n\n' +
                                                                                '⚠️ Note: All daily attendance records must be locked before calculation.\n\n' +
                                                                                'Proceed?')) {
                                                                            isProcessing = true;
                                                                            window.location.href = 'monthly-payroll?action=calculate&month=' + month + '&year=' + year;
                                                                        }
                                                                    }

                                                                    function recalculatePayroll(empId, month, year) {
                                                                        if (isProcessing) {
                                                                            alert('⏳ Processing... Please wait!');
                                                                            return;
                                                                        }

                                                                        if (confirm('🔄 Recalculate payroll for this employee?\n\n' +
                                                                                'Month: ' + month + '/' + year + '\n' +
                                                                                'Employee ID: ' + empId + '\n\n' +
                                                                                'This will update their payroll data based on current attendance records.\n\n' +
                                                                                'Proceed?')) {
                                                                            isProcessing = true;
                                                                            window.location.href = 'monthly-payroll?action=recalculate&empId=' + empId +
                                                                                    '&month=' + month + '&year=' + year;
                                                                        }
                                                                    }

                                                                    function exportPayroll(format) {
                                                                        var form = $('#filterForm');
                                                                        if (!form.length) {
                                                                            alert('Form not found!');
                                                                            return;
                                                                        }
                                                                        var params = form.serialize();
                                                                        var url;
                                                                        if (format === 'excel') {
                                                                            url = 'export-salary-excel?' + params;
                                                                        } else if (format === 'pdf') {
                                                                            url = 'export-salary-pdf?' + params;
                                                                        }
                                                                        window.location.href = url;
                                                                    }

                                                                    function lockPayroll() {
                                                                        if (isProcessing) {
                                                                            alert('⏳ Processing... Please wait!');
                                                                            return;
                                                                        }

                                                                        var month = $('#selectedMonth').val();
                                                                        var year = $('#selectedYear').val();

                                                                        if (confirm('🔒 Are you sure you want to LOCK payroll for ' + month + '/' + year + '?\n\n' +
                                                                                '⚠️ WARNING: This action is IRREVERSIBLE!\n\n' +
                                                                                'After locking, no changes can be made to this month\'s payroll.\n\n' +
                                                                                'Proceed?')) {
                                                                            isProcessing = true;
                                                                            window.location.href = 'monthly-payroll?action=lock&month=' + month + '&year=' + year;
                                                                        }
                                                                    }
        </script>
    </body>
</html>