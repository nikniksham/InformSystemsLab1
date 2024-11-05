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
        Long last_id = null, oper=null;
        try {
            last_id = Long.parseLong(request.getParameter("last_id"));
        } catch (Exception ex) {}

        try {
            oper = Long.parseLong(request.getParameter("oper"));
        } catch (Exception ex) {}

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("mainPage.jsp");
        List<Vehicle> vehicleList = vehicleManager.getPaginVehicle(last_id, oper, null);
        HashMap<Long, Boolean> resultList = vehicleManager.getUserRights(vehicleList, commonFunc.getAuthorizedUser(request, response));
        request.setAttribute("vehicleList", vehicleList);
        request.setAttribute("resultList", resultList);
        if (vehicleList != null && vehicleList.size() > 0) {
            request.setAttribute("have_greater", vehicleManager.haveElemGreaterOrLower(vehicleList.get(vehicleList.size()-1).getId(), true));
            request.setAttribute("have_lower", vehicleManager.haveElemGreaterOrLower(vehicleList.get(0).getId(), false));
        }
        request.setAttribute("averageFuelConsumption", vehicleManager.calcAverageFuelConsumption());
        requestDispatcher.forward(request, response);
    }
}

