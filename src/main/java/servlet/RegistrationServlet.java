package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

public class RegistrationServlet extends HttpServlet {
    UserService userService = UserService.userServiceInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(PageGenerator.getPageGenerator().getPage("registerPage.html", new HashMap<>()));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User newUser = new User(email, password);
        Writer writer = response.getWriter();
        response.setContentType("text/html,charset=utf-8");
        if (userService.isExistsThisUser(newUser)) {
            writer.write("Error user exist");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            userService.addUser(newUser);
            writer.write("New user add");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        writer.close();
    }
}
