package com.epam.jwd.database;

import com.epam.jwd.exception.CouldNotInitializeConnectionPool;
import com.epam.jwd.logger.LoggerProvider;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionPoolImpl implements ConnectionPool {


    public static final ConnectionPool INSTANCE = new ConnectionPoolImpl();
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    private final Queue<ProxyConnection> availableConnections = new ConcurrentLinkedQueue();
    private final Queue<ProxyConnection> givenAwayConnections = new ConcurrentLinkedQueue<>();

    private boolean initialized = false;

    @Override
    public boolean init(int connectionsAmount)
    {
        if (!initialized){
            registerDrivers();
            initializeConnections(connectionsAmount,true);
            initialized = true;
            return true;
        }
             return false;
    }

    @Override
    public boolean shutdown() {
       if (initialized){
           closeConnections();
           deregisterDrivers();
           initialized = false;
           return true;
       }
       return false;
    }

    @Override
    public Connection takeConnection() throws InterruptedException {
        while(availableConnections.isEmpty()){
            this.wait();
        }
        final ProxyConnection connection = availableConnections.poll();
        givenAwayConnections.add(connection);
        return connection;
    }

    @Override
    public void returnConnection(Connection connection) {
        if (givenAwayConnections.remove(connection)){
            availableConnections.add((ProxyConnection) connection);
            this.notifyAll();
        } else {
            LoggerProvider.getLOG().warn("Attempt to add unknown connection " +
                    "to Connection Pool. Connection: {}", connection);
        }
    }


    @Override
    public boolean isInitialized() throws CouldNotInitializeConnectionPool {
        return initialized;
    }


   private static void registerDrivers(){
       LoggerProvider.getLOG().trace("database drivers are registered");
       try {
           DriverManager.registerDriver(DriverManager.getDriver(DATABASE_URL));
       }catch (SQLException e){
           LoggerProvider.getLOG().error("could not register database drivers");
           throw new CouldNotInitializeConnectionPool("Unsuccessful db driver registration attempt", e);
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
               throw new CouldNotInitializeConnectionPool("Failed to create connection", e);
           }
       }
   }

   public static void main(String [] args) throws InterruptedException, SQLException {
        final ConnectionPoolImpl cp = new ConnectionPoolImpl();
        cp.init(1);
        final Connection conn = cp.takeConnection();
        cp.returnConnection(DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD));
        cp.shutdown();

   }
}
