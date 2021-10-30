package com.epam.jwd.dao;

import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.model.DBEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public abstract class  AbstractDAO<T extends DBEntity> implements DBEntityDAO<T> {

    private static final String INSERT_ONE_QUERY = "insert into %s (%s)";
    private static final String INSERT_MANY_QUERIES = "insert into %s ";
    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSE_PARENTHESIS = ")";
    private static final String ID_COLUMN_NAME = "id";
    private static final String COMMA = ", ";

    protected static final String SELECT_ALL_FROM = "select * from ";
    protected static final String WHERE_FIELD = "where %s = ?";
    protected static final String SPACE = " ";

    protected final ConnectionPool pool;
    private final String selectAllExpression;
    private final String selectByIdExpression;
    private final String insertSql;

    protected AbstractDAO(ConnectionPool pool) {
        this.pool = pool;
        this.selectAllExpression = SELECT_ALL_FROM + getTableName() ;
        this.selectByIdExpression = selectAllExpression + SPACE + String.format(WHERE_FIELD, ID_COLUMN_NAME);
        this.insertSql = String.format(INSERT_ONE_QUERY, getTableName(), String.join(COMMA, getFields()));
    }



    protected abstract String getTableName();

    protected abstract List<String> getFields();

  //  protected  abstract T extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException;

   // protected abstract void fillEntity(PreparedStatement statement, T entity) throws SQLException;
}
