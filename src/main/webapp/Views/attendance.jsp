<%-- 
    Document   : profile
    Created on : Oct 4, 2025, 9:18:37 PM
    Author     : Lenovo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.time.*, java.util.*" %>
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
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
                table-layout: fixed;
                background: #fff;
                border: 1px solid #ccc;
                border-radius: 6px;
                overflow: hidden;
            }

            th {
                background-color: #f39c12;
                color: white;
                text-align: center;
                padding: 10px 0;
                font-weight: 600;
                border: 1px solid #ddd;
            }

            td {
                height: 90px;
                border: 1px solid #e0e0e0;
                vertical-align: top;
                position: relative;
                padding: 5px;
            }

            td strong {
                position: absolute;
                top: 4px;
                right: 6px;
                font-size: 12px;
                color: #555;
                font-weight: 600;
            }

            .inactive {
                background-color: #f8f8f8;
                color: #ccc;
            }

            .today {
                background-color: #fff9d6;
                border: 2px solid #f1c40f;
            }

            td:hover {
                background-color: #fef7e1;
                cursor: pointer;
                transition: 0.2s;
            }
            /* --- Calendar Table --- */
            .calendar-table {
                width: 100%;
                border-collapse: collapse;
                table-layout: fixed;
                background: #fff;
                border: 1px solid #ccc;
                border-radius: 6px;
                overflow: hidden;
            }
            .calendar-table th {
                background-color: #f39c12;
                color: white;
                text-align: center;
                padding: 10px 0;
                font-weight: 600;
                border: 1px solid #ddd;
            }
            .calendar-table td {
                height: 90px;
                border: 1px solid #e0e0e0;
                vertical-align: top;
                position: relative;
                padding: 5px;
                text-align: center;
            }
            .calendar-table td strong {
                position: absolute;
                top: 4px;
                right: 6px;
                font-size: 12px;
                color: #555;
                font-weight: 600;
            }

            /* --- Attendance States --- */
            .status {
                display: inline-block;
                margin-top: 20px;
                padding: 4px 8px;
                border-radius: 5px;
                font-size: 13px;
                color: white;
                font-weight: 600;
            }
            .present {
                background-color: #28a745;
            }      
            .absent {
                background-color: #e74c3c;
            }     
            .leave {
                background-color: #17a2b8;
            }       
            .holiday {
                background-color: #7f8c8d;
            }     
            .weekend {
                background-color: #2c3e50;
            }   
            .future {
                background-color: #fef7e1;
            } 
            .today {
                border: 2px solid #f1c40f;
            }

            /* --- Legend Box --- */
            .legend {
                background: #f9f9f9;
                border: 1px solid #ddd;
                border-radius: 6px;
                padding: 15px 20px;
                margin-bottom: 20px;
            }
            .legend h4 {
                font-weight: 600;
                margin-bottom: 15px;
            }
            .legend-item {
                display: inline-block;
                text-align: center;
                margin-right: 25px;
            }
            .legend-box {
                width: 40px;
                height: 40px;
                border-radius: 6px;
                color: white;
                font-weight: bold;
                display: flex;
                align-items: center;
                justify-content: center;
                margin: auto;
            }
            .legend-label {
                margin-top: 5px;
                font-size: 13px;
                color: #555;
            }
        </style>
    </head>
    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div id="legendPanel" class="card mb-3">
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
                <div class="filter-row mb-3">
                    <form method="get" action="attendance" class="d-flex align-items-center flex-nowrap w-100" style="gap:12px;">
                        <select name="month" onchange="this.form.submit()" style="width:160px;" class="form-control filter-h">
                            <c:forEach var="m" begin="1" end="12">
                                <option value="${m}" ${m == month ? 'selected' : ''}>
                                    ${m}
                                </option>
                            </c:forEach>
                        </select>

                        <select name="year" onchange="this.form.submit()" style="width:160px;" class="form-control filter-h">
                            <c:forEach var="y" begin="<%= java.time.Year.now().getValue()-5 %>" end="<%= java.time.Year.now().getValue()+5 %>">
                                <option value="${y}" ${y == year ? 'selected' : ''}>${y}</option>
                            </c:forEach>
                        </select>

                    </form>
                </div>
                <div class="widget-inner">
                    <table class="calendar-table">
                        <tr>
                            <th>Mon</th><th>Tue</th><th>Wed</th>
                            <th>Thu</th><th>Fri</th><th>Sat</th><th>Sun</th>
                        </tr>

                        <tr>
                            <c:forEach var="i" begin="1" end="${firstDayOfWeek == 7 ? 6 : firstDayOfWeek - 1}">
                                <td class="inactive"></td>
                            </c:forEach>

                            <c:forEach var="d" items="${days}">
                                <c:set var="isSunday" value="${d.dayOfWeek == 7}" />
                                <td class="calendar-cell ${d.status} ${d.isToday ? 'today' : ''}">
                                    <strong>${d.day}</strong>
                                    <div class="attendance-detail">
                                        <div><b>In:</b> ${d.checkIn}</div>
                                        <div><b>Out:</b> ${d.checkOut}</div>
                                        <div><b>Status:</b> ${d.status}</div>
                                        <div><b>Workday:</b> ${d.workday}</div>
                                    </div>
                                </td>
                                <c:if test="${isSunday}">
                                </tr>
                                <tr>
                                </c:if>
                            </c:forEach>

                            <c:set var="endDay" value="${days[days.size()-1].dayOfWeek}" />
                            <c:if test="${endDay != 7}">
                                <c:forEach var="i" begin="${endDay}" end="6">
                                    <td class="inactive"></td>
                                </c:forEach>
                            </c:if>
                        </tr>
                    </table>
                </div>
            </div>
        </main>

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
        <script src="${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js"></script>
    </body>
</html>
