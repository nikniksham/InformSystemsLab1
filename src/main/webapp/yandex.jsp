<%@ page import="java.util.HashMap" %>
<%@ page import="com.example.demo1.DBObjects.Vehicle" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo1.ENUMs.VehicleType" %>
<%@ page import="com.example.demo1.ENUMs.FuelType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поисковик</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
    <link rel="stylesheet" href="/demo1/resources/css/beautifulForms.css" type="text/css">
    <script src="/demo1/resources/js/sortScript.js" type="text/javascript"></script>
    <script src="/demo1/resources/js/callbackFunc.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<div class="form-container">
    <h2 class="form-header">Поисковик</h2>
    <form method="post">
        <label>Подстрока в названии:
            <input type="text" name="sample"
                   value='<%=((request.getAttribute("sample") == null) ? "" : request.getAttribute("sample"))%>'/><br/>
        </label>
        <label>Начало названия
            <%
                if (request.getAttribute("is_start") != null && request.getAttribute("is_start").equals("true")) {
                    out.println("<input type=\"checkbox\" name=\"is_start\" value=\"true\" checked>");
                } else {
                    out.println("<input type=\"checkbox\" name=\"is_start\" value=\"true\">");
                }
            %>
        </label>
        <label>Мощность от (если поле пустое, то ограничения снизу нет):
            <input type="text" name="power_min"
                   value='<%=((request.getAttribute("power_min") == null) ? "" : request.getAttribute("power_min"))%>'/><br/>
        </label>
        <label>Мощность до (если поле пустое, то ограничения сверху нет):
            <input type="text" name="power_max"
                   value='<%=((request.getAttribute("power_max") == null) ? "" : request.getAttribute("power_max"))%>'/><br/>
        </label>
        <button type="submit">Найти</button>
    </form>
    <%
        out.println((request.getAttribute("error") == null) ? "" : request.getAttribute("error"));
    %>
</div>

<div class="container">
    <div class="table">
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
                if (request.getAttribute("vehicleList") != null) { // resultList
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
    </div>
</div>
<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>