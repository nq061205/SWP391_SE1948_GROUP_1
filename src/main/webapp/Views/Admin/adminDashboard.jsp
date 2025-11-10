<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- META ============================================= -->
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

                <div class="row">
                    <div class="col-md-6 col-lg-3">
                        <div class="widget-card widget-bg1">
                            <div class="wc-item">
                                <h4 class="wc-title">Total Employees</h4>
                                <span class="wc-des">All active employees</span>
                                <span class="wc-stats"><span class="counter">${totalEmployee}</span></span>
                                <div class="progress wc-progress"><div class="progress-bar" style="width: 75%;"></div></div>
                                <span class="wc-progress-bx"><span class="wc-change">Change</span><span class="wc-number ml-auto">75%</span></span>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-lg-3">
                        <div class="widget-card widget-bg2">
                            <div class="wc-item">
                                <h4 class="wc-title">New Applications</h4>
                                <span class="wc-des">Pending candidate applications</span>
                                <span class="wc-stats counter">35</span>
                                <div class="progress wc-progress"><div class="progress-bar" style="width: 60%;"></div></div>
                                <span class="wc-progress-bx"><span class="wc-change">Change</span><span class="wc-number ml-auto">60%</span></span>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-lg-3">
                        <div class="widget-card widget-bg3">
                            <div class="wc-item">
                                <h4 class="wc-title">Departments</h4>
                                <span class="wc-des">Active departments</span>
                                <span class="wc-stats counter">${totalDept}</span>
                                <div class="progress wc-progress"><div class="progress-bar" style="width: 80%;"></div></div>
                                <span class="wc-progress-bx"><span class="wc-change">Change</span><span class="wc-number ml-auto">80%</span></span>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 col-lg-3">
                        <div class="widget-card widget-bg4">
                            <div class="wc-item">
                                <h4 class="wc-title">On Leave Today</h4>
                                <span class="wc-des">Current approved leaves</span>
                                <span class="wc-stats counter">5</span>
                                <div class="progress wc-progress"><div class="progress-bar" style="width: 30%;"></div></div>
                                <span class="wc-progress-bx"><span class="wc-change">Change</span><span class="wc-number ml-auto">30%</span></span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-8 m-b30">
                        <div class="widget-box">
                            <div class="wc-title"><h4>Employee Growth Overview</h4></div>
                            <div class="widget-inner"><canvas id="chart" width="100" height="45"></canvas></div>
                        </div>
                    </div>

                    <div class="col-lg-4 m-b30">
                        <div class="widget-box">
                            <div class="wc-title"><h4>Notifications</h4></div>
                            <div class="widget-inner">
                                <div class="noti-box-list">
                                    <ul>
                                        <li><span class="notification-icon dashbg-gray"><i class="fa fa-check"></i></span><span class="notification-text"><span>Admin</span> approved your leave.</span><span class="notification-time"><span>10:30 AM</span></span></li>
                                        <li><span class="notification-icon dashbg-yellow"><i class="fa fa-bullhorn"></i></span><span class="notification-text"><span>New job posting available.</span></span><span class="notification-time"><span>2 hrs ago</span></span></li>
                                        <li><span class="notification-icon dashbg-green"><i class="fa fa-comments-o"></i></span><span class="notification-text"><span>HR</span> commented on your application.</span><span class="notification-time"><span>Yesterday</span></span></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- USERS & CALENDAR -->
                <div class="row">
                    <div class="col-lg-6 m-b30">
                        <div class="widget-box">
                            <div class="wc-title"><h4>New Employees</h4></div>
                            <div class="widget-inner">
                                <ul class="new-user-list">
                                    <li><span class="new-users-pic"><img src="${pageContext.request.contextPath}/assets2/images/testimonials/pic1.jpg" alt=""/></span><span class="new-users-text"><a href="#" class="new-users-name">Anna Strong</a><span class="new-users-info">Developer - IT Department</span></span></li>
                                    <li><span class="new-users-pic"><img src="${pageContext.request.contextPath}/assets2/images/testimonials/pic2.jpg" alt=""/></span><span class="new-users-text"><a href="#" class="new-users-name">John Smith</a><span class="new-users-info">Marketing Coordinator</span></span></li>
                                    <li><span class="new-users-pic"><img src="${pageContext.request.contextPath}/assets2/images/testimonials/pic3.jpg" alt=""/></span><span class="new-users-text"><a href="#" class="new-users-name">Sara Lee</a><span class="new-users-info">HR Assistant</span></span></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6 m-b30">
                        <div class="widget-box">
                            <div class="wc-title"><h4>Calendar</h4></div>
                            <div class="widget-inner"><div id="calendar"></div></div>
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
                setTimeout(() => {
                    const logBox = document.getElementById("logBox");
                    if (logBox)
                        logBox.remove();
                }, 3000);

                $(document).ready(function () {
                    var ctx = document.getElementById("chart").getContext("2d");
                    new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun"],
                            datasets: [{
                                    label: 'Employee Growth',
                                    data: [5, 10, 8, 15, 12, 20],
                                    backgroundColor: 'rgba(66,165,245,0.2)',
                                    borderColor: '#42a5f5',
                                    borderWidth: 2,
                                    fill: true
                                }]
                        },
                        options: {responsive: true, maintainAspectRatio: false}
                    });

                    $('#calendar').fullCalendar({
                        header: {left: 'prev,next today', center: 'title', right: 'month,agendaWeek,agendaDay'},
                        editable: false,
                        eventLimit: true,
                        events: [
                            {title: 'Company Meeting', start: '2025-10-19'},
                            {title: 'HR Interview', start: '2025-10-21'},
                            {title: 'System Maintenance', start: '2025-10-23'}
                        ]
                    });
                });
        </script>
    </body>
</html>
