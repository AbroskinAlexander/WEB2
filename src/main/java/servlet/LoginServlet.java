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

public class LoginServlet extends HttpServlet {
    UserService userService = UserService.userServiceInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User newUser = new User(email, password);
        Writer writer = response.getWriter();
        response.setContentType("text/html,charset=utf-8");
        if (userService.isExistsThisUser(newUser)) {
            if (userService.authUser(newUser)) {
                writer.write("User authorize");
            } else {
                writer.write("wrong password");
            }
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            writer.write("Error user no exist");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }
        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(PageGenerator.getPageGenerator().getPage("authPage.html", new HashMap<>()));

    }
}
