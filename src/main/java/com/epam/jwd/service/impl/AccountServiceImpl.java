package com.epam.jwd.service.impl;

import com.epam.jwd.dao.AccountDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.model.Account;
import com.epam.jwd.service.AccountService;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {

        this.accountDAO = DAOFactory.getInstance().getAccountDAO();
    }

    @Override
    public Optional<Account> authenticate(String email, String password) throws InterruptedException {
        final Optional<Account> readUser = DAOFactory.getInstance().getAccountDAO().findUserByEmail(email);
        return readUser.filter(user -> user.getPassword().equals(password));
    }

    @Override
    public List<Account> findAll() throws InterruptedException {
       return DAOFactory.getInstance().getAccountDAO().readAll();
    }

    @Override
    public Optional<Account> create(Account entity) {
        return Optional.empty();
    }
}
