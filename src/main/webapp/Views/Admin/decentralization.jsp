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
        <title>My Profile</title>

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
            .page-col {
                text-align: left !important;
                padding-left: 20px !important;
                font-weight: 600;
                vertical-align: middle;
            }
            .text-center input[type="checkbox"] {
                transform: scale(1.3);
                margin: 0;
            }
            thead.table-primary th:first-child {
                text-align: left !important;
                padding-left: 20px !important;
            }
            .bulk-action-btn {
                margin-right: 10px;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="../CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="../CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Role Permission Management</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="dashboard"><i class="fa fa-home"></i> Dashboard</a></li>
                        <li>Decentralization</li>
                    </ul>
                </div>

                <div >
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4><i class="fa fa-key"></i> Dynamic Role Permissions</h4>
                    </div>

                    <form action="${pageContext.request.contextPath}/decentralization" method="post">



                        <div class="table-responsive">
                            <table class="table table-bordered table-hover text-center align-middle">
                                <thead class="table-primary">
                                    <tr>
                                        <th>Page / Module</th>
                                            <c:forEach items="${roles}" var="r">
                                            <th>${r.roleName}</th>
                                            </c:forEach>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${permissions}" var="p">
                                        <tr>
                                            <td class="page-col">${p.permissionName}</td>
                                            <c:forEach items="${roles}" var="r">
                                                <td>
                                                    <c:set var="pair" value="${p.permissionId}-${r.roleId}" />
                                                    <c:set var="isChecked" value="false" />
                                                    <c:forEach items="${rolepermission}" var="rp">
                                                        <c:if test="${rp eq pair}">
                                                            <c:set var="isChecked" value="true" />
                                                        </c:if>
                                                    </c:forEach>
                                                    <input type="checkbox" name="rolepermission"
                                                           value="${pair}"
                                                           <c:if test="${isChecked}">checked</c:if> />
                                                    </td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="mb-3 d-flex justify-content-end">
                            <button type="button" class="btn btn-primary btn-sm bulk-action-btn me-2" id="btnSelectAll">
                                <i class="fa fa-check-square"></i> Select All
                            </button>
                            <button type="button" class="btn btn-warning btn-sm bulk-action-btn me-2" id="btnClearAll">
                                <i class="fa fa-square"></i> Clear All
                            </button>
                            <button type="submit" class="btn btn-success btn-sm bulk-action-btn">
                                <i class="fa fa-save"></i> Save Permissions
                            </button>
                        </div>

                    </form>
                </div>
            </div>
        </main>

        <!-- ==============================================================
             JAVASCRIPT FULL VERSION
        ============================================================== -->
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
            function selectAllCheckboxes(selectAll) {
                const checkboxes = document.querySelectorAll('input[name="rolepermission"]');
                checkboxes.forEach(cb => cb.checked = selectAll);
            }

            document.addEventListener('DOMContentLoaded', function () {
                const btnSelectAll = document.getElementById('btnSelectAll');
                const btnClearAll = document.getElementById('btnClearAll');

                if (btnSelectAll) {
                    btnSelectAll.addEventListener('click', function () {
                        selectAllCheckboxes(true);
                    });
                }

                if (btnClearAll) {
                    btnClearAll.addEventListener('click', function () {
                        selectAllCheckboxes(false);
                    });
                }
            });
        </script>
    </body>
</html>
