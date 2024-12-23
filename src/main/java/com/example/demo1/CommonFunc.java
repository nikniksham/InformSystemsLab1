package com.example.demo1;

import com.example.demo1.DBObjects.Users;
import com.example.demo1.Managers.TokenManager;
import com.example.demo1.Managers.UsersManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Random;

@ApplicationScoped
public class CommonFunc {
    @Inject
    private TokenManager tokenManager;
    @Inject
    private UsersManager usersManager;
    private String baza = "/demo1";
    private final Random rng = new Random(72382);


    public String getLink(String direct) {
        return baza + direct;
    }

    public String generateName(int length)
    {
        String characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        char[] code = new char[length];
        for (int i = 0; i < length; i++) {
            code[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(code);
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
            response.sendRedirect(getLink("/logout"));
        }
    }

    // Redirect if user is NOT authorized
    public void redirectIfNotAuthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean res = tokenManager.userConnectionValid(request.getCookies());
        if (!res) {
            response.sendRedirect(getLink("/login"));
        }
    }

    public void redirectIfNotAuthorizedAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean res = tokenManager.userConnectionValid(request.getCookies());
        if (!res) {
            response.sendRedirect(getLink("/login"));
        }
        if (getAuthorizedUser(request, response).getStatus() != 2) {
            response.sendRedirect(getLink("/startPage"));
        }
    }
}
