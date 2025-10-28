<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
        <title>Application Detail </title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/assets2/js/html5shiv.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/respond.min.js"></script>
        <![endif]-->

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/summernote/summernote.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/file-upload/imageuploadify.min.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <c:choose>
                    <c:when test="${type eq 'leave'}">
                        <div class="db-breadcrumb">
                            <h4 class="breadcrumb-title">Application</h4>
                            <ul class="db-breadcrumb-list">
                                <li><a href="${pageContext.request.contextPath}/application?typeapplication=leave"><i class="fa fa-home"></i>Leave Request</a></li>
                                <li>Leave Request Detail</li>
                            </ul>
                        </div>
                    </c:when>
                    <c:when test="${type eq 'ot'}">
                        <div class="db-breadcrumb">
                            <h4 class="breadcrumb-title">Application</h4>
                            <ul class="db-breadcrumb-list">
                                <li><a href="${pageContext.request.contextPath}/application?typeapplication=ot"><i class="fa fa-home"></i>Overtime Request</a></li>
                                <li>Overtime Request Detail</li>
                            </ul>
                        </div>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${type eq 'leave'}">            
                        <div class="row">
                            <div class="col-lg-8 offset-lg-2">
                                <div class="widget-box p-4 shadow-sm bg-white rounded">
                                    <div class="form-group mb-3">
                                        <label for="to">Receiver:</label>
                                        <input type="text" class="form-control" value="${leave.approvedBy.email}" disabled>
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="type">Leave Type:</label>
                                        <c:choose>
                                            <c:when test="${leave.leaveType == 'Annual Leave'}">
                                                <input type="text" class="form-control" value="Annual Leave" disabled>
                                            </c:when>
                                            <c:when test="${leave.leaveType == 'Sick'}">
                                                <input type="text" class="form-control" value="Sick" disabled>
                                            </c:when>
                                            <c:when test="${leave.leaveType == 'Unpaid'}">
                                                <input type="text" class="form-control" value="Unpaid" disabled>
                                            </c:when>
                                            <c:when test="${leave.leaveType == 'Maternity'}">
                                                <input type="text" class="form-control" value="Maternity" disabled>
                                            </c:when>
                                            <c:when test="${leave.leaveType == 'Other'}">
                                                <input type="text" class="form-control" value="Other" disabled>
                                            </c:when>
                                        </c:choose>
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="startdate">From:</label>
                                        <input type="date" name="startdate" value="${leave.startDate}" class="form-control" disabled />
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="enddate">To:</label>
                                        <input type="date" name="enddate" value="${leave.endDate}" class="form-control" disabled/>
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="content">Reason:</label>
                                        <textarea id="content" name="content" class="form-control" disabled>${leave.reason}</textarea>
                                    </div>

                                    <div class="form-group mb-3">
                                        <p>
                                            Leave Request — ${leave.createdAt}
                                            (
                                            Status:
                                            <span style="font-weight: bold;
                                                  color:
                                                  <c:choose>
                                                      <c:when test="${leave.status eq 'Approved'}">green</c:when>
                                                      <c:when test="${leave.status eq 'Rejected'}">red</c:when>
                                                      <c:otherwise>goldenrod</c:otherwise>
                                                  </c:choose>;
                                                  ">
                                                ${leave.status}
                                            </span>
                                            )
                                        </p>
                                    </div>

                                    <div class="text-right">
                                        <a href="${pageContext.request.contextPath}/application?typeapplication=leave" class="ttr-material-button">
                                            <span class="btn btn-primary btn-lg">Return</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>

                    <c:when test="${type eq  'ot'}">            
                        <div class="row">
                            <div class="col-lg-8 offset-lg-2">
                                <div class="widget-box p-4 shadow-sm bg-white rounded">
                                    <div class="form-group mb-3">
                                        <label for="to">Receiver:</label>
                                        <input type="email" id="to" name="email" class="form-control" value="${ot.approvedBy.email}" disabled>
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="type">Date:</label>
                                        <input type="date" name="date" value="${ot.date}" class="form-control" disabled/>
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="type">Overtime hours:</label>
                                        <input type="number" name="othour" value="${ot.otHours}" class="form-control" disabled/>
                                    </div>

                                      <div class="form-group mb-3">
                                        <p>
                                            Overtime Request — ${ot.createdAt}
                                            (
                                            Status:
                                            <span style="font-weight: bold;
                                                  color:
                                                  <c:choose>
                                                      <c:when test="${ot.status eq 'Approved'}">green</c:when>
                                                      <c:when test="${ot.status eq 'Rejected'}">red</c:when>
                                                      <c:otherwise>goldenrod</c:otherwise>
                                                  </c:choose>;
                                                  ">
                                                ${ot.status}
                                            </span>
                                            )
                                        </p>
                                    </div>
                                    <div class="text-right">
                                        <a href="${pageContext.request.contextPath}/application?typeapplication=ot" class="ttr-material-button">
                                            <span class="btn btn-primary btn-lg">Return</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
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
        <script src='${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js'></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/summernote/summernote.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/file-upload/imageuploadify.min.js"></script>
        <script src='${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js'></script>
        <!-- include plugin -->
        <script>
            $(document).ready(function () {
                $('.summernote').summernote({
                    height: 300,
                    tabsize: 2
                });

                $('input[type="file"]').imageuploadify();
            });
        </script>

    </body>
</html>
