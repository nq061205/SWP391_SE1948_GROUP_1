<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />
        <meta name="description" content="Recruitment Post Detail" />
        <meta property="og:title" content="Post Detail - HRM System" />
        <meta property="og:description" content="Recruitment Post Detail View" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />
        <title>Post Detail - HRM System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/assets2/js/html5shiv.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/respond.min.js"></script>
        <![endif]-->

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        
        <style>
            .badge-purple {
                background-color: #6f42c1;
                color: #fff;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <jsp:include page="../CommonItems/Header/dashboardHeader.jsp" />
        
        <!-- Include Navbar -->
        <jsp:include page="../CommonItems/Navbar/empNavbar.jsp" />

        <!--Main container start -->
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Recruitment Post Detail</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/hrrecruitment">Recruitment</a></li>
                        <li>Post Detail</li>
                    </ul>
                </div>	
                
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error!</strong> ${errorMessage}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="mb-3">
                            <a href="${pageContext.request.contextPath}/hrrecruitment" class="btn btn-secondary">
                                <i class="fa fa-arrow-left"></i> Back to List
                            </a>
                        </div>
                        
                        <c:choose>
                            <c:when test="${hasPost}">
                                <div class="widget-box">
                                    <div class="widget-inner">
                                        <div class="row mb-4">
                                            <div class="col-lg-8">
                                                <h2 class="text-primary mb-2">${post.title}</h2>
                                                <div class="d-flex flex-wrap">
                                                    <c:choose>
                                                        <c:when test="${post.status == 'New'}">
                                                            <span class="badge badge-info mr-2 mb-2">
                                                                <i class="fa fa-file"></i> ${post.status}
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${post.status == 'Waiting'}">
                                                            <span class="badge badge-warning mr-2 mb-2">
                                                                <i class="fa fa-clock"></i> ${post.status}
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${post.status == 'Rejected'}">
                                                            <span class="badge badge-danger mr-2 mb-2">
                                                                <i class="fa fa-times-circle"></i> ${post.status}
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${post.status == 'Approved'}">
                                                            <span class="badge badge-success mr-2 mb-2">
                                                                <i class="fa fa-check-circle"></i> ${post.status}
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${post.status == 'Uploaded'}">
                                                            <span class="badge badge-purple mr-2 mb-2">
                                                                <i class="fa fa-upload"></i> ${post.status}
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${post.status == 'Deleted'}">
                                                            <span class="badge badge-secondary mr-2 mb-2">
                                                                <i class="fa fa-trash"></i> ${post.status}
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-secondary mr-2 mb-2">
                                                                <i class="fa fa-question-circle"></i> ${post.status}
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <span class="badge badge-info mr-2 mb-2">
                                                        <i class="fa fa-hashtag"></i> ID: ${post.postId}
                                                    </span>
                                                    <c:if test="${hasDepartment}">
                                                        <span class="badge badge-primary mr-2 mb-2">
                                                            <i class="fa fa-building"></i> ${post.department.depName}
                                                        </span>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 text-right">
                                                <c:if test="${hasApprovedAt}">
                                                    <div class="text-muted">
                                                        <small>Approved on</small><br>
                                                        <fmt:formatDate value="${post.approvedAt}" pattern="MMM dd, yyyy 'at' HH:mm" />
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                        
                                        <div class="row mb-4">
                                            <div class="col-lg-6">
                                                <div class="card">
                                                    <div class="card-header bg-light">
                                                        <h5 class="card-title mb-0">
                                                            <i class="fa fa-info-circle text-primary"></i> Post Information
                                                        </h5>
                                                    </div>
                                                    <div class="card-body">
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Post ID:</strong></div>
                                                            <div class="col-sm-7">${post.postId}</div>
                                                        </div>
                                                        <hr>
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Title:</strong></div>
                                                            <div class="col-sm-7">${post.title}</div>
                                                        </div>
                                                        <hr>
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Status:</strong></div>
                                                            <div class="col-sm-7">
                                                                <c:choose>
                                                                    <c:when test="${post.status == 'New'}">
                                                                        <span class="badge badge-info">${post.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Waiting'}">
                                                                        <span class="badge badge-warning">${post.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Rejected'}">
                                                                        <span class="badge badge-danger">${post.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Approved'}">
                                                                        <span class="badge badge-success">${post.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Uploaded'}">
                                                                        <span class="badge badge-purple">${post.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Deleted'}">
                                                                        <span class="badge badge-secondary">${post.status}</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-secondary">${post.status}</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Department:</strong></div>
                                                            <div class="col-sm-7">
                                                                <c:choose>
                                                                    <c:when test="${hasDepartment}">
                                                                        ${post.department.depName}
                                                                        <br><small class="text-muted">${post.department.depId}</small>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Created:</strong></div>
                                                            <div class="col-sm-7">
                                                                <c:if test="${hasCreatedAt}">
                                                                    <fmt:formatDate value="${post.createdAt}" pattern="MMM dd, yyyy 'at' HH:mm" />
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                            <div class="col-lg-6">
                                                <div class="card">
                                                    <div class="card-header bg-light">
                                                        <h5 class="card-title mb-0">
                                                            <i class="fa fa-users text-success"></i> Approval Information
                                                        </h5>
                                                    </div>
                                                    <div class="card-body">
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Created by:</strong></div>
                                                            <div class="col-sm-7">
                                                                <c:choose>
                                                                    <c:when test="${hasCreatedBy}">
                                                                        ${post.createdBy.fullname}
                                                                        <br><small class="text-muted">${post.createdBy.positionTitle}</small>
                                                                        <br><small class="text-muted">${post.createdBy.email}</small>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Approved by:</strong></div>
                                                            <div class="col-sm-7">
                                                                <c:choose>
                                                                    <c:when test="${hasApprovedBy}">
                                                                        ${post.approvedBy.fullname}
                                                                        <br><small class="text-muted">${post.approvedBy.positionTitle}</small>
                                                                        <br><small class="text-muted">${post.approvedBy.email}</small>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Approved on:</strong></div>
                                                            <div class="col-sm-7">
                                                                <c:choose>
                                                                    <c:when test="${hasApprovedAt}">
                                                                        <fmt:formatDate value="${post.approvedAt}" pattern="MMM dd, yyyy 'at' HH:mm" />
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div class="row">
                                                            <div class="col-sm-5"><strong>Last Updated:</strong></div>
                                                            <div class="col-sm-7">
                                                                <c:if test="${hasUpdatedAt}">
                                                                    <fmt:formatDate value="${post.updatedAt}" pattern="MMM dd, yyyy 'at' HH:mm" />
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="card">
                                                    <div class="card-header bg-primary text-white">
                                                        <h5 class="card-title mb-0">
                                                            <i class="fa fa-file-text"></i> Job Description & Requirements
                                                        </h5>
                                                    </div>
                                                    <div class="card-body">
                                                        <c:choose>
                                                            <c:when test="${hasContent}">
                                                                <div class="content-display">
                                                                    ${post.content}
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="text-center text-muted py-4">
                                                                    <i class="fa fa-exclamation-circle fa-2x mb-2"></i>
                                                                    <p>No content available for this post.</p>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="widget-box">
                                    <div class="widget-inner text-center py-5">
                                        <div class="mb-3">
                                            <i class="fa fa-exclamation-triangle fa-3x text-warning"></i>
                                        </div>
                                        <h4 class="text-muted">Post Not Found</h4>
                                        <p class="text-muted">The recruitment post you're looking for could not be found.</p>
                                        <a href="${pageContext.request.contextPath}/hrrecruitment" class="btn btn-primary">
                                            <i class="fa fa-arrow-left"></i> Back to List
                                        </a>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
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
        <script src="${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>

        <style>
            .content-display {
                line-height: 1.6;
                font-size: 14px;
            }
            
            .content-display p {
                margin-bottom: 15px;
            }
            
            .content-display h1, .content-display h2, .content-display h3, 
            .content-display h4, .content-display h5, .content-display h6 {
                color: #333;
                margin-bottom: 10px;
                margin-top: 20px;
            }
            
            .content-display ul, .content-display ol {
                margin-bottom: 15px;
                padding-left: 20px;
            }
            
            .content-display li {
                margin-bottom: 5px;
            }
            
            .card-header {
                border-bottom: 1px solid #dee2e6;
            }
            
            .card-body hr {
                margin: 0.5rem 0;
                border-color: #e9ecef;
            }
        </style>
    </body>
</html>