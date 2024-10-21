<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Error</title>
  <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Случилась ПРЕДУСМОТРЕННАЯ АВТОРОМ ошибка</h1>
<%
  out.println((request.getAttribute("error") == null) ? "" : ("<h3>" + request.getAttribute("error") + "</h3>"));
%>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
