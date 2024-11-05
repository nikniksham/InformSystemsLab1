package com.example.demo1.Servlets.User.LK;

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

@WebServlet(name = "applicationServlet", value = "/application")
public class ApplicationServlet extends HttpServlet {
    @Inject
    UsersManager usersManager;
    @Inject
    CommonFunc commonFunc;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/lk/application.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        if (usersManager.submitAnApplication(commonFunc.getAuthorizedUser(request, response))) {
            response.sendRedirect(commonFunc.getLink("/personalInformation"));
        }
        request.setAttribute("error", "Заявка на выдвижение не подана");
        doGet(request, response);
    }
}
