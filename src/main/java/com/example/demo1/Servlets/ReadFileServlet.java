package com.example.demo1.Servlets;

import com.example.demo1.CommonFunc;
import com.example.demo1.GSONObjects.GSONVehicle;
import com.example.demo1.Managers.ImportLogsManager;
import com.example.demo1.Managers.TokenManager;
import com.example.demo1.Managers.VehicleManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "readFileServlet", value = "/readFile")
public class ReadFileServlet extends HttpServlet {
    @Inject
    VehicleManager vehicleManager;
    @Inject
    CommonFunc commonFunc;
    @Inject
    ImportLogsManager importLogsManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        Gson gson = new Gson();
        String filename = null, error = null, result = null;
        List<GSONVehicle> objects = null;
        long user_id = commonFunc.getAuthorizedUser(request, response).getId();

        try {
            filename = request.getParameter("filename");
        } catch (Exception e) {
            error = "filename передай";
        }

        if (filename != null) {
            String filepath = getServletContext().getRealPath("") + File.separator + "DATA" + File.separator + filename;
            if (new File(filepath).exists()) {
                try {
                    String content = new String(Files.readAllBytes(Paths.get(filepath)));
                    Type listType = new TypeToken<ArrayList<GSONVehicle>>() {}.getType();
                    objects = gson.fromJson(content, listType);
                    result = vehicleManager.createListOfVehicles(objects, user_id);
                } catch (Exception e) {
                    error = "Ошибка при конвертации .json файла -> " + e.getMessage();
                }
            }
        }

        boolean suc = false;
        int count = 0;

        if (result != null && result.length() == 0) {
            result = "Все объекты успешно добавлены";
            suc = true;
            count = objects.size();
        }

        if (!importLogsManager.createImportLog(user_id, filename, suc, count) ) {
            error = "Падла не создал лог";
        }

        request.setAttribute("result", result);
        request.setAttribute("error", error);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/readFile.jsp");
        requestDispatcher.forward(request, response);

    }
}
