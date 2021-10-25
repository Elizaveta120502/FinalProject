package com.epam.jwd.database.impl;

import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.exception.CouldNotInitializeConnectionPoolError;
import com.epam.jwd.logger.LoggerProvider;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPoolImpl implements ConnectionPool {



    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/flowersauction";
    public static final String DATABASE_USER = "root";
    public static final String DATABASE_PASSWORD = "root";

    private static final int AMOUNT_OF_CONNECTIONS = 8;

    private final Queue<ProxyConnection> availableConnections = new ConcurrentLinkedQueue();
    private final Queue<ProxyConnection> givenAwayConnections = new ConcurrentLinkedQueue<>();

    private final ReentrantLock reentrantLock = new ReentrantLock();
    Condition condition = reentrantLock.newCondition();

    private boolean initialized = false;


    public ConnectionPoolImpl() {
    }

    public static ConnectionPoolImpl getInstance(){
        final ConnectionPoolImpl INSTANCE;
        INSTANCE = new ConnectionPoolImpl();
        return INSTANCE;
    }


    @Override
    public boolean init()
    {
        try {
            reentrantLock.lock();
            if (!initialized) {
                registerDrivers();
                initializeConnections(AMOUNT_OF_CONNECTIONS, true);
                initialized = true;
                return true;
            }
            return false;
        }finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public boolean shutdown() {
        try {
            reentrantLock.lock();
            if (initialized) {
                closeConnections();
                deregisterDrivers();
                initialized = false;
                return true;
            }
            return false;
        }finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public Connection takeConnection() throws InterruptedException {
        try {
            reentrantLock.lock();
            while (availableConnections.isEmpty()) {
                //  this.wait();
                condition.await();
            }
            final ProxyConnection connection = availableConnections.poll();
            givenAwayConnections.add(connection);
            return connection;
        }finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public void returnConnection(Connection connection) {
        try {
            reentrantLock.lock();
            if (givenAwayConnections.remove(connection)) {
                availableConnections.add((ProxyConnection) connection);
                //this.notifyAll();
                condition.signalAll();
            } else {
                LoggerProvider.getLOG().warn("Attempt to add unknown connection " +
                        "to Connection Pool. Connection: {}", connection);
            }
        }finally{
            reentrantLock.unlock();
        }
    }


    @Override
    public boolean isInitialized() throws CouldNotInitializeConnectionPoolError {
        try {
            reentrantLock.lock();
            return initialized;
        }finally {
            reentrantLock.unlock();
        }
    }


   private static void registerDrivers(){
       LoggerProvider.getLOG().trace("database drivers are registered");
       try {
           DriverManager.registerDriver(DriverManager.getDriver(DATABASE_URL));
       }catch (SQLException e){
           LoggerProvider.getLOG().error("could not register database drivers");
           throw new CouldNotInitializeConnectionPoolError("Unsuccessful db driver registration attempt", e);
       }
   }

   private static void deregisterDrivers(){
       LoggerProvider.getLOG().trace("starting deregister drivers");
       final Enumeration<Driver>  drivers = DriverManager.getDrivers();
       while (drivers.hasMoreElements()) {
           try {
               DriverManager.deregisterDriver(drivers.nextElement());
           } catch (SQLException e) {
               LoggerProvider.getLOG().error("could not deregister driver", e);
           }
       }

   }

   private void closeConnection(ProxyConnection proxyConnection){
        try{
            proxyConnection.realClose();
            LoggerProvider.getLOG().trace("close connection {}",proxyConnection);
        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not close connection ",e);
        }

   }

   private void closeConnections(Collection<ProxyConnection> collectionOfConnections){
      for(ProxyConnection pr : collectionOfConnections){
          closeConnection(pr);
      }
   }

   private void closeConnections(){
        closeConnections(this.availableConnections);
       closeConnections(this.givenAwayConnections);
   }

   private void initializeConnections(int amount, boolean failOnConnectionException){
       try {
           for (int i = 0; i < amount; i++) {
               final Connection connection = DriverManager
                       .getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
               LoggerProvider.getLOG().trace("Initialized connection {}", connection);
               final ProxyConnection proxyConnection = new ProxyConnection(connection, this);
               availableConnections.add(proxyConnection);
           }
       } catch (SQLException e) {
           LoggerProvider.getLOG().error("Error occurred creating connection");
           if (failOnConnectionException) {
               throw new CouldNotInitializeConnectionPoolError("Failed to create connection", e);
           }
       }
   }



//   public static void main(String [] args) throws InterruptedException, SQLException {
//        final ConnectionPoolImpl cp = new ConnectionPoolImpl();
//        cp.init(3);
//        final Connection conn = cp.takeConnection();
//        cp.returnConnection(DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD));
//        cp.shutdown();
//
//   }
}
