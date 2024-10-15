package com.example.demo1.Servlets;

import com.example.demo1.CommonFunc;
import com.example.demo1.DBObjects.Vehicle;
import com.example.demo1.Managers.VehicleManager;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "mainPageServlet", value = "/mainPage")
public class MainPageServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;
    @Inject
    VehicleManager vehicleManager;


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.setAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("mainPage.jsp");
        List<Vehicle> vehicleList = vehicleManager.getAllVehicle();
        HashMap<Long, Boolean> resultList = vehicleManager.getUserRights(vehicleList, commonFunc.getAuthorizedUser(request, response));
        request.setAttribute("vehicleList", vehicleList);
        request.setAttribute("resultList", resultList);
        requestDispatcher.forward(request, response);
    }
}

