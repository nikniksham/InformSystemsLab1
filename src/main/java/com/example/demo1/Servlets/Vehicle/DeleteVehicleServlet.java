package com.example.demo1.Servlets.Vehicle;

import com.example.demo1.CommonFunc;
import com.example.demo1.DBObjects.Coordinates;
import com.example.demo1.DBObjects.Users;
import com.example.demo1.DBObjects.Vehicle;
import com.example.demo1.ENUMs.TypeOfOperation;
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

@WebServlet(name = "deleteVehicle", value = "/deleteVehicle")
public class DeleteVehicleServlet extends HttpServlet {
    @Inject
    VehicleManager vehicleManager;
    @Inject
    CoordinatesManager coordinatesManager;
    @Inject
    InformationManager informationManager;
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/vehicle/deleteVehicle.jsp");
        Map<Class, Object> result = standardChecks(request, response, requestDispatcher);

        Users users = (Users) result.get(Users.class);
        Vehicle vehicle = (Vehicle) result.get(Vehicle.class);
        Coordinates coordinates = (Coordinates) result.get(Coordinates.class);

        setAttributes(request, vehicle, coordinates);

        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/vehicle/deleteVehicle.jsp");
        Map<Class, Object> result = standardChecks(request, response, requestDispatcher);

        Users user = (Users) result.get(Users.class);
        Vehicle vehicle = (Vehicle) result.get(Vehicle.class);
        Coordinates coordinates = (Coordinates) result.get(Coordinates.class);

        if (request.getAttribute("error") != null) {
            request.setAttribute("error", "пошёл нахуй");
            doGet(request, response);
        }

        String error = null;

        if (vehicleManager.deleteVehicleById(vehicle.getId())) {
            if (coordinatesManager.deleteCoordinatesById(coordinates.getId())) {
                informationManager.createInformation(user.getId(), vehicle.getId(), TypeOfOperation.DELETE);
                response.sendRedirect(commonFunc.getLink("/"));
            } else {
                error = "Ошибка при удалении координат";
            }
        } else {
            error = "Ошибка при удалении вехикла";
        }
        request.setAttribute("error", error);
        doGet(request, response);
    }

    private void setAttributes(HttpServletRequest request, Vehicle vehicle, Coordinates coordinates) {
        request.setAttribute("name", vehicle.getName());
    }

    private Map<Class, Object> standardChecks(HttpServletRequest request, HttpServletResponse response, RequestDispatcher requestDispatcher) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        setAttributes(request, new Vehicle(), new Coordinates());
        Users user = commonFunc.getAuthorizedUser(request, response);
        if (user == null) {
            response.sendRedirect(commonFunc.getLink("/login"));
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

        if (!(user.getStatus() == 2 || informationManager.checkUserIsAuthor(user.getId(), vehicle.getId()))) {
            request.setAttribute("error", "Недостаточно прав для редактирования вехикла");
            requestDispatcher.forward(request, response);
        }

        Coordinates coordinates = coordinatesManager.getCoordinatesById(vehicle.getCoordinates_id());

        Map<Class, Object> result = new HashMap<>();
        result.put(user.getClass(), user);
        result.put(vehicle.getClass(), vehicle);
        result.put(coordinates.getClass(), coordinates);

        return result;
    }
}
