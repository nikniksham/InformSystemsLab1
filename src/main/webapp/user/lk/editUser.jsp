<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактирование</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
    <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<div class="form-container">
    <h2 class="form-header">Изменение информации об аккаунте</h2>
    <form method="post">
        <label>Логин:
            <input type="text" name="new_login" value='<%=((request.getParameter("login") == null) ? "" : request.getParameter("login"))%>' /><br/>
        </label>
        <button type="submit">подтвердить</button>
    </form>
    <form action="/demo1/login">
        <input type="submit" value="Залогиниться"/>
    </form>
    <%
        out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
    %>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>