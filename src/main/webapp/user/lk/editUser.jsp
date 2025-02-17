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
            <input type="text" name="new_login" value='<%=((request.getAttribute("login") == null) ? "" : request.getAttribute("login"))%>' /><br/>
        </label>
        <button type="submit">подтвердить</button>
    </form>
    <%
        out.println((request.getAttribute("error") == null) ? "" : "<res>" + request.getAttribute("error") + "</res>");
    %>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>