package com.epam.jwd.dao;

import com.epam.jwd.model.Account;
import com.epam.jwd.model.UserRole;

import java.util.Optional;


public interface AccountDAO<T extends Account> extends DBEntityDAO<Account> {

    Optional<T> findUserByEmail(String email) throws InterruptedException;

    Optional<T> findUserByLogin(String login) throws InterruptedException;

    UserRole returnUserRole(T entity);
}
