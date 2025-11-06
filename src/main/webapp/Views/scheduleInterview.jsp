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
        <title>Schedule Interview</title>

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
            .dropdown-menu {
                z-index: 1050 !important;
            }

            select.form-control {
                z-index: 1;
                position: relative;
            }

            .widget-box {
                background: #fff;
                border-radius: 10px;
                padding: 25px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }
        </style>
    </head>
    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Schedule Interview</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="#"><i class="fa fa-home"></i>Interview</a></li>
                        <li>Schedule</li>
                    </ul>
                </div>
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="mb-0"><i class="fa fa-calendar-alt"></i> Schedule New Interview</h4>
                    <a href="${pageContext.request.contextPath}/viewcreatedinterview" 
                       class="btn btn-outline-primary btn-sm">
                        <i class="fa fa-history"></i> View Created History
                    </a>
                </div>

                <div class="row">
                    <div class="col-lg-8 offset-lg-2">
                        <div class="widget-box">
                            <form method="post"
                                  action="scheduleinterview">
                                <div class="form-group mb-3">
                                    <label for="post"><i class="fa fa-briefcase"></i> Recruitment Post:</label>
                                    <select id="post" name="postId" class="form-control" onchange="this.form.submit()" required>
                                        <option value="all"
                                                ${selectedPostId != null ? 'disabled' : ''}
                                                ${selectedPostId == null ? 'selected' : ''}>
                                            Select recruitment post
                                        </option>

                                        <c:forEach var="p" items="${postList}">
                                            <option value="${p.postId}"
                                                    ${p.postId == selectedPostId ? 'selected' : ''}>
                                                ${p.title}
                                            </option>
                                        </c:forEach>
                                    </select>

                                </div>

                                <!-- Candidate Multi-Select -->
                                <div class="form-group mb-3">
                                    <label><i class="fa fa-user"></i> Candidates:</label>
                                    <div class="border rounded p-2" style="max-height: 220px; overflow-y: auto;">
                                        <c:forEach items="${candidateList}" var="c">
                                            <div class="form-check">
                                                <input type="checkbox" class="form-check-input candidate-checkbox"
                                                       id="${c.candidateId}" name="candidateIds" value="${c.candidateId}">
                                                <label for="${c.candidateId}" class="form-check-label">
                                                    ${c.name} (${c.email})
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <small id="selectedCandidates" class="text-muted mt-1 d-block">
                                        No candidates selected
                                    </small>
                                </div>  
                                <div class="form-group mb-3">
                                    <label><i class="fa fa-user"></i> Interview Employee</label>
                                    <div class="border rounded p-2" style="max-height: 220px; overflow-y: auto;">
                                        <select name="interviewer" class="form-select" required>
                                            <option value="">-- Select Interviewer --</option>
                                            <c:forEach items="${employeeInterview}" var="c">
                                                <option value="${c.empId}">${c.fullname}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                </div>


                                <!-- Date -->
                                <div class="form-group mb-3">
                                    <label for="date"><i class="fa fa-calendar"></i> Date:</label>
                                    <input type="date" id="date" name="date" class="form-control"
                                           value="${interview.date}"
                                           min="<%= java.time.LocalDate.now() %>" required>
                                </div>

                                <!-- Time -->
                                <div class="form-group mb-3">
                                    <label for="time"><i class="fa fa-clock"></i> Time:</label>
                                    <input type="time"
                                           id="time"
                                           name="time"
                                           class="form-control"
                                           value="${interview.time}"
                                           min="07:30"
                                           max="17:30"
                                           required>
                                </div>

                                <c:if test="${errorMessage != null}"><p style="color: red">${errorMessage}</p></c:if>
                                <c:if test="${successMessage != null}"><p style="color: green">${successMessage}</p></c:if>




                                    <!-- Buttons -->
                                    <div class="text-end">
                                    <c:if test="${not empty isEdit}">
                                        <button type="button" class="btn btn-danger btn-lg me-2"
                                                onclick="confirmDelete(${id})">
                                            <i class="fa fa-trash"></i> Delete
                                        </button>
                                        <button type="submit" class="btn btn-primary btn-lg"
                                                onclick="return confirm('Confirm update?')">
                                            <i class="fa fa-save"></i> Update
                                        </button>
                                    </c:if>

                                    <c:if test="${empty isEdit}">
                                        <button value="submit" name="action" type="submit" class="btn btn-success btn-lg">
                                            <i class="fa fa-calendar-check"></i> Create Schedule
                                        </button>
                                    </c:if>
                                </div>
                            </form>

                            <!-- Delete Form (hidden) -->
                            <form id="deleteForm" action="${pageContext.request.contextPath}/deleteSchedule"
                                  method="post" style="display:none;">
                                <input type="hidden" name="id" id="deleteId"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- JS -->
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
                                                    function confirmDelete(id) {
                                                        if (confirm("Do you want to delete this interview schedule?")) {
                                                            document.getElementById("deleteId").value = id;
                                                            document.getElementById("deleteForm").submit();
                                                        }
                                                    }

                                                    $(document).ready(function () {
                                                        $('.candidate-checkbox').on('change', function () {
                                                            const selected = [];
                                                            $('.candidate-checkbox:checked').each(function () {
                                                                selected.push($(this).next('label').text().trim());
                                                            });

                                                            if (selected.length === 0) {
                                                                $('#selectedCandidates').text('No candidates selected');
                                                            } else {
                                                                $('#selectedCandidates').text('Selected: ' + selected.join(', '));
                                                            }
                                                        });
                                                    });
        </script>
    </body>
</html>
