<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Добавка</title>
  <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
  <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<div class="form-container">
  <h2 class="form-header">Подкинуть колёса</h2>
  <form method="post">
    <label>Количество колёс (целое число >0):
      <input type="text" name="numberOfWheels" pattern="[1-9]{1}\d*" value='<%=((request.getAttribute("numberOfWheels") == null) ? "" : request.getAttribute("numberOfWheels"))%>' required/><br/>
    </label>
    <button type="submit">подтверждаю</button>
  </form>
  <%
    out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
  %>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
