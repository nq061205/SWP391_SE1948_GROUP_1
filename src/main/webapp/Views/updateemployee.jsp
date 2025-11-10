<%-- 
    Document   : profile
    Created on : Oct 4, 2025, 9:18:37 PM
    Author     : Lenovo
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
        <meta property="og:description" content="Profile" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Update Employee</title>

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
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- HEADER + NAVBAR -->
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Employee Information</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/employeelist">Employee list</a></li>
                        <li>Employee Information</li>
                    </ul>
                </div>	

                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4>Employee information</h4>
                            </div>
                            <div class="widget-inner">
                                <form class="edit-profile m-b30" action="${pageContext.request.contextPath}/updateemployee" method="POST">
                                    <div class="row">
                                        <div class="col-md-3 text-center">
                                            <div class="profile-avatar mb-3">
                                                <img src="${sessionScope.emp.image}" alt="Avatar"  width="225" height="300">
                                            </div>
                                        </div>

                                        <div class="col-md-9">
                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Employee Code:</label>
                                                <div class="col-sm-9">
                                                    <input class="form-control" type="text" name="empCode" value="${sessionScope.emp.empCode}" readonly>
                                                </div>
                                            </div>

                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Full Name:</label>
                                                <div class="col-sm-9">
                                                    <input class="form-control" type="text" value="${sessionScope.emp.fullname}" readonly>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Email:</label>
                                                <div class="col-sm-9">
                                                    <input name="email" class="form-control"  type="email" value="${sessionScope.emp.email}" required>
                                                    <p style="color: red">${EmailErr}</p>
                                                </div>
                                            </div>
                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Gender:</label>
                                                <div class="col-sm-9">
                                                    <select class="form-control" name="gender">
                                                        <option value="true" ${sessionScope.emp.gender ? 'selected' : ''}>Male</option>
                                                        <option value="false" ${!sessionScope.emp.gender ? 'selected' : ''}>Female</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Date of birth:</label>
                                                <div class="col-sm-9">
                                                    <input class="form-control" name="dob" type="date" value="${sessionScope.emp.dob}"  required>
                                                    <p style="color:red">${dobErr}</p>
                                                </div>
                                            </div>
                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Position Title:</label>
                                                <div class="col-sm-9">
                                                    <select name="positionTitle" class="form-control">
                                                        <c:forEach var="opt" items="${posOptions}">
                                                            <option value="${opt.value}"
                                                                    <c:if test="${opt.selected eq 'true'}">selected</c:if>
                                                                    <c:if test="${opt.disabled eq 'true'}">disabled</c:if>>
                                                                ${opt.value} ${opt.label}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Paid leaves day:</label>
                                                <div class="col-sm-9">
                                                    <input class="form-control" name="paidleaveday" type="number" value="${sessionScope.emp.paidLeaveDays}" readonly >
                                                </div>
                                            </div>
                                            <div class="form-group row mt-4">
                                                <div class="col-sm-9 offset-sm-3">
                                                    <button type="button" class="btn btn-secondary"
                                                            onclick="window.location.href = '${pageContext.request.contextPath}/employeelist'">
                                                        ← Back
                                                    </button>
                                                    <button type="submit" name="button" value="save"class="btn btn-primary"
                                                            onclick="return confirm('Do you confirm save change?');"
                                                            >Save</button>
                                                    <p>${message}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!-- SCRIPT ZONE -->
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