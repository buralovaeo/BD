package ru.itis.services;

import ru.itis.dto.AjaxDto;
import ru.itis.models.User;
import ru.itis.repository.UsersRepository;
import java.sql.SQLException;
import java.util.List;

public class BulkUsersServiceImpl implements BulkUsersService {
    private final UsersRepository usersRepository;

    public BulkUsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void saveUsers(List<AjaxDto> users) throws SQLException {
        for (AjaxDto dto : users) {
            User user = User.builder()
                    .name(dto.getFirstName())
                    .surname(dto.getLastName())
                    .email(dto.getEmail())
                    .password("qwerty007")
                    .build();
            usersRepository.save(user);
        }
    }
}