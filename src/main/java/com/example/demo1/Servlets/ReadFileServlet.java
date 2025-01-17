package com.example.demo1.Servlets;

import com.example.demo1.CommonFunc;
import com.example.demo1.GSONObjects.GSONVehicle;
import com.example.demo1.Managers.ImportLogsManager;
import com.example.demo1.Managers.TokenManager;
import com.example.demo1.Managers.VehicleManager;
import com.example.demo1.MinioConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.lang.reflect.Type;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private String downloadFile(String filename, String filepath) {
        S3Client s3Client = MinioConfig.createMinioClient();
        String result = "Ok";
        try {
            // Укажите запрос на получение файла
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(MinioConfig.bucketName)
                    .key(filename)
                    .build();

            // Скачайте файл в указанное место
            s3Client.getObject(getObjectRequest,
                    Paths.get(filepath));

            result = "Файл успешно загружен";
        } catch (Exception e) {
            result = "Ошибка при загрузке файла -> " + e.getMessage();
        } finally {
            s3Client.close();
        }
        return result;
    }

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
            if (filename.endsWith(".json")) {
                String filepath = getServletContext().getRealPath("") + File.separator + "DATA" + File.separator + filename;
                downloadFile(filename, filepath);
                if (new File(filepath).exists()) {
                    try {
                        String content = new String(Files.readAllBytes(Paths.get(filepath)));
                        Type listType = new TypeToken<ArrayList<GSONVehicle>>() {}.getType();
                        objects = gson.fromJson(content, listType);
                        result = vehicleManager.createListOfVehicles(objects, user_id);
                    } catch (Exception e) {
                        error = "Ошибка при конвертации .json файла -> " + e.getMessage();
                    }
                } else {
                    error = "Файл не существует";
                }
                try {
                    Files.delete(Paths.get(filepath));
                } catch (Exception e) {
//                    error = "файл не удалился?";
                }
            } else {
                error = "Переданный файл должен быть с разрешением .json";
            }
        }

        boolean suc = false;
        int count = 0;

        if (result != null && result.length() == 0) {
            result = "Все объекты успешно добавлены";
            suc = true;
            count = objects.size();
        }

        if (filename != null && filename.endsWith(".json")) {
            if (!importLogsManager.createImportLog(user_id, filename, suc, count)) {
                error = "Падла не создал лог";
            }
        }

        request.setAttribute("result", result);
        request.setAttribute("error", error);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/readFile.jsp");
        requestDispatcher.forward(request, response);

    }
}
