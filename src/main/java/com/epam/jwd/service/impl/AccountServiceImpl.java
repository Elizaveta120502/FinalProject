package com.epam.jwd.service.impl;

import com.epam.jwd.dao.AccountDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
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
    public Optional<Account> authenticate(String login, String password) throws InterruptedException {
        final Optional<Account> readUser = Optional.ofNullable(DAOFactory.getInstance().getAccountDAO().findUserByLogin(login));
        return readUser.filter(user -> user.getPassword().equals(password));
    }

    @Override
    public boolean blockUser(Account entity) {
        return false;
    }

    @Override
    public boolean deleteAccount(Account entity)  {
        try {
            DAOFactory.getInstance().getAccountDAO().delete(entity);
            if (DAOFactory.getInstance().getAccountDAO().findUserByLogin(entity.getLogin()) == null){
                return true;
            }else{
                return false;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public Account fillAccountInformation(Account entity) {
        return null;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = null;
        try {
             accounts = DAOFactory.getInstance().getAccountDAO().readAll();
        }catch (InterruptedException e){
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();

        }
        return accounts;
    }

    @Override
    public Optional<Account> create(Account entity)  {
        boolean created = false;
        try {
            created =  DAOFactory.getInstance().getAccountDAO().create(entity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (created == true){
            return Optional.of(entity);
        }else{
            return Optional.empty();
        }
    }
}
