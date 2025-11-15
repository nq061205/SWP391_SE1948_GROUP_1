<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
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
            .post-card {
                background: white;
                padding: 20px;
                margin-bottom: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                transition: all 0.3s ease;
            }
            .post-card:hover {
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.15);
            }
            .widget-box .widget-head {
                padding: 20px;
                border-bottom: 1px solid #dee2e6;
                background-color: #f8f9fa;
                border-radius: 8px 8px 0 0;
            }
            .post-body {
                padding: 15px;
            }
            .post-footer {
                background-color: #f8f9fa;
                padding: 10px 15px;
                border-top: 1px solid #dee2e6;
                border-radius: 0 0 8px 8px;
            }
            .badge-status {
                font-size: 12px;
                padding: 6px 12px;
                border-radius: 20px;
            }
            .badge-waiting {
                background-color: #ffc107;
                color: #000;
            }
            .badge-rejected {
                background-color: #dc3545;
                color: #fff;
            }
            .post-content {
                max-height: 200px;
                overflow-y: auto;
                padding: 10px;
                background-color: #f8f9fa;
                border-radius: 4px;
                margin: 10px 0;
            }
            .status-label {
                font-weight: bold;
                color: #6c757d;
                margin-right: 8px;
                min-width: 120px;
                display: inline-block;
            }
            .filter-control {
                margin-bottom: 15px;
            }
            .page-link.active {
                background-color: #007bff;
                border-color: #007bff;
                color: white;
            }
            .btn-success:hover {
                background-color: #28a745 !important;
                border-color: #28a745 !important;
                transform: translateY(-1px);
                box-shadow: 0 2px 4px rgba(40, 167, 69, 0.3);
            }
            .btn-danger:hover {
                background-color: #dc3545 !important;
                border-color: #dc3545 !important;
                transform: translateY(-1px);
                box-shadow: 0 2px 4px rgba(220, 53, 69, 0.3);
            }
            thead th.sortable {
                cursor: pointer;
                user-select: none;
                position: relative;
                white-space: nowrap;
            }
            thead th.sortable:hover {
                background-color: rgba(0,0,0,0.05);
            }
            thead th.sortable .fa {
                margin-left: 5px;
                opacity: 0.3;
                font-size: 12px;
            }
            thead th.sortable.asc .fa-sort-asc,
            thead th.sortable.desc .fa-sort-desc {
                opacity: 1;
            }
        </style>
    </head>
    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <jsp:include page="../CommonItems/Header/dashboardHeader.jsp" />
        <jsp:include page="../CommonItems/Navbar/empNavbar.jsp" />
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Post Review</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HRM/hrmDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>Recruitment</li>
                        <li>Post Review</li>
                    </ul>
                </div>

                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <strong>Success!</strong> ${successMessage}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

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
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-list-alt"></i> Post Review List</h4>
                            </div>
                            <div class="widget-inner">

                                <div class="row mb-3">
                                    <div class="col-12 mb-2">
                                        <form action="${pageContext.request.contextPath}/postreview" method="get" class="d-flex flex-wrap align-items-center" style="gap: 10px;" id="postReviewFilterForm">
                                            <input type="hidden" name="pageSize" value="${pageSize}">
                                            <c:if test="${not empty statusFilter}">
                                                <input type="hidden" name="status" value="${statusFilter}">
                                            </c:if>
                                            <c:if test="${not empty depIdFilter}">
                                                <input type="hidden" name="depId" value="${depIdFilter}">
                                            </c:if>
                                            <c:if test="${not empty fromDate}">
                                                <input type="hidden" name="fromDate" value="${fromDate}">
                                            </c:if>
                                            <c:if test="${not empty toDate}">
                                                <input type="hidden" name="toDate" value="${toDate}">
                                            </c:if>
                                            
                                            <!-- Search -->
                                            <div class="d-flex align-items-center" style="flex: 1 1 auto; min-width: 200px;">
                                                <label for="search" class="mr-2 mb-0" style="white-space: nowrap;">Search:</label>
                                                <input type="text" class="form-control" id="search" name="search"
                                                       value="${searchKeyword}" placeholder="Title, dept..." style="flex: 1;">
                                            </div>
                                            
                                            <!-- Date Range -->
                                            <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                <label for="fromDate" class="mr-2 mb-0">From:</label>
                                                <input type="date" class="form-control" id="fromDate" name="fromDate"
                                                       value="${fromDate}" style="width: 140px;">
                                            </div>
                                            <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                <label for="toDate" class="mr-2 mb-0">To:</label>
                                                <input type="date" class="form-control" id="toDate" name="toDate"
                                                       value="${toDate}" style="width: 140px;">
                                            </div>
                                            
                                            <!-- Department -->
                                            <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                <label for="depIdFilter" class="mr-2 mb-0">Dept:</label>
                                                <select class="form-control" id="depIdFilter" name="depId" style="width: 110px;">
                                                    <option value="">All</option>
                                                    <c:forEach items="${departments}" var="dept">
                                                        <option value="${dept.depId}" ${depIdFilter == dept.depId ? 'selected' : ''}>${dept.depName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            
                                            <!-- Status -->
                                            <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                <label for="statusFilter" class="mr-2 mb-0">Status:</label>
                                                <select class="form-control" id="statusFilter" name="status" style="width: 100px;">
                                                    <option value="" ${empty statusFilter ? 'selected' : ''}>All</option>
                                                    <option value="Waiting" ${statusFilter == 'Waiting' ? 'selected' : ''}>Waiting</option>
                                                    <option value="Rejected" ${statusFilter == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                                </select>
                                            </div>
                                            
                                            <!-- Page Size -->
                                            <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                <select class="form-control" id="pageSizeSelect" name="pageSize" style="width: 60px;">
                                                    <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                                                    <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                                    <option value="25" ${pageSize == 25 ? 'selected' : ''}>25</option>
                                                    <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                                                </select>
                                                <span class="ml-1 mb-0">per page</span>
                                            </div>
                                            
                                            <!-- Buttons -->
                                            <button type="submit" class="btn btn-primary" style="white-space: nowrap;">
                                                <i class="fa fa-filter"></i> Apply
                                            </button>
                                            <button type="button" class="btn btn-secondary" id="clearPostReviewFilter" style="white-space: nowrap;">
                                                <i class="fa fa-times"></i> Clear
                                            </button>
                                        </form>
                                    </div>
                                </div>


                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered">
                                        <thead class="thead-warning">
                                            <tr>
                                                <th width="60" class="sortable" data-sort="index">Index <i class="fa fa-sort"></i><i class="fa fa-sort-asc" style="display:none"></i><i class="fa fa-sort-desc" style="display:none"></i></th>
                                                <th>Title</th>
                                                <th width="150">Department</th>
                                                <th width="120" class="sortable" data-sort="time">Time <i class="fa fa-sort"></i><i class="fa fa-sort-asc" style="display:none"></i><i class="fa fa-sort-desc" style="display:none"></i></th>
                                                <th width="100">Status</th>
                                                <th width="180">Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>
                                                <c:when test="${not empty posts}">
                                                    <c:forEach items="${posts}" var="post" varStatus="loopStatus">
                                                        <tr>
                                                            <td class="text-center" data-index="${(currentPage - 1) * pageSize + loopStatus.index + 1}">
                                                                <span class="badge badge-secondary">${(currentPage - 1) * pageSize + loopStatus.index + 1}</span>
                                                            </td>
                                                            <td>
                                                                <div class="d-flex flex-column">
                                                                    <strong class="text-primary">${post.title}</strong>
                                                                    <small class="text-muted">
                                                                        ID: ${post.postId}
                                                                    </small>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty post.department}">
                                                                        <div class="d-flex flex-column">
                                                                            <span class="font-weight-bold">${post.department.depName}</span>
                                                                            <small class="text-muted">ID: ${post.department.depId}</small>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td data-time="${post.updatedAt.time}">
                                                                <c:choose>
                                                                    <c:when test="${not empty post.updatedAt}">
                                                                        <div class="d-flex flex-column">
                                                                            <fmt:formatDate value="${post.updatedAt}" pattern="MMM dd, yyyy" var="updatedDate"/>
                                                                            <fmt:formatDate value="${post.updatedAt}" pattern="HH:mm" var="updatedTime"/>
                                                                            <span class="font-weight-bold">${updatedDate}</span>
                                                                            <small class="text-muted">${updatedTime}</small>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${post.status == 'Waiting'}">
                                                                        <span class="badge badge-warning">Waiting</span>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Rejected'}">
                                                                        <span class="badge badge-danger">Rejected</span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                            <td class="text-center">
                                                                <div style="display: flex; justify-content: center; align-items: center; gap: 5px;">
                                                                    <a href="${pageContext.request.contextPath}/postreview?action=view&postId=${post.postId}"
                                                                       class="btn btn-sm btn-info" title="View details"
                                                                       style="min-width: 70px;">
                                                                        <i class="fa fa-eye"></i> View
                                                                    </a>
                                                                    <c:if test="${post.status == 'Waiting'}">
                                                                        <form action="${pageContext.request.contextPath}/postreview" method="post" style="display: inline; margin: 0;" onsubmit="return confirm('Are you sure you want to approve this post?');">
                                                                            <input type="hidden" name="action" value="approve">
                                                                            <input type="hidden" name="postId" value="${post.postId}">
                                                                            <button type="submit" class="btn btn-sm btn-success" title="Approve"
                                                                                    style="min-width: 80px;">
                                                                                <i class="fa fa-check"></i> Approve
                                                                            </button>
                                                                        </form>
                                                                        <form action="${pageContext.request.contextPath}/postreview" method="post" style="display: inline; margin: 0;" onsubmit="return confirm('Are you sure you want to reject this post?');">
                                                                            <input type="hidden" name="action" value="reject">
                                                                            <input type="hidden" name="postId" value="${post.postId}">
                                                                            <button type="submit" class="btn btn-sm btn-danger" title="Reject"
                                                                                    style="min-width: 80px;">
                                                                                <i class="fa fa-times"></i> Reject
                                                                            </button>
                                                                        </form>
                                                                    </c:if>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td colspan="6" class="text-center p-0">
                                                            <div class="text-center py-5">
                                                                <div class="mb-3">
                                                                    <i class="fa fa-inbox fa-3x text-muted"></i>
                                                                </div>
                                                                <h5 class="text-muted">No Posts Found</h5>
                                                                <p class="text-muted">There are currently no posts matching your criteria.</p>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                        </tbody>
                                    </table>
                                </div>


                                <c:if test="${totalPages > 1}">
                                    <div class="row mt-3">
                                        <div class="col-md-12">
                                            <nav aria-label="Page navigation">
                                                <ul class="pagination justify-content-center">
                                                    <c:if test="${currentPage > 1}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="${paginationBaseUrl}&page=${currentPage - 1}">
                                                                <i class="fa fa-angle-left"></i> Previous
                                                            </a>
                                                        </li>
                                                    </c:if>

                                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                            <a class="page-link" href="${paginationBaseUrl}&page=${i}">
                                                                ${i}
                                                            </a>
                                                        </li>
                                                    </c:forEach>

                                                    <c:if test="${currentPage < totalPages}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="${paginationBaseUrl}&page=${currentPage + 1}">
                                                                Next <i class="fa fa-angle-right"></i>
                                                            </a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </nav>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../CommonItems/Footer/dashboardFooter.jsp" />
    </body>
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
            $(document).ready(function() {
                // Clear filter for PostReview
                $('#clearPostReviewFilter').click(function(e) {
                    e.preventDefault();
                    $('#search').val('');
                    $('#fromDate').val('');
                    $('#toDate').val('');
                    $('#depIdFilter').val('');
                    $('#statusFilter').val('');
                    $('#pageSizeSelect').val('10');
                    $('#postReviewFilterForm').submit();
                });

                // Table sorting
                $('.sortable').click(function() {
                    var $th = $(this);
                    var sortType = $th.data('sort');
                    var $table = $th.closest('table');
                    var $tbody = $table.find('tbody');
                    var rows = $tbody.find('tr').toArray();
                    var isAsc = $th.hasClass('asc');
                    
                    // Remove sort classes from all headers
                    $('.sortable').removeClass('asc desc');
                    $('.sortable .fa').hide();
                    $('.sortable .fa-sort').show();
                    
                    // Add sort class to current header
                    if (isAsc) {
                        $th.removeClass('asc').addClass('desc');
                        $th.find('.fa').hide();
                        $th.find('.fa-sort-desc').show();
                    } else {
                        $th.addClass('asc');
                        $th.find('.fa').hide();
                        $th.find('.fa-sort-asc').show();
                    }
                    
                    // Sort rows
                    rows.sort(function(a, b) {
                        var aVal, bVal;
                        if (sortType === 'index') {
                            aVal = parseInt($(a).find('td[data-index]').data('index'));
                            bVal = parseInt($(b).find('td[data-index]').data('index'));
                        } else if (sortType === 'time') {
                            aVal = parseInt($(a).find('td[data-time]').data('time')) || 0;
                            bVal = parseInt($(b).find('td[data-time]').data('time')) || 0;
                        }
                        
                        if (isAsc) {
                            return bVal - aVal;
                        } else {
                            return aVal - bVal;
                        }
                    });
                    
                    // Reappend sorted rows
                    $.each(rows, function(index, row) {
                        $tbody.append(row);
                    });
                });
            });
        </script>
</html>