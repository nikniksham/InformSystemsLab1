<%@ page import="com.example.demo1.DBObjects.Users" %>
<%@ page import="com.example.demo1.ENUMs.UserStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>JAKARTA FOR MASOCHISTS</title>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/><%
  Users user = (Users) request.getAttribute("user");
  out.println("<h3>Личный кабинет пользователя: " + user.getLogin() + "</h3>");
  out.println("<p>Каста пользователя на сайте: " + UserStatus.values()[user.getStatus()].getTitle() + "</p>");
%>
<form action="/demo1/logout">
  <input type="submit" value="Разлогиниться"/>
</form>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
