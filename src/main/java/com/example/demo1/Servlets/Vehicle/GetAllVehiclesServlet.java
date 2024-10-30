package com.example.demo1.Servlets.Vehicle;

import com.example.demo1.DBObjects.Vehicle;
import com.example.demo1.Managers.CoordinatesManager;
import com.example.demo1.Managers.InformationManager;
import com.example.demo1.Managers.VehicleManager;
import com.google.gson.Gson;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "getAllVehiclesServlet", value = "/getAllVehicles")
public class GetAllVehiclesServlet extends HttpServlet {
    @Inject
    VehicleManager vehicleManager;
    @Inject
    CoordinatesManager coordinatesManager;
    @Inject
    InformationManager informationManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        List<Vehicle> vehicleList = vehicleManager.getAllVehicle();
        out.print("{\"Vehicles\":");
        out.print(gson.toJson(vehicleList)+",");
        out.print("\"Coordinates\":");
        out.print(gson.toJson(coordinatesManager.getAllCoordinates())+",");
        out.print("\"Authors\":[");
        for (int i = 0; i < vehicleList.size(); ++i) {
            Vehicle vehicle = vehicleList.get(i);
            if (i > 0) {
                out.print(",");
            }
            out.print("{\"vehicle_id\":"+vehicle.getId()+",\"author_id\":"+informationManager.getAuthor(vehicle.getId())+"}");
        }
        out.println("]}");
        out.close();
    }
}
