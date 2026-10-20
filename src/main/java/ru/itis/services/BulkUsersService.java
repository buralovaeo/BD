package ru.itis.services;

import ru.itis.dto.AjaxDto;
import java.sql.SQLException;
import java.util.List;

public interface BulkUsersService {
    void saveUsers(List<AjaxDto> users) throws SQLException;
}