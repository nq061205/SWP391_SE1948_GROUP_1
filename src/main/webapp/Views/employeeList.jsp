<%-- 
    Document   : listapplication
    Created on : Oct 5, 2025, 10:46:57 PM
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
        <meta name="description" content="EduChamp : Education HTML Template" />

        <!-- OG -->
        <meta property="og:title" content="EduChamp : Education HTML Template" />
        <meta property="og:description" content="Profile" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>EduChamp : Education HTML Template</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- HEADER + NAVBAR -->
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/adminNavbar.jsp" %>
        <input type="hidden" name="typeApplication" value="leaverequest" />
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Employee Listing</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/employeelist"><i class="fa fa-home"></i> Employee List</a></li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-lg-12">
                        <div class="mail-box-list" style="overflow-x: scroll;">
                            <table class="table table-bordered table-hover">
                                <thead style="background-color: #f5f5f5;">
                                    <tr>
                                        <th>ID</th>
                                        <th>Employee Code</th>
                                        <th>Full Name</th>
                                        <th>Email</th>
                                        <th>Password</th>
                                        <th>Gender</th>
                                        <th>Date of Birth</th>
                                        <th>Phone</th>
                                        <th>Position</th>
                                        <th>Image</th>
                                        <th>Dependants</th>
                                        <th>Department</th>
                                        <th>Role</th>
                                        <th>Status</th>
                                        <th>Edit</th>
                                        <th>Delete</th>
                                    </tr>                                 
                                </thead>
                                <tbody>
                                    <c:forEach var="el" items="${sessionScope.empList}">
                                        <tr>
                                            <c:choose>
                                                <c:when test="${editEmp != null && editEmp.empCode eq el.empCode}">
                                            <form action="${pageContext.request.contextPath}/employeelist" method="post">
                                                <td>${el.empId}</td>
                                                <td><input type="hidden" name="empCode" value="${el.empCode}" />${el.empCode}</td>
                                                <td><input type="text" name="fullname" value="${el.fullname}" /></td>
                                                <td><input type="text" name="email" value="${el.email}" /></td>
                                                <td><input type="text" name="password" value="${el.password}" /></td>
                                                <td>
                                                    <select name="gender">
                                                        <option value="true" ${el.gender ? 'selected' : ''}>Male</option>
                                                        <option value="false" ${!el.gender ? 'selected' : ''}>Female</option>
                                                    </select>
                                                </td>
                                                <td><input type="date" name="dob" value="${el.dob}"></td>
                                                <td><input type="text" name="phone" value="${el.phone}" /></td>
                                                <td><input type="text" name="positionTitle" value="${el.positionTitle}" /></td>
                                                <td>${el.image}</td>
                                                <td><input type="number" name="dependantCount" value="${el.dependantCount}" /></td>
                                                <td>${el.dept.depName}</td>
                                                <td>${el.role.roleName}</td>
                                                <td>
                                                    <select name="status">
                                                        <option value="true" ${el.status ? 'selected' : ''}>Active</option>
                                                        <option value="false" ${!el.status ? 'selected' : ''}>Inactive</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <button type="submit" name="action" value="save" class="btn btn-success btn-sm">Save</button>
                                                    <a href="${pageContext.request.contextPath}/employeelist" class="btn btn-secondary btn-sm">Cancel</a>
                                                </td>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${el.empId}</td>
                                            <td>${el.empCode}</td>
                                            <td>${el.fullname}</td>
                                            <td>${el.email}</td>
                                            <td>${el.password}</td>
                                            <td>${el.gender ? 'Male' : 'Female'}</td>
                                            <td>${el.dob}</td>
                                            <td>${el.phone}</td>
                                            <td>${el.positionTitle}</td>
                                            <td>${el.image}</td>
                                            <td>${el.dependantCount}</td>
                                            <td>${el.dept.depName}</td>
                                            <td>${el.role.roleName}</td>
                                            <td>${el.status}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/employeelist?type=edit&empCode=${el.empCode}" class="btn btn-sm btn-primary">Edit</a>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/employeelist?type=delete&empCode=${el.empCode}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?');">Delete</a>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>


                        </div>
                    </div>
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
        <script src='${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js'></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
        <script src='${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js'></script>
        <script>
                                                    $(document).ready(function () {
                                                        $('[data-toggle="tooltip"]').tooltip();
                                                    });
        </script>
        <style>
            .icon-circle {
                display: inline-flex;
                justify-content: center;
                align-items: center;
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background-color: #e0e0e0;
                color: #333;
                text-decoration: none;
                transition: 0.3s;
                border: none;           /* bỏ viền mặc định */
                background: none;       /* bỏ nền mặc định */
                padding: 0;             /* bỏ khoảng cách bên trong */
                cursor: pointer;        /* hiện con trỏ tay khi hover */
                color: inherit;
            }

            .icon-circle:hover {
                background-color: #d5d5d5; /* xám đậm hơn khi hover */
                color: #000;
            }
        </style>
    </body>
</html>
