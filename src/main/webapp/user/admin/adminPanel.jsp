<%@ page import="com.example.demo1.DBObjects.Users" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Панель бога</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
    <script src="/demo1/resources/js/sortScript.js" type="text/javascript"></script>
    <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Панель бога</h1>
<div class="container">
    <div class="table">
        <table border="1" id="data-table">
            <thead>
            <tr>
                <th onclick="sortTable(0, 1)" class="noselect">Id</th>
                <th onclick="sortTable(1, 0)" class="noselect">Логин</th>
                <th onclick="sortTable(2, 1)" class="noselect">Статус</th>
                <th class="noselect">Одобрить заявку</th>
                <th class="noselect">Удалить</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (request.getAttribute("usersList") != null) {
                    for (Users user : (List<Users>) request.getAttribute("usersList")) {
                        out.println("<tr>");
                        out.println("<td>" + user.getId() + "</td>");
                        out.println("<td>" + user.getLogin() + "</td>");
                        out.println("<td>" + user.getStatus() + "</td>");
                        out.println("<td>");
                        if (user.getStatus() == 1) {
                            out.println("<a class=\"button-link\" href=\"/demo1/approveApplication?user_id=" + user.getId() + "\">Одобрить</a>");
                        }
                        out.println("</td>");
                        out.println("<td>");
                        if (user.getStatus() < 2) {
                            out.println("<a class=\"button-link\" href=\"/demo1/deleteUser?user_id=" + user.getId() + "\">Удалить</a>");
                        }
                        out.println("</td>");

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
