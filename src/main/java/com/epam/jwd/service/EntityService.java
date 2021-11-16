package com.epam.jwd.service;

import com.epam.jwd.model.DBEntity;

import java.util.List;
import java.util.Optional;

public interface EntityService<T extends DBEntity> {

    List<T> findAll() throws InterruptedException;

    Optional<T> create(T entity);

}