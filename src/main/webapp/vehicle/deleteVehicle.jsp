<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Vehicle</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
    <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<div class="form-container">
    <h2 class="form-header">Удаление ТС</h2>
    <form method="post">
        <%
            if (request.getAttribute("error") == null) {
                out.println("<h3> Точно удаляем вехикл " + request.getAttribute("name") + "?</h3>");
            }
        %>
        <button type="submit">Удалить вехикл</button>
    </form>
    <%
        out.println((request.getAttribute("error") == null) ? "" : "<res>" + request.getAttribute("error") + "</res>");
    %>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
