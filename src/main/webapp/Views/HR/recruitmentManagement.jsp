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
        <meta name="description" content="HR Recruitment Management" />
        <meta property="og:title" content="Recruitment Management - HRM System" />
        <meta property="og:description" content="Human Resources Recruitment Management" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />
        <title>Recruitment Management - HRM System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link class="skin" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/color/color-1.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/vendors/summernote/summernote.css">
        <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <jsp:include page="../CommonItems/Header/dashboardHeader.jsp" />
        <jsp:include page="../CommonItems/Navbar/empNavbar.jsp" />
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Recruitment Management</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HR/hrDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>Recruitment</li>
                        <li>Approved Posts</li>
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

                <c:if test="${empty editPost}">
                    <div class="row">
                        <div class="col-lg-12 m-b30">
                            <div class="widget-box">
                                <div class="wc-title">
                                    <h4><i class="fa fa-plus-circle"></i> Create New Recruitment Post</h4>
                                </div>
                                <div class="widget-inner">
                                    <form action="${pageContext.request.contextPath}/hrrecruitment" method="post" id="createPostForm">
                                        <input type="hidden" name="action" value="create">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="title">Title <span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="title" name="title"
                                                           placeholder="Enter job title" required maxlength="255">
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="depId">Department <span class="text-danger">*</span></label>
                                                    <select class="form-control" id="depId" name="depId" required>
                                                        <option value="">Select Department</option>
                                                        <c:forEach var="dept" items="${departments}">
                                                            <option value="${dept.depId}">${dept.depName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label for="content">Job Description <span class="text-danger">*</span></label>
                                                    <textarea class="form-control summernote-editor" id="content" name="content"
                                                              placeholder="Enter detailed job description, requirements, and benefits" required></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn btn-primary">
                                                    <i class="fa fa-save"></i> Create Post
                                                </button>
                                                <button type="reset" class="btn btn-secondary ml-2">
                                                    <i class="fa fa-undo"></i> Reset
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty editPost}">
                    <div class="row">
                        <div class="col-lg-12 m-b30">
                            <div class="widget-box">
                                <div class="wc-title">
                                    <h4><i class="fa fa-edit"></i> Edit Rejected Post</h4>
                                    <span class="badge badge-warning">Post ID: ${editPost.postId}</span>
                                </div>
                                <div class="widget-inner">
                                    <form action="${pageContext.request.contextPath}/hrrecruitment" method="post" id="updatePostForm">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="postId" value="${editPost.postId}">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="editTitle">Title <span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="editTitle" name="title"
                                                           value="${editPost.title}" required maxlength="255">
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="editDepId">Department <span class="text-danger">*</span></label>
                                                    <select class="form-control" id="editDepId" name="depId" required>
                                                        <option value="">Select Department</option>
                                                        <c:forEach var="dept" items="${departments}">
                                                            <option value="${dept.depId}"
                                                                    ${editPost.department.depId == dept.depId ? 'selected' : ''}>
                                                                ${dept.depName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label for="editContent">Job Description <span class="text-danger">*</span></label>
                                                    <textarea class="form-control summernote-editor" id="editContent" name="content" required><c:out value="${editPost.content}" escapeXml="false"/></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn btn-success">
                                                    <i class="fa fa-save"></i> Update Post
                                                </button>
                                                <a href="${pageContext.request.contextPath}/hrrecruitment" class="btn btn-secondary ml-2">
                                                    <i class="fa fa-times"></i> Cancel
                                                </a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-bell"></i> Notification: Pending & Rejected Posts</h4>
                            </div>
                            <div class="widget-inner">
                                <div class="row mb-3">
                                    <div class="col-12 mb-2">
                                        <form action="${pageContext.request.contextPath}/hrrecruitment" method="get" class="d-flex flex-wrap align-items-center" style="gap: 10px;" id="notifFilterForm">
                                                    <input type="hidden" name="action" value="list">
                                                    <input type="hidden" name="notifPageSize" value="${notifPageSize}">
                                                    <c:if test="${not empty searchKeyword}">
                                                        <input type="hidden" name="search" value="${searchKeyword}">
                                                    </c:if>
                                                    <c:if test="${not empty pageSize}">
                                                        <input type="hidden" name="pageSize" value="${pageSize}">
                                                    </c:if>
                                                    <c:if test="${not empty currentPage}">
                                                        <input type="hidden" name="page" value="${currentPage}">
                                                    </c:if>
                                                    <c:if test="${not empty notifStatusFilter}">
                                                        <input type="hidden" name="notifStatus" value="${notifStatusFilter}">
                                                    </c:if>
                                                    <c:if test="${not empty notifFromDate}">
                                                        <input type="hidden" name="notifFromDate" value="${notifFromDate}">
                                                    </c:if>
                                                    <c:if test="${not empty notifToDate}">
                                                        <input type="hidden" name="notifToDate" value="${notifToDate}">
                                                    </c:if>
                                                    
                                                    <!-- Search -->
                                                    <div class="d-flex align-items-center" style="flex: 1 1 auto; min-width: 200px;">
                                                        <label for="notifSearch" class="mr-2 mb-0" style="white-space: nowrap;">Search:</label>
                                                        <input type="text" class="form-control" id="notifSearch" name="notifSearch"
                                                               value="${notifSearchKeyword}" placeholder="Title, dept..." style="flex: 1;">
                                                    </div>
                                                    
                                                    <!-- Date Range -->
                                                    <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                        <label for="notifFromDate" class="mr-2 mb-0">From:</label>
                                                        <input type="date" class="form-control" id="notifFromDate" name="notifFromDate"
                                                               value="${notifFromDate}" style="width: 140px;">
                                                    </div>
                                                    <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                        <label for="notifToDate" class="mr-2 mb-0">To:</label>
                                                        <input type="date" class="form-control" id="notifToDate" name="notifToDate"
                                                               value="${notifToDate}" style="width: 140px;">
                                                    </div>
                                                    
                                                    <!-- Department -->
                                                    <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                        <label for="notifDepIdFilter" class="mr-2 mb-0">Dept:</label>
                                                        <select class="form-control" id="notifDepIdFilter" name="notifDepId" style="width: 110px;">
                                                            <option value="">All</option>
                                                            <c:forEach items="${departments}" var="dept">
                                                                <option value="${dept.depId}" ${notifDepIdFilter == dept.depId ? 'selected' : ''}>${dept.depName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    
                                                    <!-- Status -->
                                                    <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                        <label for="notifStatusFilter" class="mr-2 mb-0">Status:</label>
                                                        <select class="form-control" id="notifStatusFilter" name="notifStatus" style="width: 100px;">
                                                            <option value="" ${empty notifStatusFilter ? 'selected' : ''}>All</option>
                                                            <option value="New" ${notifStatusFilter == 'New' ? 'selected' : ''}>New</option>
                                                            <option value="Waiting" ${notifStatusFilter == 'Waiting' ? 'selected' : ''}>Waiting</option>
                                                            <option value="Rejected" ${notifStatusFilter == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                                        </select>
                                                    </div>
                                                    
                                                    <!-- Page Size -->
                                                    <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                        <select class="form-control" id="notifPageSizeSelect" name="notifPageSize" style="width: 60px;">
                                                            <option value="5" ${notifPageSize == 5 ? 'selected' : ''}>5</option>
                                                            <option value="10" ${notifPageSize == 10 ? 'selected' : ''}>10</option>
                                                            <option value="25" ${notifPageSize == 25 ? 'selected' : ''}>25</option>
                                                            <option value="50" ${notifPageSize == 50 ? 'selected' : ''}>50</option>
                                                        </select>
                                                        <span class="ml-1 mb-0">per page</span>
                                                    </div>
                                                    
                                                    <!-- Buttons -->
                                                    <button type="submit" class="btn btn-primary" style="white-space: nowrap;">
                                                        <i class="fa fa-filter"></i> Apply
                                                    </button>
                                                    <button type="button" class="btn btn-secondary" id="clearNotifFilter" style="white-space: nowrap;">
                                                        <i class="fa fa-times"></i> Clear
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                <c:choose>
                                    <c:when test="${hasPendingOrRejected}">
                                        <div class="table-responsive">
                                            <table class="table table-striped table-bordered">
                                                <thead class="thead-warning">
                                                    <tr>
                                                        <th width="60">Index</th>
                                                        <th>Title</th>
                                                        <th width="100">Status</th>
                                                        <th width="180">Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:choose>
                                                        <c:when test="${not empty pendingAndRejectedPosts}">
                                                            <c:forEach var="post" items="${pendingAndRejectedPosts}" varStatus="status">
                                                                <tr>
                                                                    <td class="text-center">
                                                                        <span class="badge badge-secondary">${notifStartIndex + status.index + 1}</span>
                                                                    </td>
                                                                    <td>
                                                                        <div class="d-flex flex-column">
                                                                            <strong class="text-primary">${post.title}</strong>
                                                                            <small class="text-muted">
                                                                                ID: ${post.postId} | Department: ${post.department.depName}
                                                                            </small>
                                                                        </div>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <c:choose>
                                                                            <c:when test="${post.status == 'New'}">
                                                                                <span class="badge badge-info">New</span>
                                                                            </c:when>
                                                                            <c:when test="${post.status == 'Waiting'}">
                                                                                <span class="badge badge-warning">Waiting</span>
                                                                            </c:when>
                                                                            <c:when test="${post.status == 'Rejected'}">
                                                                                <span class="badge badge-danger">Rejected</span>
                                                                            </c:when>
                                                                        </c:choose>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <div style="display: flex; justify-content: center; align-items: center; gap: 5px; flex-wrap: wrap;">
                                                                            <c:choose>
                                                                                <c:when test="${post.status == 'New'}">
                                                                                    <a href="${pageContext.request.contextPath}/hrrecruitment?action=edit&postId=${post.postId}"
                                                                                       class="btn btn-sm btn-warning" title="Update post"
                                                                                       style="min-width: 80px;">
                                                                                        <i class="fa fa-edit"></i> Update
                                                                                    </a>
                                                                                    <form action="${pageContext.request.contextPath}/hrrecruitment" method="post" style="display:inline; margin: 0;">
                                                                                        <input type="hidden" name="action" value="send">
                                                                                        <input type="hidden" name="postId" value="${post.postId}">
                                                                                        <button type="submit" class="btn btn-sm btn-success" title="Send for approval"
                                                                                                style="min-width: 80px;"
                                                                                                onclick="return confirm('Are you sure you want to send this post for approval?');">
                                                                                            <i class="fa fa-paper-plane"></i> Send
                                                                                        </button>
                                                                                    </form>
                                                                                </c:when>
                                                                                <c:when test="${post.status == 'Waiting'}">
                                                                                    <a href="${pageContext.request.contextPath}/hrrecruitment?action=view&postId=${post.postId}"
                                                                                       class="btn btn-sm btn-info" title="View details"
                                                                                       style="min-width: 110px;">
                                                                                        <i class="fa fa-eye"></i> View Detail
                                                                                    </a>
                                                                                </c:when>
                                                                                <c:when test="${post.status == 'Rejected'}">
                                                                                    <a href="${pageContext.request.contextPath}/hrrecruitment?action=edit&postId=${post.postId}"
                                                                                       class="btn btn-sm btn-warning" title="Update rejected post"
                                                                                       style="min-width: 80px;">
                                                                                        <i class="fa fa-edit"></i> Update
                                                                                    </a>
                                                                                </c:when>
                                                                            </c:choose>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr>
                                                                <td colspan="4" class="text-center text-muted py-4">
                                                                    <i class="fa fa-inbox fa-2x mb-2"></i>
                                                                    <p>No data for this page</p>
                                                                </td>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tbody>
                                            </table>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-md-6">
                                                <p class="text-muted">
                                                    <c:choose>
                                                        <c:when test="${totalNotifPosts > 0}">
                                                            Showing ${notifStartDisplay} to ${notifEndDisplay} of ${totalNotifPosts} notifications
                                                        </c:when>
                                                        <c:otherwise>
                                                            Showing 0 to 0 of 0 notifications
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:if test="${not empty notifSearchKeyword}">
                                                        (filtered)
                                                    </c:if>
                                                </p>
                                            </div>
                                            <div class="col-md-6">
                                                <nav aria-label="Notification pagination">
                                                    <ul class="pagination justify-content-end">
                                                        <c:if test="${notifCurrentPage > 1}">
                                                            <li class="page-item">
                                                                <a class="page-link" href="${baseUrl}&notifPage=1&${notifParams}${approvedParams}">First</a>
                                                            </li>
                                                            <li class="page-item">
                                                                <a class="page-link" href="${baseUrl}&notifPage=${notifCurrentPage - 1}&${notifParams}${approvedParams}">Previous</a>
                                                            </li>
                                                        </c:if>

                                                        <c:forEach begin="${notifCurrentPage - 2 < 1 ? 1 : notifCurrentPage - 2}"
                                                                   end="${notifCurrentPage + 2 > notifTotalPages ? notifTotalPages : notifCurrentPage + 2}"
                                                                   var="i">
                                                            <li class="page-item ${i == notifCurrentPage ? 'active' : ''}">
                                                                <a class="page-link" href="${baseUrl}&notifPage=${i}&${notifParams}${approvedParams}">${i}</a>
                                                            </li>
                                                        </c:forEach>

                                                        <c:if test="${notifCurrentPage < notifTotalPages}">
                                                            <li class="page-item">
                                                                <a class="page-link" href="${baseUrl}&notifPage=${notifCurrentPage + 1}&${notifParams}${approvedParams}">Next</a>
                                                            </li>
                                                            <li class="page-item">
                                                                <a class="page-link" href="${baseUrl}&notifPage=${notifTotalPages}&${notifParams}${approvedParams}">Last</a>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center py-4">
                                            <div class="mb-3">
                                                <i class="fa fa-check-circle fa-3x text-success"></i>
                                            </div>
                                            <h5 class="text-muted">No pending or rejected posts</h5>
                                            <p class="text-muted">All posts are either approved or not submitted yet.</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-12 m-b30">
                        <div class="widget-box">
                            <div class="wc-title">
                                <h4><i class="fa fa-check-circle"></i> Approved Post List</h4>
                                <span class="badge badge-success">Total: ${totalPosts} posts</span>
                            </div>
                            <div class="widget-inner">
                                <div class="row mb-3">
                                    <div class="col-12 mb-2">
                                        <form action="${pageContext.request.contextPath}/hrrecruitment" method="get" class="d-flex flex-wrap align-items-center" style="gap: 10px;" id="approvedFilterForm">
                                                    <input type="hidden" name="action" value="list">
                                                    <input type="hidden" name="pageSize" value="${pageSize}">
                                                    <c:if test="${not empty notifSearchKeyword}">
                                                        <input type="hidden" name="notifSearch" value="${notifSearchKeyword}">
                                                    </c:if>
                                                    <c:if test="${not empty notifPageSize}">
                                                        <input type="hidden" name="notifPageSize" value="${notifPageSize}">
                                                    </c:if>
                                                    <c:if test="${not empty notifCurrentPage}">
                                                        <input type="hidden" name="notifPage" value="${notifCurrentPage}">
                                                    </c:if>
                                                    <c:if test="${not empty fromDate}">
                                                        <input type="hidden" name="fromDate" value="${fromDate}">
                                                    </c:if>
                                                    <c:if test="${not empty toDate}">
                                                        <input type="hidden" name="toDate" value="${toDate}">
                                                    </c:if>
                                                    
                                                    <!-- Search -->
                                                    <div class="d-flex align-items-center" style="flex: 1 1 auto; min-width: 200px;">
                                                        <label for="searchInput" class="mr-2 mb-0" style="white-space: nowrap;">Search:</label>
                                                        <input type="text" class="form-control" id="searchInput" name="search"
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
                                                    
                                                    <!-- Page Size -->
                                                    <div class="d-flex align-items-center" style="white-space: nowrap;">
                                                        <select class="form-control" id="pageSizeSelect" name="pageSize" style="width: 60px;">
                                                            <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                                                            <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                                            <option value="25" ${pageSize == 25 ? 'selected' : ''}>25</option>
                                                            <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                                                            <option value="100" ${pageSize == 100 ? 'selected' : ''}>100</option>
                                                        </select>
                                                        <span class="ml-1 mb-0">per page</span>
                                                    </div>
                                                    
                                                    <!-- Buttons -->
                                                    <button type="submit" class="btn btn-primary" style="white-space: nowrap;">
                                                        <i class="fa fa-filter"></i> Apply
                                                    </button>
                                                    <button type="button" class="btn btn-secondary" id="clearApprovedFilter" style="white-space: nowrap;">
                                                        <i class="fa fa-times"></i> Clear
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                <c:choose>
                                    <c:when test="${hasApprovedPosts}">
                                        <div class="table-responsive">
                                            <table class="table table-striped table-bordered">
                                                <thead class="thead-dark">
                                                    <tr>
                                                        <th width="60">Index</th>
                                                        <th>Title</th>
                                                        <th width="150">Department</th>
                                                        <th width="150">Approved by</th>
                                                        <th width="120">Time</th>
                                                        <th width="100">Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="post" items="${approvedPosts}" varStatus="status">
                                                        <tr>
                                                            <td class="text-center">
                                                                <span class="badge badge-secondary">${approvedStartIndex + status.index + 1}</span>
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
                                                                            <small class="text-muted">${post.department.depId}</small>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty post.approvedBy}">
                                                                        <div class="d-flex flex-column">
                                                                            <span class="font-weight-bold">${post.approvedBy.fullname}</span>
                                                                            <small class="text-muted">${post.approvedBy.positionTitle}</small>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${not empty post.approvedAt}">
                                                                        <div class="d-flex flex-column">
                                                                            <fmt:formatDate value="${post.approvedAt}" pattern="MMM dd, yyyy" var="approvedDate"/>
                                                                            <fmt:formatDate value="${post.approvedAt}" pattern="HH:mm" var="approvedTime"/>
                                                                            <span class="font-weight-bold">${approvedDate}</span>
                                                                            <small class="text-muted">${approvedTime}</small>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="text-muted">N/A</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <a href="${pageContext.request.contextPath}/hrrecruitment?action=view&postId=${post.postId}"
                                                                   class="btn btn-primary btn-sm" title="View Details">
                                                                    <i class="fa fa-eye"></i> View Detail
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-md-6">
                                                <p class="text-muted">
                                                    Showing ${approvedStartDisplay} to ${approvedEndDisplay} of ${totalPosts} posts
                                                    <c:if test="${not empty searchKeyword}">
                                                        (filtered from total posts)
                                                    </c:if>
                                                </p>
                                            </div>
                                            <div class="col-md-6">
                                                <nav aria-label="Page navigation">
                                                    <ul class="pagination justify-content-end">
                                                        <c:if test="${currentPage > 1}">
                                                            <li class="page-item">
                                                                <a class="page-link" href="${baseUrl}&page=1&${approvedPostParams}${notifPostParams}">First</a>
                                                            </li>
                                                            <li class="page-item">
                                                                <a class="page-link" href="${baseUrl}&page=${currentPage - 1}&${approvedPostParams}${notifPostParams}">Previous</a>
                                                            </li>
                                                        </c:if>

                                                        <c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
                                                                   end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
                                                                   var="i">
                                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                                <a class="page-link" href="${baseUrl}&page=${i}&${approvedPostParams}${notifPostParams}">${i}</a>
                                                            </li>
                                                        </c:forEach>

                                                        <c:if test="${currentPage < totalPages}">
                                                            <li class="page-item">
                                                                <a class="page-link" href="${baseUrl}&page=${currentPage + 1}&${approvedPostParams}${notifPostParams}">Next</a>
                                                            </li>
                                                            <li class="page-item">
                                                                <a class="page-link" href="${baseUrl}&page=${totalPages}&${approvedPostParams}${notifPostParams}">Last</a>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center py-5">
                                            <div class="mb-3">
                                                <i class="fa fa-inbox fa-3x text-muted"></i>
                                            </div>
                                            <h5 class="text-muted">No Approved Posts Found</h5>
                                            <p class="text-muted">There are currently no approved recruitment posts in the system.</p>
                                            <a href="#" class="btn btn-primary">
                                                <i class="fa fa-plus"></i> Create First Post
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
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


        <script src="${pageContext.request.contextPath}/assets2/vendors/summernote/summernote.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

        <script type="text/javascript">


            window.addEventListener('load', function() {
                console.log('Page fully loaded');
                console.log('jQuery:', typeof $);
                console.log('Summernote:', typeof $.fn.summernote);

                var config = {
                    height: 300,
                    toolbar: [
                        ['style', ['bold', 'italic', 'underline', 'clear']],
                        ['font', ['strikethrough']],
                        ['fontsize', ['fontsize']],
                        ['color', ['color']],
                        ['para', ['ul', 'ol', 'paragraph']],
                        ['table', ['table']],
                        ['insert', ['link', 'picture']], // Thêm nút chèn ảnh
                        ['view', ['fullscreen', 'codeview']]
                    ]
                };

                if ($('#content').length) {
                    console.log('Init #content');
                    $('#content').summernote(config);
                }

                if ($('#editContent').length) {
                    console.log('Init #editContent');
                    $('#editContent').summernote(config);
                }
            });


            $(document).ready(function() {
                $('#createPostForm').on('submit', function(e) {
                    var el = $('#content');
                    if (el.length && typeof el.summernote === 'function') {
                        var code = el.summernote('code');
                        var text = $('<div>').html(code).text().trim();
                        if (!text || code === '<p><br></p>') {
                            e.preventDefault();
                            alert('Please enter job description');
                            return false;
                        }
                    }
                });

                $('#updatePostForm').on('submit', function(e) {
                    var el = $('#editContent');
                    if (el.length && typeof el.summernote === 'function') {
                        var code = el.summernote('code');
                        var text = $('<div>').html(code).text().trim();
                        if (!text || code === '<p><br></p>') {
                            e.preventDefault();
                            alert('Please enter job description');
                            return false;
                        }
                    }
                });

                // Clear filter for Notification section
                $('#clearNotifFilter').click(function(e) {
                    e.preventDefault();
                    $('#notifSearch').val('');
                    $('#notifFromDate').val('');
                    $('#notifToDate').val('');
                    $('#notifDepIdFilter').val('');
                    $('#notifStatusFilter').val('');
                    $('#notifPageSizeSelect').val('10');
                    $('#notifFilterForm').submit();
                });

                // Clear filter for Approved Posts section
                $('#clearApprovedFilter').click(function(e) {
                    e.preventDefault();
                    $('#searchInput').val('');
                    $('#fromDate').val('');
                    $('#toDate').val('');
                    $('#depIdFilter').val('');
                    $('#pageSizeSelect').val('10');
                    $('#approvedFilterForm').submit();
                });
            });
        </script>
    </body>
</html>
