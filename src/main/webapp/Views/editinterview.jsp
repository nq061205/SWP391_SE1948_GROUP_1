<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Edit Interview</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Edit Interview Schedule</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="dashboard"><i class="fa fa-home"></i> Dashboard</a></li>
                        <li><a href="viewcreatedinterviews">My Created Interviews</a></li>
                        <li>Edit Interview</li>
                    </ul>
                </div>

                <div class="widget-box">
                    <h4><i class="fa fa-pen"></i> Edit Interview Details</h4>
                    <hr>

                    <form method="post" action="${pageContext.request.contextPath}/editInterview" style="max-width:600px; margin:auto;">
                        <input type="hidden" name="interviewId" value="${interview.interviewId}" />

                        <div class="form-group mb-3">
                            <label><i class="fa fa-user"></i> Candidate:</label>
                            <input type="text" class="form-control" 
                                   value="${interview.candidate.name}" readonly>
                        </div>

                        <div class="form-group mb-3">
                            <label><i class="fa fa-briefcase"></i> Recruitment Post:</label>
                            <input type="text" class="form-control" 
                                   value="${interview.candidate.post.title}" readonly>
                        </div>

                        <div class="form-group mb-3">
                            <label><i class="fa fa-calendar"></i> Date:</label>
                            <input type="date" name="date" class="form-control"
                                   value="${interview.date.toLocalDate()}" required>
                        </div>

                        <div class="form-group mb-3">
                            <label><i class="fa fa-clock"></i> Time:</label>
                            <input type="time" name="time" class="form-control"
                                   value="${interview.time.toLocalTime()}" required>
                        </div>

                        <div class="text-end">
                            <a href="${pageContext.request.contextPath}/viewcreatedinterviews" 
                               class="btn btn-outline-secondary me-2">
                                <i class="fa fa-arrow-left"></i> Back
                            </a>
                            <button type="submit" class="btn btn-success">
                                <i class="fa fa-save"></i> Save Changes
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </main>

        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
    </body>
</html>
