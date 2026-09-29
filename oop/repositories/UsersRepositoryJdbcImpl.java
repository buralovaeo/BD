package oop.repositories;

import oop.entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_FROM_DRIVERS = "select id, first_name, last_name, age from drivers";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVERS);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age")
            );
            result.add(user);
        }
        return result;
    }

    @Override
    public void saveAll(List<User> entities) {
        if (entities == null || entities.isEmpty()) return;

        StringBuilder sql = new StringBuilder(
                "insert into drivers (first_name, last_name, age) values "
        );

        for (int i = 0; i < entities.size(); i++) {
            User u = entities.get(i);
            sql.append("('")
                    .append(u.getName()).append("', '")
                    .append(u.getSurname()).append("', ")
                    .append(u.getAge()).append(")");
            if (i < entities.size() - 1) {
                sql.append(", ");
            }
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<User> findAllOlderThan(int age) {
        List<User> result = new ArrayList<>();
        String sql = SQL_SELECT_FROM_DRIVERS + " where age > " + age;

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString("last_name"),
                        rs.getInt("age")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(User entity) {
    }

    @Override
    public void update(User entity) {
    }

    @Override
    public void remove(User entity) {
    }

    @Override
    public void removeById(Long id) {
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        return null;
    }
}