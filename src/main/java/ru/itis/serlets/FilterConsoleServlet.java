package ru.itis.serlets;

import ru.itis.models.User;
import ru.itis.repository.UsersRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/filters")
public class FilterConsoleServlet extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("usersRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city") != null ? req.getParameter("city") : "Moscow";
        int age = req.getParameter("age") != null ? Integer.parseInt(req.getParameter("age")) : 18;
        String gender = req.getParameter("gender") != null ? req.getParameter("gender") : "male";

        try {
            printFilter("FILTER BY CITY = " + city, usersRepository.findByCity(city));
            printFilter("FILTER BY AGE > " + age, usersRepository.findByAgeGreaterThan(age));
            printFilter("FILTER BY GENDER = " + gender, usersRepository.findByGender(gender));
        } catch (SQLException e) { throw new ServletException(e); }

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().println("Filters executed. Check server console.");
    }

    private void printFilter(String title, List<User> users) {
        System.out.println("===== " + title + " =====");
        if (users.isEmpty()) System.out.println("  (no rows)");
        else for (User u : users) System.out.println("  " + u);
        System.out.println();
    }
}