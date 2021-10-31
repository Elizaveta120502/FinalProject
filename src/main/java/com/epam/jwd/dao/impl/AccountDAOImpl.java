package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.AccountDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.ConnectionPoolImpl;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;


import com.epam.jwd.model.DBEntity;
import com.epam.jwd.model.UserRole;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class AccountDAOImpl extends AbstractDAO<Account> implements AccountDAO<Account> {

    private static final String TABLE_NAME = "account";
    private static final String ACCOUNT_ID = "id";
    private static final String ACCOUNT_LOGIN = "login";
    private static final String ACCOUNT_PASSWORD = "password";
    private static final String ACCOUNT_EMAIL = "email";

    private static final String ACCOUNT_ROLE_ID = "role_id";


    private static final String SPACE = " ";
    private static final String SELECT_ALL_QUERY = String.format("select %s,%s,%s,%s,%s from " + TABLE_NAME,
            ACCOUNT_ID, ACCOUNT_LOGIN, ACCOUNT_PASSWORD, ACCOUNT_EMAIL, ACCOUNT_ROLE_ID);

    private final String INSERT_INTO_QUERY = "insert into `account`" +
            SPACE + "values (%s,'%s','%s','%s',%s)";

    private final String READ_BY_ID_QUERY = String.format("select %s,%s,%s,%s,%s from %s where %s = ?",
            ACCOUNT_ID, ACCOUNT_LOGIN, ACCOUNT_PASSWORD, ACCOUNT_EMAIL, ACCOUNT_ROLE_ID, getTableName(), ACCOUNT_ID);

    private final String WHERE_QUERY = "where %s = %s";
    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private final String WHERE_QUERY_FOR_STRING = "where %s = '%s'";
    private final String UPDATE_TABLE_QUERY = "update " + getTableName() + "set %s = '%s', %s = '%s', %s = '%s',%s = '%s', %s = '%s' " + WHERE_QUERY;

    private final String DELETE_ALL = "delete from " + getTableName();
    private final String DELETE_BY_QUERY = "delete from " + getTableName() + SPACE + WHERE_QUERY;
    private final String DELETE_FOR_STRINGS = "delete from " + getTableName() + SPACE + WHERE_QUERY_FOR_STRING;


    protected AccountDAOImpl(ConnectionPool pool) {
        super(pool);

    }


    @Override
    public boolean create(Account entity) {
        String sql = String.format(INSERT_INTO_QUERY, entity.getId(), entity.getLogin(),
                entity.getPassword(), entity.getEmail(), entity.getRole().getId());

        int executeUpdateIndicator = 0;
        try {
            executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }
        return executeUpdateIndicator == 1;
    }

    @Override
    public List<Account> readAll() {
        try {
            return StatementProvider.getInstance().executeStatement(SELECT_ALL_QUERY, AccountDAOImpl::extractAccount);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Account> readById(Long id) {
        try {
            List<Account> accounts = StatementProvider.executePreparedStatement(
                    READ_BY_ID_QUERY,
                    AccountDAOImpl::extractAccount, st -> st.setLong(1, id));
            return Optional.of(accounts.get(0));
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }


    @Override
    public boolean update(Account entity,Long id) {
        String sql = String.format(UPDATE_TABLE_QUERY,ACCOUNT_ID, entity.getId(),
                ACCOUNT_LOGIN, entity.getLogin(), ACCOUNT_PASSWORD, entity.getPassword(),
                ACCOUNT_EMAIL, entity.getEmail(), ACCOUNT_ROLE_ID, entity.getRole(),ACCOUNT_ID,id);
        int executeUpdateIndicator = 0;
        try {
            executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
        return executeUpdateIndicator == 1;
    }

    @Override
    public boolean delete(Account entity) {
        String sql = String.format(DELETE_FOR_STRINGS, ACCOUNT_LOGIN, entity.getLogin());
        int executeUpdateIndicator = 0;
        try {
            executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
        return executeUpdateIndicator == 1;
    }


    @Override
    public boolean deleteById(Long id) {
        String sql = String.format(DELETE_BY_QUERY, ACCOUNT_ID, id);
        int executeUpdateIndicator = 0;
        try {
            executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
        return executeUpdateIndicator == 1;

    }

    @Override
    public boolean deleteAll(List entities) { //todo:think about ability of using this method in account table
        String sql = DELETE_ALL;
        int executeUpdateIndicator = 0;
        try {
            executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
        return executeUpdateIndicator == 1;
    }


    @Override                          //todo: think about return statement
    public Account findUserByEmail(String email) {
        String sql = String.format(SELECT_ALL_QUERY + SPACE + WHERE_QUERY_WITH_PARAM, ACCOUNT_EMAIL, email);
        try {
            return StatementProvider.executePreparedStatement(
                    sql,
                    AccountDAOImpl::extractAccount, st -> st.setString(1, email)).get(0);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return null;
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return null;
        }
    }

    @Override
    public Account findUserByLogin(String login) {
        String sql = String.format(SELECT_ALL_QUERY + SPACE + WHERE_QUERY_WITH_PARAM, ACCOUNT_LOGIN, login);
        try {
            return StatementProvider.executePreparedStatement(
                    sql,
                    AccountDAOImpl::extractAccount, st -> st.setString(1, login)).get(0);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return null;
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return null;
        }
    }

    @Override
    public UserRole returnUserRole(Account entity) {
        return entity.getRole();
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected List<String> getFields() {
        String[] arrFields = {ACCOUNT_ID, ACCOUNT_LOGIN, ACCOUNT_EMAIL, ACCOUNT_PASSWORD, ACCOUNT_ROLE_ID};
        List<String> fields = Arrays.asList(arrFields);
        return fields;
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Account entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getLogin());
        statement.setString(3, entity.getEmail());
        statement.setString(4, entity.getPassword());
        statement.setObject(5, entity.getRole());

    }


    private static Account extractAccount(ResultSet resultSet) throws EntityExtractionFailedException {
        try {
            return new Account(
                    resultSet.getLong(ACCOUNT_ID),
                    resultSet.getString(ACCOUNT_LOGIN),
                    resultSet.getString(ACCOUNT_PASSWORD),
                    resultSet.getString(ACCOUNT_EMAIL),
                    new UserRole(
                            resultSet.getLong(ACCOUNT_ROLE_ID)));

        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract user", e);
        }
    }

//  public static void main (String [] args){
//      LoggerProvider.getLOG().trace("Starting program");
//      StatementProvider.getInstance();
//      AccountDAOImpl instance = new AccountDAOImpl(ConnectionPoolImpl.getInstance());
//      final List<Account> users;
//      boolean delete;
//      boolean create;
//      Optional read;
//      Account userByEmail;
//      Account userByLogin;
//
//      Account newAccount = new Account(16L,"Bob","qwerty",
//              "btyu@gmail.com",new UserRole("simple user",2L));
//      Account newAccount1 = new Account(17L,"Mery","qw1234",
//              "bmb@gmail.com",new UserRole("simple user",2L));
//      userByLogin = instance.findUserByLogin("LaizyCat");
//
//      delete = instance.deleteById(16L);
//       LoggerProvider.getLOG().info(userByLogin);
////     users = instance.readAll();
////     for (Account a : users){
////         LoggerProvider.getLOG().info(a);
////     }
//
//      StatementProvider.getInstance().close();
//      LoggerProvider.getLOG().trace("program end");
//  }
}
