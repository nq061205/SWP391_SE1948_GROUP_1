<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Change Password</title>

        <!-- CSS Links (giống homepage.jsp) -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets1/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets1/images/favicon.png" />

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/color/color-1.css">

        <style>
            /* Tùy chỉnh hiển thị thông báo */
            .alert-box {
                padding: 12px 16px;
                border-radius: 8px;
                margin-bottom: 20px;
                font-weight: 500;
                text-align: center;
            }
            .alert-error {
                background-color: #ffe6e6;
                color: #cc0000;
                border: 1px solid #cc0000;
            }
            .alert-success {
                background-color: #e6ffed;
                color: #006600;
                border: 1px solid #009900;
            }
        </style>
    </head>

    <body id="bg">
        <div class="page-wraper">
            <div id="loading-icon-bx"></div>

            <!-- Header -->
            <%@ include file="CommonItems/Header/homepageHeader.jsp" %>

            <!-- Change Password Section -->
            <div class="page-content bg-white">
                <div class="section-area section-sp1">
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-8 col-md-10 col-sm-12">
                                <div class="profile-content-bx">
                                    <div class="tab-pane" id="change-password">
                                        <div class="profile-head text-center mb-4">
                                            <h3>Change Password</h3>
                                        </div>

                                        <!-- Hiển thị thông báo -->
                                        <c:if test="${not empty errorMessage}">
                                            <div class="alert-box alert-error">
                                                ${errorMessage}
                                            </div>
                                        </c:if>

                                        <c:if test="${not empty successMessage}">
                                            <div class="alert-box alert-success">
                                                ${successMessage}
                                            </div>
                                        </c:if>

                                        <!-- Form đổi mật khẩu -->
                                        <form class="edit-profile" action="changepassword" method="post">
                                            <div class="form-group row">
                                                <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">
                                                    Current Password
                                                </label>
                                                <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                    <input class="form-control" type="password" name="currentPassword" required>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">
                                                    New Password
                                                </label>
                                                <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                    <input class="form-control" type="password" name="newPassword" required>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">
                                                    Re-Type New Password
                                                </label>
                                                <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                    <input class="form-control" type="password" name="confirmPassword" required>
                                                </div>
                                            </div>

                                            <div class="row mt-4">
                                                <div class="col-12 col-sm-4 col-md-4 col-lg-3"></div>
                                                <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                    <button type="submit" class="btn btn-primary">Save changes</button>
                                                    <button type="reset" class="btn btn-secondry">Delete</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div> 
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="CommonItems/Footer/homepageFooter.jsp" %>

            <button class="back-to-top fa fa-chevron-up"></button>
        </div>

        <!-- JS Scripts -->
        <script src="${pageContext.request.contextPath}/assets1/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/counter/waypoints-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/counter/counterup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets1/js/functions.js"></script>
    </body>
</html>
