<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.RecruitmentPost" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <!-- META ============================================= -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="Job, HRM, Human Tech, Career" />
    <meta name="author" content="Human Tech" />
    <meta name="robots" content="index, follow" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Career Opportunities</title>

    <!-- FAVICONS ICON ============================================= -->
    <link rel="icon" href="${pageContext.request.contextPath}/assets1/images/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets1/images/favicon.png" />

    <!-- CSS ============================================= -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/assets.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/typography.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/shortcodes/shortcodes.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/style.css">
    <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets1/css/color/color-1.css">

    <style>
        .career-section {
            padding: 80px 0;
            background-color: #f9f9f9;
        }
        .career-header {
            text-align: center;
            margin-bottom: 50px;
        }
        .career-header h2 {
            font-size: 36px;
            font-weight: 700;
            color: #3d4465;
            text-transform: uppercase;
        }
        .career-header p {
            color: #6c757d;
            font-size: 16px;
        }
        .job-card {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 5px 25px rgba(0,0,0,0.06);
            margin-bottom: 30px;
            padding: 25px 30px;
            transition: all .3s ease;
        }
        .job-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 30px rgba(0,0,0,0.1);
        }
        .job-title {
            font-size: 20px;
            color: #222;
            font-weight: 600;
            margin-bottom: 10px;
        }
        .job-meta {
            color: #555;
            font-size: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .job-meta i {
            color: var(--primary);
        }
        .apply-btn {
            margin-top: 15px;
            display: inline-block;
        }
        .no-jobs {
            text-align: center;
            padding: 60px 20px;
            background: #fff;
            border-radius: 6px;
            box-shadow: 0 5px 25px rgba(0,0,0,0.05);
        }
        .no-jobs i {
            font-size: 60px;
            color: #ccc;
            margin-bottom: 20px;
            display: block;
        }
    </style>
</head>

<body id="bg">
<div class="page-wraper">
    <div class="page-content bg-white">
        <div class="career-section">
            <div class="container">
                <div class="career-header">
                    <h2>Cơ Hội Nghề Nghiệp</h2>
                    <p>Khám phá các vị trí tuyển dụng đang mở tại Human Tech</p>
                </div>

                <div class="row">
                    <%
                        List<RecruitmentPost> posts = (List<RecruitmentPost>) request.getAttribute("posts");
                        if (posts != null && !posts.isEmpty()) {
                            for (RecruitmentPost post : posts) {
                    %>
                        <div class="col-lg-6 col-md-12">
                            <div class="job-card">
                                <h4 class="job-title"><%= post.getTitle() %></h4>
                                <div class="job-meta">
                                    <i class="ti-briefcase"></i>
                                    <span><%= post.getDepartment() != null ? post.getDepartment().getDepName() : "Chưa xác định" %></span>
                                </div>
                                <a href="${pageContext.request.contextPath}/applyjob?postId=<%= post.getPostId() %>" 
                                   class="btn radius-xl m-t20 apply-btn">
                                   <i class="ti-arrow-right"></i> Ứng tuyển ngay
                                </a>
                            </div>
                        </div>
                    <%
                            }
                        } else {
                    %>
                        <div class="col-md-12">
                            <div class="no-jobs">
                                <i class="ti-folder"></i>
                                <p>Hiện tại chưa có vị trí tuyển dụng nào. Vui lòng quay lại sau!</p>
                            </div>
                        </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>

        <%@ include file="CommonItems/Footer/homepageFooter.jsp" %>
    </div>

    <button class="back-to-top fa fa-chevron-up"></button>
</div>

<!-- Scripts -->
<script src="${pageContext.request.contextPath}/assets1/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assets1/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets1/vendors/owl-carousel/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/assets1/js/functions.js"></script>
</body>
</html>
