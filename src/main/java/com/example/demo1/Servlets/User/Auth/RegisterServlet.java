package com.example.demo1.Servlets.User.Auth;

import com.example.demo1.Managers.UsersManager;
import com.example.demo1.CommonFunc;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Inject
    UsersManager usersManager;
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/auth/register.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        String error = null;
        if (usersManager.checkLoginDontExists(request.getParameter("login"))) {
            error = "Login already exists";
        }
        if (!request.getParameter("password1").equals(request.getParameter("password2"))) {
            error = "Passwords don't match";
        }
        if (error == null) {
            if (usersManager.addUser(request.getParameter("login"), request.getParameter("password1"))) {
                response.sendRedirect(commonFunc.getLink("/login"));
            }
            error = "Some internal error";
        }
        request.setAttribute("error", error);
        doGet(request, response);
    }
}
