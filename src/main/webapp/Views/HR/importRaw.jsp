<%-- 
    Document   : dashboard
    Created on : Oct 4, 2025, 4:14:15 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <meta name="description" content="EduChamp : Education HTML Template" />

        <!-- OG -->
        <meta property="og:title" content="EduChamp : Education HTML Template" />
        <meta property="og:description" content="EduChamp : Education HTML Template" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="../error-404.html" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Human Tech - Import Attendance Raw</title>

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

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="../CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="../CommonItems/Navbar/hrNavbar.jsp" %>
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Import Raw Attendance</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>Attendance Management</li>
                        <li>Import Raw Attendance</li>
                    </ul>
                </div>

                <div class="container bg-white shadow-sm p-4 rounded">
                    <!-- Download Sample Button -->
                    <form action="downloadtemplate" method="get" class="mb-3 text-center">
                        <button type="submit" class="btn btn-outline-secondary">
                            <i class="bi bi-download"></i> Download Sample Template
                        </button>
                    </form>

                    <!-- Upload Form -->
                    <form action="uploadExcel" method="post" enctype="multipart/form-data" class="d-flex justify-content-center align-items-center gap-2 mb-4">
                        <input type="file" name="file" accept=".xlsx" class="form-control w-50" required>
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-upload"></i> Upload & Preview
                        </button>
                    </form>

                    <!-- Message -->
                    <c:if test="${not empty success}">
                        <div class="alert alert-success text-center">${success}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger text-center">${error}</div>
                    </c:if>

                    <!-- Preview Table -->
                    <c:if test="${not empty preview}">
                        <hr class="my-4">
                        <h4 class="text-secondary mb-3">Preview First ${preview.size()} Records</h4>

                        <div class="table-responsive">
                            <table class="table table-bordered table-striped align-middle">
                                <thead class="table-primary text-center">
                                    <tr>
                                        <th>Employee ID</th>
                                        <th>Date</th>
                                        <th>Check Time</th>
                                        <th>Check Type</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="row" items="${preview}">
                                        <tr>
                                            <td>${row.emp.empId}</td>
                                            <td>${row.date}</td>
                                            <td>${row.checkTime}</td>
                                            <td>${row.checkType}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Confirm Import Button -->
                        <div class="d-flex justify-content-center gap-3 mt-3">
                            <form action="uploadExcel" method="post">
                                <input type="hidden" name="action" value="confirm">
                                <button type="submit" class="btn btn-success">
                                    <i class="bi bi-check-circle"></i> Confirm Import
                                </button>
                            </form>

                            <form action="uploadExcel" method="post">
                                <input type="hidden" name="action" value="cancel">
                                <button type="submit" class="btn btn-secondary">
                                    <i class="bi bi-x-circle"></i> Cancel Import
                                </button>
                            </form>
                        </div>
                    </c:if>
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
        $(document).ready(function () {

            $('#calendar').fullCalendar({
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay,listWeek'
                },
                defaultDate: '2019-03-12',
                navLinks: true, // can click day/week names to navigate views

                weekNumbers: true,
                weekNumbersWithinDays: true,
                weekNumberCalculation: 'ISO',

                editable: true,
                eventLimit: true, // allow "more" link when too many events
                events: [
                    {
                        title: 'All Day Event',
                        start: '2019-03-01'
                    },
                    {
                        title: 'Long Event',
                        start: '2019-03-07',
                        end: '2019-03-10'
                    },
                    {
                        id: 999,
                        title: 'Repeating Event',
                        start: '2019-03-09T16:00:00'
                    },
                    {
                        id: 999,
                        title: 'Repeating Event',
                        start: '2019-03-16T16:00:00'
                    },
                    {
                        title: 'Conference',
                        start: '2019-03-11',
                        end: '2019-03-13'
                    },
                    {
                        title: 'Meeting',
                        start: '2019-03-12T10:30:00',
                        end: '2019-03-12T12:30:00'
                    },
                    {
                        title: 'Lunch',
                        start: '2019-03-12T12:00:00'
                    },
                    {
                        title: 'Meeting',
                        start: '2019-03-12T14:30:00'
                    },
                    {
                        title: 'Happy Hour',
                        start: '2019-03-12T17:30:00'
                    },
                    {
                        title: 'Dinner',
                        start: '2019-03-12T20:00:00'
                    },
                    {
                        title: 'Birthday Party',
                        start: '2019-03-13T07:00:00'
                    },
                    {
                        title: 'Click for Google',
                        url: 'http://google.com/',
                        start: '2019-03-28'
                    }
                ]
            });

        });
    </script>

</html>
