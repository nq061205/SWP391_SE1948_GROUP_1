<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Monthly Salary</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/assets.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets2/css/dashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

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

        <main class="ttr-wrapper">
            <div class="salary-container">
                <div class="salary-header">
                    <h3>${payroll.month} - ${payroll.year}</h3>
                    <div class="salary-info">
                        <p><strong>Employee:</strong> ${sessionScope.user.fullname} &nbsp; | &nbsp;
                            <strong>Department:</strong> ${sessionScope.user.dept.depName} &nbsp; | &nbsp;
                            <strong>Base Salary:</strong> ${salary.baseSalary}</p>
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
                                <td>${payroll.regularSalary}</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>OT Earning</td>
                                <td>${otEarning}</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Allowance</td>
                                <td>${salary.allowance}</td>
                            </tr>
                            <tr class="highlight">
                                <td>4</td>
                                <td colspan="2"><strong>Total Gross</strong></td>
                                <td><strong>${payroll.regularSalary+otEarning+salary.allowance}</strong></td>
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
                                <td>${payroll.insuranceBase}</td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td>SI</td>
                                <td>${payroll.si}</td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td>HI</td>
                                <td>${payroll.hi}</td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td>UI</td>
                                <td>${payroll.ui}</td>
                            </tr>
                            <tr>
                                <td>9</td>
                                <td>Taxable Income</td>
                                <td>${payroll.taxIncome}</td>
                            </tr>
                            <tr>
                                <td>10</td>
                                <td>Tax</td>
                                <td>${payroll.tax}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Net Salary -->
                <div class="salary-section">
                    <div class="salary-section-title"><i class="fa fa-credit-card"></i>Net Salary</div>
                    <div class="net-box">
                        <br>👉 <strong>${payroll.regularSalary+otEarning+salary.allowance-payroll.si-payroll.hi-payroll.ui-payroll.tax}</strong>
                    </div>
                </div>
            </div>
        </main>

        <script src="${pageContext.request.contextPath}/assets2/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets2/vendors/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
