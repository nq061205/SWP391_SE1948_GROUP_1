<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Role Permission Management</title>
        <style>
            body {
                font-family: "Segoe UI", sans-serif;
                background-color: #f6f8fa;
                padding: 30px;
            }
            h2 {
                color: #333;
                margin-bottom: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                text-align: center;
                background: white;
                box-shadow: 0 0 6px rgba(0,0,0,0.1);
            }
            th, td {
                border: 1px solid #ccc;
                padding: 10px;
            }
            th {
                background: #007bff;
                color: white;
                position: sticky;
                top: 0;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            .btn-save {
                background-color: #28a745;
                color: white;
                border: none;
                padding: 10px 25px;
                margin-top: 20px;
                border-radius: 5px;
                cursor: pointer;
            }
            .btn-save:hover {
                background-color: #218838;
            }
            .page-col {
                text-align: left;
                padding-left: 15px;
                font-weight: 600;
            }
        </style>
    </head>
    <body>

        <h2>Dynamic Role Permission</h2>

        <form action="${pageContext.request.contextPath}/decentralization" method="post">
            <table>
                <thead>
                    <tr>
                        <th>Page / Module</th>
                            <c:forEach items="${roles}" var="r">
                            <th>${r.roleName}</th>
                            </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${permissions}" var="p">
                        <tr>
                            <td class="page-col">${p.permissionName}</td>
                            <c:forEach items="${roles}" var="r">
                                <td>
                                    <c:set var="pair" value="${p.permissionId}-${r.roleId}" />
                                    <c:set var="isChecked" value="false" />

                                    <c:forEach items="${rolepermission}" var="rp">
                                        <c:if test="${rp eq pair}">
                                            <c:set var="isChecked" value="true" />
                                        </c:if>
                                    </c:forEach>

                                    <input type="checkbox" name="rolepermission"
                                           value="${pair}"
                                           <c:if test="${isChecked}">checked</c:if> />

                                    </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <button type="submit" class="btn-save">💾 Save Permissions</button>
        </form>

    </body>
</html>