<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />
        <meta name="description" content="HR Management System" />
        <meta property="og:title" content="HR Dashboard - HRM System" />
        <meta property="og:description" content="Human Resources Management Dashboard" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />
        <title>HR Dashboard - HRM System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/assets2/js/html5shiv.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/respond.min.js"></script>
        <![endif]-->

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <jsp:include page="../CommonItems/Header/dashboardHeader.jsp" />
        
        <!-- Include Navbar -->
        <jsp:include page="../CommonItems/Navbar/empNavbar.jsp" />

        <!--Main container start -->
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">HR Dashboard</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>HR Dashboard</li>
                    </ul>
                </div>	
                
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4>Quick Actions</h4>
                            </div>
                            <div class="widget-inner">
                                <div class="row">
                                    <div class="col-lg-2 col-md-4 col-sm-6 col-6">
                                        <div class="card text-center">
                                            <div class="card-body">
                                                <div class="text-primary mb-2">
                                                    <i class="fa fa-users fa-2x"></i>
                                                </div>
                                                <h6 class="card-title">Manage Employees</h6>
                                                <a href="#" class="btn btn-outline-primary btn-sm">View All</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-md-4 col-sm-6 col-6">
                                        <div class="card text-center">
                                            <div class="card-body">
                                                <div class="text-success mb-2">
                                                    <i class="fa fa-calendar-check-o fa-2x"></i>
                                                </div>
                                                <h6 class="card-title">Leave Approvals</h6>
                                                <a href="#" class="btn btn-outline-success btn-sm">Review</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-md-4 col-sm-6 col-6">
                                        <div class="card text-center">
                                            <div class="card-body">
                                                <div class="text-info mb-2">
                                                    <i class="fa fa-briefcase fa-2x"></i>
                                                </div>
                                                <h6 class="card-title">Job Postings</h6>
                                                <a href="${pageContext.request.contextPath}/hrrecruitment" class="btn btn-outline-info btn-sm">Manage</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-md-4 col-sm-6 col-6">
                                        <div class="card text-center">
                                            <div class="card-body">
                                                <div class="text-warning mb-2">
                                                    <i class="fa fa-user-plus fa-2x"></i>
                                                </div>
                                                <h6 class="card-title">Candidates</h6>
                                                <a href="#" class="btn btn-outline-warning btn-sm">View</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-md-4 col-sm-6 col-6">
                                        <div class="card text-center">
                                            <div class="card-body">
                                                <div class="text-purple mb-2">
                                                    <i class="fa fa-clock-o fa-2x"></i>
                                                </div>
                                                <h6 class="card-title">Attendance</h6>
                                                <a href="#" class="btn btn-outline-secondary btn-sm">Reports</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-md-4 col-sm-6 col-6">
                                        <div class="card text-center">
                                            <div class="card-body">
                                                <div class="text-danger mb-2">
                                                    <i class="fa fa-file-text-o fa-2x"></i>
                                                </div>
                                                <h6 class="card-title">Reports</h6>
                                                <a href="#" class="btn btn-outline-danger btn-sm">Generate</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
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
        <script src="${pageContext.request.contextPath}/assets2/vendors/calendar/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js"></script>

        <script>
            $(document).ready(function () {
            });
        </script>
    </body>
</html>
