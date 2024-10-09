<%@ page import="com.example.demo1.DBObjects.Vehicle" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.demo1.ENUMs.FuelType" %>
<%@ page import="com.example.demo1.ENUMs.VehicleType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>MainPage</title>
<%--  <link rel="stylesheet" href="css/baza.css">--%>
  <script src="js/sortScript.js"></script>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Главная страница</h1>
<table border="1" id="data-table">
  <thead>
    <tr>
      <th>Название |<a onclick="sortTable(0, false, true)">▲</a>|<a onclick="sortTable(0, false, false)">▼</a></th>
      <th>Дата создания |<a onclick="sortTable(1, false, true)">▲</a>|<a onclick="sortTable(1, false, false)">▼</a></th>
      <th>Тип транспорта |<a onclick="sortTable(2, false, true)">▲</a>|<a onclick="sortTable(2, false, false)">▼</a></th>
      <th>Мощность двигателя |<a onclick="sortTable(3, true, true)">▲</a>|<a onclick="sortTable(3, true, false)">▼</a></th>
      <th>Количество колёс |<a onclick="sortTable(4, true, true)">▲</a>|<a onclick="sortTable(4, true, false)">▼</a></th>
      <th>Вместимость |<a onclick="sortTable(5, true, true)">▲</a>|<a onclick="sortTable(5, true, false)">▼</a></th>
      <th>Пробег |<a onclick="sortTable(6, true, true)">▲</a>|<a onclick="sortTable(6, true, false)">▼</a></th>
      <th>Расход топлива |<a onclick="sortTable(7, true, true)">▲</a>|<a onclick="sortTable(7, true, false)">▼</a></th>
      <th>Тип товлива |<a onclick="sortTable(8, false, true)">▲</a>|<a onclick="sortTable(8, false, false)">▼</a></th>
    </tr>
  </thead>
  <tbody>
    <%
      if (request.getAttribute("vehicleList") != null) {
        for (Vehicle vehicle : (List<Vehicle>) request.getAttribute("vehicleList")) {
          out.println("<tr>");
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
