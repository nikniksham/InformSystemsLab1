<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактирование</title>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Изменение информации</h1>
<form method="post">
    <label>Логин:
        <input type="text" name="login" value='<%=((request.getParameter("login") == null) ? "" : request.getParameter("login"))%>' /><br/>
    </label>
    <button type="submit">подтвердить</button>
</form>
<form action="/demo1/login">
    <input type="submit" value="Залогиниться"/>
</form>
<%
    out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
%>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>