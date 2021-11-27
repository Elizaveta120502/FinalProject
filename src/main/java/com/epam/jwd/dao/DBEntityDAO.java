package com.epam.jwd.dao;


import com.epam.jwd.model.DBEntity;

import java.util.List;


public interface DBEntityDAO<T extends DBEntity> {


    boolean create(T entity) throws InterruptedException;

    List<T> readAll() throws InterruptedException;

    DBEntity readById(Long id) throws InterruptedException;

    boolean update(T entity, Long id) throws InterruptedException;

    boolean deleteById(Long id) throws InterruptedException;

    boolean delete(T entity) throws InterruptedException;

    //boolean deleteAll(List<T> entities) throws InterruptedException;


}
