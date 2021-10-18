package com.epam.jwd.database;

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


    public static final ConnectionPool INSTANCE = new ConnectionPoolImpl();
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    private final Queue<ProxyConnection> availableConnections = new ConcurrentLinkedQueue();
    private final Queue<ProxyConnection> givenAwayConnections = new ConcurrentLinkedQueue<>();

    private final ReentrantLock rentrantLock = new ReentrantLock();
    Condition condition = rentrantLock.newCondition();

    private boolean initialized = false;

    @Override
    public boolean init(int connectionsAmount)
    {
        try {
            rentrantLock.lock();
            if (!initialized) {
                registerDrivers();
                initializeConnections(connectionsAmount, true);
                initialized = true;
                return true;
            }
            return false;
        }finally {
            rentrantLock.unlock();
        }
    }

    @Override
    public boolean shutdown() {
        try {
            rentrantLock.lock();
            if (initialized) {
                closeConnections();
                deregisterDrivers();
                initialized = false;
                return true;
            }
            return false;
        }finally {
            rentrantLock.unlock();
        }
    }

    @Override
    public Connection takeConnection() throws InterruptedException {
        try {
            rentrantLock.lock();
            while (availableConnections.isEmpty()) {
                //  this.wait();
                condition.await();
            }
            final ProxyConnection connection = availableConnections.poll();
            givenAwayConnections.add(connection);
            return connection;
        }finally {
            rentrantLock.unlock();
        }
    }

    @Override
    public void returnConnection(Connection connection) {
        try {
            rentrantLock.lock();
            if (givenAwayConnections.remove(connection)) {
                availableConnections.add((ProxyConnection) connection);
                //this.notifyAll();
                condition.signalAll();
            } else {
                LoggerProvider.getLOG().warn("Attempt to add unknown connection " +
                        "to Connection Pool. Connection: {}", connection);
            }
        }finally{
            rentrantLock.unlock();
        }
    }


    @Override
    public boolean isInitialized() throws CouldNotInitializeConnectionPoolError {
        try {
            rentrantLock.lock();
            return initialized;
        }finally {
            rentrantLock.unlock();
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
               LoggerProvider.getLOG().info("Initialized connection {}", connection);
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

   public static void main(String [] args) throws InterruptedException, SQLException {
        final ConnectionPoolImpl cp = new ConnectionPoolImpl();
        cp.init(3);
        final Connection conn = cp.takeConnection();
        cp.returnConnection(DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD));
        cp.shutdown();

   }
}
