package com.epam.jwd.dao;

import com.epam.jwd.model.Account;
import com.epam.jwd.model.UserRole;

import java.util.List;


public interface AccountDAO<T extends Account> extends DBEntityDAO<Account> {

    T findUserByEmail(String email) throws InterruptedException;

    T findUserByLogin(String login) throws InterruptedException;

    UserRole returnUserRole(T entity);
}
