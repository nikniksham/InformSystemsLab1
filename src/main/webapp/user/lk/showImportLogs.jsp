<%@ page import="com.example.demo1.DBObjects.Users" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo1.DBObjects.ImportLogs" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Логи импортов</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
    <script src="/demo1/resources/js/sortScript.js" type="text/javascript"></script>
    <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Логи импортов</h1>
<div class="container">
    <div class="table">
        <table border="1" id="data-table">
            <thead>
            <tr>
                <th onclick="sortTable(0, 1)" class="noselect">Id</th>
                <th onclick="sortTable(1, 1)" class="noselect">user_id</th>
                <th onclick="sortTable(2, 0)" class="noselect">filename</th>
                <th onclick="sortTable(3, 0)" class="noselect">result</th>
                <th onclick="sortTable(4, 2)" class="noselect">executionDate</th>
                <th onclick="sortTable(5, 1)" class="noselect">count</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (request.getAttribute("ImportLogs") != null) {
                    for (ImportLogs log : (List<ImportLogs>) request.getAttribute("ImportLogs")) {
                        out.println("<tr>");
                        out.println("<td>" + log.getId() + "</td>");
                        out.println("<td>" + log.getUser_id() + "</td>");
                        out.println("<td>" + "<a href=\"/demo1/downloadFile?filename=" + log.getFilename() + "\">" + log.getFilename() + "</a>" + "</td>");
                        out.println("<td>" + log.isResult() + "</td>");
                        out.println("<td>" + log.getCreationDate() + "</td>");
                        out.println("<td>" + log.getCount() + "</td>");
                        out.println("</tr>");
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
