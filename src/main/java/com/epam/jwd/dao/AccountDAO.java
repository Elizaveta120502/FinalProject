package com.epam.jwd.dao;

import com.epam.jwd.model.Account;
import com.epam.jwd.model.Role;

import java.util.List;
import java.util.Optional;


public interface AccountDAO<T> extends DBEntityDAO<Account> {

    Optional<Account> findUserByEmail(String email) throws InterruptedException;

    Optional<Account> findUserByLogin(String login) throws InterruptedException;

    Optional<Role> returnUserRole(String role);

//    List<Account> findBestUsers() throws InterruptedException;


}
