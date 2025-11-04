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
        <style>
            /* ----- Select cao cấp custom ----- */
            .custom-select {
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                background-color: #fff;
                border: 1px solid #ced4da;
                border-radius: 8px;
                padding: 10px 40px 10px 12px;
                font-size: 15px;
                line-height: 1.5;
                cursor: pointer;
                transition: all 0.2s ease-in-out;
                background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3E%3Cpath fill='none' stroke='%23666' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M2 5l6 6 6-6'/%3E%3C/svg%3E");
                background-repeat: no-repeat;
                background-position: right 12px center;
                background-size: 14px;
            }

            .custom-select:hover {
                border-color: #86b7fe;
                box-shadow: 0 0 0 3px rgba(13,110,253,.1);
            }

            .custom-select:focus {
                border-color: #0d6efd;
                box-shadow: 0 0 0 3px rgba(13,110,253,.25);
                outline: none;
            }

            .custom-select option {
                padding: 8px;
            }

            /* Giống hiệu ứng “bootstrap-select” */
            .custom-select::-ms-expand {
                display: none; /* Ẩn mũi tên mặc định trên IE/Edge cũ */
            }
        </style>
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
                        <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                        <li>Passed Candidate Profile List</li>
                    </ul>
                </div>


                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4>Passed Candidate Profile List</h4>
                            </div>
                            <div class="widget-inner">
                                <form class="edit-profile m-b30" action="${pageContext.request.contextPath}/createaccount" method="POST">
                                    <table class="table table-striped table-bordered table-hover align-middle text-center" 
                                           style="table-layout: fixed; width: 100%; border-collapse: collapse;">
                                        <thead class="thead-dark">
                                            <tr>
                                                <th>No</th>
                                                <th>Candidate Name</th>
                                                <th>Email</th>
                                                <th>Phone</th>
                                                <th>Apply Date</th>
                                                <th>Interviewd by</th>
                                                <th>Interview Date</th>
                                                <th>Interview Time</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${passedList}" var="pl" varStatus="loop">
                                                <tr>
                                                    <td>${loop.index+1}</td>
                                                    <td>${pl.candidate.name}</td>
                                                    <td>${pl.candidate.email}</td>
                                                    <td>${pl.candidate.phone}</td>
                                                    <td>${pl.candidate.appliedAt}</td>
                                                    <td>${pl.interviewedBy.fullname}</td>
                                                    <td>${pl.date}</td>
                                                    <td>${pl.time}</td>
                                                    <td>
                                                        <button type="button"
                                                                style="padding: 0.5rem 0"
                                                                class="btn btn-primary btn-sm open-modal-btn w-100"
                                                                data-bs-toggle="modal"
                                                                data-bs-target="#addModal"
                                                                data-name="${pl.candidate.name}"
                                                                data-email="${pl.candidate.email}"
                                                                data-phone="${pl.candidate.phone}">
                                                            Create Profile
                                                        </button>
                                                    </td>
                                                </tr>

                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </form>
                            </div> 
                        </div>
                    </div>
                    <div class="modal fade" id="addModal">
                        <div class="modal-dialog">
                            <div class="modal-content">

                                <form action="${pageContext.request.contextPath}/createaccount" method="post">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Add New Account</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal">X</button>
                                    </div>

                                    <div class="modal-body">
                                        <label class="form-label">Candidate Name</label>
                                        <input type="text" name="canName" class="form-control" id="modalName" required>

                                        <label class="form-label mt-2">Email:</label>
                                        <input type="email" name="email" class="form-control"  id="modalEmail" required>

                                        <label class="form-label mt-2">Phone:</label>
                                        <input type="text" name="phone" class="form-control" id="modalPhone" required>

                                        <label class="form-label mt-2">Department:</label>
                                        <br>
                                        <select name="deptId"
                                                class="custom-select"
                                                style="min-width:150px;">
                                            <c:forEach var="dep" items="${sessionScope.deptList}">
                                                <option value="${dep.depId}"> 
                                                    ${dep.depName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <br>
                                        <label class="form-label mt-2">Role:</label>
                                        <br>
                                        <select name="roleId" class="custom-select"
                                                style="min-width:150px;">
                                            <c:forEach var="rl" items="${sessionScope.roleList}">
                                                <option value="${rl.roleId}" 
                                                        <c:if test="${rl.roleId == roleId}">selected</c:if> >
                                                    ${rl.roleName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" name="action" value="add" class="btn btn-success">Add</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    </main>
                    <script>
                        document.addEventListener("DOMContentLoaded", function () {
                            const openBtns = document.querySelectorAll('.open-modal-btn');

                            openBtns.forEach(btn => {
                                btn.addEventListener('click', function () {
                                    const name = this.getAttribute('data-name');
                                    const email = this.getAttribute('data-email');
                                    const phone = this.getAttribute('data-phone');

                                    document.getElementById('modalName').value = name || '';
                                    document.getElementById('modalEmail').value = email || '';
                                    document.getElementById('modalPhone').value = phone || '';
                                });
                            });
                        });

                    </script>
                    <script>
                        $.fn.selectpicker = function () {
                            return this;
                        };
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