<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Candidate Management</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Base CSS -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">

        <style>
            .nav-tabs .nav-link.active {
                background-color: #007bff !important;
                color: #fff !important;
                border-color: #007bff #007bff #fff;
            }
            .nav-tabs .nav-link:hover {
                background-color: #e9f2ff;
            }
            .widget-box {
                background: #fff;
                border-radius: 10px;
                padding: 30px 40px !important;
                margin-bottom: 40px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }
            form .form-group {
                margin-bottom: 20px;
            }
            label {
                font-weight: 600;
                margin-bottom: 6px;
                display: block;
            }
            .form-control, .form-select {
                border-radius: 8px;
                padding: 10px;
            }
            .text-end {
                margin-top: 25px;
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
                        <li><i class="fa fa-home"></i> Interview</li>
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

                            <form method="post" action="scheduleinterview">

                                <!-- Recruitment Post -->
                                <div class="form-group mb-3">
                                    <label><i class="fa fa-briefcase"></i> Recruitment Post:</label>

                                    <select name="postId" class="form-control" onchange="this.form.submit()">
                                        <option value="all"
                                                ${selectedPostId == null ? 'selected' : ''}>Select recruitment post</option>

                                        <c:forEach var="p" items="${postList}">
                                            <option value="${p.postId}"
                                                    ${p.postId == selectedPostId ? 'selected' : ''}>
                                                ${p.title}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Candidates -->
                                <div class="form-group mb-3">
                                    <label><i class="fa fa-user"></i> Candidates:</label>

                                    <c:choose>
                                        <c:when test="${not empty candidatesList}">

                                            <div class="border rounded p-3 bg-light" style="max-height:230px; overflow-y:auto;">
                                                <c:forEach var="c" items="${candidatesList}">
                                                    <div class="form-check mb-1">

                                                        <input type="checkbox" class="form-check-input candidate-checkbox"
                                                               id="${c.candidateId}"
                                                               name="candidateIds"
                                                               value="${c.candidateId}"
                                                               <c:if test="${selectedCandidatesData != null 
                                                                             and fn:contains(selectedCandidatesData, c.candidateId)}">checked</c:if>
                                                                     >

                                                               <label for="${c.candidateId}" class="form-check-label">
                                                            ${c.name} <small class="text-muted">(${c.email})</small>
                                                        </label>

                                                    </div>
                                                </c:forEach>
                                            </div>

                                            <small id="selectedCandidates" class="text-muted mt-2 d-block">
                                                No candidates selected
                                            </small>

                                        </c:when>

                                        <c:otherwise>
                                            <div class="text-center p-4 border rounded bg-light-subtle">
                                                <i class="fa fa-user-slash fa-2x text-secondary mb-2"></i>
                                                <h6 class="fw-semibold text-secondary mb-1">No candidates available</h6>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- Interviewer -->
                                <div class="form-group mb-3">
                                    <label><i class="fa fa-user-tie"></i> Interview Employee</label>

                                    <select name="interviewer" class="form-control" required>
                                        <option value="">-- Select Interviewer --</option>

                                        <c:forEach var="e" items="${employeeInterview}">
                                            <option value="${e.empId}"
                                                    ${e.empId == selectedInterviewer ? 'selected' : ''}>
                                                ${e.fullname}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Date -->
                                <div class="form-group mb-3">
                                    <label><i class="fa fa-calendar"></i> Date:</label>

                                    <input type="date" name="date" class="form-control"
                                           value="${selectedDate}"
                                           min="<%= java.time.LocalDate.now() %>" required>
                                </div>

                                <!-- Time -->
                                <div class="form-group mb-3">
                                    <label><i class="fa fa-clock"></i> Time:</label>

                                    <input type="time" name="time" class="form-control"
                                           value="${selectedTime}"
                                           min="07:30" max="17:30" required>
                                </div>

                                <!-- Messages -->
                                <c:if test="${errorMessage != null}">
                                    <p style="color:red">${errorMessage}</p>
                                </c:if>

                                <c:if test="${successMessage != null}">
                                    <p style="color:green">${successMessage}</p>
                                </c:if>

                                <!-- Buttons -->
                                <div class="text-end">
                                    <button type="submit" name="action" value="submit"
                                            class="btn btn-success btn-lg">
                                        <i class="fa fa-calendar-check"></i> Create Schedule
                                    </button>
                                </div>

                            </form>

                        </div>

                    </div>
                </div>

            </div>
        </main>

        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>

        <script>
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
