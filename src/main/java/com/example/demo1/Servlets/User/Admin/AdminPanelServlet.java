package com.example.demo1.Servlets.User.Admin;

import com.example.demo1.CommonFunc;
import com.example.demo1.Managers.UsersManager;
import com.example.demo1.Managers.VehicleManager;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "adminPanelServlet", value = "/adminPanel")

public class AdminPanelServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;
    @Inject
    UsersManager usersManager;


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorizedAdmin(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/admin/adminPanel.jsp");
        request.setAttribute("usersList", usersManager.getAllUsers());
        requestDispatcher.forward(request, response);
    }
}
