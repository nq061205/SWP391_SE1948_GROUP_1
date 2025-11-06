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
        <meta name="description" content="HRM - Manage Recruitment Posts" />
        <meta property="og:title" content="Manage Posts - HRM System" />
        <meta property="og:description" content="Manage Approved, Uploaded and Deleted Posts" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">
        <link rel="icon" href="${pageContext.request.contextPath}/assets2/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets2/images/favicon.png" />
        <title>Manage Posts - HRM System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/typography.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets2/css/shortcodes/shortcodes.css">
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
            .badge-approved {
                background-color: #28a745;
                color: #fff;
            }
            .badge-uploaded {
                background-color: #007bff;
                color: #fff;
            }
            .badge-deleted {
                background-color: #6c757d;
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
        </style>
    </head>
    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <jsp:include page="../CommonItems/Header/dashboardHeader.jsp" />
        <jsp:include page="../CommonItems/Navbar/hrManNavbar.jsp" />
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Manage Posts</h4>
                    <ul class="db-breadcrumb-list">
                        <li><a href="${pageContext.request.contextPath}/Views/HRM/hrmDashboard.jsp"><i class="fa fa-home"></i>Home</a></li>
                        <li>Recruitment</li>
                        <li>Manage Posts</li>
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
                                <h4><i class="fa fa-tasks"></i> Manage Posts List</h4>
                            </div>
                            <div class="widget-inner">

                                <div class="row mb-3">
                                    <div class="col-md-4">
                                        <form action="${pageContext.request.contextPath}/managepost" method="get" class="form-inline">
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
                                            <div class="form-group mr-2">
                                                <label for="search" class="mr-2">Search:</label>
                                                <input type="text" class="form-control" id="search" name="search"
                                                       value="${searchKeyword}" placeholder="Search by title, department...">
                                            </div>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fa fa-search"></i> Search
                                            </button>
                                            <c:if test="${not empty searchKeyword}">
                                                <a href="${searchClearUrl}" class="btn btn-secondary ml-2">
                                                    <i class="fa fa-times"></i> Clear
                                                </a>
                                            </c:if>
                                        </form>
                                    </div>
                                    <div class="col-md-4 text-center">
                                        <form action="${pageContext.request.contextPath}/managepost" method="get" class="form-inline justify-content-center">
                                            <c:if test="${not empty searchKeyword}">
                                                <input type="hidden" name="search" value="${searchKeyword}">
                                            </c:if>
                                            <c:if test="${not empty statusFilter}">
                                                <input type="hidden" name="status" value="${statusFilter}">
                                            </c:if>
                                            <c:if test="${not empty depIdFilter}">
                                                <input type="hidden" name="depId" value="${depIdFilter}">
                                            </c:if>
                                            <input type="hidden" name="pageSize" value="${pageSize}">
                                            <div class="form-group mr-2">
                                                <label for="fromDate" class="mr-2" style="white-space: nowrap;">From:</label>
                                                <input type="date" class="form-control" id="fromDate" name="fromDate"
                                                       value="${fromDate}" style="width: 150px;">
                                            </div>
                                            <div class="form-group mr-2">
                                                <label for="toDate" class="mr-2" style="white-space: nowrap;">To:</label>
                                                <input type="date" class="form-control" id="toDate" name="toDate"
                                                       value="${toDate}" style="width: 150px;">
                                            </div>
                                            <button type="submit" class="btn btn-info">
                                                <i class="fa fa-filter"></i> Filter
                                            </button>
                                            <c:if test="${not empty fromDate || not empty toDate}">
                                                <a href="${dateClearUrl}" class="btn btn-secondary ml-2">
                                                    <i class="fa fa-times"></i> Clear
                                                </a>
                                            </c:if>
                                        </form>
                                    </div>
                                    <div class="col-md-4 text-right">
                                        <form action="${pageContext.request.contextPath}/managepost" method="get" class="form-inline float-right" style="display: flex !important; align-items: center; flex-wrap: nowrap;">
                                            <c:if test="${not empty searchKeyword}">
                                                <input type="hidden" name="search" value="${searchKeyword}">
                                            </c:if>
                                            <c:if test="${not empty fromDate}">
                                                <input type="hidden" name="fromDate" value="${fromDate}">
                                            </c:if>
                                            <c:if test="${not empty toDate}">
                                                <input type="hidden" name="toDate" value="${toDate}">
                                            </c:if>
                                            <label for="depIdFilter" class="mr-2" style="white-space: nowrap;">Department:</label>
                                            <select class="form-control mr-2" id="depIdFilter" name="depId" style="width: 120px; height: 38px; flex-shrink: 0;">
                                                <option value="">All</option>
                                                <c:forEach items="${departments}" var="dept">
                                                    <option value="${dept.depId}" ${depIdFilter == dept.depId ? 'selected' : ''}>${dept.depName}</option>
                                                </c:forEach>
                                            </select>
                                            <label for="statusFilter" class="mr-2" style="white-space: nowrap;">Status:</label>
                                            <select class="form-control mr-2" id="statusFilter" name="status" style="width: 120px; height: 38px; flex-shrink: 0;">
                                                <option value="" ${empty statusFilter ? 'selected' : ''}>All</option>
                                                <option value="Approved" ${statusFilter == 'Approved' ? 'selected' : ''}>Approved</option>
                                                <option value="Uploaded" ${statusFilter == 'Uploaded' ? 'selected' : ''}>Uploaded</option>
                                                <option value="Deleted" ${statusFilter == 'Deleted' ? 'selected' : ''}>Deleted</option>
                                            </select>
                                            <select class="form-control" id="pageSizeSelect" name="pageSize" style="width: 60px; height: 38px; margin-right: 8px; flex-shrink: 0;">
                                                <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                                                <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                                <option value="25" ${pageSize == 25 ? 'selected' : ''}>25</option>
                                                <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                                            </select>
                                            <button type="submit" class="btn btn-primary" style="height: 38px; padding: 0.375rem 0.75rem; margin-right: 8px; flex-shrink: 0; white-space: nowrap;">
                                                <i class="fa fa-check"></i> Apply
                                            </button>
                                            <span style="white-space: nowrap; height: 38px; display: flex; align-items: center; flex-shrink: 0;">per page</span>
                                        </form>
                                    </div>
                                </div>


                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered">
                                        <thead class="thead-success">
                                            <tr>
                                                <th width="60">Index</th>
                                                <th>Title</th>
                                                <th width="100">Status</th>
                                                <th width="250">Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>
                                                <c:when test="${not empty posts}">
                                                    <c:forEach items="${posts}" var="post" varStatus="loopStatus">
                                                        <tr>
                                                            <td class="text-center">
                                                                <span class="badge badge-secondary">${(currentPage - 1) * pageSize + loopStatus.index + 1}</span>
                                                            </td>
                                                            <td>
                                                                <div class="d-flex flex-column">
                                                                    <strong class="text-primary">${post.title}</strong>
                                                                    <small class="text-muted">
                                                                        <i class="fa fa-building"></i>
                                                                        <c:choose>
                                                                            <c:when test="${post.department != null}">
                                                                                ${post.department.depName}
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                N/A
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </small>
                                                                    <small class="text-muted">
                                                                        <i class="fa fa-calendar"></i>
                                                                        <fmt:formatDate value="${post.createdAt}" pattern="MMM dd, yyyy" />
                                                                    </small>
                                                                </div>
                                                            </td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${post.status == 'Approved'}">
                                                                        <span class="badge badge-approved badge-status">${post.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Uploaded'}">
                                                                        <span class="badge badge-uploaded badge-status">${post.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Deleted'}">
                                                                        <span class="badge badge-deleted badge-status">${post.status}</span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${post.status == 'Approved'}">
                                                                        <div style="display: flex; justify-content: center; align-items: center; gap: 5px;">
                                                                            <a href="${pageContext.request.contextPath}/managepost?action=view&postId=${post.postId}"
                                                                               class="btn btn-sm btn-info" title="View Details" style="min-width: 70px;">
                                                                                <i class="fa fa-eye"></i> View
                                                                            </a>
                                                                            <form action="${pageContext.request.contextPath}/managepost?action=upload" method="post" style="display:inline; margin: 0;">
                                                                                <input type="hidden" name="postId" value="${post.postId}">
                                                                                <button type="submit" class="btn btn-sm btn-primary" title="Upload Post"
                                                                                        onclick="return confirm('Are you sure you want to upload this post?')"
                                                                                        style="min-width: 80px;">
                                                                                    <i class="fa fa-upload"></i> Upload
                                                                                </button>
                                                                            </form>
                                                                            <form action="${pageContext.request.contextPath}/managepost?action=delete" method="post" style="display:inline; margin: 0;">
                                                                                <input type="hidden" name="postId" value="${post.postId}">
                                                                                <button type="submit" class="btn btn-sm btn-danger" title="Delete Post"
                                                                                        onclick="return confirm('Are you sure you want to delete this post?')"
                                                                                        style="min-width: 80px;">
                                                                                    <i class="fa fa-trash"></i> Delete
                                                                                </button>
                                                                            </form>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Uploaded'}">
                                                                        <div style="display: flex; justify-content: center; align-items: center; gap: 5px;">
                                                                            <a href="${pageContext.request.contextPath}/managepost?action=view&postId=${post.postId}"
                                                                               class="btn btn-sm btn-info" title="View Details" style="min-width: 70px;">
                                                                                <i class="fa fa-eye"></i> View
                                                                            </a>
                                                                            <form action="${pageContext.request.contextPath}/managepost?action=takedown" method="post" style="display:inline; margin: 0;">
                                                                                <input type="hidden" name="postId" value="${post.postId}">
                                                                                <button type="submit" class="btn btn-sm btn-warning" title="Take Down Post"
                                                                                        onclick="return confirm('Are you sure you want to take down this post?')"
                                                                                        style="min-width: 100px;">
                                                                                    <i class="fa fa-arrow-down"></i> Take Down
                                                                                </button>
                                                                            </form>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:when test="${post.status == 'Deleted'}">
                                                                        <div style="display: flex; justify-content: center; align-items: center; gap: 5px;">
                                                                            <a href="${pageContext.request.contextPath}/managepost?action=view&postId=${post.postId}"
                                                                               class="btn btn-sm btn-info" title="View Details" style="min-width: 70px;">
                                                                                <i class="fa fa-eye"></i> View
                                                                            </a>
                                                                        </div>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td colspan="4" class="text-center">
                                                            <div class="alert alert-info" role="alert" style="margin: 20px 0;">
                                                                <i class="fa fa-info-circle"></i> No posts found.
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
                                                                <i class="fa fa-chevron-left"></i> Previous
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
                                                                Next <i class="fa fa-chevron-right"></i>
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
</html>
