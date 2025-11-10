<%--
    Document   : dashboardNavbar
    Created on : Oct 2, 2025, 4:05:59 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Employee" %>
<%@ page import="dal.RolePermissionDAO" %>
<%@ page import="model.Role" %>

<%
    Employee user = (Employee) session.getAttribute("user");
    RolePermissionDAO rpDAO = new RolePermissionDAO();
    int roleId = (user != null && user.getRole() != null) ? user.getRole().getRoleId() : -1;
%>

<div class="ttr-sidebar">
    <div class="ttr-sidebar-wrapper content-scroll">
       
        <%-- (ĐÃ LOẠI BỎ CÁC THẺ BỊ LẶP ttr-sidebar và ttr-sidebar-wrapper) --%>
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
                        <span class="ttr-icon"><i class="ti-user"></i></span>
                        <span class="ttr-label">Profile</span>
                    </a>
                </li>

                <% if (rpDAO.hasPermission(roleId, 7)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/Views/listapplication.jsp" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-clipboard"></i></span>
                        <span class="ttr-label">Application</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/application?typeapplication=leave" class="ttr-material-button"><span class="ttr-label">Leave Request</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/application?typeapplication=ot" class="ttr-material-button"><span class="ttr-label">Overtime Request</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/compose?type=LEAVE" class="ttr-material-button"><span class="ttr-label">Create Leave Application</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/compose?type=OT" class="ttr-material-button"><span class="ttr-label">Create Overtime Application</span></a></li>
                    </ul>
                </li>
                <% } %>

                <% if (rpDAO.hasPermission(roleId, 2)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/candidatelist" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-id-badge"></i></span>
                        <span class="ttr-label">Candidate List</span>
                    </a>
                </li>
                <% } %>

                <% if (rpDAO.hasPermission(roleId, 6)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/interview" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-agenda"></i></span>
                        <span class="ttr-label">Interview List</span>
                    </a>
                </li>
                <% } %>

                <% if (rpDAO.hasPermission(roleId, 1)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/accountlist" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-key"></i></span>
                        <span class="ttr-label">Account List</span>
                    </a>
                </li>
                <% } %>

                <% if (rpDAO.hasPermission(roleId, 4)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/employeelist" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-briefcase"></i></span>
                        <span class="ttr-label">Employee List</span>
                    </a>
                </li>
                <% } %>

                <% if (rpDAO.hasPermission(roleId, 3)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/departmentlist" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-home"></i></span>
                        <span class="ttr-label">Department List</span>
                    </a>
                </li>
                <% } %>

                <% if (rpDAO.hasPermission(roleId, 10)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/hrrecruitment" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-write"></i></span>
                        <span class="ttr-label">HR Recruitment</span>
                    </a>
                </li>
                <% } %>

                <% if (rpDAO.hasPermission(roleId, 11)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/postreview" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-check-box"></i></span>
                        <span class="ttr-label">Post Review</span>
                    </a>
                </li>
                <% } %>

                <% if (rpDAO.hasPermission(roleId, 11)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/managepost" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-pencil-alt"></i></span>
                        <span class="ttr-label">Manage Post</span>
                    </a>
                </li>
                <% } %>
                <% if (rpDAO.hasPermission(roleId, 5)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/scheduleinterview" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-alarm-clock"></i></span>
                        <span class="ttr-label">Schedule Interview</span>
                    </a>
                </li>
                <% } %>
                <% if (roleId == 1) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/decentralization" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-shield"></i></span>
                        <span class="ttr-label">Decentralization</span>
                    </a>
                </li>
                <% } %>


                <% if (rpDAO.hasPermission(roleId, 12)) { %>

                <li>
                    <a href="${pageContext.request.contextPath}/raw-attendance" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-timer"></i></span>
                        <span class="ttr-label">Raw Attendance</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/daily-attendance" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-calendar"></i></span>
                        <span class="ttr-label">Daily Attendance</span>
                    </a>
                </li>
                <% } %>

                <li>
                    <a href="${pageContext.request.contextPath}/payrollreportdetail" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-money"></i></span>
                        <span class="ttr-label">Payroll Report</span>
                    </a>
                </li>
                
                <li>
                    <a href="${pageContext.request.contextPath}/attendance" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-check-box"></i></span>
                        <span class="ttr-label">Personal Attendance</span>
                    </a>
                </li>
                <% if (rpDAO.hasPermission(roleId, 9)) { %>

                <li>
                    <a href="${pageContext.request.contextPath}/systemlog" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-server"></i></span>
                        <span class="ttr-label">System Log</span>
                    </a>
                </li>
                <% } %>
                
                <% if (rpDAO.hasPermission(roleId, 8)) { %>
                <li>
                    <a href="${pageContext.request.contextPath}/Views/listapplication.jsp" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-ruler-pencil"></i></span>
                        <span class="ttr-label">Application Management</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/applicationmanagement?typeapplication=leave" class="ttr-material-button"><span class="ttr-label">Leave Approval</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/applicationmanagement?typeapplication=ot" class="ttr-material-button"><span class="ttr-label">Overtime Approval</span></a></li>
                    </ul>
                </li>
                <% } %>
                <% if (rpDAO.hasPermission(roleId, 13)) { %>
                 <a href="${pageContext.request.contextPath}/monthly-payroll" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-server"></i></span>
                        <span class="ttr-label">Payroll Management</span>
                    </a>
                <% } %>
                <li class="ttr-seperate"></li>
            </ul>
        </nav>
    </div>
</div>
