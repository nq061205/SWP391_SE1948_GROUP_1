<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
        <meta property="og:description" content="Human Tech" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="../error-404.html" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Compose Overtime Request Application</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- All PLUGINS CSS ============================================= -->
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
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb mb-3">
                        <h4 class="breadcrumb-title">Application</h4>
                        <ul class="db-breadcrumb-list">
                            <li><a href="${pageContext.request.contextPath}/application?typeapplication=ot">Overtime Request Application List</a></li>
                            <li><a href="${pageContext.request.contextPath}/compose?type=OT">Create Overtime Application</a></li>
                        </ul>
                    </div>
                <div class="row">
                    <div class="col-lg-8 offset-lg-2">
                        <div class="widget-box p-4 shadow-sm bg-white rounded">
                            <c:choose>
                                <c:when test="${not empty isEdit}">
                                    <form class="mail-compose" 
                                          method="post" 
                                          action="${pageContext.request.contextPath}/editapplication?type=OT&id=${id}">
                                    </c:when>
                                    <c:otherwise>
                                        <form class="mail-compose" 
                                              method="post" 
                                              action="${pageContext.request.contextPath}/compose">
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="hidden" name="type" value="OT"/>
                                    <div class="form-group mb-3">
                                        <label>Receiver:</label>
                                        <input type="text" name="email" class="form-control" value="${receiver.email}" readonly/>
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="type">Date:</label>
                                        <input
                                            type="date" 
                                            name="date" 
                                            value="${date}" 
                                            class="form-control"
                                            min="<%= java.time.LocalDate.now() %>"
                                            required/>
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="type">Overtime hours:</label>
                                        <input type="number" name="hours" value="${hours}" step="0.5" class="form-control" min="0.5" max="4" required/>
                                    </div>

                                    <div class="text-right">
                                        <c:if test="${not empty isEdit}">
                                            <button type="button" class="btn btn-warning btn-lg ms-2" style="background: red"
                                                    onclick="confirmDelete(${id})">
                                                Delete
                                            </button>
                                            <button type="submit" class="btn btn-primary btn-lg" onclick="return confirm('Confirm to update!!')">Update</button>
                                        </c:if> 
                                        <c:if test="${empty isEdit}">
                                            <button type="submit" class="btn btn-primary btn-lg">Send</button>
                                        </c:if> 
                                    </div>
                                </form>
                        </div>
                    </div>
                </div>
            </div>
            <form id="deleteForm" action="${pageContext.request.contextPath}/deleteapplication" method="post" style="display:none;">
                <input type="hidden" name="type" value="OT"/>
                <input type="hidden" name="id" id="deleteId"/>
            </form>
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
        <script src='${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js'></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/summernote/summernote.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/file-upload/imageuploadify.min.js"></script>
        <script src='${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js'></script>
        <script>
                                                function confirmDelete(id) {
                                                    if (confirm("Do you confirm delete this application?")) {
                                                        document.getElementById("deleteId").value = id;
                                                        document.getElementById("deleteForm").submit();
                                                    }
                                                }
        </script>
    </body>
</html>
