package com.epam.jwd.service.impl;

import com.epam.jwd.dao.AccountDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;
import com.epam.jwd.model.Role;
import com.epam.jwd.model.Status;
import com.epam.jwd.service.AccountService;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;
    private static Set<Account> blockedUsers = new LinkedHashSet<>();

    public AccountServiceImpl(AccountDAO accountDAO) {

        this.accountDAO = DAOFactory.getInstance().getAccountDAO();
    }

    @Override
    public Optional<Account> authenticate(String login, String password) throws InterruptedException {
            final Optional<Account> readUser = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
            return readUser.filter(user -> user.getPassword().equals(password));

    }

    @Override
    public Optional<Account> registrationForClients(String login, String password, String email) {
        long newId;
        try {
            if (login == null || password == null || email == null
                    || password.length() < 6 || password.length() > 6 ) {
                return Optional.empty();
            } else {
                newId = DAOFactory.getInstance().getAccountDAO().readAll().size() + 1;
                Optional<Account> newAccount = Optional.of(new Account(newId, login, password, email, Role.CLIENT,
                        Status.MYSTERY));
                DAOFactory.getInstance().getAccountDAO().create(newAccount.get());
                return newAccount;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public boolean registrationForAdmins(String login, String password, String email) {
        long newId;
        try {
            if (login == null || password == null || email == null
                    || password.length() <= 6 || password.length() > 0) {
                newId = DAOFactory.getInstance().getAccountDAO().readAll().size() + 1;
                Account newAccount = new Account(newId, login, password, email, Role.ADMINISTRATOR,
                        Status.SUPREME);
                DAOFactory.getInstance().getAccountDAO().create(newAccount);
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public Set<Account> blockUser(String login, String email) {
        // Set<Account> blockedUsers = new LinkedHashSet<>();

        try {
            if (login == null || email == null) {
                return Collections.emptySet();
            } else {
                Optional<Account> accountToBlock =
                        DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
                AccountServiceImpl.blockedUsers.add(accountToBlock.get());
                DAOFactory.getInstance().getAccountDAO().delete(accountToBlock.get());
                return AccountServiceImpl.blockedUsers;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return AccountServiceImpl.blockedUsers;
        }

    }

    @Override
    public boolean deleteAccount(String login, String password) {
        try {
            if (login == null || password == null || password.length() != 6) {
                return false;
            } else {
                Optional<Account> deleteAccount = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
                if (deleteAccount.get().getPassword().equals(password)) {
                    DAOFactory.getInstance().getAccountDAO().delete(deleteAccount.get());
                    if (!DAOFactory.getInstance().getAccountDAO().findUserByLogin(deleteAccount.get().getLogin()).isPresent()) {
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


}
