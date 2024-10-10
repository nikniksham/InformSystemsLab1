package com.example.demo1;

import com.example.demo1.DBObjects.Users;
import com.example.demo1.Managers.TokenManager;
import com.example.demo1.Managers.UsersManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@ApplicationScoped
public class CommonFunc {
    @Inject
    private TokenManager tokenManager;
    @Inject
    private UsersManager usersManager;
    private String baza = "/demo1";

    public String getLink(String direct) {
        return baza + direct;
    }

    public Users getAuthorizedUser(HttpServletRequest request, HttpServletResponse response) {
        return usersManager.getUserById(tokenManager.getUserId(request.getCookies()));
    }

    public void setAuthorizedUser(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("user", getAuthorizedUser(request, response));
    }

    // Redirect if user is authorized
    public void redirectIfAuthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean res = tokenManager.userConnectionValid(request.getCookies());
        if (res) {
            response.sendRedirect(baza);
        }
    }

    // Redirect if user is NOT authorized
    public void redirectIfNotAuthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean res = tokenManager.userConnectionValid(request.getCookies());
        if (!res) {
            response.sendRedirect(baza);
        }
    }
}
