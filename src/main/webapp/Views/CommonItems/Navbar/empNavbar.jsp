<%-- 
    Document   : dashboardNavbar
    Created on : Oct 2, 2025, 4:05:59 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="ttr-sidebar">
    <div class="ttr-sidebar-wrapper content-scroll">
        <!-- side menu logo start -->
        <div class="ttr-sidebar-logo">
            <a href="#"><img alt="" src="assets/images/logo.png" width="122" height="27"></a>
            <!-- <div class="ttr-sidebar-pin-button" title="Pin/Unpin Menu">
                    <i class="material-icons ttr-fixed-icon">gps_fixed</i>
                    <i class="material-icons ttr-not-fixed-icon">gps_not_fixed</i>
            </div> -->
            <div class="ttr-sidebar-toggle-button">
                <i class="ti-arrow-left"></i>
            </div>
        </div>
        <!-- side menu logo end -->
        <!-- sidebar menu start -->
        <nav class="ttr-sidebar-navi">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/dashboard" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-dashboard"></i></span>
                        <span class="ttr-label">Dashboard</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/profile" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-label">Profile</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/Views/listapplication.jsp" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-email"></i></span>
                        <span class="ttr-label">Application</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li>
                            <a href="${pageContext.request.contextPath}/application?typeapplication=leave" class="ttr-material-button"><span class="ttr-label">Leave request</span></a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/application?typeapplication=ot" class="ttr-material-button"><span class="ttr-label">Overtime request</span></a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/compose?type=LEAVE" class="ttr-material-button"><span class="ttr-label">Send leave application</span></a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/compose?type=OT" class="ttr-material-button"><span class="ttr-label">Send overtime application</span></a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/candidatelist" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-post-media">Candidate List</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/accountlist" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-accordion">Account List</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/employeelistservlet" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-post-media">Employee List</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/departmentlistservlet" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-post-media">Department List</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/hrrecruitment" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-post-media">HR Recuitment</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/hrmanagerrecruitment" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-post-media">HRM Recuitment</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/raw-attendance" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-post-media">Raw Attendance</span>
                    </a>
                </li>
<!--                <li>
                    <a href="${pageContext.request.contextPath}/hrmanagerrecruitment" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-post-media">HRM Recuitment</span>
                    </a>
                </li>-->
                <li class="ttr-seperate"></li>
            </ul>
            <!-- sidebar menu end -->
        </nav>
        <!-- sidebar menu end -->
    </div>
</div>