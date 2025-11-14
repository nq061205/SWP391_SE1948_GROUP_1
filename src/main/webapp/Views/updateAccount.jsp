<%-- 
    Document   : profile
    Created on : Oct 4, 2025, 9:18:37 PM
    Author     : Lenovo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Update Account</title>

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
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- HEADER + NAVBAR -->
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Account information</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/accountlist">Account list</a></li>
                        <li>Account Update Information</li>
                    </ul>
                </div>	

                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4>Account information</h4>
                            </div>
                            <div class="widget-inner">
                                <form class="edit-profile m-b30" action="${pageContext.request.contextPath}/updateaccount" method="POST">
                                    <div class="row">
                                        <div class="col-md-3 text-center">
                                            <div class="profile-avatar mb-3">
                                                <img src="${sessionScope.emp.image}" alt="Avatar"  width="225" height="300">
                                            </div>
                                        </div>

                                        <div class="col-md-9">
                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Employee Code:</label>
                                                <div class="col-sm-9">
                                                    <input class="form-control" type="text" name="empCode" value="${sessionScope.emp.empCode}" readonly>
                                                </div>
                                            </div>

                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Full Name:</label>
                                                <div class="col-sm-9">
                                                    <input class="form-control" type="text" value="${sessionScope.emp.fullname}" readonly>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label">Email:</label>
                                                <div class="col-sm-9">
                                                    <input name="email" class="form-control" name="email" type="email" value="${sessionScope.emp.email}" required>
                                                    <c:if test="${not empty EmailErr and param.email ne sessionScope.emp.email}">
                                                        <p style="color: red">${EmailErr}</p>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Department:</label>
                                                <div class="col-sm-9">
                                                    <select id="modalDepId" name="deptId" class="form-control" onchange="updateRoleOptions(this.value)">
                                                        <c:forEach var="dept" items="${sessionScope.departments}">
                                                            <option value="${dept.depId}" 
                                                                    <c:if test="${dept.depId == sessionScope.emp.dept.depId}">selected</c:if>>
                                                                ${dept.depName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Role:</label>
                                                <div class="col-sm-9">
                                                    <select id="modalRoleId" name="roleId" class="form-control">
                                                        <c:forEach var="role" items="${sessionScope.roles}">
                                                            <option value="${role.roleId}"  
                                                                    <c:if test="${role.roleId == sessionScope.emp.role.roleId}">selected</c:if>>
                                                                ${role.roleName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group row mt-2">
                                                <label class="col-sm-3 col-form-label">Status:</label>
                                                <div class="col-sm-9">
                                                    <input class="form-control" type="text" value="${sessionScope.emp.status ? 'Active' :'Inactive'}" readonly>
                                                </div>
                                            </div>


                                            <div class="form-group row mt-4">
                                                <div class="col-sm-9 offset-sm-3">
                                                    <button type="button" class="btn btn-secondary"
                                                            onclick="window.location.href = '${pageContext.request.contextPath}/accountlist'">
                                                        ← Back
                                                    </button>
                                                    <button type="submit" name="button" value="save"class="btn btn-primary"
                                                            onclick="return confirm('Do you confirm save change?');"
                                                            >Save</button>
                                                    <p>${message}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <script>
            const managerDepIds = [
            <c:forEach var="id" items="${sessionScope.managerDepIds}" varStatus="loop">
            "${id}"<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];

            const hasAdmin = ${sessionScope.hasAdmin ? "true" : "false"};

            function updateRoleOptions(selectedDep) {
                const roleSelect = document.getElementById("modalRoleId");
                const isDeptHasManager = managerDepIds.includes(selectedDep);

                for (let opt of roleSelect.options) {
                    let roleName = opt.text.toLowerCase();
                    opt.text =
                            roleName.includes("hr manager") ? "HR Manager" :
                            roleName.includes("dept manager") ? "Dept Manager" :
                            roleName.includes("admin") ? "Admin" :
                            opt.text;

                    opt.disabled = false;
                    roleName = opt.text.toLowerCase();
                    if (roleName.toLowerCase().includes("admin") && hasAdmin) {
                        opt.disabled = true;
                        opt.text = "Admin (Already Assigned)";
                    }

                    if (roleName.includes("hr manager")) {
                        if (selectedDep !== "D002") {
                            opt.disabled = true;
                            opt.text = "HR Manager (Only for HR)";
                        }
                    } else if (roleName.includes("dept manager")) {
                        if (selectedDep === "D002") {
                            opt.disabled = true;
                            opt.text = "Dept Manager (Not available in HR)";
                        } else if (isDeptHasManager) {
                            opt.disabled = true;
                            opt.text = "Dept Manager (Already Assigned)";
                        }
                    }
                }
                if (roleSelect.options[roleSelect.selectedIndex]?.disabled) {
                    roleSelect.selectedIndex = 0;
                }
            }

            document.getElementById("modalDepId").addEventListener("change", function () {
                updateRoleOptions(this.value);
            });

            document.addEventListener("DOMContentLoaded", function () {
                const depSelect = document.getElementById("modalDepId");
                if (depSelect.value) {
                    updateRoleOptions(depSelect.value);
                }
            });
        </script>
        <!-- SCRIPT ZONE -->
        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!--                    <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap-select/bootstrap-select.min.js"></script>-->
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
    </body>
</html>