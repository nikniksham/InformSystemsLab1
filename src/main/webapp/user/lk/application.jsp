<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Выдвижение</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
    <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<div class="form-container">
    <h2 class="form-header">Самовыдвинуться</h2>
    <form method="post">
        <button type="submit">Точно</button>
    </form>
    <%
        out.println((request.getAttribute("error") == null) ? "" : "<res>" + request.getAttribute("error") + "</res>");
    %>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
