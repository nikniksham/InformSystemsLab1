package com.example.demo1.Servlets;

import java.io.*;

import com.example.demo1.Managers.UsersManager;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    @Inject
    private UsersManager usersManager;

    private String message;

    public void init() {
        message = "SOSI MOYU JOPU pidaras MAVEN HUESOS!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
//        out.println("<h1>" + this.usersManager.testAdd("Lolita") + "</h1>");
        out.println("</body></html>");
    }
    public void destroy() {
    }
}