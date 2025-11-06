<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />
        <meta name="description" content="Job Detail - Human Tech" />
        <meta property="og:title" content="Job Detail - Human Tech" />
        <meta property="og:description" content="${post.title}" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets1/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets1/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>${post.title} - Human Tech</title>

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

        <style>
            body, html {
                height: 100%;
                margin: 0;
                background: #f5f7fa;
            }
            .page-wraper {
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }
            .page-content {
                flex: 1;
            }
            .detail-header-section {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                padding: 150px 0 60px 0;
                margin-bottom: 0;
            }
            .detail-header-title {
                color: #fff;
                font-size: 36px;
                font-weight: 700;
                text-align: center;
                margin: 0;
            }
            .job-detail-container {
                background: #fff;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                padding: 40px;
                margin: 40px 0;
            }
            .job-header {
                border-bottom: 2px solid #e6e6e6;
                padding-bottom: 30px;
                margin-bottom: 30px;
            }
            .job-title-main {
                font-size: 32px;
                font-weight: 700;
                color: #333;
                margin-bottom: 20px;
            }
            .job-meta-row {
                display: flex;
                gap: 30px;
                flex-wrap: wrap;
                margin-bottom: 25px;
            }
            .job-meta-item-detail {
                display: flex;
                align-items: center;
                gap: 10px;
                font-size: 15px;
                color: #666;
            }
            .job-meta-item-detail i {
                color: #f7b205;
                font-size: 18px;
            }
            .apply-btn-large {
                background: #f7b205;
                color: #000;
                padding: 15px 40px;
                border-radius: 8px;
                font-weight: 600;
                font-size: 16px;
                text-decoration: none;
                display: inline-block;
                transition: all 0.3s ease;
                border: none;
            }
            .apply-btn-large:hover {
                background: #4c1864;
                color: #fff;
                text-decoration: none;
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(247, 178, 5, 0.3);
            }
            .back-btn {
                background: #fff;
                color: #f7b205;
                padding: 12px 30px;
                border-radius: 8px;
                font-weight: 600;
                font-size: 14px;
                text-decoration: none;
                display: inline-block;
                transition: all 0.3s ease;
                border: 2px solid #f7b205;
                margin-right: 15px;
            }
            .back-btn:hover {
                background: #f7b205;
                color: #000;
                text-decoration: none;
            }
            .section-title {
                font-size: 24px;
                font-weight: 700;
                color: #333;
                margin-bottom: 25px;
                position: relative;
                padding-bottom: 15px;
            }
            .section-title:after {
                content: '';
                position: absolute;
                left: 0;
                bottom: 0;
                width: 60px;
                height: 3px;
                background: #f7b205;
            }
            .job-description-section {
                line-height: 1.8;
                color: #555;
                font-size: 15px;
            }
            .job-description-section p {
                margin-bottom: 15px;
            }
            .button-group {
                margin-top: 40px;
                padding-top: 30px;
                border-top: 2px solid #e6e6e6;
            }
        </style>
    </head>
    <body id="bg">
        <div class="page-wraper">
            <!-- Header -->
            <%@ include file="CommonItems/Header/homepageHeader.jsp" %>

            <div class="page-content bg-white">
                <!-- Header Section -->
                <div class="detail-header-section">
                    <div class="container">
                        <h1 class="detail-header-title">Job Details</h1>
                    </div>
                </div>

                <div class="container">
                    <div class="job-detail-container">
                        <!-- Job Header -->
                        <div class="job-header">
                            <h1 class="job-title-main">${post.title}</h1>
                            
                            <div class="job-meta-row">
                                <c:if test="${post.department != null}">
                                    <div class="job-meta-item-detail">
                                        <i class="fa fa-building-o"></i>
                                        <span>${post.department.depName}</span>
                                    </div>
                                </c:if>
                                
                                <c:if test="${post.approvedAt != null}">
                                    <div class="job-meta-item-detail">
                                        <i class="fa fa-calendar"></i>
                                        <span>Posted <fmt:formatDate value="${post.approvedAt}" pattern="MMM dd, yyyy" /></span>
                                    </div>
                                </c:if>
                            </div>
                            
                            <div class="button-group" style="border: none; margin-top: 20px; padding-top: 0;">
                                <a href="${pageContext.request.contextPath}/jobsite" class="back-btn">
                                    <i class="fa fa-arrow-left"></i> Back to Jobs
                                </a>
                            </div>
                        </div>

                        <!-- Job Description -->
                        <div>
                            <h3 class="section-title">Job Description</h3>
                            <div class="job-description-section">
                                ${post.content}
                            </div>
                        </div>

                        <!-- Action Buttons -->
                        <div class="button-group">
                            <a href="${pageContext.request.contextPath}/applyjob?postId=${post.postId}" 
                               class="apply-btn-large">
                                Apply Now <i class="fa fa-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <%@ include file="CommonItems/Footer/homepageFooter.jsp" %>

        <!-- External JavaScripts -->
        <script src="${pageContext.request.contextPath}/assets1/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/jquery.bootstrap-touchspin.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/counter/waypoints-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/counter/counterup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/masonry/masonry-3.1.4.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/masonry/masonry.filter.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/contact.js"></script>
    </body>
</html>
