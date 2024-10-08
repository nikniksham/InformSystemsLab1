<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="hello-servlet">Hello Servlet</a><br>
<a href="test">Test</a><br>
<a href="login">Login</a><br>
<a href="register">Register</a><br>
<a href="logout">Logout</a><br>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>