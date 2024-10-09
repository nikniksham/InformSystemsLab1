<%@ page import="com.example.demo1.ENUMs.VehicleType" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo1.ENUMs.FuelType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Vehicle</title>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<form method="post">
    <label>Координата X (дробное число, через точку):
        <input type="text" name="x_coords" pattern="-?\d*\.?\d*" value='<%=((request.getParameter("x_coords") == null) ? "" : request.getParameter("x_coords"))%>' required/><br/>
    </label>
    <label>Координата Y (целое число):
        <input type="text" name="y_coords" pattern="-?\d+" value='<%=((request.getParameter("y_coords") == null) ? "" : request.getParameter("y_coords"))%>' required/><br/>
    </label>

    <label>Название:
        <input type="text" name="name" minlength="1" value='<%=((request.getParameter("name") == null) ? "" : request.getParameter("name"))%>' required/><br/>
    </label>

    <label>Тип вехикла:
        <select name="vehicleType">
            <%
                VehicleType selecVeh = VehicleType.values()[request.getParameter("vehicleType") == null ? 0 : Integer.parseInt(request.getParameter("vehicleType"))];
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
        <input type="text" name="enginePower" pattern="([1-9]{1}\d*\.?\d*|(\d*\.?[1-9]{1}\d*))" value='<%=((request.getParameter("enginePower") == null) ? "" : request.getParameter("enginePower"))%>' required/><br/>
    </label>

    <label>Количество колёс (целое число >0):
        <input type="text" name="numberOfWheels" pattern="[1-9]{1}\d*" value='<%=((request.getParameter("numberOfWheels") == null) ? "" : request.getParameter("numberOfWheels"))%>' required/><br/>
    </label>

    <label>Вместимость (дробное число >0):
        <input type="text" name="capacity" pattern="([1-9]{1}\d*\.?\d*|(\d*\.?[1-9]{1}\d*))" value='<%=((request.getParameter("capacity") == null) ? "" : request.getParameter("capacity"))%>' required/><br/>
    </label>

    <label>Пробег (целое число >0):
        <input type="text" name="distanceTravelled" pattern="[1-9]{1}\d*" value='<%=((request.getParameter("distanceTravelled") == null) ? "" : request.getParameter("distanceTravelled"))%>' required/><br/>
    </label>

    <label>Расход топлива (целое число >0):
        <input type="text" name="fuelConsumption" pattern="[1-9]{1}\d*" value='<%=((request.getParameter("fuelConsumption") == null) ? "" : request.getParameter("fuelConsumption"))%>' required/><br/>
    </label>

    <label>Тип топлива:
        <select name="fuelType">
            <%
                FuelType selecFuel = FuelType.values()[request.getParameter("vehicleType") == null ? 0 : Integer.parseInt(request.getParameter("fuelType"))];
                for (FuelType fuelType : (FuelType[]) request.getAttribute("listFuelTypes")) {
                    if (fuelType == selecFuel) {
                        out.println("<option value=" + fuelType.getId() + " selected>" + fuelType.getTitle() + "</option>");
                    } else {
                        out.println("<option value=" + fuelType.getId() + ">" + fuelType.getTitle() + "</option>");
                    }
                }
            %>
        </select>
    </label><br>
    <button type="submit">Создать вехикл</button>
</form>
<%
    out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
%>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
