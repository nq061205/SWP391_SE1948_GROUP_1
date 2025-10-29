<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Schedule Interview</title>

        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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

                <div class="row">
                    <div class="col-lg-8 offset-lg-2">
                        <div class="widget-box p-4 shadow-sm bg-white rounded">
                            <c:choose>
                                <c:when test="${not empty isEdit}">
                                    <form class="interview-form" 
                                          method="post" 
                                          action="${pageContext.request.contextPath}/editSchedule?id=${id}">
                                    </c:when>
                                    <c:otherwise>
                                        <form class="interview-form" 
                                              method="post" 
                                              action="${pageContext.request.contextPath}/scheduleInterview">
                                        </c:otherwise>
                                    </c:choose>

                                    <!-- Interview Date -->
                                    <div class="form-group mb-3">
                                        <label for="date"><i class="fa fa-calendar"></i> Date:</label>
                                        <input type="date" id="date" name="date" class="form-control"
                                               value="${interview.date}"
                                               min="<%= java.time.LocalDate.now() %>"
                                               required>
                                    </div>

                                    <!-- Interview Time -->
                                    <div class="form-group mb-3">
                                        <label for="time"><i class="fa fa-clock"></i> Time:</label>
                                        <input type="time" id="time" name="time" class="form-control"
                                               value="${interview.time}"
                                               required>
                                    </div>

                                    <!-- Recruitment Post -->
                                    <div class="form-group mb-3">
                                        <label for="post"><i class="fa fa-briefcase"></i> Recruitment Post:</label>
                                        <select id="post" name="postId" class="form-control" required>
                                            <option value="" selected>Select recruitment post</option>
                                            <c:forEach var="p" items="${postList}">
                                                <option value="${p.postId}"
                                                        <c:if test="${interview.postId eq p.postId}">selected</c:if>>
                                                    ${p.title}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Candidate -->
                                    <!-- ✅ Candidate Multi-Select Dropdown -->
                                    <div class="form-group mb-3">
                                        <label for="candidate"><i class="fa fa-user"></i> Candidates:</label>

                                        <div class="dropdown">
                                            <button class="btn btn-outline-primary dropdown-toggle w-100 text-start" type="button" id="dropdownCandidates"
                                                    data-bs-toggle="dropdown" aria-expanded="false">
                                                Select candidates
                                            </button>

                                            <ul class="dropdown-menu w-100 p-2" aria-labelledby="dropdownCandidates" style="max-height: 250px; overflow-y: auto;">
                                                <c:forEach var="candi" items="${candidateList}">
                                                    <li>
                                                        <div class="form-check">
                                                            <input class="form-check-input candidate-checkbox" type="checkbox"
                                                                   name="candidateIds" value="${candi.candidateId}" id="c${candi.candidateId}">
                                                            <label class="form-check-label" for="c${candi.candidateId}">
                                                                ${candi.name} (${candi.email})
                                                            </label>
                                                        </div>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>

                                        <small id="selectedCandidates" class="text-muted d-block mt-1">No candidates selected</small>
                                    </div>


                                    <!-- Notes -->
                                    <div class="form-group mb-3">
                                        <label for="note"><i class="fa fa-pencil-alt"></i> Notes:</label>
                                        <textarea id="note" name="note" class="form-control" rows="5"
                                                  placeholder="Add any interview details...">${interview.note}</textarea>
                                    </div>

                                    <!-- Submit Buttons -->
                                    <div class="text-right">
                                        <c:if test="${not empty isEdit}">
                                            <button type="button" class="btn btn-danger btn-lg me-2"
                                                    onclick="confirmDelete(${id})">
                                                <i class="fa fa-trash"></i> Delete
                                            </button>
                                            <button type="submit" class="btn btn-primary btn-lg"
                                                    onclick="return confirm('Confirm to update this schedule?')">
                                                <i class="fa fa-save"></i> Update
                                            </button>
                                        </c:if>

                                        <c:if test="${empty isEdit}">
                                            <button type="submit" class="btn btn-success btn-lg">
                                                <i class="fa fa-calendar-check"></i> Create Schedule
                                            </button>
                                        </c:if>
                                    </div>
                                </form>

                                <form id="deleteForm" action="${pageContext.request.contextPath}/deleteSchedule" method="post" style="display:none;">
                                    <input type="hidden" name="id" id="deleteId"/>
                                </form>

                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- JS -->
        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>

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
                                                                    $('#dropdownCandidates').text('Select candidates');
                                                                } else {
                                                                    $('#selectedCandidates').text('Selected: ' + selected.join(', '));
                                                                    $('#dropdownCandidates').text(selected.length + ' candidate(s) selected');
                                                                }
                                                            });
                                                        });
        </script>
    </body>
</html>
