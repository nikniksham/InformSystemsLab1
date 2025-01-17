<%@ page import="com.example.demo1.DBObjects.Vehicle" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo1.ENUMs.FuelType" %>
<%@ page import="com.example.demo1.ENUMs.VehicleType" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MainPage</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
    <script src="/demo1/resources/js/sortScript.js" type="text/javascript"></script>
    <script src="/demo1/resources/js/callbackFunc.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Главная страница</h1>
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
                <%
                    if (request.getAttribute("user") != null) {
                        out.println("<th class=\"noselect\">Добавить колёс</th>");
                    }
                    if (request.getAttribute("resultList") != null) {
                        out.println("<th class=\"noselect\">Редактировать вехикл</th>");
                        out.println("<th class=\"noselect\">Удалить вехикл</th>");
                    }
                %>
            </tr>
            </thead>
            <tbody>
            <%
                Long start_id = null;
                Long end_id = null;
                if (request.getAttribute("vehicleList") != null) { // resultList
                    List<Vehicle> vehicleList = (List<Vehicle>) request.getAttribute("vehicleList");
                    if (vehicleList.size() > 0) {
                        start_id = vehicleList.get(0).getId();
                        end_id = vehicleList.get(0).getId();
                    }
                    HashMap<Long, Boolean> resultList = null;
                    if (request.getAttribute("resultList") != null) {
                        resultList = (HashMap<Long, Boolean>) request.getAttribute("resultList");
                    }
                    for (Vehicle vehicle : vehicleList) {
                        end_id = vehicle.getId();
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

                        if (request.getAttribute("user") != null) {
                            out.println("<td><a class=\"button-link\" href=\"/demo1/addWheels?vehicle_id=" + vehicle.getId() + "\">Добавить колёс</a></td>");
                        }

                        if (resultList != null) {
                            if (resultList.get(vehicle.getId()) == Boolean.TRUE) {
                                out.println("<td><a class=\"button-link\" href=\"/demo1/editVehicle?vehicle_id=" + vehicle.getId() + "\">Редактировать</a></td>");
                                out.println("<td><a class=\"button-link\" href=\"/demo1/deleteVehicle?vehicle_id=" + vehicle.getId() + "\">Удалить</a></td>");
                            } else {
                                out.println("<td></td>");
                                out.println("<td></td>");
                            }
                        }

                        out.println("</tr>");
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<%
    Object have_greater = request.getAttribute("have_greater"), have_lower = request.getAttribute("have_lower");
//    out.println("<a>"+have_greater+"</a><br>");
//    out.println("<a>"+have_lower+"</a><br>");
    if (start_id != null && have_lower != null && (boolean) have_lower) {
        out.println("<a class=\"button-link\" href=\"/demo1/mainPage?last_id=" + start_id + "&oper=1\"> Страница назад </a>");
    } else {
        out.println("<a class=\"button-link-disable\"> Страница назад </a>");
    }
    if (end_id != null && have_greater != null && (boolean) have_greater) {
        out.println("<a class=\"button-link\" href=\"/demo1/mainPage?last_id="+end_id+"&oper=0\"> Страница вперёд </a>");
    } else {
        out.println("<a class=\"button-link-disable\"> Страница вперёд </a>");
    }
    out.println("<p>Среднее значение поля FuelConsumption:" + request.getAttribute("averageFuelConsumption") + "</p>");
    if (request.getAttribute("user") != null) {
        out.println("<a class=\"button-link\" href=\"/demo1/createVehicle\"> Создать вехикл </a><br>");
    }
%>
<a class="button-link" href="importFile">Загрузить файл с объектами</a><br>

<jsp:include page="/shablons/footer.jsp"/>
</body>
</html>
