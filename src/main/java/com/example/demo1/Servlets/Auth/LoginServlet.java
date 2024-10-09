package com.example.demo1.Servlets.Auth;

import com.example.demo1.Managers.TokenManager;
import com.example.demo1.Managers.UsersManager;
import com.example.demo1.CommonFunc;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Inject
    UsersManager usersManager;
    @Inject
    TokenManager tokenManager;
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfAuthorized(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("auth/login.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfAuthorized(request, response);
        Long result = usersManager.loginUser(request.getParameter("login"), request.getParameter("password"));
        if (result != null) {
            String new_code = tokenManager.createNewAccessToken(result);
            Cookie code_cookie = new Cookie("avtoritet", new_code);
            response.addCookie(code_cookie);
            response.sendRedirect(commonFunc.getLink("/"));
        }
        request.setAttribute("error", "Invalid login or password");
        doGet(request, response);
    }
}
