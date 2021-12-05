package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.PictureDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Picture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PictureDAOImpl extends AbstractDAO<Picture> implements PictureDAO {

    private static final String TABLE_NAME = "pictures";

    private static final String PICTURE_ID = "id";
    private static final String PICTURE_URL = "url";
    private static final String PICTURE_NAME = "name";

    private static final String SPACE = " ";
    private final String INSERT_INTO_QUERY = "insert into " + getTableName() +
            SPACE + "values (%s,'%s','%s')";

    private final String READ_BY_ID_QUERY = String.format("select %s,%s,%s from %s where %s = ?",
            PICTURE_ID, PICTURE_URL, PICTURE_NAME, getTableName(), PICTURE_ID);


    private final String WHERE_QUERY = "where %s = %s";
    private final String WHERE_QUERY_FOR_STRING = "where %s = '%s'";

    private final String DELETE_BY_QUERY = "delete from " + getTableName() + SPACE + WHERE_QUERY;
    private final String DELETE_FOR_STRINGS = "delete from " + getTableName() + SPACE + WHERE_QUERY_FOR_STRING;
    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private static final String SELECT_ALL_QUERY = String.format("select %s,%s from " + TABLE_NAME,
            PICTURE_ID, PICTURE_URL, PICTURE_NAME);


    protected PictureDAOImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Picture entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getPictureURL());
        statement.setString(3, entity.getPictureName());
    }

    @Override
    public boolean create(Picture entity) throws InterruptedException {
        String sql = String.format(INSERT_INTO_QUERY, entity.getId(), entity.getPictureURL(),
                entity.getPictureName());

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
    public List<Picture> readAll() throws InterruptedException {
        try {
            return StatementProvider.getInstance().executeStatement(SELECT_ALL_QUERY, PictureDAOImpl::extractPicture);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Picture> readById(Long id) throws InterruptedException {
        try {
            List<Picture> pictures = StatementProvider.executePreparedStatement(
                    READ_BY_ID_QUERY,
                    PictureDAOImpl::extractPicture, st -> st.setLong(1, id));
            return Optional.of(pictures.get(0));
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Picture entity, Long id) throws InterruptedException {
        throw new UnsupportedOperationException("unsupported operation");
    }

    @Override
    public boolean deleteById(Long id) throws InterruptedException {
        String sql = String.format(DELETE_BY_QUERY, PICTURE_ID, id);
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
    public boolean delete(Picture entity) throws InterruptedException {
        String sql = String.format(DELETE_FOR_STRINGS, PICTURE_NAME, entity.getPictureURL());
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
    public Optional<Picture> findPictureByName(String name) {
        String sql = String.format(SELECT_ALL_QUERY + SPACE + WHERE_QUERY_WITH_PARAM, PICTURE_NAME, name);
        try {

            List<Picture> pictures = StatementProvider.executePreparedStatement(
                    sql,
                    PictureDAOImpl::extractPicture, st -> st.setString(1, name));

            return Optional.of(pictures.get(0));

        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return Optional.empty();
        }
    }

    private static Picture extractPicture(ResultSet resultSet) throws EntityExtractionFailedException {
        try {
            return new Picture(
                    resultSet.getLong(PICTURE_ID),
                    resultSet.getString(PICTURE_URL),
                    resultSet.getString(PICTURE_NAME));


        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract picture", e);
        }
    }
}
