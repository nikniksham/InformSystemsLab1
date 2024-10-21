package com.example.demo1.Servlets.User.LK;

import com.example.demo1.CommonFunc;
import com.example.demo1.DBObjects.Users;
import com.example.demo1.ENUMs.FuelType;
import com.example.demo1.Managers.UsersManager;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "editUserServlet", value = "/editUser")
public class EditUserServlet extends HttpServlet {
    @Inject
    UsersManager usersManager;
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        request.setAttribute("login", commonFunc.getAuthorizedUser(request, response).getLogin());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/lk/editUser.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        Users user = commonFunc.getAuthorizedUser(request, response);
        if (usersManager.editUser(request.getParameter("new_login"), user)) {
            response.sendRedirect(commonFunc.getLink("/personalInformation"));
        }
        request.setAttribute("error", "Логин уже занят");
        doGet(request, response);
    }
}
