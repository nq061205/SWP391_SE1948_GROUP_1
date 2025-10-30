<%-- 
    Document   : candidateDetail
    Created on : Oct 11, 2025
    Author     : Hoàng Duy
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Employee Detail</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Base CSS -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <style>
            .cv-container {
                border: 1px solid #ddd;
                border-radius: 8px;
                background-color: #fff;
                height: 85vh;
                overflow: auto;
                text-align: center;
                padding: 10px;
            }
            .cv-container iframe {
                width: 100%;
                height: 100%;
                border: none;
                border-radius: 8px;
            }
            .candidate-info {
                background: #fff;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .candidate-info h5 {
                font-weight: 600;
                color: #333;
            }
            .info-item {
                margin-bottom: 12px;
            }
            .info-label {
                font-weight: 600;
                color: #555;
            }
            .nav-tabs .nav-link.active {
                background-color: #007bff !important; /* xanh dương */
                color: #fff !important;
                border-color: #007bff #007bff #fff;
            }
            .nav-tabs .nav-link:hover {
                background-color: #e9f2ff;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <!-- Header + Navbar -->
        <jsp:include page="CommonItems/Header/dashboardHeader.jsp" />
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>

        <main class="ttr-wrapper">
            <div class="container-fluid">

                <!-- Breadcrumb -->
                <div class="db-breadcrumb mb-3">
                    <h4 class="breadcrumb-title">Employee Detail</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/employeelist">Employee list</a></li>
                        <li>Detail</li>
                    </ul>
                </div>
                <ul class="nav nav-tabs mb-4" id="employeeTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <a class="nav-link ${tab eq 'Contract' ? 'active bg-primary text-white' : ''}" 
                           href="${pageContext.request.contextPath}/employeedetail?tab=Contract&empCode=${empCode}" role="tab">
                            <i class="fa fa-list"></i> Contract
                        </a>
                    </li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link ${tab eq 'Dependant' ? 'active bg-primary text-white' : ''}" 
                           href="${pageContext.request.contextPath}/employeedetail?tab=Dependant&empCode=${empCode}" role="tab">
                            <i class="fa fa-list"></i> Dependant
                        </a>
                    </li>
                </ul>
                <c:if test="${tab eq 'Contract'}">
                    <div class="row">
                        <div class="col-lg-8 col-md-7 mb-4">
                            <div class="widget-box">
                                <div class="wc-title">
                                    <h4><i class="fa fa-file-alt"></i> Contract Image</h4>
                                </div>
                                <div class="widget-inner cv-container">
                                    <iframe 
                                        src="${contract.contractImg}" 
                                        type="application/pdf" 
                                        width="100%"    
                                        height="600px">
                                    </iframe>
                                </div>



                            </div>
                        </div>

                        <div class="col-lg-4 col-md-5">
                            <div class="widget-box candidate-info">
                                <div class="wc-title mb-3">
                                    <h4><i class="fa fa-user"></i> Contract Information</h4>
                                </div>
                                <c:choose>
                                    <c:when test="${option eq 'edit'}">
                                        <form action="contractdetail" method="post">
                                            <input type="hidden" name="empCode" value="${empCode}">
                                            <input type="hidden" name="tab" value="${tab}">
                                            <div class="info-item">
                                                <span class="info-label">Type:</span>
                                                <select name="type">
                                                    <c:forEach items="${typeList}" var="tl">
                                                        <option value="${tl}"  <c:if test="${tl eq contract.type}">selected</c:if>>${tl}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="info-item">
                                                <span class="info-label">Start Date:</span>
                                                <input class="form-control" type="date" name="start" value="${contract.startDate}">
                                            </div>

                                            <div class="info-item">
                                                <span class="info-label">End Date:</span>
                                                <input class="form-control" type="date" name="end" value="${contract.endDate}">
                                            </div>
                                            <hr>
                                            <div class="d-flex justify-content-between mt-3">
                                                <button type="submit" class="btn btn-sm btn-primary" name="option" value="save">Save</button>
                                                <a href="${pageContext.request.contextPath}/employeedetail?tab=Contract&empCode=${empCode}" role="tab" 
                                                   class="btn btn-sm btn-primary">Cancel</a>
                                            </div>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="info-item">
                                            <span class="info-label">Type:</span>
                                            <p>${contract.type}</p>
                                        </div>

                                        <div class="info-item">
                                            <span class="info-label">Start Date:</span>
                                            <p><fmt:formatDate value="${contract.startDate}" pattern="MMM dd, yyyy HH:mm" /></p>
                                        </div>

                                        <div class="info-item">
                                            <span class="info-label">End Date:</span>
                                            <p><fmt:formatDate value="${contract.endDate}" pattern="MMM dd, yyyy HH:mm" /></p>
                                        </div>
                                        <hr>
                                        <div class="d-flex justify-content-between mt-3">
                                            <a href="${pageContext.request.contextPath}/employeelist" class="btn btn-secondary">
                                                <i class="fa fa-arrow-left"></i> Back
                                            </a>
                                            <a href="${pageContext.request.contextPath}/employeedetail?empCode=${empCode}&option=edit" 
                                               class="btn btn-sm btn-primary">Edit information</a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>

                    </div>
                </c:if>
            </div>
        </main>

        <!-- JS Libraries -->
        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
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
    </body>
</html>
