package com.epam.jwd.service;

import com.epam.jwd.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService extends EntityService<Account> {

    Optional<Account> authenticate(String login, String password) throws InterruptedException;

    boolean registrationForClients(String login, String password, String email);

    boolean registrationForAdmins(String login, String password, String email);

    List<Account> blockUser(String login, String email);

    boolean deleteAccount(String login, String password) throws InterruptedException;



}
