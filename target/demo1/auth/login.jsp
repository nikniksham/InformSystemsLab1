<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JAKARTA FOR MASOCHISTS</title>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<form method="post">
  <label>Login:
    <input type="text" name="login" value='<%=((request.getParameter("login") == null) ? "" : request.getParameter("login"))%>' /><br/>
  </label>
  <label>Password:
    <input type="text" name="password"><br/>
  </label>
  <button type="submit">Тык</button>
</form>
<form action="/demo1/register">
  <input type="submit" value="Зарегистрироваться"/>
</form>
<%
  out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
%>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
