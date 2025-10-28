<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- META ============================================= -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content=""/>
    <meta name="author" content=""/>
    <meta name="robots" content=""/>

    <!-- DESCRIPTION -->
    <meta name="description" content="Human Tech"/>

    <!-- OG -->
    <meta property="og:title" content="Human Tech"/>
    <meta property="og:description" content="Human Tech"/>
    <meta property="og:image" content=""/>
    <meta name="format-detection" content="telephone=no">

    <!-- FAVICONS ICON ============================================= -->
    <link rel="icon" href="${pageContext.request.contextPath}/assets1/images/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets1/images/favicon.png"/>

    <!-- PAGE TITLE HERE ============================================= -->
    <title>Forget Password</title>

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
    <div class="account-form">
        <div class="account-head"
             style="background-image:url(${pageContext.request.contextPath}/assets1/images/background/bg2.jpg);">
            <a href="index.html">
                <img src="${pageContext.request.contextPath}/assets1/images/logo-white.png" alt="">
            </a>
        </div>

        <div class="account-form-inner">
            <div class="account-container">
                <div class="heading-bx left">
                    <h2 class="title-head">Forget <span>Password</span></h2>
                    <p>Login your account
                        <a href="${pageContext.request.contextPath}/login">Click here</a>
                    </p>
                </div>

                <form id="forget-password" class="contact-bx"
                      action="${pageContext.request.contextPath}/forgetpassword"
                      method="post" onsubmit="return checkCaptcha()">

                    <div class="row placeani">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <div class="input-group">
                                    <label>Your Email Address</label>
                                    <input name="email" type="email" required class="form-control">
                                </div>
                                <div style="margin-top: 20px" class="g-recaptcha"
                                     data-sitekey="6LeMV98rAAAAAIR7RyVdgvHsw_q2WtpVVHpd4crB"></div>
                                <div id="error" style="color: red; margin-top: 10px;"></div>
                            </div>
                        </div>

                        <!-- Hiển thị thông báo -->
                        <c:if test="${requestScope.errorMessage != null}">
                            <div style="color: red; text-align:center;">${requestScope.errorMessage}</div>
                        </c:if>
                        <c:if test="${sessionScope.successMessage != null}">
                            <div style="color: green; text-align:center;">${sessionScope.successMessage}</div>
                        </c:if>

                        <c:if test="${sessionScope.resetToken != null}">
                            <div class="text-center mt-3">
                                <p id="countdown" class="fw-bold text-danger"></p>
                            </div>
                        </c:if>

                        <div class="col-lg-12 m-b30 text-center">
                            <button id="submitBtn" name="submit" type="submit"
                                    <c:if test="${sessionScope.successMessage != null}">disabled</c:if>
                                    class="btn button-md">
                                Get Link
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- CAPTCHA & COUNTDOWN SCRIPT -->
<script type="text/javascript">
    function checkCaptcha() {
        const error = document.getElementById("error");
        const captcha = grecaptcha.getResponse();

        if (captcha.length === 0) {
            error.textContent = "Please verify that you are not a robot.";
            return false;
        } else {
            error.textContent = "";
            return true;
        }
    }

    const countdownElement = document.getElementById("countdown");
    if (countdownElement) {
        let timeLeft = 60;
        const btn = document.getElementById("submitBtn");
        btn.disabled = true;

        const timer = setInterval(() => {
            if (timeLeft <= 0) {
                clearInterval(timer);
                countdownElement.innerHTML = "⏰ Link has expired. You can request again.";
                btn.disabled = false;
            } else {
                countdownElement.innerHTML = `Please wait 60s before requesting again...`;
                timeLeft--;
            }
        }, 1000);
    }
</script>

<!-- JS FILES -->
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
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
</body>
</html>
