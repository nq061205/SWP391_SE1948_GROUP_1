<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />
        <meta name="description" content="Human Tech - Apply Job Form" />
        <meta property="og:title" content="Human Tech - Candidate Form" />
        <meta property="og:description" content="Submit your application for open positions at Human Tech." />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets1/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets1/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Apply for a Job | Human Tech</title>

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

            <div class="page-content bg-white">
                <!-- Inner Banner -->
                <div class="page-banner ovbl-dark" style="background-image:url(${pageContext.request.contextPath}/assets1/images/banner/banner2.jpg);">
                    <div class="container">
                        <div class="page-banner-entry">
                            <h1 class="text-white">Candidate Application Form</h1>
                        </div>
                    </div>
                </div>
                <!-- Breadcrumb -->
                <div class="breadcrumb-row">
                    <div class="container">
                        <ul class="list-inline">
                            <li><a href="${pageContext.request.contextPath}/homepage">Home</a></li>
                            <li><a href="${pageContext.request.contextPath}/jobsite">Apply Job</a></li>
                        </ul>
                    </div>
                </div>

                <!-- Candidate Form Section -->
                <div class="content-block">
                    <div class="section-area section-sp1">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-8 col-md-10">
                                    <div class="heading-bx text-center mb-4">
                                        <h2 class="title-head">Submit Your <span>Application</span></h2>
                                        <p>Fill out the form below to apply for the selected job post.</p>
                                    </div>

                                    <form class="contact-bx" action="${pageContext.request.contextPath}/applyjob" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="postId" value="${requestScope.postId}">

                                        <div class="row placeani">
                                            <div class="col-lg-12 form-group">
                                                <label>Full Name</label>
                                                <input type="text" value="${requestScope.name}"name="name" class="form-control" placeholder="Enter your full name" required>
                                            </div>

                                            <div class="col-lg-12 form-group">
                                                <label>Email</label>
                                                <input type="email" value="${requestScope.email}" name="email" class="form-control" placeholder="Enter your email address" required>
                                            </div>

                                            <div class="col-lg-12 form-group">
                                                <label>Phone Number</label>
                                                <input type="text" name="phone" value="${requestScope.phone}" class="form-control" placeholder="Enter your phone number" required>
                                            </div>

                                            <div class="col-lg-12 form-group">
                                                <label>Upload CV (PDF only, max 5MB)</label>
                                                <input type="file" name="cvFile" class="form-control" accept=".pdf" required>
                                                <c:if test="${not empty fileName}">
                                                    <p style="margin-top: 5px; color: green;">
                                                        File uploaded: ${fileName}
                                                    </p>
                                                </c:if>
                                            </div>

                                            <div class="col-lg-12 text-center mt-3">
                                                <button type="submit" class="btn radius-xl btn-primary">Submit Application</button>
                                                <button type="reset" class="btn radius-xl btn-secondary">Reset</button>
                                            </div>
                                        </div>
                                    </form>

                                    <!-- Success / Error Notifications -->
                                    <c:if test="${not empty successMessage}">
                                        <div class="alert alert-success mt-3 text-center">
                                            ${successMessage}
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger mt-3 text-center">
                                            ${errorMessage}
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <%@ include file="CommonItems/Footer/homepageFooter.jsp" %>
            </div>

            <button class="back-to-top fa fa-chevron-up"></button>
        </div>

        <!-- External JavaScripts -->
        <script src="${pageContext.request.contextPath}/assets1/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/functions.js"></script>
    </body>
</html>
