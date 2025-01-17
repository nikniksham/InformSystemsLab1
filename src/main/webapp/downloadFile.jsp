<%--
  Created by IntelliJ IDEA.
  User: Inherency
  Date: 18.12.2024
  Time: 0:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Скачивание файла</title>
  <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
  <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<form method="post">
  <input type="submit" value="Скачать файл" />
  <a class="button-link" href="/demo1/showImportLogs">Вернуться назад</a>
  <%
    out.println((request.getAttribute("error") == null) ? "" : "<res>" + request.getAttribute("error") + "</res>");
  %>
</form>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
