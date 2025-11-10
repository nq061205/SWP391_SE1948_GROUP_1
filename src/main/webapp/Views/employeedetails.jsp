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
            /* Căn giữa form trong box */
            .cv-container {
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 40px 20px;
                background: #f9fafc;
                border-radius: 16px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.08);
            }

            /* Form upload */
            .cv-container form {
                background: #fff;
                padding: 30px 40px;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.05);
                display: flex;
                flex-direction: column;
                align-items: center;
                width: 100%;
                max-width: 400px;
                transition: all 0.3s ease;
            }
            .cv-container form:hover {
                transform: translateY(-4px);
                box-shadow: 0 6px 18px rgba(0,0,0,0.1);
            }

            .cv-container input[type="file"] {
                border: 2px dashed #4e73df;
                border-radius: 10px;
                padding: 25px;
                background-color: #f8faff;
                width: 100%;
                cursor: pointer;
                transition: all 0.3s ease;
                text-align: center;
            }

            .cv-container input[type="file"]:hover {
                background-color: #e9f0ff;
                border-color: #3759c5;
            }

            /* Nút submit */
            .cv-container input[type="submit"] {
                background: linear-gradient(135deg, #4e73df, #2e59d9);
                color: white;
                border: none;
                border-radius: 8px;
                padding: 10px 24px;
                margin-top: 20px;
                font-weight: 600;
                letter-spacing: 0.5px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .cv-container input[type="submit"]:hover {
                background: linear-gradient(135deg, #2e59d9, #224abe);
                transform: scale(1.03);
            }

            /* Tiêu đề box */
            .wc-title h4 {
                display: flex;
                align-items: center;
                gap: 8px;
                color: #2e59d9;
                font-weight: 600;
                border-bottom: 2px solid #e3e6f0;
                padding-bottom: 10px;
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
                        <li><a href="${pageContext.request.contextPath}/employeelist">Employee list</a></li>
                        <li>Employee Detail</li>
                    </ul>
                </div>
                <ul class="nav nav-tabs mb-4" id="employeeTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <a class="nav-link ${tab eq 'Contract' ? 'active bg-primary text-white' : ''}" 
                           href="${pageContext.request.contextPath}/employeedetail?tab=Contract&empId=${empId}" role="tab">
                            <i class="fa fa-list"></i> Contract
                        </a>
                    </li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link ${tab eq 'Dependant' ? 'active bg-primary text-white' : ''}" 
                           href="${pageContext.request.contextPath}/employeedetail?tab=Dependant&empId=${empId}" role="tab">
                            <i class="fa fa-list"></i> Dependant
                        </a>
                    </li>
                </ul>
                <c:if test="${tab eq 'Contract'}">
                    <form action="${pageContext.request.contextPath}/contractdetail"
                          method="post" enctype="multipart/form-data">

                        <input type="hidden" name="empId" value="${empId}">
                        <input type="hidden" name="tab" value="${tab}">
                        <input type="hidden" name="option" value="save">

                        <div class="row">
                            <div class="col-lg-8 col-md-7 mb-4">
                                <div class="widget-box">
                                    <div class="wc-title">
                                        <h4><i class="fa fa-file-alt"></i> Contract File</h4>
                                    </div>

                                    <div class="widget-inner cv-container">
                                        <c:choose>

                                            <c:when test="${empty contract.contractImg}">
                                                <div style="text-align:center;">
                                                    <p><strong>No contract file uploaded yet.</strong></p>
                                                    <c:if test="${option eq 'edit'}">
                                                        <input type="file" name="contractFile" accept=".pdf,image/*" required>
                                                        <p style="color:red;">${avatarErr}</p>
                                                    </c:if>
                                                </div>
                                            </c:when>

                                            <c:otherwise>
                                                <div style="margin-top:10px;width: 100%;height:100%">
                                                    <c:choose>
                                                        <c:when test="${fn:endsWith(contract.contractImg, '.pdf')}">
                                                            <iframe src="${pageContext.request.contextPath}/${contract.contractImg}#zoom=83"
                                                                    width="100%" height="100%"></iframe>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/${contract.contractImg}"
                                                                 alt="Contract Image"
                                                                 style="width:100%; max-height:600px; object-fit:contain;" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
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
                                            <div class="info-item">
                                                <span class="info-label">Type:</span>
                                                <select name="type" class="form-control">
                                                    <c:forEach items="${typeList}" var="tl">
                                                        <option value="${tl}" <c:if test="${tl eq contract.type}">selected</c:if>>
                                                            ${tl}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="info-item">
                                                <span class="info-label">Start Date:</span>
                                                <input class="form-control" type="date"
                                                       name="start" value="${contract.startDate}">
                                                <p>${DateErr}</p>
                                            </div>

                                            <div class="info-item">
                                                <span class="info-label">End Date:</span>
                                                <input class="form-control" type="date"
                                                       name="end" value="${contract.endDate}">
                                                <p>${DateErr}</p>
                                            </div>
                                            <div class="mt-3">
                                                <label for="contractFile" style="font-weight:600;">Replace file:</label>
                                                <input type="file" name="contractFile"
                                                       accept=".pdf,image/*" class="form-control mb-2">
                                                <p style="color:red;">${avatarErr}</p>
                                            </div>

                                            <hr>
                                            <div class="d-flex justify-content-between mt-3">
                                                <button type="submit" class="btn btn-sm btn-primary">Save</button>
                                                <a href="${pageContext.request.contextPath}/employeedetail?tab=Contract&empId=${empId}"
                                                   class="btn btn-sm btn-secondary">Cancel</a>
                                            </div>
                                        </c:when>

                                        <c:otherwise>
                                            <div class="info-item">
                                                <span class="info-label">Type:</span>
                                                <p>${contract.type}</p>
                                            </div>

                                            <div class="info-item">
                                                <span class="info-label">Start Date:</span>
                                                <p><fmt:formatDate value="${contract.startDate}" pattern="MMM dd, yyyy" /></p>
                                            </div>

                                            <div class="info-item">
                                                <span class="info-label">End Date:</span>
                                                <p><fmt:formatDate value="${contract.endDate}" pattern="MMM dd, yyyy" /></p>
                                            </div>
                                            <hr>
                                            <div class="d-flex justify-content-between mt-3">
                                                <a href="${pageContext.request.contextPath}/employeelist" class="btn btn-secondary">
                                                    <i class="fa fa-arrow-left"></i> Back
                                                </a>
                                                <a href="${pageContext.request.contextPath}/employeedetail?empId=${empId}&tab=Contract&option=edit"
                                                   class="btn btn-sm btn-primary">Edit Information</a>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:if>
                <c:if test="${tab eq 'Dependant'}">
                    <div class="widget-box">
                        <div class="wc-title">
                            <h4>Dependant information</h4>
                        </div>
                        <div style="margin: 1% 3% 1%;">
                            <a href="${pageContext.request.contextPath}/employeelist" class="btn btn-secondary">
                                <i class="fa fa-arrow-left"></i> Back
                            </a>
                            <button type="button"
                                    class="btn btn-sm btn-primary"
                                    data-bs-toggle="modal"
                                    data-bs-target="#addDependantModal">
                                <i class="fa-solid fa-user-plus"></i> Add New Dependant
                            </button>
                        </div>
                        <div class="filter-row mb-3" style="margin: 0 1%; display:flex; align-items:center; gap:35px;">
                            <form action="${pageContext.request.contextPath}/dependantdetail" method="get"
                                  class="d-flex align-items-center gap-2 flex-grow-1">
                                <input type="hidden" name="tab" value="${param.tab}">
                                <input type="hidden" name="empId" value="${param.empId}">
                                <input type="hidden" name="page" value="${param.page}">
                                <input  type="text" name="searchkey" class="form-control filter-h"
                                        placeholder="Search by name"
                                        value="${searchkey}">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-search me-1"></i> Search
                                </button>
                            </form>
                            <form action="${pageContext.request.contextPath}/dependantdetail" method="get"
                                  class="d-flex align-items-center gap-2" style="min-width:250px;gap: 25px">
                                <div style="display:flex;flex-direction: row;min-width:400px;gap: 20px">
                                    <select name="relationship" class="form-control filter-h" style="width:170px;">                                            >
                                        <option value="">-- All --</option>
                                        <c:forEach items="${relationList}" var="rl">
                                            <option value="${rl}" ${param.relationship == rl ? 'selected' : ''}>${rl}</option>
                                        </c:forEach>
                                    </select>
                                    <select name="gender" class="form-control filter-h">
                                        <option value="">-- All --</option>
                                        <option value="true" ${param.gender == 'true' ? 'selected' : ''}>Male</option>
                                        <option value="false" ${param.gender == 'false' ? 'selected' : ''}>Female</option>
                                    </select>
                                </div>
                                <div style="display:flex;gap: 30px">
                                    <button type="submit" class="btn btn-outline-secondary filter-h"> <i class="fa fa-filter"></i>Apply</button>
                                    <a href="${pageContext.request.contextPath}/createaccount"
                                       class="btn btn-warning filter-h"><i class="fa fa-times"></i>Clear</a>
                                </div>
                            </form>
                        </div>
                        <div class="modal fade" id="addDependantModal" tabindex="-1" aria-labelledby="addDependantModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="addDependantModalLabel">Add New Dependant</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">X</button>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/dependantdetail" method="post">
                                        <div class="modal-body">
                                            <div class="form-group row mb-2">
                                                <label class="col-sm-3 col-form-label">Name:</label>
                                                <div class="col-sm-9">
                                                    <input type="text" name="name" class="form-control" value="${name}" required>
                                                </div>
                                            </div>

                                            <div class="form-group row mb-2">
                                                <label class="col-sm-3 col-form-label">Relationship:</label>
                                                <div class="col-sm-9">
                                                    <select name="relationship" class="form-control">
                                                        <c:forEach items="${relationList}" var="rl">
                                                            <option value="${rl}">${rl}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group row mb-2">
                                                <label class="col-sm-3 col-form-label">Date of Birth:</label>
                                                <div class="col-sm-9">
                                                    <input type="date" value="${dob}" name="dob" class="form-control" required>
                                                    <p>${DobErr}</p>
                                                </div>
                                            </div>

                                            <div class="form-group row mb-2">
                                                <label class="col-sm-3 col-form-label">Gender:</label>
                                                <div class="col-sm-9">
                                                    <select name="gender" class="form-control">
                                                        <option value="true">Male</option>
                                                        <option value="false">Female</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group row mb-2">
                                                <label class="col-sm-3 col-form-label">Phone:</label>
                                                <div class="col-sm-9">
                                                    <input type="text" value="${phone}" name="phone" class="form-control" maxlength="10" pattern="0[0-9]{9}" required>
                                                </div>
                                            </div>
                                            <input type="hidden" name="empId" value="${empId}">
                                            <input type="hidden" name="tab" value="${tab}">
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            <button type="submit" class="btn btn-primary">Create</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div style="margin-left: 1%;margin-right:1%">
                            <table class="table table-striped table-bordered table-hover align-middle text-center" 
                                   style="table-layout: fixed; width: 100%; border-collapse: collapse;">
                                <thead class="thead-dark">
                                    <tr>
                                        <th>No</th>
                                        <th>Dependant Name</th>
                                        <th>Relationship</th>
                                        <th>Dob</th>
                                        <th>Gender</th>
                                        <th>Phone</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${dependantList}" var="dl" varStatus="loop">
                                        <tr>
                                            <td style="overflow-wrap: break-word;">${loop.index+1}</td>
                                            <td style="overflow-wrap: break-word;">${dl.name}</td>
                                            <td style="overflow-wrap: break-word;">${dl.relationship}</td>
                                            <td style="overflow-wrap: break-word;">${dl.dob}</td>
                                            <td style="overflow-wrap: break-word;">${dl.gender ?'Male':'Female'}</td>
                                            <td style="overflow-wrap: break-word;">${dl.phone}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/updatedependant?empId=${dl.employee.empId}" 
                                                   class="btn btn-sm btn-primary">Edit</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <c:url var="baseUrlWithSort" value="dependantdetail">
                            <c:if test="${not empty param.relationship}">
                                <c:param name="relationship" value="${param.relationship}" />
                            </c:if>
                            <c:if test="${not empty param.gender}">
                                <c:param name="gender" value="${param.gender}" />
                            </c:if>
                            <c:if test="${not empty searchkey}">
                                <c:param name="searchkey" value="${searchkey}" />
                            </c:if>
                            <c:if test="${not empty param.tab}">
                                <c:param name="tab" value="${param.tab}" />
                            </c:if>
                            <c:if test="${not empty param.empId}">
                                <c:param name="empId" value="${param.empId}" />
                            </c:if>
                        </c:url>

                        <c:set var="urlPrefixWithSort" value="${baseUrlWithSort}${fn:contains(baseUrlWithSort, '?') ? '&' : '?'}" />
                        <nav class="mt-3">
                            <ul class="pagination justify-content-center">
                                <c:set var="startPage" value="${page - 1}" />
                                <c:set var="endPage" value="${page + 1}" />

                                <c:if test="${startPage < 1}">
                                    <c:set var="endPage" value="${endPage + (1 - startPage)}" />
                                    <c:set var="startPage" value="1" />
                                </c:if>

                                <c:if test="${endPage > totalPages}">
                                    <c:set var="startPage" value="${startPage - (endPage - totalPages)}" />
                                    <c:set var="endPage" value="${totalPages}" />
                                </c:if>

                                <c:if test="${startPage < 1}">
                                    <c:set var="startPage" value="1" />
                                </c:if>
                                <li class="page-item ${page <= 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="${urlPrefixWithSort}&page=${page-1}">Prev</a>
                                </li>

                                <c:forEach var="p" begin="${startPage}" end="${endPage}">
                                    <li class="page-item ${p == page ? 'active' : ''}">
                                        <a class="page-link" href="${urlPrefixWithSort}&page=${p}">${p}</a>
                                    </li>
                                </c:forEach>

                                <li class="page-item ${page >= totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="${urlPrefixWithSort}&page=${page+1}">Next</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </c:if>
            </div>
        </main>
        <c:if test="${not empty DobErr}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    var modalEl = document.getElementById('addDependantModal');
                    if (modalEl) {
                        var myModal = new bootstrap.Modal(modalEl);
                        myModal.show();
                    }
                });
            </script>
        </c:if>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

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
    </body>
</html>
