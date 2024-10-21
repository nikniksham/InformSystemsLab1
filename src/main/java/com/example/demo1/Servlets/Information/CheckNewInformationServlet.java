package com.example.demo1.Servlets.Information;

import com.example.demo1.Managers.InformationManager;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "checkNewInformationServlet", value = "/checkNewInformation")
public class CheckNewInformationServlet extends HttpServlet {
    @Inject
    InformationManager informationManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            out.println(informationManager.checkNewLogs(Long.parseLong(request.getParameter("last_id"))));
        } catch (Exception e) {
            out.println(false);
        }
    }
}
