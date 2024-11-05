package com.example.demo1.Servlets.Vehicle;

import com.example.demo1.CommonFunc;
import com.example.demo1.DBObjects.Users;
import com.example.demo1.ENUMs.TypeOfOperation;
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

@WebServlet(name = "addWheelsServlet", value = "/addWheels")
public class AddWheelsServlet extends HttpServlet {
    @Inject
    VehicleManager vehicleManager;
    @Inject
    InformationManager informationManager;
    @Inject
    CommonFunc commonFunc;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/vehicle/addWheels.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        Users user = commonFunc.getAuthorizedUser(request, response);
        String error = null;
        Long vehicle_id = null;
        Integer count_wheels = null;
        try {
            vehicle_id = Long.parseLong(request.getParameter("vehicle_id"));
        } catch (Exception e) {
            error = "vehicle_id должно быть целым числом";
        }

        try {
            count_wheels = Integer.parseInt(request.getParameter("numberOfWheels"));
            if (count_wheels <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            error = "Количество колёс должног быть целым числом >0 и <= 2147483647";
        }

        if (error == null) {
            String res = vehicleManager.addWheels(vehicle_id, count_wheels);
            if (res == null) {
                informationManager.createInformation(user.getId(), vehicle_id, TypeOfOperation.CHANGE);
                request.setAttribute("error", "Вехикл успешно изменён");
                doGet(request, response);
            } else {
                error = res;
            }
        }
        request.setAttribute("error", error);
        doGet(request, response);
    }
}
