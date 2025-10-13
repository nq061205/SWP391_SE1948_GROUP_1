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

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap4.min.css">
        <style>
            /* Attendance Calendar - Simplified Version */
            .attendance-calendar {
                font-size: 0.9rem;
            }

            .employee-col {
                width: 180px;
                min-width: 180px;
                position: sticky;
                left: 0;
                z-index: 10;
                background-color: #fff;
                border-right: 2px solid #dee2e6;
            }

            .day-header {
                width: 40px;
                min-width: 40px;
                text-align: center;
                font-size: 0.75rem;
            }

            .weekend-header {
                background-color: #343a40 !important;
                color: white !important;
            }

            .day-cell {
                width: 40px;
                min-width: 40px;
                height: 50px;
                text-align: center;
                vertical-align: middle;
                cursor: pointer;
                padding: 2px;
            }

            .day-cell:hover:not(.weekend-cell) {
                opacity: 0.8;
                transform: scale(1.05);
                transition: all 0.2s;
            }

            .work-day-text {
                font-weight: bold;
                font-size: 0.9rem;
                color: white;
                text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
            }

            /* Status Colors */
            .status-present {
                background-color: #28a745 !important; /* Green */
            }

            .status-absent {
                background-color: #dc3545 !important; /* Red */
            }

            .status-leave {
                background-color: #007bff !important; /* Blue */
            }

            .status-holiday {
                background-color: #6c757d !important; /* Gray */
            }

            .weekend-cell {
                background-color: #343a40 !important; /* Dark */
                color: #adb5bd !important;
            }

            .weekend-cell .work-day-text {
                color: #adb5bd !important;
                font-weight: normal;
            }

            .summary-cell {
                background-color: #f8f9fa;
                font-size: 0.9rem;
                padding: 8px;
            }

            /* Legend */
            .legend-box {
                width: 35px;
                height: 35px;
                border-radius: 4px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: bold;
                color: white;
                margin: 0 auto;
                text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
            }

            .status-weekend {
                background-color: #343a40 !important;
                color: #adb5bd !important;
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
                    <h4 class="breadcrumb-title">Monthly Attendance Report</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>Attendance Management</li>
                        <li>Monthly Report</li>
                    </ul>
                </div>

                <!-- Monthly Attendance -->
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-calendar"></i> Monthly Attendance Report</h4>
                                <div class="float-right">
                                    <button class="btn btn-success btn-sm" onclick="exportAttendance()">
                                        <i class="fa fa-download"></i> Export Excel
                                    </button>
                                    <button class="btn btn-info btn-sm" onclick="toggleLegend()">
                                        <i class="fa fa-info-circle"></i> Legend
                                    </button>
                                </div>
                            </div>
                            <div class="widget-inner">
                                <!-- Month/Year Selection -->
                                <form method="get" action="monthlyAttendance" class="row align-items-center mb-4">
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label for="selectedMonth">Select Month</label>
                                            <select name="month" id="selectedMonth" class="form-control" onchange="this.form.submit()">
                                                <option value="1" ${selectedMonth == 1 ? 'selected' : ''}>January</option>
                                                <option value="2" ${selectedMonth == 2 ? 'selected' : ''}>February</option>
                                                <!-- ... other months ... -->
                                                <option value="12" ${selectedMonth == 12 ? 'selected' : ''}>December</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <label for="selectedYear">Year</label>
                                            <select name="year" id="selectedYear" class="form-control" onchange="this.form.submit()">
                                                <option value="2024" ${selectedYear == 2024 ? 'selected' : ''}>2024</option>
                                                <option value="2025" ${selectedYear == 2025 ? 'selected' : ''}>2025</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label for="departmentFilter">Department</label>
                                            <select name="department" id="departmentFilter" class="form-control" onchange="this.form.submit()">
                                                <option value="">All Departments</option>
                                                <option value="IT" ${selectedDepartment == 'IT' ? 'selected' : ''}>IT</option>
                                                <option value="HR" ${selectedDepartment == 'HR' ? 'selected' : ''}>HR</option>
                                            </select>
                                        </div>
                                    </div>
                                </form>

                                <!-- Legend Panel -->
                                <div id="legendPanel" class="card mb-3" style="display: none;">
                                    <div class="card-header">
                                        <h6 class="mb-0"><i class="fa fa-info-circle"></i> Legend</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="text-center p-2">
                                                    <div class="legend-box status-present">1</div>
                                                    <small>Full Day<br>1 work day</small>
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="text-center p-2">
                                                    <div class="legend-box status-present">0.5</div>
                                                    <small>Half Day<br>0.5 work day</small>
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="text-center p-2">
                                                    <div class="legend-box status-absent">0</div>
                                                    <small>Absent<br>0 work day</small>
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="text-center p-2">
                                                    <div class="legend-box status-present">1T</div>
                                                    <small>With Overtime<br>T = OT Hours</small>
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="text-center p-2">
                                                    <div class="legend-box status-weekend">-</div>
                                                    <small>Weekend<br>Non-working</small>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mt-2">
                                            <div class="col-md-12">
                                                <small class="text-muted">
                                                    <strong>Colors:</strong> 
                                                    <span class="badge status-present">Green = Present</span>
                                                    <span class="badge status-absent">Red = Absent</span>
                                                    <span class="badge status-leave">Blue = Leave</span>
                                                    <span class="badge status-holiday">Gray = Holiday</span>
                                                    <span class="badge status-weekend">Dark = Weekend</span>
                                                </small>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Monthly Attendance Calendar -->
                                <div class="table-responsive attendance-calendar">
                                    <table class="table table-bordered table-sm">
                                        <thead>
                                            <tr class="bg-dark text-white">
                                                <th rowspan="2" class="employee-col">Employee</th>
                                                <th colspan="${daysInMonth}" class="text-center">${monthName} ${selectedYear}</th>
                                                <th rowspan="2" class="summary-col">Total<br>Work Days</th>
                                                <th rowspan="2" class="summary-col">Total<br>OT Hours</th>
                                            </tr>
                                            <tr class="bg-secondary text-white">
                                                <!-- Day numbers -->
                                                <c:forEach begin="1" end="${daysInMonth}" var="day">
                                                    <th class="day-header ${weekendDays.contains(day) ? 'weekend-header' : ''}">
                                                        ${day}
                                                        <br><small class="day-name">${dayNames[day-1]}</small>
                                                    </th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="employee" items="${employeeAttendanceList}">
                                                <tr>
                                                    <!-- Employee Info -->
                                                    <td class="employee-info">
                                                        <div class="employee-name">
                                                            <strong>${employee.empCode}</strong>
                                                            <br><small class="text-muted">${employee.name}</small>
                                                        </div>
                                                    </td>

                                                    <!-- Daily Attendance -->
                                                    <c:forEach begin="1" end="${daysInMonth}" var="day">
                                                        <c:set var="attendance" value="${employee.dailyAttendance[day-1]}" />
                                                        <c:set var="isWeekend" value="${weekendDays.contains(day)}" />

                                                        <td class="day-cell ${isWeekend ? 'weekend-cell' : 'status-'.concat(attendance.status)}" 
                                                            onclick="showDayDetail('${employee.empCode}', '${day}')">
                                                            <c:choose>
                                                                <c:when test="${isWeekend}">
                                                                    <span class="work-day-text">-</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="work-day-text">
                                                                        ${attendance.workDay}${attendance.otHours > 0 ? 'T' : ''}
                                                                    </span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </c:forEach>

                                                    <!-- Summary -->
                                                    <td class="summary-cell text-center">
                                                        <strong class="text-primary">${employee.totalWorkDays}</strong>
                                                    </td>
                                                    <td class="summary-cell text-center">
                                                        <strong class="text-warning">${employee.totalOTHours}h</strong>
                                                    </td>
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
    <script>
        function toggleLegend() {
            const panel = document.getElementById('legendPanel');
            panel.style.display = panel.style.display === 'none' ? 'block' : 'none';
        }

        function showDayDetail(empCode, day) {
            // Show attendance details for specific day
            alert(`View details: ${empCode}, Day ${day}`);
        }

        function exportAttendance() {
            // Export to Excel
            window.location.href = 'monthlyAttendance?action=export&month=' +
                    document.getElementById('selectedMonth').value + '&year=' +
                    document.getElementById('selectedYear').value;
        }
    </script>

</html>
