package ru.itis.listeners;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.repository.ConfigRepository;
import ru.itis.repository.UsersRepository;
import ru.itis.repository.UsersRepositoryJdbcImpl;
import ru.itis.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomServletContextListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Buralova2007";
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/servlets_11-503";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        SignUpService signUpService = new SignUpServiceImpl(usersRepository);
        UsersService usersService = new UsersServiceImpl(usersRepository);

        servletContext.setAttribute("usersRepository", usersRepository);
        servletContext.setAttribute("signUpService", signUpService);
        servletContext.setAttribute("usersService", usersService);

        ConfigRepository configRepository = new ConfigRepository(dataSource);
        BulkUsersService bulkUsersService = new BulkUsersServiceImpl(usersRepository);

        servletContext.setAttribute("configRepository", configRepository);
        servletContext.setAttribute("bulkUsersService", bulkUsersService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
