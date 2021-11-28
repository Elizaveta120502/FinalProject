package com.epam.jwd.dao;

import com.epam.jwd.model.Account;
import com.epam.jwd.model.Role;
import com.epam.jwd.model.Status;

import java.util.Optional;


public interface AccountDAO<T> extends DBEntityDAO<Account> {

    Optional<T> findUserByEmail(String email) throws InterruptedException;

    Optional<T> findUserByLogin(String login) throws InterruptedException;

    Optional<Role> returnUserRole(String role);


}
