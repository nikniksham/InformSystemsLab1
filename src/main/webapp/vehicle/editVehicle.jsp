<%@ page import="com.example.demo1.ENUMs.VehicleType" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo1.ENUMs.FuelType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit Vehicle</title>
  <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
  <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<div class="form-container">
  <h2 class="form-header">Изменение ТС</h2>
  <form method="post">
    <label>Координата X (дробное число, через точку):
      <input type="text" name="x_coords" pattern="-?\d*\.?\d*" value='<%=((request.getAttribute("x_coords") == null) ? "" : request.getAttribute("x_coords"))%>' required/><br/>
    </label>
    <label>Координата Y (целое число):
      <input type="text" name="y_coords" pattern="-?\d+" value='<%=((request.getAttribute("y_coords") == null) ? "" : request.getAttribute("y_coords"))%>' required/><br/>
    </label>

    <label>Название:
      <input type="text" name="name" minlength="1" value='<%=((request.getAttribute("name") == null) ? "" : request.getAttribute("name"))%>' required/><br/>
    </label>

    <label>Тип вехикла:
      <select name="vehicleType">
        <%
          VehicleType selecVeh = VehicleType.values()[request.getAttribute("vehicleType") == null ? 0 : (Integer) request.getAttribute("vehicleType")];
          for (VehicleType vehicleType : (VehicleType[]) request.getAttribute("listVehicleTypes")) {
            if (vehicleType == selecVeh) {
              out.println("<option value=" + vehicleType.getId() + " selected>" + vehicleType.getTitle() + "</option>");
            } else {
              out.println("<option value=" + vehicleType.getId() + ">" + vehicleType.getTitle() + "</option>");
            }
          }
        %>
      </select>
    </label><br>

    <label>Мощность двигателя (дробное число >0):
      <input type="text" name="enginePower" pattern="([1-9]{1}\d*\.?\d*|(\d*\.?[1-9]{1}\d*))" value='<%=((request.getAttribute("enginePower") == null) ? "" : request.getAttribute("enginePower"))%>' required/><br/>
    </label>

    <label>Количество колёс (целое число >0):
      <input type="text" name="numberOfWheels" pattern="[1-9]{1}\d*" value='<%=((request.getAttribute("numberOfWheels") == null) ? "" : request.getAttribute("numberOfWheels"))%>' required/><br/>
    </label>

    <label>Вместимость (дробное число >0):
      <input type="text" name="capacity" pattern="([1-9]{1}\d*\.?\d*|(\d*\.?[1-9]{1}\d*))" value='<%=((request.getAttribute("capacity") == null) ? "" : request.getAttribute("capacity"))%>' required/><br/>
    </label>

    <label>Пробег (целое число >0):
      <input type="text" name="distanceTravelled" pattern="[1-9]{1}\d*" value='<%=((request.getAttribute("distanceTravelled") == null) ? "" : request.getAttribute("distanceTravelled"))%>' required/><br/>
    </label>

    <label>Расход топлива (целое число >0):
      <input type="text" name="fuelConsumption" pattern="[1-9]{1}\d*" value='<%=((request.getAttribute("fuelConsumption") == null) ? "" : request.getAttribute("fuelConsumption"))%>' required/><br/>
    </label>

    <label>Тип топлива:
      <select name="fuelType">
        <%
          FuelType selecFuel = FuelType.values()[request.getAttribute("vehicleType") == null ? 0 : (Integer) request.getAttribute("fuelType")];
          for (FuelType fuelType : (FuelType[]) request.getAttribute("listFuelTypes")) {
            if (fuelType == selecFuel) {
              out.println("<option value=" + fuelType.getId() + " selected>" + fuelType.getTitle() + "</option>");
            } else {
              out.println("<option value=" + fuelType.getId() + ">" + fuelType.getTitle() + "</option>");
            }
          }
        %>
      </select>
    </label>
    <label>Разрешаю админам редактировать
      <%
        if (request.getAttribute("commonAccess") != null && request.getAttribute("commonAccess").toString().equals("true")) {
          out.println("<input type=\"checkbox\" name=\"commonAccess\" value=\"true\" checked>");
        } else {
          out.println("<input type=\"checkbox\" name=\"commonAccess\" value=\"true\">");
        }
      %>
    </label><br>
    <button type="submit">Изменить вехикл</button>
  </form>
  <%
    out.println((request.getAttribute("error") == null) ? "" : "<res>" + request.getAttribute("error") + "</res>");
  %>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
