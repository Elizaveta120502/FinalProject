package com.epam.jwd.dao;


import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    T save(T entity);

    List<T> recieveAll();

    Optional<T> findById(Long id);

    T update (T entity);

    void deleteById(Long id);

}
