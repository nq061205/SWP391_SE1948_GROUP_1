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
        <title>EduChamp : Education HTML Template </title>

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
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Compose Application</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Compose Application</a></li>
                    </ul>
                </div>
                <c:choose>
                    <c:when test="${empty type}">
                        <div class="row">
                            <div class="col-lg-8 offset-lg-2">
                                <div class="widget-box p-4 shadow-sm bg-white rounded">
                                    <form class="mail-compose" method="get" action="${pageContext.request.contextPath}/compose">
                                        <div class="form-group mb-3">
                                            <label for="type">Application Type:</label>
                                            <select id="type" name="type" class="form-control"  required>
                                                <option value="LEAVE">Leave Request</option>
                                                <option value="OT">OT Request</option>
                                            </select>
                                        </div>
                                        <div class="text-right">
                                            <button type="submit" class="btn btn-primary btn-lg">Next</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:when>

                    <c:when test="${type eq 'LEAVE'}">            
                        <div class="row">
                            <div class="col-lg-8 offset-lg-2">
                                <div class="widget-box p-4 shadow-sm bg-white rounded">
                                    <form class="mail-compose" method="post" action="${pageContext.request.contextPath}/compose">
                                        <input type="hidden" name="type" value="LEAVE"/>
                                        <div class="form-group mb-3">
                                            <label for="to">Receiver:</label>
                                            <input type="email" id="to" name="email" class="form-control" placeholder="Email receiver" value="${email}" required>
                                            <c:if test="${message!=null}">
                                                <input type="text" class="form-control" value="${message}">
                                            </c:if>
                                        </div>
                                        <div class="form-group mb-3">
                                            <label for="type">Leave Type:</label>
                                            <select id="type" name="type_leave" class="form-control" required>
                                                <option value="" selected>Select application type</option>
                                                <option value="Annual"
                                                        <c:if test="${type_value eq 'Annual'}">
                                                            selected
                                                        </c:if>
                                                        >Annual Leave</option>
                                                <option value="Sick"
                                                        <c:if test="${type_value eq 'Sick'}">
                                                            selected
                                                        </c:if>
                                                        >Sick</option>
                                                <option value="Unpaid"
                                                        <c:if test="${type_value eq 'Unpaid'}">
                                                            selected
                                                        </c:if>
                                                        >Unpaid</option>
                                                <option value="Maternity"
                                                        <c:if test="${type_value eq 'Maternity'}">
                                                            selected
                                                        </c:if>
                                                        >Maternity</option>
                                                <option value="Other"
                                                        <c:if test="${type_value eq 'Other'}">
                                                            selected
                                                        </c:if>
                                                        >Other</option>
                                            </select>
                                        </div>

                                        <div class="form-group mb-3">
                                            <label for="startdate">From:</label>
                                            <input type="date" name="startdate" value="${startdate}" class="form-control" required />
                                        </div>

                                        <div class="form-group mb-3">
                                            <label for="enddate">To:</label>
                                            <input type="date" name="enddate" value="${enddate}" class="form-control" required/>
                                        </div>

                                        <div class="form-group mb-3">
                                            <label for="content">Reason:</label>
                                            <textarea id="content" name="content" class="form-control" rows="6" placeholder="Typing here..." required>${content}</textarea>
                                        </div>

                                        <div class="text-right">
                                            <button type="submit" class="btn btn-primary btn-lg">Send</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:when>


                    <c:when test="${type eq 'OT'}">            
                        <div class="row">
                            <div class="col-lg-8 offset-lg-2">
                                <div class="widget-box p-4 shadow-sm bg-white rounded">
                                    <form class="mail-compose" method="post" action="${pageContext.request.contextPath}/compose">
                                        <input type="hidden" name="type" value="OT"/>
                                        <div class="form-group mb-3">
                                            <label for="to">Receiver:</label>
                                            <input type="email" id="to" name="email" class="form-control" placeholder="Email receiver" value="${email}" required>
                                            <c:if test="${message!=null}">
                                                <input type="text" class="form-control" value="${message}">
                                            </c:if>
                                        </div>

                                        <div class="form-group mb-3">
                                            <label for="type">Date:</label>
                                            <input type="date" name="date" value="${date}" class="form-control" required/>
                                        </div>

                                        <div class="form-group mb-3">
                                            <label for="type">Overtime hours:</label>
                                            <input type="number" name="othour" value="${othour}" class="form-control" min="0" max="4"/>
                                        </div>

                                        <div class="text-right">
                                            <button type="submit" class="btn btn-primary btn-lg">Send</button>
                                        </div>
                                    </form>
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
