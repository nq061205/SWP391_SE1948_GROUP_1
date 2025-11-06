<%-- 
    Document   : monthly-salary-report
    Created on : Nov 4, 2025
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
        <meta name="description" content="Human Tech" />
        <meta property="og:title" content="Human Tech" />
        <meta property="og:description" content="Human Tech" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Human Tech - Monthly Salary Report</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <style>
            .salary-report-table {
                font-size: 0.9rem;
            }
            .salary-col {
                width: 120px;
            }
            .summary-col {
                width: 100px;
                font-weight: bold;
            }
            .status-badge {
                padding: 4px 8px;
                border-radius: 3px;
                font-size: 0.85rem;
            }
            .badge-positive {
                background-color: #d4edda;
                color: #155724;
            }
            .badge-negative {
                background-color: #f8d7da;
                color: #721c24;
            }
            .badge-neutral {
                background-color: #e2e3e5;
                color: #383d41;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="../CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="../CommonItems/Navbar/hrNavbar.jsp" %>
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <!-- Breadcrumb -->
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Monthly Salary Report</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>Payroll Management</li>
                        <li>Monthly Salary Report</li>
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

                <!-- Main Content -->
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-money"></i> Monthly Salary Report</h4>
                                <div class="float-right">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fa fa-download"></i> Export
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <a class="dropdown-item" href="#" onclick="exportSalary('excel')">
                                                <i class="fa fa-file-excel-o text-success"></i> Export to Excel
                                            </a>
                                            <a class="dropdown-item" href="#" onclick="exportSalary('pdf')">
                                                <i class="fa fa-file-pdf-o text-danger"></i> Export to PDF
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="widget-inner">
                                <!-- Filters -->
                                <form method="get" action="monthly-salary" id="filterForm" class="mb-4">
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
                                        <div class="col-md-2"></div>

                                        <!-- Search -->
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="searchInput">Search</label>
                                                <div class="input-group">
                                                    <input type="text" name="search" id="searchInput" 
                                                           value="${search}" class="form-control" 
                                                           placeholder="Code, Name or Position...">
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
                                                <a href="monthly-salary" class="btn btn-secondary">
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

                                <!-- Salary Table -->
                                <c:choose>
                                    <c:when test="${not empty salaryList}">
                                        <div class="table-responsive">
                                            <table class="table table-bordered table-sm table-hover salary-report-table">
                                                <thead class="thead-dark">
                                                    <tr>
                                                        <th class="employee-col">Emp Code</th>
                                                        <th>Name</th>
                                                        <th>Position</th>
                                                        <th class="dept-col">Department</th>
                                                        <th class="salary-col text-right">Regular Salary</th>
                                                        <th class="salary-col text-right">OT Earning</th>
                                                        <th class="salary-col text-right">Allowance</th>
                                                        <th class="salary-col text-right">Insurance</th>
                                                        <th class="salary-col text-right">SI</th>
                                                        <th class="salary-col text-right">HI</th>
                                                        <th class="salary-col text-right">UI</th>
                                                        <th class="salary-col text-right">Taxable Income</th>
                                                        <th class="salary-col text-right">Tax</th>
                                                        <th class="summary-col text-right">Net Salary</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="salary" items="${salaryList}">
                                                        <tr>
                                                            <td class="employee-col bg-light">
                                                                <strong class="text-primary">${salary.employee.empCode}</strong>
                                                            </td>
                                                            <td class="bg-light">
                                                                <small>${salary.employee.fullname}</small>
                                                            </td>
                                                            <td class="bg-light">
                                                                <small>${salary.employee.position}</small>
                                                            </td>
                                                            <td class="dept-col bg-light">
                                                                <small class="text-muted">
                                                                    <c:if test="${not empty salary.employee.dept}">
                                                                        ${salary.employee.dept.depName}
                                                                    </c:if>
                                                                </small>
                                                            </td>
                                                            <td class="text-right">
                                                                <fmt:formatNumber value="${salary.regularSalary}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                            </td>
                                                            <td class="text-right">
                                                                <fmt:formatNumber value="${salary.otEarning}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                            </td>
                                                            <td class="text-right">
                                                                <fmt:formatNumber value="${salary.allowance}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                            </td>
                                                            <td class="text-right">
                                                                <fmt:formatNumber value="${salary.insuranceBase}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                            </td>
                                                            <td class="text-right">
                                                                <span class="status-badge badge-neutral">
                                                                    <fmt:formatNumber value="${salary.socialInsurance}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                                </span>
                                                            </td>
                                                            <td class="text-right">
                                                                <span class="status-badge badge-neutral">
                                                                    <fmt:formatNumber value="${salary.healthInsurance}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                                </span>
                                                            </td>
                                                            <td class="text-right">
                                                                <span class="status-badge badge-neutral">
                                                                    <fmt:formatNumber value="${salary.unemploymentInsurance}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                                </span>
                                                            </td>
                                                            <td class="text-right">
                                                                <fmt:formatNumber value="${salary.taxableIncome}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                            </td>
                                                            <td class="text-right">
                                                                <span class="status-badge badge-negative">
                                                                    <fmt:formatNumber value="${salary.tax}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                                </span>
                                                            </td>
                                                            <td class="summary-col text-right">
                                                                <span class="status-badge badge-positive">
                                                                    <fmt:formatNumber value="${salary.netSalary}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                                <tfoot class="thead-dark">
                                                    <tr>
                                                        <th colspan="4" class="text-right">TOTAL</th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalRegularSalary}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalOTEarning}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalAllowance}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalInsuranceBase}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalSI}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalHI}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalUI}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalTaxableIncome}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalTax}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                        <th class="text-right">
                                                            <fmt:formatNumber value="${totalNetSalary}" type="currency" currencySymbol="₫" pattern="###,###"/>
                                                        </th>
                                                    </tr>
                                                </tfoot>
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
                                            <a href="monthly-salary?month=${selectedMonth}&year=${selectedYear}" class="btn btn-primary">
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
    </body>

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

        function exportSalary(format) {
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
    </script>
</html>
