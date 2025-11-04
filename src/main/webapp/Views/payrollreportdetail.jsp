<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
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
        <title>Monthly Salary</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

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
            .salary-container {
                background: #fff;
                border-radius: 12px;
                padding: 30px;
                box-shadow: 0 3px 12px rgba(0,0,0,0.08);
                margin: 30px auto;
                max-width: 1000px;
            }
            .salary-header {
                text-align: center;
                border-bottom: 2px solid #eee;
                padding-bottom: 10px;
                margin-bottom: 25px;
            }
            .salary-header h3 {
                font-weight: 700;
                color: #333;
                margin-bottom: 5px;
            }
            .salary-info {
                text-align: center;
                margin-bottom: 20px;
                color: #555;
            }
            table.salary-table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 30px;
            }
            .salary-table th, .salary-table td {
                border: 1px solid #ccc;
                padding: 10px 12px;
                text-align: center;
                vertical-align: middle;
            }
            .salary-table th {
                background: #f4f7fc;
                color: #333;
                font-weight: 600;
            }
            .salary-section-title {
                font-weight: 600;
                margin-bottom: 10px;
                color: #2e7d32;
                font-size: 18px;
            }
            .highlight {
                background: #f9fff2;
                font-weight: bold;
                color: #2b7a0b;
            }
            .net-box {
                background: #f0faf0;
                border: 2px solid #c9e9c9;
                padding: 15px;
                border-radius: 10px;
                text-align: center;
                font-size: 18px;
                font-weight: 700;
                color: #1a611a;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <%@ include file="CommonItems/Header/dashboardHeader.jsp" %>
        <%@ include file="CommonItems/Navbar/empNavbar.jsp" %>
        <%
            model.Payroll payroll = (model.Payroll) request.getAttribute("payroll");
            model.Salary salary = (model.Salary) request.getAttribute("salary");
        %>
        <main class="ttr-wrapper">
            <div class="filter-row mb-3">
                <form action="${pageContext.request.contextPath}/payrollreportdetail" method="get"
                      class="d-flex align-items-center flex-nowrap w-100" style="gap:12px;">
                    <select name="month" class="form-control filter-h" style="width:160px;" onchange="this.form.submit()">
                        <option value="<%= java.time.LocalDate.now().getMonthValue() %>" selected>Now</option>
                        <c:forEach var="i" begin="1" end="12">
                            <option value="${i}">${i}</option>
                        </c:forEach>
                    </select>

                    <select name="year" class="form-control filter-h" style="width:170px;" onchange="this.form.submit()">
                        <option value="<%= java.time.Year.now().getValue() %>" selected>Now</option>
                        <c:forEach var="i" begin="2020" end="<%= java.time.Year.now().getValue() %>">
                            <option value="${i}">${i}</option>
                        </c:forEach>
                    </select>

                    <a href="" class="btn btn-warning filter-h">Now</a>
                </form>
            </div>
            <div class="salary-container">
                <div class="salary-header">
                    <h3>${payroll.month} - ${payroll.year}</h3>
                    <div class="salary-info">
                        <p><strong>Employee:</strong> ${sessionScope.user.fullname} &nbsp; | &nbsp;
                            <strong>Department:</strong> ${sessionScope.user.dept.depName} &nbsp; | &nbsp;
                            <strong>Base Salary:</strong> <%= String.format("%,.0f", salary.getBaseSalary()) %></p>
                    </div>
                </div>

                <div class="salary-section">
                    <div class="salary-section-title"><i class="fa fa-money"></i> Gross Earnings</div>
                    <table class="salary-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Description</th>
                                <th>Amount (VND)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>Regular Salary</td>
                                <td><%= String.format("%,.0f", payroll.getRegularSalary()) %></td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>OT Earning</td>
                                <td><%= String.format("%,.0f", payroll.getOtEarning()) %></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Allowance</td>
                                <td><%= String.format("%,.0f", salary.getAllowance()) %></td>
                            </tr>
                            <tr class="highlight">
                                <td>4</td>
                                <td colspan="1"><strong>Total Gross</strong></td>
                                <td><strong><%= String.format("%,.0f", payroll.getRegularSalary()+payroll.getOtEarning()+salary.getAllowance()) %></strong></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="salary-section">
                    <div class="salary-section-title"><i class="fa fa-scissors"></i> Mandatory Deductions</div>
                    <table class="salary-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Description</th>
                                <th>Amount (VND)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>(5)</td>
                                <td>Insurance Base</td>
                                <td><%= String.format("%,.0f", payroll.getInsuranceBase()) %></td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td>SI</td>
                                <td><%= String.format("%,.0f", payroll.getSi()) %></td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td>HI</td>
                                <td><%= String.format("%,.0f", payroll.getHi()) %></td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td>UI</td>
                                <td><%= String.format("%,.0f", payroll.getUi()) %></td>
                            </tr>
                            <tr>
                                <td>9</td>
                                <td>Taxable Income</td>
                                <td><%= String.format("%,.0f", payroll.getTaxIncome()) %></td>
                            </tr>
                            <tr>
                                <td>10</td>
                                <td>Tax</td>
                                <td><%= String.format("%,.0f", payroll.getTax()) %></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Net Salary -->
                <div class="salary-section">
                    <div class="salary-section-title"><i class="fa fa-credit-card"></i>Net Salary</div>
                    <div class="net-box">
                        <br>👉 <strong><%= String.format("%,.0f", payroll.getRegularSalary()+payroll.getOtEarning()+salary.getAllowance()-payroll.getSi()-payroll.getHi()-payroll.getUi()-payroll.getTax()) %></strong>
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
        <script src="${pageContext.request.contextPath}/assets2/vendors/switcher/switcher.js"></script>
    </body>
</html>
