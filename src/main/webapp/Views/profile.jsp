<%-- 
    Document   : profile
    Created on : Oct 4, 2025, 9:18:37 PM
    Author     : Lenovo
--%>

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
        <title>EduChamp : Education HTML Template</title>

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
                    <h4 class="breadcrumb-title">Personal information</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>Personal Profile</li>
                    </ul>
                </div>	

                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4>Personal record</h4>
                            </div>
                            <div class="widget-inner">
                                <form class="edit-profile m-b30" action="${pageContext.request.contextPath}/profile" method="POST" enctype="multipart/form-data">
                                    <div class="row">
                                        <div class="col-md-3 text-center">
                                            <div class="profile-avatar mb-3">
                                                <img src="${sessionScope.user.image}" alt="Avatar"  width="225" height="300">
                                            </div>
                                        </div>

                                        <div class="col-md-9">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Fullname</label>
                                                <div class="col-sm-9">
                                                    <input name="fullname" class="form-control" type="text" value="${sessionScope.user.fullname}"
                                                           <c:if test="${click != 'save'}"> readonly required </c:if>>
                                                    <c:if test="${fullnameErr!=null}">
                                                        <input style="color: red" type="text" class="form-control" value="${fullnameErr}">
                                                    </c:if>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Position</label>
                                                <div class="col-sm-9">
                                                    <input name="position" class="form-control" type="text" value="${sessionScope.user.positionTitle}" readonly>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Email</label>
                                                <div class="col-sm-9">
                                                    <input name = "email" class="form-control" type="text" value="${sessionScope.user.email}" readonly>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Gender</label>
                                                <div class="col-sm-9">
                                                    <c:if test="${click != 'save'}">
                                                        <input class="form-control" name = "gender" type="text"
                                                               value='${sessionScope.user.gender == "true" ? "Male" : "Female"}'
                                                               readonly>
                                                    </c:if>
                                                    <c:if test="${click == 'save'}">
                                                        <select name="gender" class="form-control">
                                                            <option value="Male" ${sessionScope.user.gender == "true" ? "selected" : ""}>Male</option>
                                                            <option value="Female" ${sessionScope.user.gender == "false" ? "selected" : ""}>Female</option>
                                                        </select>
                                                    </c:if>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Day of birth</label>
                                                <div class="col-sm-9">
                                                    <input name="dob" class="form-control" type="date" value="${sessionScope.user.dob}"
                                                           <c:if test="${click != 'save'}"> readonly </c:if>>
                                                    <c:if test="${dobErr!=null}">
                                                        <input style="color: red" type="text" class="form-control" value="${dobErr}">
                                                    </c:if>   
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Phone</label>
                                                <div class="col-sm-9">
                                                    <input name="phone" 
                                                           class="form-control" type="text" value="${sessionScope.user.phone}" pattern="\d{10}" title="Phone must include 10 number" minlength="10" maxlength="10"
                                                           <c:if test="${click != 'save'}"> readonly </c:if>>
                                                    <c:if test="${phoneErr!=null}">
                                                        <input style="color: red" type="text" class="form-control" value="${phoneErr}">
                                                    </c:if>      
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Paid Leave Remain Days </label>
                                                <div class="col-sm-9">
                                                    <input name="paidLeaveDay" class="form-control" type="text" value="${sessionScope.user.paidLeaveDays}" readonly>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label" <c:if test="${click != 'save'}">hidden</c:if> >Image</label>
                                                    <div class="col-sm-9">
                                                    <c:if test="${click == 'save'}">
                                                        <input type="file" class="form-control" name="image" accept="image/*" title="Upload your avatar" required/>
                                                        <c:if test="${not empty avatarErr}">
                                                            <div class="text-danger small mt-1">${avatarErr}</div>
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${click != 'save'}">
                                                        <input type="hidden" name="image" value="${sessionScope.user.image}">
                                                    </c:if>
                                                </div>
                                            </div>

                                            <div class="form-group row mt-4">
                                                <div class="col-sm-9 offset-sm-3">
                                                    <c:if test="${click != 'save'}">
                                                        <button type="submit" name="click" value=""class="btn btn-primary">Change information</button>
                                                    </c:if>
                                                    <c:if test="${click == 'save'}">
                                                        <button type="reset"class="btn btn-secondary">Cancel</button>
                                                        <button type="submit" name="click" value="save"class="btn btn-primary"
                                                                onclick="return confirm('Do you confirm save change?');"
                                                                >Save</button>
                                                    </c:if>
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