<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Dashboard | Human Tech HR System</title>

        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">

        <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>

        <style>
            .chart-section {
                margin-top: 30px;
            }
            .chart-container {
                background-color: white;
                border-radius: 8px;
                padding: 30px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.08);
                height: 100%;
            }
            .chart-title {
                color: #6c757d;
                font-size: 18px;
                font-weight: 600;
                margin-bottom: 25px;
                text-align: left;
            }
            .chart-canvas-wrapper {
                position: relative;
                margin: 0 auto;
            }
            .legend-container {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                margin-top: 25px;
                justify-content: flex-start;
            }
            .legend-item {
                display: flex;
                align-items: center;
                gap: 8px;
                font-size: 13px;
                color: #6c757d;
            }
            .legend-color {
                width: 14px;
                height: 14px;
                border-radius: 3px;
            }
            .chart-note {
                display: flex;
                align-items: center;
                gap: 8px;
                margin-top: 20px;
                font-size: 13px;
                color: #6b7280;
            }
            .chart-note::before {
                content: "";
                width: 8px;
                height: 8px;
                background-color: #ff6b35;
                border-radius: 50%;
            }
            .note-increase {
                color: #10b981;
                font-weight: 600;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="../CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="../CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <!-- Breadcrumb -->
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Dashboard</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>Dashboard</li>
                    </ul>
                </div>

                <!-- Widget Cards -->
                <div class="row">
                    <div class="col-md-6 col-lg-3">
                        <div class="widget-card widget-bg1">
                            <div class="wc-item">
                                <h4 class="wc-title">Total Account</h4>
                                <span class="wc-des">All active and inactive accounts</span>
                                <span class="wc-stats"><span class="counter">${totalEmployee}</span></span>                               
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-lg-3">
                        <div class="widget-card widget-bg2">
                            <div class="wc-item">
                                <h4 class="wc-title">Active Account</h4>
                                <span class="wc-des">Active Accounts</span>
                                <span class="wc-stats counter">35</span>                             
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-lg-3">
                        <div class="widget-card widget-bg3">
                            <div class="wc-item">
                                <h4 class="wc-title">Departments</h4>
                                <span class="wc-des">Active departments</span>
                                <span class="wc-stats counter">${totalDept}</span>                             
                            </div>
                        </div>
                    </div>


                </div>

                <!-- Charts Section -->
                <div class="row chart-section">
                    <!-- Account Roles Distribution Chart -->
                    <div class="col-md-6">
                        <div class="chart-container">
                            <h2 class="chart-title">Account Roles Distribution</h2>
                            <div class="chart-canvas-wrapper" style="max-width: 500px; margin: 0 auto;">
                                <canvas id="rolesChart"></canvas>
                            </div>
                            <div class="legend-container">
                                <div class="legend-item">
                                    <span class="legend-color" style="background-color: #92afd7;"></span>
                                    <span>Admin</span>
                                </div>
                                <div class="legend-item">
                                    <span class="legend-color" style="background-color: #9ca8c2;"></span>
                                    <span>HR</span>
                                </div>
                                <div class="legend-item">
                                    <span class="legend-color" style="background-color: #8ac4ba;"></span>
                                    <span>HR Manager</span>
                                </div>
                                <div class="legend-item">
                                    <span class="legend-color" style="background-color: #f4d791;"></span>
                                    <span>Employee</span>
                                </div>
                                <div class="legend-item">
                                    <span class="legend-color" style="background-color: #eb8c8c;"></span>
                                    <span>Dept Manager</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Employees By Department Chart -->
                    <div class="col-md-6">
                        <div class="chart-container">
                            <h2 class="chart-title">Employees By Department</h2>
                            <div class="chart-canvas-wrapper">
                                <canvas id="deptChart"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <c:if test="${not empty logMessage}">
            <script>
                alert("${logMessage}");
            </script>
        </c:if>

        <!-- JS FILES -->
        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/counter/waypoints-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/counter/counterup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/calendar/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
        <script>
                // Roles Donut Chart
                const rolesChart = new Chart(document.getElementById('rolesChart').getContext('2d'), {
                type: 'doughnut',
                        data: {
                        labels: [
            <c:forEach var="entry" items="${roleData}" varStatus="status">
                        "${entry.key}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                        ],
                                datasets: [{
                                data: [
            <c:forEach var="entry" items="${roleData}" varStatus="status">
                ${entry.value}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                                ],
                                        backgroundColor: [
                                                '#92afd7', '#9ca8c2', '#8ac4ba', '#f4d791', '#eb8c8c'
                                        ],
                                        borderWidth: 0,
                                        cutout: '65%'
                                }]
                        },
                        options: {
                        responsive: true,
                                maintainAspectRatio: true,
                                plugins: {
                                legend: { display: false },
                                        tooltip: {
                                        backgroundColor: 'rgba(0, 0, 0, 0.8)',
                                                padding: 12,
                                                titleColor: '#fff',
                                                bodyColor: '#fff',
                                                borderColor: '#ddd',
                                                borderWidth: 1,
                                                callbacks: {
                                                label: function(context) {
                                                const label = context.label || '';
                                                const value = context.parsed || 0;
                                                const total = context.dataset.data.reduce((a, b) => a + b, 0);
                                                const percentage = ((value / total) * 100).toFixed(1);
                                                return label + ': ' + value + ' (' + percentage + '%)';
                                                }
                                                }
                                        }
                                }
                        }
                });
                // Departments Bar Chart
                const deptChart = new Chart(document.getElementById('deptChart').getContext('2d'), {
                type: 'bar',
                        data: {
                        labels: [
            <c:forEach var="entry" items="${deptData}" varStatus="status">
                        "${entry.key}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                        ],
                                datasets: [{
                                label: 'Number of Employees',
                                        data: [
            <c:forEach var="entry" items="${deptData}" varStatus="status">
                ${entry.value}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                                        ],
                                        backgroundColor: '#ff6b35',
                                        borderRadius: 4,
                                        barThickness: 28
                                }]
                        },
                        options: {
                        indexAxis: 'y',
                                responsive: true,
                                maintainAspectRatio: true,
                                scales: {
                                x: {
                                type: 'linear', // BẮT BUỘC
                                        min: 0, // quan trọng
                                        beginAtZero: true,
                                        suggestedMin: 0, // giúp scale khởi tạo từ 0 nếu Chart.js optimize lại
                                        grid: {
                                        color: '#f0f0f0',
                                                drawBorder: false
                                        },
                                        ticks: {
                                        color: '#999',
                                                font: { size: 11 }
                                        }
                                },
                                        y: {
                                        type: 'category', // Labels phòng ban
                                                grid: {
                                                display: false,
                                                        drawBorder: false
                                                },
                                                ticks: {
                                                color: '#6c757d',
                                                        font: { size: 13 },
                                                        padding: 8
                                                }
                                        }
                                },
                                plugins: {
                                legend: { display: false },
                                        tooltip: {
                                        backgroundColor: 'rgba(0, 0, 0, 0.8)',
                                                padding: 12,
                                                titleColor: '#fff',
                                                bodyColor: '#fff',
                                                callbacks: {
                                                label: function(context) {
                                                return 'Employees: ' + context.parsed.x;
                                                }
                                                }
                                        }
                                }
                        }
                });

        </script>

    </body>
</html>