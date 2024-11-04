package com.example.demo1.Servlets.Vehicle;

import com.example.demo1.CommonFunc;
import com.example.demo1.DBObjects.Users;
import com.example.demo1.ENUMs.FuelType;
import com.example.demo1.ENUMs.TypeOfOperation;
import com.example.demo1.ENUMs.VehicleType;
import com.example.demo1.Managers.CoordinatesManager;
import com.example.demo1.Managers.InformationManager;
import com.example.demo1.Managers.VehicleManager;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "createVehicleServlet", value = "/createVehicle")
public class CreateVehicleServlet extends HttpServlet {
    @Inject
    VehicleManager vehicleManager;
    @Inject
    CoordinatesManager coordinatesManager;
    @Inject
    InformationManager informationManager;
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/vehicle/createVehicle.jsp");
        request.setAttribute("listVehicleTypes", VehicleType.values());
        request.setAttribute("listFuelTypes", FuelType.values());
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        Users user = commonFunc.getAuthorizedUser(request, response);
        String error = null;
        double x_coords = 0, capacity = 0;
        float enginePower = 0;
        long distanceTravelled = 0, fuelConsumption = 0;
        int y_coords = 0, numberOfWheels = 0;
        boolean commonAccess = false;

        try {
            x_coords = Double.parseDouble(request.getParameter("x_coords"));
            if (Math.abs(x_coords) > 1E307) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "X должно быть дробным числом и не превосходить по модулю 1E+307";
        }

        try {
            y_coords = Integer.parseInt(request.getParameter("y_coords"));
        } catch (Exception e) {
            error = "Y должно быть целым числом И по модулю не превосходить 2147483647";
        }

        String name = request.getParameter("name");

        if (name.length() == 0) {
            error = "Длина имени должна быть хотя бы 1 символ";
        }

        VehicleType vehicleType = VehicleType.values()[Integer.parseInt(request.getParameter("vehicleType"))];

        try {
            enginePower = Float.parseFloat(request.getParameter("enginePower"));
            if (enginePower <= 0 || enginePower > 1E37) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Мощность двигателя должна быть дробным числом > 0 и < 1E+37";
        }

        try {
            numberOfWheels = Integer.parseInt(request.getParameter("numberOfWheels"));
            if (numberOfWheels <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Количество колёс должно быть целым числом > 0 И <= 2147483647";
        }

        try {
            capacity = Double.parseDouble(request.getParameter("capacity"));
            if (capacity <= 0 || capacity > 1E307) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Вместимость должна быть дробным числом > 0 И < 1E+307";
        }

        try {
            distanceTravelled = Long.parseLong(request.getParameter("distanceTravelled"));
            if (distanceTravelled <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Пробег должен быть целым числом > 0 И <= 9223372036854775807";
        }

        try {
            fuelConsumption = Long.parseLong(request.getParameter("fuelConsumption"));
            if (fuelConsumption <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Расход топлива должен быть целым числом > 0 И <= 9223372036854775807";
        }

        try {
            commonAccess = Boolean.parseBoolean(request.getParameter("commonAccess"));
            request.setAttribute("commonAccess", request.getParameter("commonAccess"));
        } catch (Exception e) {
            error = "Как?";
        }

        FuelType fuelType = FuelType.values()[Integer.parseInt(request.getParameter("fuelType"))];

        if (error == null) {
            Long coordinates_id = coordinatesManager.createNewCoordinates(x_coords, y_coords);
            if (coordinates_id != null) {
                Long vehicle_id = vehicleManager.createNewVehicle(name, coordinates_id, vehicleType, enginePower, numberOfWheels, capacity, distanceTravelled, fuelConsumption, fuelType, commonAccess);
                if (vehicle_id != null) {
                    informationManager.createInformation(user.getId(), vehicle_id, TypeOfOperation.CREATE);
                    request.setAttribute("error", "Вехикл успешно создан");
                    doGet(request, response);
                } else {
                    coordinatesManager.deleteCoordinatesById(coordinates_id);
                    error = "Ошибка при создании вехикла";
                }
            } else {
                error = "Ошибка при создании координат";
            }
        }
        request.setAttribute("error", error);
        doGet(request, response);
    }
}
