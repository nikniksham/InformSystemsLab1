<%@ page import="com.example.demo1.DBObjects.Users" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Графика</title>
    <link rel="stylesheet" href="/demo1/resources/css/baza.css" type="text/css">
</head>
<body>
<jsp:include page="/shablons/header.jsp"/>
<h1>Графическое представление вехиклов</h1>

<div class="canvasContainer">
    <canvas id="drawLine" width="1120" height="630"></canvas>
    <div class="info_about">
        <h3>Информация о выбранном объекте</h3>
        <p id="id"></p>
        <p id="name"></p>
        <p id="coords"></p>
        <p id="capacity"></p>
        <p id="creationDate"></p>
        <p id="enginePower"></p>
        <p id="numberOfWheels"></p>
        <p id="distanceTravelled"></p>
        <p id="fuelConsumption"></p>
        <p id="author_id"></p>

        <a class="button-link-disable" href="" id="editWheels">Изменить кол-во колёс</a>
        <a class="button-link-disable" href="" id="editVehicle">Изменить объект</a>
        <a class="button-link-disable" href="" id="deleteVehicle">Удалить объект</a>

    </div>
</div>
<%
    if (request.getAttribute("user") != null) {
        Users user = (Users) request.getAttribute("user");
        out.println("<p style=\"visibility: hidden;\" id=\"userId\">"+ user.getId()+"</p>");
        out.println("<p style=\"visibility: hidden;\" id=\"userStatus\">"+ user.getStatus()+"</p>");
    } else {
        out.println("<p style=\"visibility: hidden;\" id=\"userId\">-1</p>");
        out.println("<p style=\"visibility: hidden;\" id=\"userStatus\">-1</p>");
    }
%>
<jsp:include page="/shablons/footer.jsp"/>
<script src="/demo1/resources/js/graphics.js" type="text/javascript"></script>
</body>
</html>