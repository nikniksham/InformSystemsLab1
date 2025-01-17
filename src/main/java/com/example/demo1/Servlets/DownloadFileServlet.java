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

import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "downloadFileServlet", value = "/downloadFile")
public class DownloadFileServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;

    private String downloadFile(String filename, String filepath) {
        String result;
        try (S3Client s3Client = MinioConfig.createMinioClient()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(MinioConfig.bucketName)
                    .key(filename)
                    .build();

            deleteFile(filepath);

            s3Client.getObject(getObjectRequest,
                    Paths.get(filepath));

            result = "";
        } catch (Exception e) {
            result = "Ошибка при загрузке файла -> " + e.getMessage();
        }
        return result;
    }

    private void deleteFile(String filepath) {
        try {
            Files.delete(Paths.get(filepath));
        } catch (Exception e) {}
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/downloadFile.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        String error = "";
        String filename = null;
//        String new_filename = commonFunc.generateName(100) + ".json";

        try {
            filename = request.getParameter("filename");
        } catch (Exception e) {
            error = "filename передай";
        }

        if (filename != null) {
            String filepath = getServletContext().getRealPath("") + File.separator + "DATA" + File.separator + filename;
            error = downloadFile(filename, filepath);
            if (new File(filepath).exists()) {
                File file = new File(filepath);

                // Установите заголовки для скачивания
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
                response.setContentLengthLong(file.length());

                // Чтение файла и отправка его клиенту
                try (FileInputStream fileInputStream = new FileInputStream(file);
                     OutputStream outputStream = response.getOutputStream()) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                error = "Файл скачивается";
            }
            deleteFile(filepath);
        }

        request.setAttribute("error", error);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/downloadFile.jsp");
        requestDispatcher.forward(request, response);

    }
}
