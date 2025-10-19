<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.RecruitmentPost" %>
<html>
<head>
    <title>Job Listings</title>
    <link rel="stylesheet" href="../assets1/css/style.css">
    <link rel="stylesheet" href="../assets2/css/style.css">
    <style>
        body { background: #f6f7fb; }
        .container { max-width: 900px; margin: 40px auto; }
        .job-listing { background: #fff; border: 1px solid #e0e0e0; box-shadow: 0 2px 8px #e0e0e0; padding: 24px; margin-bottom: 28px; border-radius: 12px; display: flex; flex-direction: column; }
        .job-title { font-size: 1.35em; color: #e65c00; font-weight: bold; margin-bottom: 6px; cursor: pointer; letter-spacing: 0.5px; }
        .job-department { color: #666; font-size: 0.98em; margin-bottom: 10px; font-style: italic; }
        .apply-btn { background: #e65c00; color: #fff; border: none; padding: 8px 22px; border-radius: 4px; cursor: pointer; font-weight: 500; align-self: flex-end; }
        .apply-btn:hover { background: #c94d00; }
        /* Popup */
        #jobDetailModal { display:none; position:fixed; z-index:9999; left:0; top:0; width:100vw; height:100vh; background:rgba(0,0,0,0.35); }
        #jobDetailModal .modal-content { background:#fff; max-width:520px; margin:60px auto; padding:32px 28px; position:relative; border-radius:12px; box-shadow:0 2px 16px #bbb; }
        #jobDetailModal .close { position:absolute; top:12px; right:18px; font-size:1.5em; cursor:pointer; color:#e65c00; }
        #modalTitle { color:#e65c00; font-weight:bold; margin-bottom:12px; font-size:1.2em; }
        #modalContent { margin-bottom:16px; color:#333; font-size:1em; }
    </style>
</head>
<body>
<div class="container">
    <h2 style="margin-bottom:32px;">Danh sách bài tuyển dụng</h2>

    
    <%
        List<RecruitmentPost> posts = (List<RecruitmentPost>) request.getAttribute("posts");
        if (posts != null && !posts.isEmpty()) {
            for (RecruitmentPost post : posts) {
    %>
        <div class="job-listing">
            <div class="job-title">
                <%= post.getTitle() %>
            </div>
            <div class="job-department">
                <%= post.getDepartment() != null ? post.getDepartment().getDepName() : "" %>
            </div>
            <a class="apply-btn" href="${pageContext.request.contextPath}/applyjob?postId=<%= post.getPostId() %>">Ứng tuyển ngay</a>
        </div>
    <%
            }
        } else {
    %>
        <div>Không có bài tuyển dụng nào.</div>
    <%
        }
    %>
</div>
    
</body>
</html>
