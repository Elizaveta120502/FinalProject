package com.epam.jwd.dao;


import com.epam.jwd.model.DBEntity;

import java.util.List;
import java.util.Optional;


public interface DBEntityDAO<T extends DBEntity> {


    boolean create(T entity) throws InterruptedException;

    List<T> readAll() throws InterruptedException;

    Optional<T> readById(Long id) throws InterruptedException;

    boolean update(T entity, Long id) throws InterruptedException;

    boolean deleteById(Long id) throws InterruptedException;

    boolean delete(T entity) throws InterruptedException;



}
