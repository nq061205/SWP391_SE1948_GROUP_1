<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.RecruitmentPost" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Job Listings - HRM System</title>
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
    <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
    
    <style>
        body { 
            background: #f3f4f7; 
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
        }
        .page-header {
            background: #3d4465;
            padding: 50px 0 35px;
            margin-bottom: 35px;
            color: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .page-header h1 {
            font-size: 2.2em;
            font-weight: 600;
            margin: 0;
            text-align: center;
            color: #ffffff;
        }
        .page-header p {
            text-align: center;
            font-size: 1em;
            margin-top: 8px;
            opacity: 0.85;
            color: #e0e0e0;
        }
        .container { 
            max-width: 1100px; 
            margin: 0 auto; 
            padding: 0 20px 50px;
        }
        .job-listing { 
            background: #ffffff; 
            border: 1px solid #e5e7eb; 
            box-shadow: 0 1px 4px rgba(0,0,0,0.06); 
            padding: 28px 32px; 
            margin-bottom: 20px; 
            border-radius: 6px; 
            transition: all 0.25s ease;
            position: relative;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .job-listing:hover {
            box-shadow: 0 3px 12px rgba(0,0,0,0.1);
            border-color: #ffbc3b;
        }
        .job-info {
            flex: 1;
        }
        .job-title { 
            font-size: 1.4em; 
            color: #3d4465; 
            font-weight: 600; 
            margin-bottom: 10px; 
            letter-spacing: 0.2px;
            line-height: 1.3;
        }
        .job-meta {
            display: flex;
            align-items: center;
            gap: 20px;
            margin-top: 10px;
        }
        .job-department { 
            color: #6c757d; 
            font-size: 0.95em;
            display: flex;
            align-items: center;
        }
        .job-department i {
            margin-right: 8px;
            color: #ffbc3b;
            font-size: 1.1em;
        }
        .job-badge {
            background: #e8f5e9;
            color: #2e7d32;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: 500;
        }
        .apply-btn { 
            background: #ffbc3b;
            color: #3d4465; 
            border: none; 
            padding: 12px 32px; 
            border-radius: 4px; 
            cursor: pointer; 
            font-weight: 600;
            font-size: 0.95em;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            transition: all 0.25s ease;
            box-shadow: 0 2px 6px rgba(255, 188, 59, 0.25);
        }
        .apply-btn i {
            font-size: 1em;
        }
        .apply-btn:hover { 
            background: #ffa000;
            box-shadow: 0 4px 10px rgba(255, 188, 59, 0.35);
            text-decoration: none;
            color: #3d4465;
            transform: translateY(-1px);
        }
        .no-jobs {
            text-align: center;
            padding: 80px 20px;
            color: #6c757d;
            font-size: 1.1em;
            background: #ffffff;
            border-radius: 6px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.06);
        }
        .no-jobs i {
            font-size: 4.5em;
            margin-bottom: 25px;
            color: #d1d5db;
            display: block;
        }
        .no-jobs p {
            margin: 0;
            font-size: 1.05em;
        }
        @media (max-width: 768px) {
            .job-listing {
                flex-direction: column;
                align-items: flex-start;
            }
            .apply-btn {
                margin-top: 15px;
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <div class="page-header">
        <div class="container">
            <h1>Cơ Hội Nghề Nghiệp</h1>
            <p>Khám phá các vị trí tuyển dụng đang mở tại công ty chúng tôi</p>
        </div>
    </div>
    
    <div class="container">

        <%
            List<RecruitmentPost> posts = (List<RecruitmentPost>) request.getAttribute("posts");
            if (posts != null && !posts.isEmpty()) {
                for (RecruitmentPost post : posts) {
        %>
            <div class="job-listing">
                <div class="job-info">
                    <div class="job-title">
                        <%= post.getTitle() %>
                    </div>
                    <div class="job-meta">
                        <div class="job-department">
                            <i class="ti-briefcase"></i>
                            <%= post.getDepartment() != null ? post.getDepartment().getDepName() : "Chưa xác định" %>
                        </div>
                    </div>
                </div>
                <a class="apply-btn" href="/HRMSystem/Views/applyjob.jsp?jobId=<%= post.getPostId() %>">
                    <i class="ti-arrow-right"></i> Ứng tuyển ngay
                </a>
            </div>
        <%
                }
            } else {
        %>
            <div class="no-jobs">
                <i class="ti-folder"></i>
                <p>Hiện tại chưa có vị trí tuyển dụng nào. Vui lòng quay lại sau!</p>
            </div>
        <%
            }
        %>
    </div>
    
    <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets2/js/bootstrap.min.js"></script>
</body>
</html>
