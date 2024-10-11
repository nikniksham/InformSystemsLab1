package com.example.demo1.Servlets.Vehicle;

import com.example.demo1.CommonFunc;
import com.example.demo1.DBObjects.Coordinates;
import com.example.demo1.DBObjects.Users;
import com.example.demo1.DBObjects.Vehicle;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "editVehicle", value = "/vehicle/editVehicle")
public class EditVehicleServlet extends HttpServlet {
    @Inject
    VehicleManager vehicleManager;
    @Inject
    CoordinatesManager coordinatesManager;
    @Inject
    InformationManager informationManager;
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("vehicle/editVehicle.jsp");
        Map<Class, Object> result = standardChecks(request, response, requestDispatcher);

        Users users = (Users) result.get(Users.class);
        Vehicle vehicle = (Vehicle) result.get(Vehicle.class);
        Coordinates coordinates = (Coordinates) result.get(Coordinates.class);

        setAttributes(request, vehicle, coordinates);

        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("vehicle/editVehicle.jsp");
        Map<Class, Object> result = standardChecks(request, response, requestDispatcher);

        Users user = (Users) result.get(Users.class);
        Vehicle vehicle = (Vehicle) result.get(Vehicle.class);
        Coordinates coordinates = (Coordinates) result.get(Coordinates.class);

        if (request.getAttribute("error") != null) {
            request.setAttribute("error", "пошёл нахуй");
            doGet(request, response);
        }

        String error = null;
        double x_coords = 0, capacity = 0;
        float enginePower = 0;
        long distanceTravelled = 0, fuelConsumption = 0;
        int y_coords = 0, numberOfWheels = 0;

        try {
            x_coords = Double.parseDouble(request.getParameter("x_coords"));
        } catch (Exception e) {
            error = "X должно быть дробным числом";
        }

        try {
            y_coords = Integer.parseInt(request.getParameter("y_coords"));
        } catch (Exception e) {
            error = "Y должно быть целым числом";
        }

        String name = request.getParameter("name");

        if (name.length() == 0) {
            error = "Длина имени должна быть хотя бы 1 символ";
        }

        VehicleType vehicleType = VehicleType.values()[Integer.parseInt(request.getParameter("vehicleType"))];

        try {
            enginePower = Float.parseFloat(request.getParameter("enginePower"));
            if (enginePower <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Мощность двигателя должна быть дробным числом >0";
        }

        try {
            numberOfWheels = Integer.parseInt(request.getParameter("numberOfWheels"));
            if (numberOfWheels <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Количество колёс должно быть целым числом >0";
        }

        try {
            capacity = Double.parseDouble(request.getParameter("capacity"));
            if (capacity <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Вместимость должна быть дробным числом >0";
        }

        try {
            distanceTravelled = Long.parseLong(request.getParameter("distanceTravelled"));
            if (distanceTravelled <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Пробег должен быть целым числом >0";
        }

        try {
            fuelConsumption = Long.parseLong(request.getParameter("fuelConsumption"));
            if (fuelConsumption <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Расход топлива должен быть целым числом >0";
        }

        FuelType fuelType = FuelType.values()[Integer.parseInt(request.getParameter("fuelType"))];

        setAttributes(request, vehicle, coordinates);

        if (error == null) {
            if (coordinatesManager.editCoordinatesById(coordinates.getId(), x_coords, y_coords)) {
                if (vehicleManager.editVehicleById(vehicle.getId(), name, vehicleType, enginePower, numberOfWheels, capacity, distanceTravelled, fuelConsumption, fuelType)) {
                    informationManager.createInformation(user.getId(), vehicle.getId(), TypeOfOperation.CHANGE);
                    request.setAttribute("error", "Вехикл успешно изменён");
                    doGet(request, response);
                } else {
                    error = "Внутренняя ошибка при изменении вехикла";
                }
            } else {
                error = "Внутренняя ошибка при изменении координат";
            }
        }

        request.setAttribute("error", error);
        doGet(request, response);
    }

    private void setAttributes(HttpServletRequest request, Vehicle vehicle, Coordinates coordinates) {
        request.setAttribute("listVehicleTypes", VehicleType.values());
        request.setAttribute("listFuelTypes", FuelType.values());
        request.setAttribute("x_coords", coordinates.getX());
        request.setAttribute("y_coords", coordinates.getY());
        request.setAttribute("name", vehicle.getName());
        request.setAttribute("vehicleType", vehicle.getVehicleType_id());
        request.setAttribute("enginePower", vehicle.getEnginePower());
        request.setAttribute("numberOfWheels", vehicle.getNumberOfWheels());
        request.setAttribute("capacity", vehicle.getCapacity());
        request.setAttribute("distanceTravelled", vehicle.getDistanceTravelled());
        request.setAttribute("fuelConsumption", vehicle.getFuelConsumption());
        request.setAttribute("fuelType", vehicle.getFuelType_id());
    }

    private Map<Class, Object> standardChecks(HttpServletRequest request, HttpServletResponse response, RequestDispatcher requestDispatcher) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        setAttributes(request, new Vehicle(), new Coordinates());
        Users user = commonFunc.getAuthorizedUser(request, response);

        if (user == null) {
            response.sendRedirect(commonFunc.getLink("/user/auth/login"));
        }

        long vehicle_id = 0;
        try {
            vehicle_id = Long.parseLong(request.getParameter("vehicle_id"));
        } catch (Exception e) {
            request.setAttribute("error", "vehicle_id должен быть целым числом");
            requestDispatcher.forward(request, response);
        }

        Vehicle vehicle = vehicleManager.getVehicleById(vehicle_id);
        if (vehicle == null) {
            request.setAttribute("error", "Vehicle по заданному id не найден");
            requestDispatcher.forward(request, response);
        }

        if (user != null && vehicle != null) {
            if (!(user.getStatus() == 2 || informationManager.checkUserIsAuthor(user.getId(), vehicle.getId()))) {
                request.setAttribute("error", "Недостаточно прав для редактирования вехикла");
                requestDispatcher.forward(request, response);
            }
        }

        Coordinates coordinates = null;
        if (vehicle != null) {
            coordinates = coordinatesManager.getCoordinatesById(vehicle.getCoordinates_id());
        }

        Map<Class, Object> result = new HashMap<>();
        result.put(Users.class, user);
        result.put(Vehicle.class, vehicle);
        result.put(Coordinates.class, coordinates);

        return result;
    }
}
