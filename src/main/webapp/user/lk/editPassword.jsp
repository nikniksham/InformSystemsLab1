<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Смена пароля</title>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Изменение пароля</h1>
<form method="post">
    <label>Введите старый пароль:
        <input type="text" name="old_password"/><br/>
    </label>
    <label>Введите новый пароль:
        <input type="text" name="new_password1"><br/>
    </label>
    <label>Введите новый пароль:
        <input type="text" name="new_password2"><br/>
    </label>
    <button type="submit">Подтвердить смену пароля</button>
</form>
<%
    out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
%>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>