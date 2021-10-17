package com.epam.jwd.database;

import com.epam.jwd.exception.CouldNotInitializeConnectionPool;

import java.sql.Connection;

public interface ConnectionPool {

    boolean init(int connectionsAmount);

    boolean shutdown();

    Connection takeConnection() throws InterruptedException;

    void returnConnection(Connection connection);

    boolean isInitialized() throws CouldNotInitializeConnectionPool;

    static ConnectionPool locking() {
        return ConnectionPoolImpl.INSTANCE;
    }
}
