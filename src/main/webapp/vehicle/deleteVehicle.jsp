<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit Vehicle</title>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<form method="post">
  <%
    if (request.getAttribute("error") == null) {
      out.println("<h3> Точно удаляем вехикл " + request.getAttribute("name") + "?</h3>");
    }
  %>
  <button type="submit">Удалить вехикл</button>
</form>
<%
  out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
%>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
