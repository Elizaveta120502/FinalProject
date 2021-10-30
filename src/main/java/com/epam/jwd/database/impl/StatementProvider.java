package com.epam.jwd.database.impl;

import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.ResultSetExtractor;
import com.epam.jwd.database.StatementPreparator;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class StatementProvider implements AutoCloseable {
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.locking();

    public StatementProvider()
    {
        CONNECTION_POOL.init();
    }

    @Override
    public void close()  {
        CONNECTION_POOL.shutdown();
    }

    public static StatementProvider getInstance() {
        final StatementProvider INSTANCE;
        INSTANCE = new StatementProvider();
        return INSTANCE;
    }

    public static <T> List<T> executePreparedStatement(String sql, ResultSetExtractor<T> extractor,
                                                      StatementPreparator preparedStatement) throws InterruptedException {
        try {

            final Connection connection = CONNECTION_POOL.takeConnection();
            final PreparedStatement statement = connection.prepareStatement(sql);
            if (preparedStatement != null) {
                preparedStatement.accept(statement);
            }
            final ResultSet resultSet = statement.executeQuery();
            return extractor.extractAll(resultSet);

        } catch (SQLException ex) {
            LoggerProvider.getLOG().error("SQL exception ", ex);
            LoggerProvider.getLOG().error(sql + "is not correct");
        } catch (EntityExtractionFailedException ex) {
            LoggerProvider.getLOG().error("Extraction failed:", ex);
        } catch (InterruptedException ex) {
            LoggerProvider.getLOG().warn("Thread interrupted", ex);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }


    public <T> List<T> executeStatement(String sql, ResultSetExtractor<T> extractor) throws InterruptedException {
        try (final Connection connection = CONNECTION_POOL.takeConnection()) {
            try (final Statement statement = connection.createStatement()) {
                try (final ResultSet resultSet = statement.executeQuery(sql)) {
                    return extractor.extractAll(resultSet);
                }
            }
        } catch (SQLException ex) {
            LoggerProvider.getLOG().error("SQL exception ", ex);
            LoggerProvider.getLOG().error(sql + "is not correct");
        } catch (EntityExtractionFailedException ex) {
            LoggerProvider.getLOG().error("Extraction failed:", ex);
        } catch (InterruptedException ex) {
            LoggerProvider.getLOG().warn("Thread interrupted", ex);
            Thread.currentThread().interrupt();

        }
        return Collections.emptyList();
    }

    public int executeUpdate(String sql) throws InterruptedException {
        try (final Connection connection = CONNECTION_POOL.takeConnection();
             final Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException ex) {
            LoggerProvider.getLOG().error("SQL exception ", ex);
            LoggerProvider.getLOG().error(sql + "is not correct");
        } catch (InterruptedException ex) {
            LoggerProvider.getLOG().warn("Thread interrupted", ex);
            Thread.currentThread().interrupt();
        }
        return 0;
    }
}




