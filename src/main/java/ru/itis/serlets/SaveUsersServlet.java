package ru.itis.serlets;

import ru.itis.dto.AjaxDto;
import ru.itis.repository.ConfigRepository;
import ru.itis.services.BulkUsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/save")
public class SaveUsersServlet extends HttpServlet {
    private ConfigRepository configRepository;
    private BulkUsersService bulkUsersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        configRepository = (ConfigRepository) config.getServletContext().getAttribute("configRepository");
        bulkUsersService = (BulkUsersService) config.getServletContext().getAttribute("bulkUsersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("usersCount", configRepository.getUsersCount());
            req.getRequestDispatcher("/jsp/saveUsers.jsp").forward(req, resp);
        } catch (SQLException e) { throw new ServletException(e); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int count = configRepository.getUsersCount();
            List<AjaxDto> users = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                String fn = req.getParameter("firstName_" + i);
                String ln = req.getParameter("lastName_" + i);
                String em = req.getParameter("email_" + i);
                if (fn == null && ln == null && em == null) continue;
                users.add(AjaxDto.builder().firstName(fn).lastName(ln).email(em).build());
            }
            bulkUsersService.saveUsers(users);
            resp.sendRedirect(req.getContextPath() + "/save/success");
        } catch (SQLException e) { throw new ServletException(e); }
    }
}