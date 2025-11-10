<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />

        <meta name="description" content="Human Tech" />

        <meta property="og:title" content="Human Tech" />
        <meta property="og:description" content="Profile" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <title>My Created Interview</title>

        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type_ ="text/css" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" type_ ="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type_ ="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type_ ="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type_ ="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type_ ="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        
        <%-- 
            Chúng ta s? s? d?ng font-awesome (gi?ng candidatelist) 
            thay vì ch? d?a vào font c?a theme, ?? ??m b?o icon chevron hi?n th?
        --%>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

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
            
            /* --- CSS CHO S?P X?P --- */
            thead.table-primary th a {
                color: inherit;
                text-decoration: none;
                display: block;
            }
            thead.table-primary th a:hover {
                color: #000;
            }
            thead.table-primary th .fa {
                margin-left: 5px;
                opacity: 0.6;
            }
            thead.table-primary th a .fa.active-sort {
                opacity: 1;
            }
            
            /* --- CSS CHO THANH TÌM KI?M --- */
            .search-form {
                max-width: 400px;
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

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4><i class="fa fa-list"></i> Created Interview Schedules</h4>
                    <a href="${pageContext.request.contextPath}/scheduleinterview" class="btn btn-secondary btn-sm">
                        <i class="fa fa-arrow-left"></i> Back to Scheduling
                    </a>
                </div>

                <div class="mb-3">
                    <form action="viewcreatedinterview" method="get" class="d-flex search-form">
                        <input type="hidden" name="sortField" value="${sortField}">
                        <input type="hidden" name="sortOrder" value="${sortOrder}">
                        
                        <input type="text" name="search" class="form-control me-2" 
                               placeholder="Search by candidate name..." value="${search}">
                        <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
                    </form>
                </div>

                <c:if test="${totalRecordsUnfiltered == 0}">
                    <p class="text-muted"><i class="fa fa-info-circle"></i> You haven't created any interviews yet.</p>
                </c:if>

                <c:if test="${totalRecordsUnfiltered > 0 && empty interviewList}">
                    <p class="text-muted"><i class="fa fa-info-circle"></i> No interviews found matching your search criteria.</p>
                </c:if>
                
                <c:if test="${not empty interviewList}">
                    
                    <c:set var="nextOrder" value="${(sortOrder == 'asc') ? 'desc' : 'asc'}" />
                    
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover text-center align-middle">
                            <thead class="table-primary">
                                <tr>
                                    <th>#</th>
                                    
                                    <th>
                                        <c:url var="sortLinkName" value="viewcreatedinterview">
                                            <c:param name="sortField" value="name" />
                                            <c:param name="sortOrder" value="${(sortField == 'name') ? nextOrder : 'asc'}" />
                                            <c:param name="search" value="${search}" />
                                        </c:url>
                                        <a href="${sortLinkName}">
                                            Candidate
                                            <c:choose>
                                                <c:when test="${sortField == 'name' && sortOrder == 'asc'}"><i class="fa fa-sort-asc active-sort"></i></c:when>
                                                <c:when test="${sortField == 'name' && sortOrder == 'desc'}"><i class="fa fa-sort-desc active-sort"></i></c:when>
                                                <c:otherwise><i class="fa fa-sort"></i></c:otherwise>
                                            </c:choose>
                                        </a>
                                    </th>
                                    
                                    <th>Recruitment Post</th>
                                    
                                    <th>
                                        <c:url var="sortLinkDate" value="viewcreatedinterview">
                                            <c:param name="sortField" value="date" />
                                            <c:param name="sortOrder" value="${(sortField == 'date') ? nextOrder : 'asc'}" />
                                            <c:param name="search" value="${search}" />
                                        </c:url>
                                        <a href="${sortLinkDate}">
                                            Date
                                            <c:choose>
                                                <c:when test="${sortField == 'date' && sortOrder == 'asc'}"><i class="fa fa-sort-asc active-sort"></i></c:when>
                                                <c:when test="${sortField == 'date' && sortOrder == 'desc'}"><i class="fa fa-sort-desc active-sort"></i></c:when>
                                                <c:otherwise><i class="fa fa-sort"></i></c:otherwise>
                                            </c:choose>
                                        </a>
                                    </th>
                                    
                                    <th>Time</th>
                                    <th>Interviewer</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="iv" items="${interviewList}" varStatus="st">
                                    <tr>
                                        <td>${(currentPage - 1) * 5 + st.index + 1}</td>
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
                    
                    <c:if test="${totalPages > 0}"> 
                        <div class="d-flex justify-content-between align-items-center mt-4">
                            
                            <div class="text-muted">
                                Page <strong>${currentPage}</strong> of <strong>${totalPages}</strong>
                            </div>

                            <nav aria-label="Page navigation">
                                <ul class="pagination mb-0">
                                    
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <c:url var="prevLink" value="viewcreatedinterview">
                                            <c:param name="page" value="${currentPage - 1}" />
                                            <c:param name="sortField" value="${sortField}" />
                                            <c:param name="sortOrder" value="${sortOrder}" />
                                            <c:param name="search" value="${search}" />
                                        </c:url>
                                        <a class="page-link" href="${prevLink}">
                                            <i class="fa fa-chevron-left"></i> Previous
                                        </a>
                                    </li>

                                    <li class="page-item active">
                                        <a class="page-link">${currentPage}</a>
                                    </li>

                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <c:url var="nextLink" value="viewcreatedinterview">
                                            <c:param name="page" value="${currentPage + 1}" />
                                            <c:param name="sortField" value="${sortField}" />
                                            <c:param name="sortOrder" value="${sortOrder}" />
                                            <c:param name="search" value="${search}" />
                                        </c:url>
                                        <a class="page-link" href="${nextLink}">
                                            Next <i class="fa fa-chevron-right"></i>
                                        </a>
                                    </li>
                                    
                                </ul>
                            </nav>
                        </div>
                    </c:if>
                </c:if>
                
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