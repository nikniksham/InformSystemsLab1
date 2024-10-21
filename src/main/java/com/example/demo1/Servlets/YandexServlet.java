package com.example.demo1.Servlets;
import com.example.demo1.CommonFunc;
import com.example.demo1.Managers.VehicleManager;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "yandexServlet", value = "/yandex")
public class YandexServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;
    @Inject
    VehicleManager vehicleManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.setAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("yandex.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.setAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("yandex.jsp");
        String sample = request.getParameter("sample"), error=null;
        boolean is_start = false;
        Double power_min = null, power_max = null;

        request.setAttribute("sample", request.getParameter("sample"));

        try {
            is_start = Boolean.parseBoolean(request.getParameter("is_start"));
            request.setAttribute("is_start", request.getParameter("is_start"));
        } catch (Exception e) {
            error = "Как?";
        }

        try {
            if (!request.getParameter("power_min").equals("")) {
                request.setAttribute("power_min", request.getParameter("power_min"));
                power_min = Double.parseDouble(request.getParameter("power_min"));
            }
        } catch (Exception e) {
            error = "power_min должно быть числом типа double";
        }

        try {
            if (!request.getParameter("power_max").equals("")) {
                request.setAttribute("power_max", request.getParameter("power_max"));
                power_max = Double.parseDouble(request.getParameter("power_max"));
            }
        } catch (Exception e) {
            error = "power_max должно быть числом типа double";
        }

        if (error == null) {
            request.setAttribute("vehicleList", vehicleManager.searchVehicle(sample, is_start, power_min, power_max));
        }
        request.setAttribute("error", error);
        requestDispatcher.forward(request, response);
    }
}
