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
</div>

<jsp:include page="/shablons/footer.jsp"/>
<script src="/demo1/resources/js/graphics.js" type="text/javascript"></script>
</body>
</html>