<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JAKARTA FOR MASOCHISTS</title>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Регистрация</h1>
<form method="post">
    <label>Логин:
        <input type="text" name="login" value='<%=((request.getParameter("login") == null) ? "" : request.getParameter("login"))%>' /><br/>
    </label>
    <label>Пароль:
        <input type="text" name="password1"><br/>
    </label>
    <label>Пароль ещё раз:
        <input type="text" name="password2"><br/>
    </label>
    <button type="submit">Регистрация</button>
</form>
<form action="/demo1/login">
    <input type="submit" value="Войти"/>
</form>
<%
    out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
%>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>