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
            <div class="ttr-sidebar-toggle-button">
                <i class="ti-arrow-left"></i>
            </div>
        </div>
        <!-- side menu logo end -->

        <!-- sidebar menu start -->
        <nav class="ttr-sidebar-navi">
            <ul>
                <!-- Dashboard -->
                <li>
                    <a href="${pageContext.request.contextPath}/dashboard" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-dashboard"></i></span>
                        <span class="ttr-label">Dashboard</span>
                    </a>
                </li>

                <!-- Profile -->
                <li>
                    <a href="${pageContext.request.contextPath}/profile" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-user"></i></span>
                        <span class="ttr-label">Profile</span>
                    </a>
                </li>

                <!-- Applications -->
                <li>
                    <a href="${pageContext.request.contextPath}/Views/listapplication.jsp" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-write"></i></span>
                        <span class="ttr-label">Application</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/application?typeapplication=leave" class="ttr-material-button"><span class="ttr-label">Leave request</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/application?typeapplication=ot" class="ttr-material-button"><span class="ttr-label">Overtime request</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/compose?type=LEAVE" class="ttr-material-button"><span class="ttr-label">Send leave application</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/compose?type=OT" class="ttr-material-button"><span class="ttr-label">Send overtime application</span></a></li>
                    </ul>
                </li>

                <!-- Candidate List -->
                <li>
                    <a href="${pageContext.request.contextPath}/candidatelist" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-id-badge"></i></span>
                        <span class="ttr-label">Candidate List</span>
                    </a>
                </li>

                <!-- Account List -->
                <li>
                    <a href="${pageContext.request.contextPath}/accountlist" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-lock"></i></span>
                        <span class="ttr-label">Account List</span>
                    </a>
                </li>

                <!-- Employee List -->
                <li>
                    <a href="${pageContext.request.contextPath}/employeelistservlet" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-briefcase"></i></span>
                        <span class="ttr-label">Employee List</span>
                    </a>
                </li>

                <!-- Department List -->
                <li>
                    <a href="${pageContext.request.contextPath}/departmentlistservlet" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-home"></i></span>
                        <span class="ttr-label">Department List</span>
                    </a>
                </li>

                <!-- HR Recruitment -->
                <li>
                    <a href="${pageContext.request.contextPath}/hrrecruitment" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-user"></i></span>
                        <span class="ttr-label">HR Recruitment</span>
                    </a>
                </li>

                <!-- HRM Recruitment -->
                <li>
                    <a href="${pageContext.request.contextPath}/hrmanagerrecruitment" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-user"></i></span>
                        <span class="ttr-label">HRM Recruitment</span>
                    </a>
                </li>

                <!-- Raw Attendance -->
                <li>
                    <a href="${pageContext.request.contextPath}/raw-attendance" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-time"></i></span>
                        <span class="ttr-label">Raw Attendance</span>
                    </a>
                </li>

                <li class="ttr-seperate"></li>
            </ul>
        </nav>
        <!-- sidebar menu end -->
    </div>
</div>
