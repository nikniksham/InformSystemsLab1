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

  if (user.getStatus() == 0) {
    out.println("<form action=\"/demo1/user/lk/application\">");
    out.println("<input type=\"submit\" value=\"Подать заявку на админство\"/>");
    out.println("</form>");
  }

  out.println("<form action=\"/demo1/user/lk/editUser\">");
  out.println("<input type=\"submit\" value=\"Изменить информацию об аккаунте\"/>");
  out.println("</form>");

  out.println("<form action=\"/demo1/user/lk/editPassword\">");
  out.println("<input type=\"submit\" value=\"Изменить пароль\"/>");
  out.println("</form>");

  if (user.getStatus() == 2) {
    out.println("<form action=\"/demo1/user/admin/adminPanel\">");
    out.println("<input type=\"submit\" value=\"Админская панель\"/>");
    out.println("</form>");
  }
%>
<form action="/demo1/user/auth/logout">
  <input type="submit" value="Разлогиниться"/>
</form>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
