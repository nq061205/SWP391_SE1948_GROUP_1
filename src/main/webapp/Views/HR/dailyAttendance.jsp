<%-- 
    Document   : dashboard
    Created on : Oct 4, 2025, 4:14:15 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
        <title>Human Tech - Monthly Report</title>

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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/daily-attendance-style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="../CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="../CommonItems/Navbar/hrNavbar.jsp" %>
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <!-- Breadcrumb -->
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Monthly Attendance Report</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>Attendance Management</li>
                        <li>Monthly Report</li>
                    </ul>
                </div>

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

                <!-- Monthly Attendance -->
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-calendar"></i> Monthly Attendance Report</h4>
                                <div class="float-right">
                                    <button class="btn btn-info btn-sm mr-2" onclick="toggleLegend()">
                                        <i class="fa fa-info-circle"></i> Legend
                                    </button>
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fa fa-download"></i> Export
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <a class="dropdown-item" href="#" onclick="exportAttendance('excel')">
                                                <i class="fa fa-file-excel-o text-success"></i> Export to Excel
                                            </a>
                                            <a class="dropdown-item" href="#" onclick="exportAttendance('pdf')">
                                                <i class="fa fa-file-pdf-o text-danger"></i> Export to PDF
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="widget-inner">
                                <!-- Legend Panel -->
                                <div id="legendPanel" class="card mb-3" style="display: none;">
                                    <div class="card-header">
                                        <h6 class="mb-0"><i class="fa fa-info-circle"></i> Attendance Legend</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col text-center">
                                                <div class="legend-box status-Present rounded">1</div>
                                                <small class="d-block mt-1">Full Day</small>
                                            </div>
                                            <div class="col text-center">
                                                <div class="legend-box status-Present rounded">0.5</div>
                                                <small class="d-block mt-1">Half Day</small>
                                            </div>
                                            <div class="col text-center">
                                                <div class="legend-box status-Absent rounded">0</div>
                                                <small class="d-block mt-1">Absent</small>
                                            </div>
                                            <div class="col text-center">
                                                <div class="legend-box status-Leave rounded">0/1</div>
                                                <small class="d-block mt-1">Leave</small>
                                            </div>
                                            <div class="col text-center">
                                                <div class="legend-box status-Holiday rounded">1</div>
                                                <small class="d-block mt-1">Holiday</small>
                                            </div>
                                            <div class="col text-center">
                                                <div class="legend-box status-Present rounded">1 T</div>
                                                <small class="d-block mt-1">With OT</small>
                                            </div>
                                            <div class="col text-center">
                                                <div class="legend-box weekend-cell rounded">-</div>
                                                <small class="d-block mt-1">Weekend</small>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="text-center">
                                            <span class="badge badge-success">Present</span>
                                            <span class="badge badge-danger">Absent</span>
                                            <span class="badge badge-info">Leave</span>
                                            <span class="badge badge-secondary">Holiday</span>
                                            <span class="badge badge-dark">Weekend</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- Filters -->
                                <form method="get" action="daily-attendance" id="filterForm" class="mb-4">
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

                                        <!-- Department -->
                                        <div class="col-md-2">
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
                                        <div class="col-md-3"></div>

                                        <!-- Search -->
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="searchInput">Search</label>
                                                <div class="input-group">
                                                    <input type="text" name="search" id="searchInput" 
                                                           value="${search}" class="form-control" 
                                                           placeholder="Search by Code or Name...">
                                                    <div class="input-group-append">
                                                        <button type="submit" onclick="resetPageBeforeSubmit()" class="btn btn-outline-secondary">
                                                            <i class="fas fa-search"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Second Row -->
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
                                                <a href="daily-attendance" class="btn btn-secondary">
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
                                            <strong>${selectedMonth} ${selectedYear}</strong>
                                            <c:if test="${not empty selectedDepartment}"> | ${selectedDepartment}</c:if>
                                            </small>
                                        </div>
                                    </div>

                                    <!-- Monthly Attendance Calendar -->
                                <c:choose>
                                    <c:when test="${not empty employees}">
                                        <div class="table-responsive">
                                            <table class="table table-bordered table-sm table-hover">
                                                <thead class="thead-dark">
                                                    <tr>
                                                        <th rowspan="2" class="employee-col">Code</th>
                                                        <th rowspan="2" class="name-col">Employee</th>
                                                        <th rowspan="2" class="dept-col">Department</th>
                                                        <th colspan="${daysInMonth}" class="text-center">${selectedMonth} ${selectedYear}</th>
                                                        <th rowspan="2" class="summary-col">Days</th>
                                                        <th rowspan="2" class="summary-col">OT</th>
                                                    </tr>
                                                    <tr>
                                                        <c:forEach begin="1" end="${daysInMonth}" var="day">
                                                            <th class="day-header text-center ${weekendDays.contains(day) ? 'bg-dark text-white' : 'bg-secondary text-white'}">
                                                                ${day}<br>
                                                                <small>${dayNames[day-1]}</small>
                                                            </th>
                                                        </c:forEach>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="employee" items="${employees}">
                                                        <tr>
                                                            <td class="employee-col bg-light">
                                                                <strong class="text-primary">${employee.empCode}</strong>
                                                            </td>
                                                            <td class="name-col bg-light">
                                                                <small>${employee.fullname}</small>
                                                            </td>
                                                            <td class="dept-col bg-light">
                                                                <small class="text-muted">
                                                                    <c:if test="${not empty employee.dept}">
                                                                        ${employee.dept.depName}
                                                                    </c:if>
                                                                </small>
                                                            </td>
                                                            <c:set var="attendanceList" value="${groupedAttendance[employee.empId]}" />
                                                            <c:forEach begin="1" end="${daysInMonth}" var="day">
                                                                <c:set var="attendance" value="${attendanceByDay[employee.empId][day]}" />
                                                                <c:set var="isWeekend" value="${weekendDays.contains(day)}" />

                                                                <td class="day-cell ${isWeekend ? 'weekend-cell' : (attendance != null ? 'status-'.concat(attendance.status) : '')}" 
                                                                    title="Click to view details">
                                                                    <c:choose>
                                                                        <c:when test="${isWeekend}">
                                                                            <span class="work-day-text">-</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <c:if test="${attendance != null}">
                                                                                <span class="work-day-text">
                                                                                    <fmt:formatNumber value="${attendance.workDay}" pattern="#.##"/><c:if test="${attendance.otHours > 0}">T</c:if>
                                                                                    </span>
                                                                            </c:if>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                            </c:forEach>
                                                            <!-- Summary -->
                                                            <td class="summary-col text-center">
                                                                <strong class="text-primary">
                                                                    <fmt:formatNumber value="${totalWorkDaysMap[employee.empId]}" maxFractionDigits="1"/>
                                                                </strong>
                                                            </td>
                                                            <td class="summary-col text-center">
                                                                <strong class="text-warning">
                                                                    <fmt:formatNumber value="${totalOTHoursMap[employee.empId]}" maxFractionDigits="1"/>h
                                                                </strong>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <c:if test="${totalPages > 1}">
                                            <nav aria-label="Page navigation" class="mt-3">
                                                <ul class="pagination justify-content-center">
                                                    <!-- Previous -->
                                                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                                        <a class="page-link" href="?page=${currentPage - 1}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&sortBy=${param.sortBy}&pageSize=${pageSize}">
                                                            <i class="fa fa-chevron-left"></i>
                                                        </a>
                                                    </li>

                                                    <!-- Page Numbers -->
                                                    <c:choose>
                                                        <c:when test="${totalPages <= 7}">
                                                            <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                                                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                                                    <a class="page-link" href="?page=${pageNum}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&sortBy=${param.sortBy}&pageSize=${pageSize}">
                                                                        ${pageNum}
                                                                    </a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <!-- First -->
                                                            <li class="page-item ${1 == currentPage ? 'active' : ''}">
                                                                <a class="page-link" href="?page=1&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&sortBy=${param.sortBy}&pageSize=${pageSize}">1</a>
                                                            </li>

                                                            <c:if test="${currentPage > 4}">
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                </c:if>

                                                            <!-- Middle -->
                                                            <c:forEach begin="${currentPage - 2 > 2 ? currentPage - 2 : 2}" 
                                                                       end="${currentPage + 2 < totalPages - 1 ? currentPage + 2 : totalPages - 1}" 
                                                                       var="pageNum">
                                                                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                                                    <a class="page-link" href="?page=${pageNum}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&sortBy=${param.sortBy}&pageSize=${pageSize}">
                                                                        ${pageNum}
                                                                    </a>
                                                                </li>
                                                            </c:forEach>

                                                            <c:if test="${currentPage < totalPages - 3}">
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                </c:if>

                                                            <!-- Last -->
                                                            <li class="page-item ${totalPages == currentPage ? 'active' : ''}">
                                                                <a class="page-link" href="?page=${totalPages}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&sortBy=${param.sortBy}&pageSize=${pageSize}">
                                                                    ${totalPages}
                                                                </a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <!-- Next -->
                                                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                                        <a class="page-link" href="?page=${currentPage + 1}&month=${selectedMonth}&year=${selectedYear}&department=${selectedDepartment}&search=${param.search}&sortBy=${param.sortBy}&pageSize=${pageSize}">
                                                            <i class="fa fa-chevron-right"></i>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </nav>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center py-5">
                                            <i class="fa fa-calendar-times-o fa-4x text-muted mb-3"></i>
                                            <h5 class="text-muted">No Attendance Records Found</h5>
                                            <p class="text-muted">No records match your filter criteria.</p>
                                            <a href="daily-attendance?month=${selectedMonth}&year=${selectedYear}" class="btn btn-primary">
                                                <i class="fa fa-refresh"></i> Reset Filters
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- Detail Modal -->
        <div class="modal fade" id="detailModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title"><i class="fa fa-info-circle"></i> Attendance Detail</h5>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body" id="detailContent">
                        <div class="text-center"><i class="fa fa-spinner fa-spin"></i> Loading...</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>



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
                                                    var isProcessing = false;

                                                    $(document).ready(function () {
                                                        setTimeout(function () {
                                                            $('.alert').fadeOut('slow');
                                                        }, 5000);
                                                    });

                                                    function toggleLegend() {
                                                        var panel = $('#legendPanel');
                                                        if (panel.length === 0) {
                                                            alert('Legend panel not found!');
                                                            return;
                                                        }
                                                        panel.slideToggle(300);
                                                    }

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

                                                    function exportAttendance(format) {
                                                        var form = $('#filterForm');
                                                        if (!form.length) {
                                                            alert('Form not found!');
                                                            return;
                                                        }
                                                        var params = form.serialize();
                                                        var url;
                                                        if (format === 'excel') {
                                                            url = 'export-attendance-excel?' + params;
                                                        } else if (format === 'pdf') {
                                                            url = 'export-attendance-pdf?' + params;
                                                        }
                                                        window.location.href = url;
                                                    }

    </script>
</html>
