package com.example.demo1.Servlets;

import java.io.*;

import com.example.demo1.Managers.UsersManager;
import jakarta.inject.Inject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "testServlet", value = "/test")
public class TestServlet extends HttpServlet {
    @Inject
    UsersManager usersManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + usersManager.loginUser("Lolita", "qwerty") + "</h1>");
        out.println("</body></html>");
    }
}