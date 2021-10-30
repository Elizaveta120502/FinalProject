package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.AccountDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;

import com.epam.jwd.model.UserRole;


import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;


public class AccountDAOImpl extends AbstractDAO<Account> implements AccountDAO<Account> {

    private static final String TABLE_NAME = "account";
    private static final String ACCOUNT_ID = "id";
    private static final String ACCOUNT_LOGIN = "login";
    private static final String ACCOUNT_PASSWORD = "password";
    private static final String ACCOUNT_EMAIL = "email";

    private static final String ACCOUNT_ROLE_ID = "role_id";


    private static final String SPACE  = " ";
    private static final String SELECT_ALL_QUERY = String.format("select %s,%s,%s,%s,%s from account",
            ACCOUNT_ID,ACCOUNT_LOGIN,ACCOUNT_PASSWORD,ACCOUNT_EMAIL,ACCOUNT_ROLE_ID);

    private final String INSERT_INTO_QUERY = String.format("insert into  " + getTableName()+
            SPACE + "values %s,%s,%s,%s,%s");

    private  final String READ_BY_ID_QUERY = String.format("select %s,%s,%s,%s,%s from %s where %s = ?",
            ACCOUNT_ID,ACCOUNT_LOGIN,ACCOUNT_PASSWORD,ACCOUNT_EMAIL,ACCOUNT_ROLE_ID,getTableName(),ACCOUNT_ID);

    private final String WHERE_QUERY = "where %s = %s";
    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private  final String  UPDATE_TABLE_QUERY = "update "+getTableName()+"set %s = '%s', %s = '%s', %s = '%s' " + WHERE_QUERY;

    private  final String  DELETE_ALL = "delete from " + getTableName();
    private final String DELETE_BY_QUERY = "delete from " + getTableName() + SPACE + WHERE_QUERY_WITH_PARAM;

    private final String FIND_USER_BY_QUERY = "select" + SPACE + ACCOUNT_ID + ACCOUNT_LOGIN +
    ACCOUNT_PASSWORD +ACCOUNT_EMAIL + ACCOUNT_ROLE_ID +WHERE_QUERY_WITH_PARAM;


    protected AccountDAOImpl(ConnectionPool pool) {
        super(pool);

    }

    @Override
    public boolean create(Account entity) throws InterruptedException {
        String sql = String.format(INSERT_INTO_QUERY,entity.getId(),entity.getLogin(),entity.getEmail(),
                entity.getPassword(),entity.getRole().getId());

        int executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        return executeUpdateIndicator == 1;
    }

    @Override
    public List<Account> readAll() throws InterruptedException {
            return StatementProvider.getInstance().executeStatement(SELECT_ALL_QUERY, AccountDAOImpl::extractAccount);
    }

    @Override
    public List<Account> readById(Long id) throws InterruptedException {
        return StatementProvider.executePreparedStatement(
                READ_BY_ID_QUERY,
                AccountDAOImpl::extractAccount,st -> st.setLong(1,id));
    }


    @Override
    public boolean update(Account entity) throws InterruptedException {
       String sql = String.format(UPDATE_TABLE_QUERY,ACCOUNT_LOGIN,entity.getLogin(),
               ACCOUNT_PASSWORD,entity.getPassword(),ACCOUNT_EMAIL,entity.getEmail(),ACCOUNT_ID,entity.getId());
        int executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        return executeUpdateIndicator == 1;
    }

    @Override
    public boolean delete(Account entity) throws InterruptedException {
    String sql = String.format(DELETE_BY_QUERY,ACCOUNT_LOGIN,entity.getLogin());
        int executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        return executeUpdateIndicator == 1;
    }


    @Override
    public boolean deleteById(Long id) throws InterruptedException {
        String sql = String.format(DELETE_BY_QUERY,ACCOUNT_ID,id);
        int executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        return executeUpdateIndicator == 1;

    }

    @Override
    public boolean deleteAll(List entities) throws InterruptedException {
        String sql = DELETE_ALL;
        int executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        return executeUpdateIndicator == 1;
    }


    @Override                          //todo: think about return statement
    public Account findUserByEmail(String email) throws InterruptedException {
       String sql = String.format(FIND_USER_BY_QUERY,ACCOUNT_EMAIL,email);
        return (Account) StatementProvider.executePreparedStatement(
                sql,
                AccountDAOImpl::extractAccount,st -> st.setString(1,email));
    }

    @Override
    public Account findUserByLogin(String login) throws InterruptedException {
        String sql = String.format(FIND_USER_BY_QUERY,ACCOUNT_LOGIN,login);
        return (Account) StatementProvider.executePreparedStatement(
                sql,
                AccountDAOImpl::extractAccount,st -> st.setString(1,login));
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
        String [] arrFields = {ACCOUNT_ID,ACCOUNT_LOGIN,ACCOUNT_EMAIL,ACCOUNT_PASSWORD,ACCOUNT_ROLE_ID};
        List<String> fields = Arrays.asList(arrFields);
        return fields;
    }

//    @Override
//    protected DBEntity extractResult(ResultSet resultSet) throws SQLException, EntityExtractionFailedException {
//
//    }
//
//    @Override
//    protected void fillEntity(PreparedStatement statement, DBEntity entity) throws SQLException {
//
//    }


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
            throw new EntityExtractionFailedException("failed to extract user",e);
        }
    }




}
