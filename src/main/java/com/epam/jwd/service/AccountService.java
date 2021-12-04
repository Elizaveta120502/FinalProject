package com.epam.jwd.service;

import com.epam.jwd.model.Account;

import java.util.Optional;
import java.util.Set;

public interface AccountService extends EntityService<Account> {

    Optional<Account> authenticate(String login, String password) throws InterruptedException;

    Optional<Account> registrationForClients(String login, String password, String email);

    boolean registrationForAdmins(String login, String password, String email);

    Set<Account> blockUser(String login, String email);

    boolean deleteAccount(String login, String password) throws InterruptedException;


}
