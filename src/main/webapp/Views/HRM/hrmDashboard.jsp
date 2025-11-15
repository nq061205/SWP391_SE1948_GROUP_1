<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Employees Dashboard</title>
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/hrmDashboard-style.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>

    </head>
    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="../CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="../CommonItems/Navbar/empNavbar.jsp" %>
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Dashboard</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>Dashboard</li>
                    </ul>
                </div>
                <div>
                    <div class="row g-4 mb-4">
                        <div class="col-12">
                            <div class="card-container mb-4">
                                <div class="section-title font-weight-bold mb-4">HR Reports</div>
                                <div class="row g-4">
                                    <div class="col-md-6">
                                        <div class="card-container">
                                            <div class="card-header">
                                                <h2 class="card-title">Top Absence Employees</h2>
                                                <form method="get" style="display:inline;">
                                                    <input type="hidden" name="year" value="${selectedYear}"/>
                                                    <select name="monthAbsence" class="month-filter-select" onchange="this.form.submit()">
                                                        <c:forEach var="i" begin="1" end="12">
                                                            <option value="${i}" <c:if test="${i == selectedMonthAbsence}">selected</c:if>>
                                                                <c:choose>
                                                                    <c:when test="${i==1}">January</c:when>
                                                                    <c:when test="${i==2}">February</c:when>
                                                                    <c:when test="${i==3}">March</c:when>
                                                                    <c:when test="${i==4}">April</c:when>
                                                                    <c:when test="${i==5}">May</c:when>
                                                                    <c:when test="${i==6}">June</c:when>
                                                                    <c:when test="${i==7}">July</c:when>
                                                                    <c:when test="${i==8}">August</c:when>
                                                                    <c:when test="${i==9}">September</c:when>
                                                                    <c:when test="${i==10}">October</c:when>
                                                                    <c:when test="${i==11}">November</c:when>
                                                                    <c:when test="${i==12}">December</c:when>
                                                                </c:choose>
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </form>
                                            </div>
                                            <div class="absence-table">
                                                <div class="table-header">
                                                    <div class="col-code">Code</div>
                                                    <div class="col-name">Name</div>
                                                    <div class="col-days">Days Off</div>
                                                    <div class="col-dept">Department</div>
                                                </div>
                                                <c:forEach var="item" items="${topAbsences}">
                                                    <div class="absence-row">
                                                        <div class="absence-code">${item.employee.empCode}</div>
                                                        <div class="absence-name">${item.employee.fullname}</div>
                                                        <div class="absence-days"><span class="absence-badge">${item.totalWorkDay} days</span></div>
                                                        <div class="absence-dept"><span class="dept-badge">${item.employee.dept.depName}</span></div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="card-container">
                                            <div class="card-header">
                                                <h2 class="card-title">Top Attendance Employees</h2>
                                                <form method="get" style="display:inline;">
                                                    <input type="hidden" name="year" value="${selectedYear}"/>
                                                    <select name="monthAttendance" class="month-filter-select" onchange="this.form.submit()">
                                                        <c:forEach var="i" begin="1" end="12">
                                                            <option value="${i}" <c:if test="${i == selectedMonthAttendance}">selected</c:if>>
                                                                <c:choose>
                                                                    <c:when test="${i==1}">January</c:when>
                                                                    <c:when test="${i==2}">February</c:when>
                                                                    <c:when test="${i==3}">March</c:when>
                                                                    <c:when test="${i==4}">April</c:when>
                                                                    <c:when test="${i==5}">May</c:when>
                                                                    <c:when test="${i==6}">June</c:when>
                                                                    <c:when test="${i==7}">July</c:when>
                                                                    <c:when test="${i==8}">August</c:when>
                                                                    <c:when test="${i==9}">September</c:when>
                                                                    <c:when test="${i==10}">October</c:when>
                                                                    <c:when test="${i==11}">November</c:when>
                                                                    <c:when test="${i==12}">December</c:when>
                                                                </c:choose>
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </form>
                                            </div>
                                            <div class="attendance-table">
                                                <table>
                                                    <thead>
                                                        <tr>
                                                            <th>Code</th>
                                                            <th>Name</th>
                                                            <th>Total Workday</th>
                                                            <th>Total OT Hour</th>
                                                            <th>Department</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="item" items="${topAttendance}">
                                                            <tr>
                                                                <td><span class="employee-code">${item.employee.empCode}</span></td>
                                                                <td>${item.employee.fullname}</td>
                                                                <td><span class="workday-badge">${item.totalWorkDay} days</span></td>
                                                                <td><span class="ot-badge">${item.totalOTHours}h</span></td>
                                                                <td>${item.employee.dept.depName}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div> 
                            </div>
                        </div>
                    </div>  

                    <div class="row mb-4">
                        <div class="col-12">
                            <div class="card-container">
                                <div class="chart-header">
                                    <h2 class="card-title" style="margin-bottom:0;">Company Salary Payment by Month</h2>
                                    <form method="get" style="display:inline;margin-left:20px;">
                                        <select name="year" class="month-filter-select" onchange="this.form.submit()">
                                            <c:forEach var="y" begin="2022" end="2025">
                                                <option value="${y}" <c:if test="${y eq selectedYear}">selected</c:if>>${y}</option>
                                            </c:forEach>
                                        </select>
                                    </form>
                                </div>
                                <canvas id="salaryChart"></canvas>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </main>
        <script>
            const salaryChart = new Chart(document.getElementById('salaryChart').getContext('2d'), {
            type: 'bar',
                    data: {
                    labels: [
            <c:forEach var="entry" items="${salaryData}" varStatus="status">
                    "${entry.key}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                    ],
                            datasets: [{
                            label: 'Total Paid Salary (VND)',
                                    data: [
            <c:forEach var="entry" items="${salaryData}" varStatus="status">
                ${entry.value}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                                    ],
                                    backgroundColor: '#92afd7',
                                    borderRadius: 6,
                                    barThickness: 32
                            }]
                    },
                    options: {
                    responsive: true,
                            plugins: {
                            legend: { display: true },
                                    tooltip: {
                                    backgroundColor: 'rgba(0, 0, 0, 0.85)',
                                            titleColor: '#fff',
                                            bodyColor: '#fff',
                                            callbacks: {
                                            label: function(context) {
                                            let val = context.parsed.y || 0;
                                            return 'Salary: ' + val.toLocaleString() + ' ₫';
                                            }
                                            }
                                    }
                            },
                            scales: {
                            y: {
                            beginAtZero: true,
                                    ticks: {
                                    stepSize: 50000000,
                                            callback: function (value) {
                                            return value.toLocaleString() + ' ₫';
                                            },
                                            color: '#333',
                                            font: { size: 13 }
                                    },
                                    grid: { color: '#ececec', drawBorder: false }
                            },
                                    x: {
                                    grid: { display: false }
                                    }
                            }
                    }
            });
        </script>


    </body>
</html>
