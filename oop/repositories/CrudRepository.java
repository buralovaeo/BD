package oop.repositories;

import oop.entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<User> findAll() throws SQLException;
    Optional<T> findById(Long id);
    void save(T entity);
    void saveAll(List<T> entities);
    void update(T entity);
    void remove(T entity);
    void removeById(Long id);
}