package com.example.demo1.Servlets;

import com.example.demo1.CommonFunc;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "errorPageServlet", value = "/errorPage")
public class ErrorPageServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.setAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
