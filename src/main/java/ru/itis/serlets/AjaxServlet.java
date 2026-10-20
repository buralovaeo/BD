package ru.itis.serlets;

import ru.itis.dto.AjaxDto;
import ru.itis.models.User;
import ru.itis.services.UsersService;
import ru.itis.services.UsersServiceImpl;
import tools.jackson.databind.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "gjhfqr102";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_11_503";

    private UsersService usersService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/ajax_example.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AjaxDto ajaxDto = objectMapper.readValue(req.getReader(), AjaxDto.class);
        System.out.println(ajaxDto.getFirstName());
        try {
            usersService.addUser(ajaxDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("*****");
        List<User> users;
        try {
            users = usersService.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String usersAsJson = objectMapper.writeValueAsString(users);
//        System.out.println(usersAsJson.getBytes());
        resp.setContentType("application/json");
        resp.getWriter().println(usersAsJson);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }
}
