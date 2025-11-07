<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
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
        <meta property="og:title" content="Human Tech" />
        <meta property="og:description" content="Profile" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>My Created Interview</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/assets2/js/html5shiv.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/respond.min.js"></script>
        <![endif]-->

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">

        <style>
            .action-btn {
                padding: 3px 10px;
                font-size: 13px;
                border-radius: 6px;
            }
            .edit-form {
                display: none;
                background: #f9f9f9;
                padding: 10px;
                border-radius: 8px;
                margin-top: 5px;
            }
            .edit-form input {
                margin-right: 10px;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">My Created Interviews</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="dashboard"><i class="fa fa-home"></i> Dashboard</a></li>
                        <li>Interview History</li>
                    </ul>
                </div>

                <div class="widget-box">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4><i class="fa fa-list"></i> Created Interview Schedules</h4>
                        <a href="${pageContext.request.contextPath}/scheduleinterview" class="btn btn-secondary btn-sm">
                            <i class="fa fa-arrow-left"></i> Back to Scheduling
                        </a>
                    </div>

                    <c:if test="${empty interviewList}">
                        <p class="text-muted"><i class="fa fa-info-circle"></i> You haven't created any interviews yet.</p>
                    </c:if>

                    <c:if test="${not empty interviewList}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover text-center align-middle">
                                <thead class="table-primary">
                                    <tr>
                                        <th>#</th>
                                        <th>Candidate</th>
                                        <th>Recruitment Post</th>
                                        <th>Date</th>
                                        <th>Time</th>
                                        <th>Interviewer</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="iv" items="${interviewList}" varStatus="st">
                                        <tr>
                                            <td>${st.index + 1}</td>
                                            <td>
                                                <strong>${iv.candidate.name}</strong><br>
                                                <small class="text-muted">${iv.candidate.email}</small>
                                            </td>
                                            <td>${iv.candidate.post.title}</td>
                                            <td><fmt:formatDate value="${iv.date}" pattern="MMM dd, yyyy"/></td>
                                            <td><fmt:formatDate value="${iv.time}" pattern="HH:mm"/></td>
                                            <td>${iv.interviewedBy.fullname}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${editableMap[iv.interviewId]}">
                                                        <a href="editinterview?id=${iv.interviewId}" 
                                                           class="btn btn-primary btn-sm action-btn">
                                                            <i class="fa fa-edit"></i> Edit
                                                        </a>

                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn btn-secondary btn-sm action-btn" disabled>
                                                            <i class="fa fa-ban"></i> Locked
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>

                                        <!-- Form ch?nh s?a ?n -->
                                        <tr id="editForm-${iv.interviewId}" class="edit-form" style="display:none;">
                                            <td colspan="7">
                                                <form action="${pageContext.request.contextPath}/editInterview" method="post"
                                                      class="d-flex align-items-center justify-content-center">
                                                    <input type="hidden" name="interviewId" value="${iv.interviewId}">
                                                    <label class="me-2">Date:</label>
                                                    <input type="date" name="date" class="form-control w-auto me-3"
                                                           value="<fmt:formatDate value='${iv.date}' pattern='yyyy-MM-dd'/>" required>
                                                    <label class="me-2">Time:</label>
                                                    <input type="time" name="time" class="form-control w-auto me-3"
                                                           value="<fmt:formatDate value='${iv.time}' pattern='HH:mm'/>" required>
                                                    <button type="submit" class="btn btn-success btn-sm me-2">
                                                        <i class="fa fa-save"></i> Save
                                                    </button>
                                                    <button type="button" class="btn btn-outline-secondary btn-sm"
                                                            onclick="toggleEdit('${iv.interviewId}')">
                                                        Cancel
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </main>

        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/counter/waypoints-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/counter/counterup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/masonry/masonry.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/masonry/filter.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js"></script>

        <script>
                                                                function toggleEdit(id) {
                                                                    const form = document.getElementById(`editForm-${id}`);
                                                                    if (form) {
                                                                        form.style.display = (form.style.display === 'none' || form.style.display === '')
                                                                                ? 'table-row'
                                                                                : 'none';
                                                                    } else {
                                                                        console.error("Form with id editForm-" + id + " not found!");
                                                                    }
                                                                }
        </script>
    </body>
</html>
