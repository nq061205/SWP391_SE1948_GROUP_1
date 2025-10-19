<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Application Submitted Successfully</title>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
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
        <style>
            .success-hero {
                max-width: 760px;
                margin: 2rem auto;
            }
            .success-badge {
                width: 72px;
                height: 72px;
                border-radius: 50%;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                background: #eaf8ef;
                border: 1px solid #d2f0dc;
                box-shadow: 0 6px 18px rgba(16,185,129,.15);
            }
            .success-title {
                font-weight: 700;
                letter-spacing: .2px;
            }
            .kv {
                display: flex;
                gap: .5rem;
                align-items: center;
                padding: .5rem .75rem;
                border: 1px dashed #e5e7eb;
                border-radius: .5rem;
                background: #fafafa;
            }
            .btn-wrap .btn {
                min-width: 210px;
            }
            .fade-in-up {
                animation: fadeInUp .5s ease both;
            }
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translate3d(0, 10px, 0);
                }
                to   {
                    opacity: 1;
                    transform: translate3d(0, 0, 0);
                }
            }
        </style>
    </head>
    <body>
        <main class="ttr-wrapper">
            <div class="container-fluid mt-4">

                <section class="success-hero animate__animated animate__fadeIn">
                    <div class="card shadow-lg border-0">
                        <div class="card-body p-4 p-md-5 text-center">

                            <!-- Icon OK (SVG sắc nét) -->
                            <div class="success-badge mb-3 mx-auto fade-in-up" aria-hidden="true">
                                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="#10b981" viewBox="0 0 16 16">
                                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M6.97 10.97l5-5a.75.75 0 0 0-1.06-1.06L6.5 8.31 5.09 6.9a.75.75 0 1 0-1.06 1.06l2.44 2.44a.75.75 0 0 0 1.06 0z"/>
                                </svg>
                            </div>

                            <h2 class="success-title text-success mb-2">Send application successfully!</h2>
                            <p class="text-muted mb-4">
                                <c:if test="${type eq 'LEAVE'}">Leave</c:if>
                                <c:if test="${type eq 'OT'}">Overtime</c:if>
                                    application have been sent
                                </p> 

                                <!-- Tóm tắt nhanh (hiển thị nếu có dữ liệu) -->
                                <div class="row g-3 justify-content-center mb-4">

                                    <!-- Ví dụ thêm: thời gian, ca làm, số giờ OT... nếu servlet set attribute -->
                                <c:if test="${not empty startdate}">
                                    <div class="col-auto">
                                        <div class="kv">
                                            <i class="ti-calendar"></i>
                                            <span><strong>Từ ngày:</strong> ${startdate}</span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty enddate}">
                                    <div class="col-auto">
                                        <div class="kv">
                                            <i class="ti-calendar"></i>
                                            <span><strong>Đến ngày:</strong> ${enddate}</span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty othour}">
                                    <div class="col-auto">
                                        <div class="kv">
                                            <i class="ti-time"></i>
                                            <span><strong>Số giờ OT:</strong> ${othour}</span>
                                        </div>
                                    </div>
                                </c:if>
                            </div>

                            <!-- Nút điều hướng -->
                            <div class="btn-wrap d-flex flex-column flex-sm-row justify-content-center gap-2">
<!--                                <a href="${pageContext.request.contextPath}/Views/"
                                   class="btn btn-primary">
                                    <i class="ti-list"></i> Xem lịch
                                </a>-->

                                <c:choose>
                                    <c:when test="${type eq 'LEAVE'}">
                                        <a href="${pageContext.request.contextPath}/application?typeapplication=LEAVE"
                                           class="btn btn-outline-secondary">
                                            <i class="ti-dashboard"></i> Tracking leave application
                                        </a>
                                    </c:when>
                                    <c:when test="${type eq 'OT'}">
                                        <a href="${pageContext.request.contextPath}/application?typeapplication=OT"
                                           class="btn btn-outline-secondary">
                                            <i class="ti-dashboard"></i> Tracking leave overtime
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/Views/dashboard.jsp"
                                           class="btn btn-outline-secondary">
                                            <i class="ti-home"></i> Về Dashboard
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <!-- Gợi ý nhỏ -->
                            <p class="text-muted mt-3 mb-0" style="font-size:.925rem">
                                You will receive your notify when have a new status
                            </p>
                        </div>
                    </div>
                </section>
            </div>
        </main>

        <div class="ttr-overlay"></div>

        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/scripts.js"></script>
    </body>
</html>
