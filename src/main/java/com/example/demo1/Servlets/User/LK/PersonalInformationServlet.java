package com.example.demo1.Servlets.User.LK;


import com.example.demo1.Managers.UsersManager;
import com.example.demo1.CommonFunc;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "personalInformationServlet", value = "/personalInformation")
public class PersonalInformationServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        commonFunc.setAuthorizedUser(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/lk/personalInformation.jsp");
        requestDispatcher.forward(request, response);
    }
}
