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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">

        <!-- HEADER + NAVBAR -->
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/adminNavbar.jsp" %>
        <input type="hidden" name="typeApplication" value="leaverequest" />
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Department Listing</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/departmentlistservlet"><i class="fa fa-home"></i> Department List</a></li>
                    </ul>
                </div>
                        <div>
                            <form action="${pageContext.request.contextPath}/accountlist" method="get" class="d-flex mb-3">
                                <input type="text" name="searchkey" class="form-control me-2" placeholder="Search by code or name" value="${searchkey}">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </form>

                        </div>
                        <div class="row">
                            <div class="col-md-5">
                                <c:if test="${not empty searchkey}">
                                    <p>Found <strong>${totalResults}</strong> products with search key is <strong>${searchkey}</strong></p>  
                                </c:if>
                            </div>

                            <div class="col-md-7" style="display: flex; align-items: center; gap: 15px;">
                                <p style="margin: 1%;">Sort by:</p>
                                <div style="display: flex; gap: 20px;">
                                    Code: <div style="display: flex; flex-direction: column; line-height: 1.2;">
                                        <a href="accountlist?sortBy=emp_code&order=asc">ASC</a>
                                        <a href="accountlist?sortBy=emp_code&order=desc">DESC</a>
                                    </div>
                                    Name: <div style="display: flex; flex-direction: column; line-height: 1.2;">
                                        <a href="accountlist?sortBy=fullname&order=asc">ASC</a>
                                        <a href="accountlist?sortBy=fullname&order=desc">DESC</a>
                                    </div>
                                    Email: <div style="display: flex; flex-direction: column; line-height: 1.2;">
                                        <a href="accountlist?sortBy=email&order=asc">ASC</a>
                                        <a href="accountlist?sortBy=email&order=desc">DESC</a>
                                    </div>
                                    Department: <div style="display: flex; flex-direction: column; line-height: 1.2;">
                                        <a href="accountlist?sortBy=dep_id&order=asc">ASC</a>
                                        <a href="accountlist?sortBy=dep_id&order=desc">DESC</a>
                                    </div>


                                </div>
                            </div>
                        </div>
                        <div class="mail-box-list" style="overflow-x: scroll;">
                            <table class="table table-bordered table-hover">
                                <thead style="background-color: #f5f5f5;">
                                    <tr style="text-align: center">
                                        <th>ID</th>
                                        <th>DepartmentID</th>
                                        <th>Department Name</th>
                                        <th>Description</th>
                                        <th>Action</th>
                                    </tr>                                 
                                </thead>
                                <tbody>

                                    <c:forEach var="el" items="${sessionScope.deptList}" varStatus ="loop">
                                        <tr style="text-align:center">
                                            <c:choose>
                                                <c:when test="${editDept != null && editDept.depId eq el.depId}">
                                            <form action="${pageContext.request.contextPath}/departmentlistservlet" method="post">
                                                <td>${loop.index+1}</td>
                                                <td>${el.depId}</td>
                                                <td><input type="text" name="depName" value="${el.depName}" /></td>
                                                <td><input type="text" name="description" value="${el.description}" /></td>
                                                <td>
                                                    <button type="submit" name="action" value="save" class="btn btn-success btn-sm">Save</button>
                                                    <a href="${pageContext.request.contextPath}/departmentlistservlet" class="btn btn-secondary btn-sm">Cancel</a>
                                                </td>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${loop.index+1}</td>
                                            <td>${el.depId}</td>
                                            <td>${el.depName}</td>
                                            <td>${el.description}</td>                                           
                                            <td>
                                                <div style="display: flex;gap: 30px">
                                                <a style="width: 50%" href="${pageContext.request.contextPath}/departmentlistservlet?type=edit&depId=${el.depId}" class="btn btn-sm btn-primary">Edit</a>
                                                <a style="width:50%"href="${pageContext.request.contextPath}/accountlist?deptId=${el.depId}" class="btn btn-sm btn-primary">View</a>
                                                </div>
                                            </td>                                                   
                                        </c:otherwise>
                                    </c:choose>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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
            #candidateModal {
                display: none;
                position: relative; /* giữ vị trí ngay dưới button */
                margin-top: 15px; /* khoảng cách với nút Add Employee */
                z-index: 1;
            }

            #candidateModal .modal-content {
                background-color: #ffffff;
                border: 1px solid #ccc;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            }


            #candidateModal .close {
                color: #555;
                float: right;
                font-size: 20px;
                font-weight: bold;
                cursor: pointer;
            }

            #candidateModal .close:hover {
                color: #000;
            }

            #candidateModal table {
                margin-top: 10px;
            }

            #candidateModal table thead {
                background-color: #007bff;
                color: #fff;
            }

            #candidateModal table td,
            #candidateModal table th {
                text-align: center;
                vertical-align: middle;
            }

        </style>
    </body>
</html>

</html>
