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
        <title>Candidate Detail - HRM System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Base CSS -->
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

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
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <!-- Header + Navbar -->
        <jsp:include page="CommonItems/Header/dashboardHeader.jsp" />
        <jsp:include page="CommonItems/Navbar/adminNavbar.jsp" />

        <main class="ttr-wrapper">
            <div class="container-fluid">

                <!-- Breadcrumb -->
                <div class="db-breadcrumb mb-3">
                    <h4 class="breadcrumb-title">Candidate Detail</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/candidatelist">Candidate</a></li>
                        <li>Detail</li>
                    </ul>
                </div>

                <div class="row">
                    <!-- LEFT: CV Display -->
                    <div class="col-lg-8 col-md-7 mb-4">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-file-alt"></i> Candidate CV</h4>
                            </div>
                            <div class="widget-inner cv-container">
                                <iframe 
                                    src="${candidate.cv}" 
                                    type="application/pdf" 
                                    width="100%"    
                                    height="600px">
                                </iframe>
                            </div>



                        </div>
                    </div>

                    <!-- RIGHT: Candidate Info -->
                    <div class="col-lg-4 col-md-5">
                        <div class="widget-box candidate-info">
                            <div class="wc-title mb-3">
                                <h4><i class="fa fa-user"></i> Personal Information</h4>
                            </div>

                            <div class="info-item">
                                <span class="info-label">Full Name:</span>
                                <p>${candidate.name}</p>
                            </div>

                            <div class="info-item">
                                <span class="info-label">Email:</span>
                                <p>${candidate.email}</p>
                            </div>

                            <div class="info-item">
                                <span class="info-label">Phone:</span>
                                <p>${candidate.phone}</p>
                            </div>

                            <div class="info-item">
                                <span class="info-label">Applied Post:</span>
                                <p>${candidate.post.title}</p>
                            </div>

                            <div class="info-item">
                                <span class="info-label">Applied At:</span>
                                <p><fmt:formatDate value="${candidate.appliedAt}" pattern="MMM dd, yyyy HH:mm" /></p>
                            </div>


                            <hr>
                            <div class="d-flex justify-content-between mt-3">
                                <a href="${pageContext.request.contextPath}/candidatelist" class="btn btn-secondary">
                                    <i class="fa fa-arrow-left"></i> Back
                                </a>
                                <c:if test="${candidate.result == null}">
                                    <div>
                                        <a href="${pageContext.request.contextPath}/candidateaction?id=${candidate.candidateId}&action=approve"
                                           class="btn btn-success"
                                           onclick="return confirm('Approve this candidate?');">
                                            <i class="fa fa-check"></i> Approve
                                        </a>
                                        <a href="${pageContext.request.contextPath}/candidateaction?id=${candidate.candidateId}&action=reject"
                                           class="btn btn-danger"
                                           onclick="return confirm('Reject this candidate?');">
                                            <i class="fa fa-times"></i> Reject
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- JS Libraries -->
        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
