package com.epam.jwd.service;

import com.epam.jwd.model.Account;

import java.util.Optional;

public interface AccountService extends EntityService<Account> {

    Optional<Account> authenticate(String login, String password) throws InterruptedException;

    boolean blockUser(Account entity);

    boolean deleteAccount(Account entity) throws InterruptedException;

    Account fillAccountInformation(Account entity);


}
