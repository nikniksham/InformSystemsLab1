package com.example.demo1.Servlets;

import com.example.demo1.CommonFunc;
import com.example.demo1.GSONObjects.GSONVehicle;
import com.example.demo1.Managers.ImportLogsManager;
import com.example.demo1.Managers.VehicleManager;
import com.example.demo1.MinioConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.nio.file.Path;

@MultipartConfig
@WebServlet(name = "importFileServlet", value = "/importFile")
public class ImportFileServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;
    @Inject
    VehicleManager vehicleManager;
    @Inject
    ImportLogsManager importLogsManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("importFile.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        String error = "";
        String result = "";
        String filename = "";
        int count = 0;
        long user_id = commonFunc.getAuthorizedUser(request, response).getId();
        Path filePath = null;
        String uploadPath = getServletContext().getRealPath("") + File.separator + "DATA";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        for (Part part : request.getParts()) {
            if (!Objects.requireNonNull(getFileName(part)).endsWith(".json")) {
                error = "Переданный файл должен иметь расширение .json";
            }
        }

        if (error.equals("")) {
            filename = commonFunc.generateName(100) + ".json";
            filePath = Paths.get(uploadPath + File.separator + filename);
            for (Part part : request.getParts()) {
                part.write(uploadPath + File.separator + filename);
            }
        }

        if (!filename.equals("")) {
            S3Client s3Client = MinioConfig.createMinioClient();
            try {
                s3Client.putObject(PutObjectRequest.builder()
                                .bucket(MinioConfig.bucketName)
                                .key(filename)
                                .build(),
                        filePath);

                try {
                    String content = new String(Files.readAllBytes(filePath));
                    Type listType = new TypeToken<ArrayList<GSONVehicle>>() {}.getType();
                    List<GSONVehicle> objects = new Gson().fromJson(content, listType);
                    count = objects.size();
                    error = vehicleManager.createListOfVehicles(objects, user_id);

                    if (!error.equals("")) {
                        s3Client.deleteObject(DeleteObjectRequest.builder()
                                .bucket(MinioConfig.bucketName)
                                .key(filename)
                                .build());
                    }
                } catch (Exception e) {
                    error = "Ошибка при конвертации .json файла -> " + e.getMessage();
                }

                if (!error.equals("")) {
                    s3Client.deleteObject(DeleteObjectRequest.builder()
                            .bucket(MinioConfig.bucketName)
                            .key(filename)
                            .build());
                } else {
                    result = "Все объекты успешно добавлены";
                    if (!importLogsManager.createImportLog(user_id, filename, true, count)) {
                        error = "Падла не создал лог";
                    }
                }

            } catch (Exception e) {
                error = "Ошибка с хранилищем Minio -> " + e.getMessage();
            } finally {
                s3Client.close();
            }

            try {
                Files.delete(filePath);
            } catch (Exception e) {
                error = "файл не удалился?";
            }
        }

        request.setAttribute("filename", filename);
        request.setAttribute("result", result);
        request.setAttribute("error", error);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("importFile.jsp");
        requestDispatcher.forward(request, response);
    }

    private String getFileName(Part part) {
        for (String contentDisposition : part.getHeader("Content-Disposition").split(";")) {
            if (contentDisposition.trim().startsWith("filename")) {
                return contentDisposition.substring(contentDisposition.indexOf("=") + 2, contentDisposition.length() - 1);
            }
        }
        return null;
    }

}


// ImportFileServlet