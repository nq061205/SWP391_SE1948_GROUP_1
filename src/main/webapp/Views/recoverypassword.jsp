

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <meta property="og:title" content="Reset Password" />
        <meta property="og:description" content="Reset your Human Tech account password" />

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets1/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets1/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Reset Password | EduChamp</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/assets.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/color/color-1.css">
    </head>

    <body id="bg">
        <div class="page-wraper">
            <div id="loading-icon-bx"></div>

            <!-- Content -->
            <div class="account-form">
                <div class="account-head" style="background-image:url(${pageContext.request.contextPath}/assets1/images/background/bg2.jpg);">
                    <a href="${pageContext.request.contextPath}/index.jsp">
                        <img src="${pageContext.request.contextPath}/assets1/images/logo-white-2.png" alt=""/>
                    </a>
                </div>
                <div class="account-form-inner">
                    <div class="account-container">
                        <div class="heading-bx left">
                            <h2 class="title-head">Reset <span>Password</span></h2>
                            <p>Please enter your new password below.</p>
                        </div>

                        <form class="contact-bx" id="resetForm" action="${pageContext.request.contextPath}/recovery" method="post">
                            <div class="form-group">
                                <label>New Password</label>
                                <input name="newPassword" id="newPassword" type="password" required class="form-control" placeholder="Enter new password">
                            </div>
                            <div class="form-group">
                                <label>Confirm Password</label>
                                <input name="confirmPassword" id="confirmPassword" type="password" required class="form-control" placeholder="Re-enter new password">
                            </div>

                            <div id="message" class="m-b10" style="color:red; display:none;"></div>

                            <div class="form-group text-center">
                                <button type="submit" class="btn radius-xl" >Reset Password</button>
                            </div>




                            <div class="form-group text-center">
                                <a href="${pageContext.request.contextPath}/login" class="text-primary">Back to Login</a>
                            </div>
                        </form>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger text-center" role="alert" 
                                 style="color: #fff; background-color: #e74c3c; padding: 10px; border-radius: 8px; margin-bottom: 15px;">
                                ${errorMessage}
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <!-- External JavaScripts -->
        <script src="${pageContext.request.contextPath}/assets1/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/counter/waypoints-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/counter/counterup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/masonry/masonry.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/masonry/filter.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/contact.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/switcher/switcher.js"></script>



    </body>

</html>
