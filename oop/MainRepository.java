package oop;

import oop.entities.User;
import oop.repositories.UserRepository;
import oop.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Buralova2007";
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/testdb_11_503";

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);


        List<User> newUsers = new ArrayList<>();
        newUsers.add(new User(null, "Ivan", "Ivanov", 20));
        newUsers.add(new User(null, "Petr", "Petrov", 25));
        newUsers.add(new User(null, "Anna", "Sidorova", 30));
        newUsers.add(new User(null, "Olga", "Smirnova", 35));
        newUsers.add(new User(null, "Dmitry", "Kozlov", 40));
        newUsers.add(new User(null, "Elena", "Popova", 45));

        userRepository.saveAll(newUsers);

        List<User> olderThan30 = userRepository.findAllOlderThan(30);
        olderThan30.forEach(u -> System.out.println(u.getName() + " " + u.getAge()));
    }
}