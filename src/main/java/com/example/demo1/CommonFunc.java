package com.example.demo1;

import com.example.demo1.Managers.TokenManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@ApplicationScoped
public class CommonFunc {
    @Inject
    private TokenManager tokenManager;
    private String baza = "/demo1";

    public String getLink(String direct) {
        return baza + direct;
    }

    public void checkAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean res = tokenManager.userConnectionValid(request.getCookies());
        if (res) {
            response.sendRedirect(baza);
        }
    }

    public void checkAndRedirectFalse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean res = tokenManager.userConnectionValid(request.getCookies());
        if (!res) {
            response.sendRedirect(baza);
        }
    }
}
