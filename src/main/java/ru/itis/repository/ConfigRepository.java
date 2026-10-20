package ru.itis.repository;

import javax.sql.DataSource;
import java.sql.*;

public class ConfigRepository {
    private final DataSource dataSource;
    private static final String SQL = "SELECT users_count FROM config ORDER BY id LIMIT 1";

    public ConfigRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getUsersCount() throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt("users_count") : 0;
        }
    }
}