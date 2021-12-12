package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.AccountDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;
import com.epam.jwd.model.Role;
import com.epam.jwd.model.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class AccountDAOImpl extends AbstractDAO<Account> implements AccountDAO<Account> {

    private static final String TABLE_NAME = "account join role on account.role_id = role.id" +
            " join status on account.status_id = status.id" +
            " join benefits on status.benefits_id = benefits.id";
    private static final String ACCOUNT_ID = "account.id";
    private static final String ACCOUNT_LOGIN = "account.login";
    private static final String ACCOUNT_PASSWORD = "account.password";
    private static final String ACCOUNT_EMAIL = "account.email";

    private static final String ACCOUNT_ROLE_ID = "account.role_id";
    private static final String ACCOUNT_ROLE_NAME = "role.role_name";

    private static final String ACCOUNT_STATUS_ID = "account.status_id";
    private static final String ACCOUNT_STATUS_DESCRIPTION = "status.description";

    private static final String BENEFIT_ID = "status.benefits_id";
    private static final String BENEFIT_SIZE = "benefits.size";


    private static final String SPACE = " ";
    private static final String SELECT_ALL_QUERY = String.format("select %s,%s,%s,%s,%s,%s,%s,%s,%s,%s from " + TABLE_NAME,
            ACCOUNT_ID, ACCOUNT_LOGIN, ACCOUNT_PASSWORD, ACCOUNT_EMAIL, ACCOUNT_ROLE_ID, ACCOUNT_ROLE_NAME, ACCOUNT_STATUS_ID, ACCOUNT_STATUS_DESCRIPTION,
            BENEFIT_ID, BENEFIT_SIZE);

    private final String INSERT_INTO_QUERY = "insert into `account`" +
            SPACE + "values (%s,'%s','%s','%s',%s,%s)";


    private final String WHERE_QUERY = "where %s = %s";
    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private final String WHERE_QUERY_FOR_STRING = "where %s = '%s'";

    private final String READ_BY_ID_QUERY = SELECT_ALL_QUERY + SPACE + String.format(WHERE_QUERY_WITH_PARAM, ACCOUNT_ID);
    private final String UPDATE_TABLE_QUERY = "update " + getTableName() + " set %s = '%s', %s = '%s', %s = '%s',%s = '%s', %s = '%s',%s = '%s' " + WHERE_QUERY;

    private final String DELETE_BY_QUERY = "delete from account" + SPACE + WHERE_QUERY;
    private final String DELETE_FOR_STRINGS = "delete from account " + SPACE + WHERE_QUERY_FOR_STRING;
    private final String READ_BY_QUERY = SELECT_ALL_QUERY + SPACE + WHERE_QUERY_WITH_PARAM;

    protected AccountDAOImpl(ConnectionPool pool) {
        super(pool);

    }


    @Override
    public boolean create(Account entity) {
        String sql = String.format(INSERT_INTO_QUERY, entity.getId(), entity.getLogin(),
                entity.getPassword(), entity.getEmail(), entity.getRole().getRoleId(), entity.getStatus().getId());

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
            LoggerProvider.getLOG().debug(READ_BY_ID_QUERY);
            return Optional.of(accounts.get(0));
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }


    @Override
    public boolean update(Account entity, Long id) {
        String sql = String.format(UPDATE_TABLE_QUERY, ACCOUNT_ID, entity.getId(),
                ACCOUNT_LOGIN, entity.getLogin(), ACCOUNT_PASSWORD, entity.getPassword(),
                ACCOUNT_EMAIL, entity.getEmail(), ACCOUNT_ROLE_ID, entity.getRole().getRoleId(),
                ACCOUNT_STATUS_ID, entity.getStatus().getId(), ACCOUNT_ID, id);
        int executeUpdateIndicator;
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
        int executeUpdateIndicator;
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
        int executeUpdateIndicator;
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
    public Optional<Account> findUserByEmail(String email) {
        String sql = String.format(SELECT_ALL_QUERY + SPACE + WHERE_QUERY_WITH_PARAM, ACCOUNT_EMAIL, email);
        try {

            List<Account> accounts = StatementProvider.executePreparedStatement(
                    sql,
                    AccountDAOImpl::extractAccount, st -> st.setString(1, email));

            return Optional.of(accounts.get(0));

        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> findUserByLogin(String login) {
        String sql = String.format(SELECT_ALL_QUERY + SPACE + WHERE_QUERY_WITH_PARAM, ACCOUNT_LOGIN, login);
        try {
            List<Account> accounts = StatementProvider.executePreparedStatement(
                    sql,
                    AccountDAOImpl::extractAccount, st -> st.setString(1, login));
            return Optional.of(accounts.get(0));
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> returnUserRole(String login) {
        String sql = String.format(READ_BY_QUERY, ACCOUNT_LOGIN);

        try {
            List<Account> roles = StatementProvider.executePreparedStatement(sql, AccountDAOImpl::extractAccount,
                    st -> st.setString(1, login));
            Role roleName = roles.get(0).getRole();
            return Optional.of(roleName);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return Optional.empty();
        }
    }


    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


    @Override
    protected void fillEntity(PreparedStatement statement, Account entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getLogin());
        statement.setString(3, entity.getEmail());
        statement.setString(4, entity.getPassword());
        statement.setObject(5, entity.getRole());
        statement.setObject(6, entity.getStatus());


    }


    private static Account extractAccount(ResultSet resultSet) throws EntityExtractionFailedException {
        try {
            return new Account(
                    resultSet.getLong(ACCOUNT_ID),
                    resultSet.getString(ACCOUNT_LOGIN),
                    resultSet.getString(ACCOUNT_PASSWORD),
                    resultSet.getString(ACCOUNT_EMAIL),
                    Role.of(resultSet.getString(ACCOUNT_ROLE_NAME)),
                    Status.of(resultSet.getString(ACCOUNT_STATUS_DESCRIPTION)));


        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract user", e);
        }
    }
}

//    public static void main(String[] args) {
//       LoggerProvider.getLOG().trace("Starting program");
//        StatementProvider.getInstance();
//        AccountDAOImpl instance = new AccountDAOImpl(ConnectionPoolImpl.getInstance());
//        final List<Account> users;
//
//        Account newAccount = new Account(19L, "James1", "qwerty",
//               "btyu@gmail.com",Role.CLIENT,Status.MYSTERY);
//        Account newAccount1 = new Account(18L, "Lily", "qwevbb",
//                "bty3u@gmail.com",Role.CLIENT,Status.MYSTERY);
//
//       // LoggerProvider.getLOG().info(instance.create(newAccount));
//       LoggerProvider.getLOG().info(instance.returnUserRole("Emily"));
//
////        users = instance.readAll();
////        for (Account a : users) {
////            LoggerProvider.getLOG().info(a);
////        }
//
//        StatementProvider.getInstance().close();
//        LoggerProvider.getLOG().trace("program end");
//    }





