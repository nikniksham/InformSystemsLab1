package com.example.demo1.Servlets;
import com.example.demo1.CommonFunc;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
        String uploadPath = getServletContext().getRealPath("") + File.separator + "DATA";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filename = commonFunc.generateName(100) + ".json";
        for (Part part : request.getParts()) {
            part.write(uploadPath + File.separator + filename);
        }

        request.setAttribute("filename", filename);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("uploadFile.jsp");
        requestDispatcher.forward(request, response);
    }
}

