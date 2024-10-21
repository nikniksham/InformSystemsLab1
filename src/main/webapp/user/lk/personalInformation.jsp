<%@ page import="com.example.demo1.DBObjects.Users" %>
<%@ page import="com.example.demo1.ENUMs.UserStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Личный кабинет</title>
  <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
  <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/><%
  Users user = (Users) request.getAttribute("user");
  out.println("<h2>Личный кабинет пользователя: " + user.getLogin() + "</h3>");
  out.println("<p>Каста пользователя на сайте: " + UserStatus.values()[user.getStatus()].getTitle() + "</p>");

  if (user.getStatus() == 0) {
    out.println("<form action=\"/demo1/application\">");
    out.println("<input type=\"submit\" value=\"Подать заявку на админство\"/>");
    out.println("</form>");
  }

  out.println("<form action=\"/demo1/editUser\">");
  out.println("<input type=\"submit\" value=\"Изменить информацию об аккаунте\"/>");
  out.println("</form>");

  out.println("<form action=\"/demo1/editPassword\">");
  out.println("<input type=\"submit\" value=\"Изменить пароль\"/>");
  out.println("</form>");

  if (user.getStatus() == 2) {
    out.println("<form action=\"/demo1/adminPanel\">");
    out.println("<input type=\"submit\" value=\"Админская панель\"/>");
    out.println("</form>");
  }
%>
<form action="/demo1/logout">
  <input type="submit" value="Разлогиниться"/>
</form>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
