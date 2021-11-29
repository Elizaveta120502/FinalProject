package com.epam.jwd.service.impl;

import com.epam.jwd.dao.AccountDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;
import com.epam.jwd.model.Role;
import com.epam.jwd.model.Status;
import com.epam.jwd.service.AccountService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {

        this.accountDAO = DAOFactory.getInstance().getAccountDAO();
    }

    @Override
    public Optional<Account> authenticate(String login, String password) throws InterruptedException {
        if (login == null || password == null ||
                password.length()!=6) {
            return Optional.empty();
        } else {
            final Optional<Account> readUser = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
            return readUser.filter(user -> user.getPassword().equals(password));
        }
    }

    @Override
    public boolean registrationForClients(String login, String password, String email) {
        long newId;
        try {
            if (login == null || password == null || email == null
                    || password.length()!=6) {
                return false;
            } else {
                newId = DAOFactory.getInstance().getAccountDAO().readAll().size() + 1;
                Account newAccount = new Account(newId, login, password, email, Role.CLIENT,
                        Status.MYSTERY);
                DAOFactory.getInstance().getAccountDAO().create(newAccount);
                return true;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean registrationForAdmins(String login, String password, String email) {
        long newId;
        try {
            if (login == null || password == null || email == null
                    || password.length()!=6) {
                return false;
            } else {
                newId = DAOFactory.getInstance().getAccountDAO().readAll().size() + 1;
                Account newAccount = new Account(newId, login, password, email, Role.ADMINISTRATOR,
                        Status.SUPREME);
                DAOFactory.getInstance().getAccountDAO().create(newAccount);
                return true;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public List<Account> blockUser(String login, String email) {
        List<Account> blockedUsers = new ArrayList<>();

        try {
            if (login == null || email == null) {
                return Collections.emptyList();
            } else {
                Optional<Account> accountToBlock =
                        DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
                blockedUsers.add(accountToBlock.get());
                DAOFactory.getInstance().getAccountDAO().delete(accountToBlock.get());
                return blockedUsers;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return blockedUsers;
        }

    }

    @Override
    public boolean deleteAccount(String login, String password) {
        try {
            if (login == null || password == null || password.length()!=6) {
                return false;
            } else {
                Optional<Account> deleteAccount = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
                if (deleteAccount.get().getPassword() == password) {
                    DAOFactory.getInstance().getAccountDAO().delete(deleteAccount.get());
                    if (DAOFactory.getInstance().getAccountDAO().findUserByLogin(deleteAccount.get().getLogin()) == null) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    LoggerProvider.getLOG().error("password not confirmed");
                    return false;
                }

            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }


    @Override
    public List<Account> findAll() {
        List<Account> accounts = null;
        try {
            accounts = DAOFactory.getInstance().getAccountDAO().readAll();
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();

        }
        return accounts;
    }

    @Override
    public Optional<Account> create(Account entity) {
        boolean created = false;
        try {
            created = DAOFactory.getInstance().getAccountDAO().create(entity);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }
        if (created == true) {
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }
}
