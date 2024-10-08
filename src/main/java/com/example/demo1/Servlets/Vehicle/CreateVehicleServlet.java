package com.example.demo1.Servlets.Vehicle;

import com.example.demo1.CommonFunc;
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

@WebServlet(name = "createVehicle", value = "/createVehicle")
public class CreateVehicleServlet extends HttpServlet {
    @Inject
    VehicleManager vehicleManager;
    @Inject
    CoordinatesManager coordinatesManager;
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.checkAndRedirectFalse(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("vehicle/createVehicle.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        commonFunc.checkAndRedirectFalse(request, response);
//        String error = null;
//        if (usersManager.checkLoginDontExists(request.getParameter("login"))) {
//            error = "Login already exists";
//        }
//        if (!request.getParameter("password1").equals(request.getParameter("password2"))) {
//            error = "Passwords don't match";
//        }
//        if (error == null) {
//            if (usersManager.addUser(request.getParameter("login"), request.getParameter("password1"))) {
//                response.sendRedirect(commonFunc.getLink("/login"));
//            }
//            error = "Some internal error";
//        }
//        request.setAttribute("error", error);
//        doGet(request, response);
    }
}
