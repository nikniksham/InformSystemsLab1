<%@ page import="com.example.demo1.DBObjects.Users" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header>
    <nav>
        <a href="/demo1/mainPage">На гилавную</a>  |
        <%
            if (request.getAttribute("user") != null) {
                Users user = (Users) request.getAttribute("user");
                out.println("<a href=\"/demo1/personalInformation\"> Пользователь: " + user.getLogin() + "</a>");
            } else {
                out.println("<a href=\"/demo1/login\">Войти</a> / <a href=\"/demo1/register\">Зарегистрироваться</a>");
            }
        %>
    </nav>
<%--    <h1>Добро пожаловать на мой вебсайт</h1>--%>
</header>