package com.example.demo1.Servlets.User.LK;


import com.example.demo1.DBObjects.ImportLogs;
import com.example.demo1.DBObjects.Users;
import com.example.demo1.Managers.ImportLogsManager;
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
import java.util.List;

@WebServlet(name = "showImportLogsServlet", value = "/showImportLogs")
public class ShowImportLogsServlet extends HttpServlet {
    @Inject
    CommonFunc commonFunc;
    @Inject
    ImportLogsManager importLogsManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        commonFunc.redirectIfNotAuthorized(request, response);
        List<ImportLogs> importLogs;
        Users user = commonFunc.getAuthorizedUser(request, response);
        if (user.getStatus() == 0) {
            importLogs = importLogsManager.getAllLogsPropUser(user.getId());
        } else {
            importLogs = importLogsManager.getAllLogs();
        }
        request.setAttribute("ImportLogs", importLogs);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/lk/showImportLogs.jsp");
        requestDispatcher.forward(request, response);
    }
}
