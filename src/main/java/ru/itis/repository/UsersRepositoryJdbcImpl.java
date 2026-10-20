package ru.itis.repository;

import ru.itis.models.User;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    private static final String SQL_INSERT =
            "insert into account (email, first_name, last_name, password, age, city, gender) values (?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL = "select * from account";
    private static final String SQL_SELECT_BY_CITY = "select * from account where city = ?";
    private static final String SQL_SELECT_BY_AGE_GT = "select * from account where age > ?";
    private static final String SQL_SELECT_BY_GENDER = "select * from account where gender = ?";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User entity) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_INSERT)) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getSurname());
            ps.setString(4, entity.getPassword());
            if (entity.getAge() != null) ps.setInt(5, entity.getAge()); else ps.setNull(5, Types.INTEGER);
            ps.setString(6, entity.getCity());
            ps.setString(7, entity.getGender());
            ps.executeUpdate();
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        try (Connection c = dataSource.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_SELECT_ALL)) {
            return map(rs);
        }
    }

    @Override
    public List<User> findByCity(String city) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SELECT_BY_CITY)) {
            ps.setString(1, city);
            return map(ps.executeQuery());
        }
    }

    @Override
    public List<User> findByAgeGreaterThan(int age) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SELECT_BY_AGE_GT)) {
            ps.setInt(1, age);
            return map(ps.executeQuery());
        }
    }

    @Override
    public List<User> findByGender(String gender) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SELECT_BY_GENDER)) {
            ps.setString(1, gender);
            return map(ps.executeQuery());
        }
    }

    private List<User> map(ResultSet rs) throws SQLException {
        List<User> result = new ArrayList<>();
        while (rs.next()) {
            result.add(User.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("first_name"))
                    .surname(rs.getString("last_name"))
                    .email(rs.getString("email"))
                    .age(rs.getObject("age") == null ? null : rs.getInt("age"))
                    .city(rs.getString("city"))
                    .gender(rs.getString("gender"))
                    .build());
        }
        return result;
    }
}