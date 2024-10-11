package com.example.demo1.Servlets.User.LK;

import com.example.demo1.CommonFunc;
import com.example.demo1.DBObjects.Users;
import com.example.demo1.Managers.UsersManager;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "editPasswordServlet", value = "/editPassword")
public class EditPasswordServlet extends HttpServlet {
    @Inject
    UsersManager usersManager;
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/lk/editPassword.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        Users user = commonFunc.getAuthorizedUser(request, response);
        String error = null;
        if (!usersManager.checkPasswordUser(request.getParameter("old_password"), user)) {
            error = "Старый пароль введён неверно";
        }
        if (!request.getParameter("new_password1").equals(request.getParameter("new_password2"))) {
            error = "Новые пароли не совпадают";
        }
        if (error == null) {
            if (usersManager.setNewPassword(request.getParameter("new_password1"), user)) {
                response.sendRedirect(commonFunc.getLink("/personalInformation"));
            }
            error = "Внутренняя ошибка";
        }
        request.setAttribute("error", error);
        doGet(request, response);
    }
}
