<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Смена пароля</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
    <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<div class="form-container">
    <h2 class="form-header">Изменение пароля</h2>
    <form method="post">
        <label>Введите старый пароль:
            <input type="password" name="old_password"/><br/>
        </label>
        <label>Введите новый пароль:
            <input type="password" name="new_password1"><br/>
        </label>
        <label>Введите новый пароль:
            <input type="password" name="new_password2"><br/>
        </label>
        <button type="submit">Подтвердить смену пароля</button>
    </form>
    <%
        out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
    %>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>