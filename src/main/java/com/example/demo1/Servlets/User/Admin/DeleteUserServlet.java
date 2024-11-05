package com.example.demo1.Servlets.User.Admin;

import com.example.demo1.CommonFunc;
import com.example.demo1.Managers.UsersManager;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "deleteUserServlet", value = "/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    @Inject
    UsersManager usersManager;
    @Inject
    CommonFunc commonFunc;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/admin/deleteUser.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorizedAdmin(request, response);
        String error = null;
        Long user_id = null;
        try {
            user_id = Long.parseLong(request.getParameter("user_id"));
        } catch (Exception e) {
            error = "user_id должно быть числом";
        }
        if (usersManager.deleteUser(user_id)) {
            response.sendRedirect(commonFunc.getLink("/adminPanel"));
        } else {
            error = "Пользователь не найден или у вас недостаточно прав для его удаления";
        }
        request.setAttribute("error", error);
        doGet(request, response);
    }
}
