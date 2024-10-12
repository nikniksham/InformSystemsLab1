<%@ page import="com.example.demo1.DBObjects.Vehicle" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo1.ENUMs.FuelType" %>
<%@ page import="com.example.demo1.ENUMs.VehicleType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>MainPage</title>
  <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
  <script src="/demo1/resources/js/sortScript.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Главная страница</h1>
<table border="1" id="data-table">
  <thead>
    <tr>
      <th onclick="sortTable(0, 1)" class="noselect">Id</th>
      <th onclick="sortTable(1, 0)" class="noselect">Название</th>
      <th onclick="sortTable(2, 2)" class="noselect">Дата создания</th>
      <th onclick="sortTable(3, 0)" class="noselect">Тип транспорта</th>
      <th onclick="sortTable(4, 1)" class="noselect">Мощность двигателя</th>
      <th onclick="sortTable(5, 1)" class="noselect">Количество колёс</th>
      <th onclick="sortTable(6, 1)" class="noselect">Вместимость</th>
      <th onclick="sortTable(7, 1)" class="noselect">Пробег</th>
      <th onclick="sortTable(8, 1)" class="noselect">Расход топлива</th>
      <th onclick="sortTable(9, 0)" class="noselect">Тип товлива</th>
    </tr>
  </thead>
  <tbody>
    <%
      if (request.getAttribute("vehicleList") != null) {
        for (Vehicle vehicle : (List<Vehicle>) request.getAttribute("vehicleList")) {
          out.println("<tr>");
          out.println("<td>" + vehicle.getId() + "</td>");
          out.println("<td>" + vehicle.getName() + "</td>");
          out.println("<td>" + vehicle.getCreationDate() + "</td>");
          out.println("<td>" + VehicleType.values()[vehicle.getVehicleType_id()].getTitle() + "</td>");
          out.println("<td>" + vehicle.getEnginePower() + "</td>");
          out.println("<td>" + vehicle.getNumberOfWheels() + "</td>");
          out.println("<td>" + vehicle.getCapacity() + "</td>");
          out.println("<td>" + vehicle.getDistanceTravelled() + "</td>");
          out.println("<td>" + vehicle.getFuelConsumption() + "</td>");
          out.println("<td>" + FuelType.values()[vehicle.getFuelType_id()].getTitle() + "</td>");
          out.println("</tr>");
        }
      }
    %>
  </tbody>
</table>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
