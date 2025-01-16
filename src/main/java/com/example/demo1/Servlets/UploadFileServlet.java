package com.example.demo1.Servlets;
import com.example.demo1.CommonFunc;
import com.example.demo1.MinioConfig;
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
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Objects;
import java.nio.file.Path;

@MultipartConfig
@WebServlet(name = "uploadFileServlet", value = "/uploadFile")
public class UploadFileServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("uploadFile.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        String error = "";
        String filename = "";
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
            for (Part part : request.getParts()) {
                part.write(uploadPath + File.separator + filename);
            }
        }

        if (!filename.equals("")) {
            S3Client s3Client = MinioConfig.createMinioClient();

            Path filePath = Paths.get(uploadPath + File.separator + filename);

            // Загрузка файла
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(MinioConfig.bucketName)
                            .key(filename)
                            .build(),
                    filePath);

            error = "Файл успешно загружен в MinIO!";
        }

        request.setAttribute("filename", filename);
        request.setAttribute("error", error);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("uploadFile.jsp");
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

