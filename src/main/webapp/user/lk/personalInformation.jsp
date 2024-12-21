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
    out.println("<a class=\"button-link\" href=\"/demo1/application\">Подать заявку на админство</a><br>");
  }

  out.println("<a class=\"button-link\" href=\"/demo1/editUser\">Изменить информацию об аккаунте</a><br>");

  out.println("<a class=\"button-link\" href=\"/demo1/editPassword\">Изменить пароль</a><br>");

  out.println("<a class=\"button-link\" href=\"/demo1/showImportLogs\">Посмотреть логи загрузки</a><br>");

  if (user.getStatus() == 2) {
    out.println("<a class=\"button-link\" href=\"/demo1/adminPanel\">Админская панель</a><br>");
  }
%>
<a class="button-link" href="/demo1/logout">Разлогиниться</a>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
