package com.example.demo1.Servlets.Auth;

import com.example.demo1.Managers.TokenManager;
import com.example.demo1.CommonFunc;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "logoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Inject
    TokenManager tokenManager;
    @Inject
    CommonFunc commonFunc;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setUserIfAuthorized(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("auth/logout.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setUserIfAuthorized(request, response);
        tokenManager.deleteTokenByCode(tokenManager.getAuthCode(request.getCookies()));
        doGet(request, response);
    }
}
