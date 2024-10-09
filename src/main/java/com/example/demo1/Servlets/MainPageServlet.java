package com.example.demo1.Servlets;

import com.example.demo1.CommonFunc;
import com.example.demo1.DBObjects.Users;
import com.example.demo1.Managers.CoordinatesManager;
import com.example.demo1.Managers.VehicleManager;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "mainPageServlet", value = "/mainPage")
public class MainPageServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;
    @Inject
    VehicleManager vehicleManager;
    @Inject
    CoordinatesManager coordinatesManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Users user = commonFunc.getAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("mainPage.jsp");
        request.setAttribute("vehicleList", vehicleManager.getAllVehicle());
        requestDispatcher.forward(request, response);
    }
}

