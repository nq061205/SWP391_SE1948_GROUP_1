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
        <meta name="description" content="HRM - Recruitment Post Review" />
        <meta property="og:title" content="Post Review - HRM System" />
        <meta property="og:description" content="Review and Approve Recruitment Posts" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />
        <title>Post Review - HRM System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap4.min.css">
        
        <style>
            .post-card {
                border: 1px solid #dee2e6;
                border-radius: 8px;
                margin-bottom: 20px;
                transition: all 0.3s ease;
            }
            
            .post-card:hover {
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                transform: translateY(-2px);
            }
            
            .post-header {
                background-color: #f8f9fa;
                padding: 15px;
                border-bottom: 1px solid #dee2e6;
                border-radius: 8px 8px 0 0;
            }
            
            .post-body {
                padding: 20px;
            }
            
            .post-footer {
                padding: 15px;
                background-color: #f8f9fa;
                border-top: 1px solid #dee2e6;
                border-radius: 0 0 8px 8px;
            }
            
            .status-badge {
                font-size: 0.9rem;
                padding: 5px 15px;
                border-radius: 20px;
            }
            
            .btn-action {
                min-width: 100px;
            }
            
            .post-content {
                max-height: 200px;
                overflow-y: auto;
                padding: 10px;
                background-color: #f8f9fa;
                border-radius: 5px;
                margin: 10px 0;
            }
            
            .info-item {
                margin-bottom: 10px;
            }
            
            .info-label {
                font-weight: bold;
                color: #6c757d;
                min-width: 120px;
                display: inline-block;
            }
            
            /* Tabs Styling */
            .nav-tabs {
                border-bottom: 2px solid #dee2e6;
                margin-bottom: 0;
            }
            
            .nav-tabs .nav-link {
                border: none;
                border-bottom: 3px solid transparent;
                color: #6c757d;
                font-weight: 500;
                padding: 12px 20px;
                transition: all 0.3s;
            }
            
            .nav-tabs .nav-link:hover {
                border-bottom-color: #dee2e6;
                color: #495057;
            }
            
            .nav-tabs .nav-link.active {
                color: #007bff;
                border-bottom-color: #007bff;
                background-color: transparent;
            }
            
            .nav-tabs .nav-link i {
                margin-right: 5px;
            }
            
            .tab-content {
                background-color: #fff;
            }
            
            .thead-warning {
                background-color: #ffc107;
                color: #000;
            }
            
            .thead-danger {
                background-color: #dc3545;
                color: #fff;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <jsp:include page="../CommonItems/Header/dashboardHeader.jsp" />
        <jsp:include page="../CommonItems/Navbar/adminNavbar.jsp" />

        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Recruitment Post Review</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/Admin/adminDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>HRM</li>
                        <li>Post Review</li>
                    </ul>
                </div>
                
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <strong><i class="fa fa-check-circle"></i> Success!</strong> ${successMessage}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong><i class="fa fa-exclamation-triangle"></i> Error!</strong> ${errorMessage}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                
                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-clipboard-check"></i> Post Review</h4>
                            </div>
                            <div class="widget-inner">
                                <ul class="nav nav-tabs" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#pendingTab" role="tab">
                                            <i class="fa fa-clock"></i> Pending Posts
                                            <span class="badge badge-warning ml-2">${pendingCount}</span>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#rejectedTab" role="tab">
                                            <i class="fa fa-times-circle"></i> Rejected Posts
                                            <span class="badge badge-danger ml-2">${rejectedCount}</span>
                                        </a>
                                    </li>
                                </ul>
                                
                                <div class="tab-content">
                                    <div class="tab-pane fade show active" id="pendingTab" role="tabpanel">
                                        <div class="p-3">
                                            <c:set var="hasPending" value="false" />
                                            <c:forEach var="post" items="${pendingAndRejectedPosts}">
                                                <c:if test="${post.status == 'Pending'}">
                                                    <c:set var="hasPending" value="true" />
                                                </c:if>
                                            </c:forEach>
                                            
                                            <c:choose>
                                                <c:when test="${hasPending}">
                                                    <div class="table-responsive">
                                                        <table id="pendingTable" class="table table-striped table-bordered">
                                                            <thead class="thead-warning">
                                                                <tr>
                                                                    <th width="60">Index</th>
                                                                    <th>Title</th>
                                                                    <th width="150">Department</th>
                                                                    <th width="120">Created</th>
                                                                    <th width="200">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:set var="pendingIndex" value="0" />
                                                                <c:forEach var="post" items="${pendingAndRejectedPosts}">
                                                                    <c:if test="${post.status == 'Pending'}">
                                                                        <c:set var="pendingIndex" value="${pendingIndex + 1}" />
                                                                        <tr>
                                                                            <td class="text-center">
                                                                                <span class="badge badge-secondary">${pendingIndex}</span>
                                                                            </td>
                                                                            <td>
                                                                                <div class="d-flex flex-column">
                                                                                    <strong class="text-primary">${post.title}</strong>
                                                                                    <small class="text-muted">ID: ${post.postId}</small>
                                                                                </div>
                                                                            </td>
                                                                            <td>
                                                                                <span class="badge badge-info">${post.department.depName}</span>
                                                                            </td>
                                                                            <td class="text-center">
                                                                                <small><fmt:formatDate value="${post.createdAt}" pattern="MMM dd, yyyy" /></small>
                                                                            </td>
                                                                            <td class="text-center">
                                                                                <a href="${pageContext.request.contextPath}/postreview?action=view&postId=${post.postId}" 
                                                                                   class="btn btn-sm btn-info" title="View Details">
                                                                                    <i class="fa fa-eye"></i> View
                                                                                </a>
                                                                                <form action="${pageContext.request.contextPath}/postreview" method="post" style="display:inline;" 
                                                                                      onsubmit="return confirm('Are you sure you want to approve this post: ${post.title}?');">
                                                                                    <input type="hidden" name="action" value="approve">
                                                                                    <input type="hidden" name="postId" value="${post.postId}">
                                                                                    <button type="submit" class="btn btn-sm btn-success" title="Approve Post">
                                                                                        <i class="fa fa-check"></i> Approve
                                                                                    </button>
                                                                                </form>
                                                                                <form action="${pageContext.request.contextPath}/postreview" method="post" style="display:inline;" 
                                                                                      onsubmit="return confirm('Are you sure you want to reject this post: ${post.title}?');">
                                                                                    <input type="hidden" name="action" value="reject">
                                                                                    <input type="hidden" name="postId" value="${post.postId}">
                                                                                    <button type="submit" class="btn btn-sm btn-danger" title="Reject Post">
                                                                                        <i class="fa fa-times"></i> Reject
                                                                                    </button>
                                                                                </form>
                                                                            </td>
                                                                        </tr>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-center py-5">
                                                        <div class="mb-3">
                                                            <i class="fa fa-check-circle fa-3x text-success"></i>
                                                        </div>
                                                        <h5 class="text-muted">No Pending Posts</h5>
                                                        <p class="text-muted">All posts have been reviewed.</p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    
                                    <div class="tab-pane fade" id="rejectedTab" role="tabpanel">
                                        <div class="p-3">
                                            <c:set var="hasRejected" value="false" />
                                            <c:forEach var="post" items="${pendingAndRejectedPosts}">
                                                <c:if test="${post.status == 'Rejected'}">
                                                    <c:set var="hasRejected" value="true" />
                                                </c:if>
                                            </c:forEach>
                                            
                                            <c:choose>
                                                <c:when test="${hasRejected}">
                                                    <div class="table-responsive">
                                                        <table id="rejectedTable" class="table table-striped table-bordered">
                                                            <thead class="thead-danger">
                                                                <tr>
                                                                    <th width="60">Index</th>
                                                                    <th>Title</th>
                                                                    <th width="150">Department</th>
                                                                    <th width="120">Created</th>
                                                                    <th width="120">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:set var="rejectedIndex" value="0" />
                                                                <c:forEach var="post" items="${pendingAndRejectedPosts}">
                                                                    <c:if test="${post.status == 'Rejected'}">
                                                                        <c:set var="rejectedIndex" value="${rejectedIndex + 1}" />
                                                                        <tr>
                                                                            <td class="text-center">
                                                                                <span class="badge badge-secondary">${rejectedIndex}</span>
                                                                            </td>
                                                                            <td>
                                                                                <div class="d-flex flex-column">
                                                                                    <strong class="text-danger">${post.title}</strong>
                                                                                    <small class="text-muted">ID: ${post.postId}</small>
                                                                                </div>
                                                                            </td>
                                                                            <td>
                                                                                <span class="badge badge-info">${post.department.depName}</span>
                                                                            </td>
                                                                            <td class="text-center">
                                                                                <small><fmt:formatDate value="${post.createdAt}" pattern="MMM dd, yyyy" /></small>
                                                                            </td>
                                                                            <td class="text-center">
                                                                                <a href="${pageContext.request.contextPath}/postreview?action=view&postId=${post.postId}" 
                                                                                   class="btn btn-sm btn-info" title="View Details">
                                                                                    <i class="fa fa-eye"></i> View Details
                                                                                </a>
                                                                            </td>
                                                                        </tr>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-center py-5">
                                                        <div class="mb-3">
                                                            <i class="fa fa-check-circle fa-3x text-success"></i>
                                                        </div>
                                                        <h5 class="text-muted">No Rejected Posts</h5>
                                                        <p class="text-muted">No posts have been rejected.</p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
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
        <script src="${pageContext.request.contextPath}/assets2/vendors/scroll/scrollbar.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/functions.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/chart/chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/js/admin.js"></script>
        
        <script type="text/javascript" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap4.min.js"></script>

        <script>
            $(document).ready(function () {
                $('#pendingTable').DataTable({
                    "pageLength": 5,
                    "lengthMenu": [[5, 10, 25, 50], [5, 10, 25, 50]],
                    "order": [[3, "desc"]],
                    "columnDefs": [
                        { "orderable": false, "targets": 4 },
                        { "className": "text-center", "targets": [0, 3, 4] }
                    ],
                    "language": {
                        "search": "Search pending posts:",
                        "lengthMenu": "Show _MENU_ posts",
                        "info": "Showing _START_ to _END_ of _TOTAL_ pending posts",
                        "infoEmpty": "No pending posts",
                        "infoFiltered": "(filtered from _MAX_ total posts)",
                        "zeroRecords": "No matching pending posts found"
                    }
                });
                
                $('#rejectedTable').DataTable({
                    "pageLength": 5,
                    "lengthMenu": [[5, 10, 25, 50], [5, 10, 25, 50]],
                    "order": [[3, "desc"]],
                    "columnDefs": [
                        { "orderable": false, "targets": 4 },
                        { "className": "text-center", "targets": [0, 3, 4] }
                    ],
                    "language": {
                        "search": "Search rejected posts:",
                        "lengthMenu": "Show _MENU_ posts",
                        "info": "Showing _START_ to _END_ of _TOTAL_ rejected posts",
                        "infoEmpty": "No rejected posts",
                        "infoFiltered": "(filtered from _MAX_ total posts)",
                        "zeroRecords": "No matching rejected posts found"
                    }
                });
                
                setTimeout(function() {
                    $('.alert').fadeOut('slow');
                }, 5000);
            });
            
            function viewPostDetail(postId) {
                window.location.href = '${pageContext.request.contextPath}/postreview?action=view&postId=' + postId;
            }
        </script>
    </body>
</html>
