package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.ShipmentDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.DBEntity;
import com.epam.jwd.model.Shipment;
import com.epam.jwd.model.ShipmentMethod;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShipmentDAOImpl extends AbstractDAO<Shipment> implements ShipmentDAO<Shipment> {
    private static final String TABLE_NAME = "shipment";
    private static final String TABLE_NAME_EXTENDED = "shipment join shipment_methods on " +
            "shipment.shipment_methods_id = shipment_methods.id";

    private static final String SHIPMENT_ID = "shipment.id";
    private static final String SHIPMENT_EXPECTED_DATE = "shipment.expected_date";
    private static final String SHIPMENT_ACTUAL_DATE = "shipment.actual_date";
    private static final String SHIPMENT_COST = "shipment.cost";
    private static final String SHIPMENT_METHOD_ID = "shipment.shipment_methods_id";
    private static final String SHIPMENT_METHOD = "shipment_methods.description";

    private final String WHERE_QUERY = "where %s = %s";
    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private final String COMMA = ",";
    private final String ALL_COLUMNS = SPACE+ SHIPMENT_ID+ COMMA +SHIPMENT_EXPECTED_DATE +
            COMMA +SHIPMENT_ACTUAL_DATE + COMMA +SHIPMENT_COST + COMMA +SHIPMENT_METHOD_ID + COMMA +SHIPMENT_METHOD;

    private static final String SPACE = " ";
    private final String INSERT_QUERY = "insert into " + TABLE_NAME + SPACE +
            "values(%s,'%s','%s',%s,%s)";
    private final String SELECT_ALL = "select" + ALL_COLUMNS +" from " + TABLE_NAME_EXTENDED;
    private final String READ_BY_QUERY = SELECT_ALL+ SPACE+ WHERE_QUERY_WITH_PARAM;
    private final String UPDATE_TABLE_QUERY = "update " + getTableName() + " set %s = '%s', %s = '%s', " +
            "%s = '%s',%s = '%s', %s = '%s' " + WHERE_QUERY;
    private final String DELETE_BY_QUERY = "delete from " + TABLE_NAME + SPACE + WHERE_QUERY;


    protected ShipmentDAOImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME_EXTENDED;
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Shipment entity) throws SQLException {
        statement.setLong(1,entity.getId());
        statement.setTimestamp(2,entity.getExpectedDate());
        statement.setTimestamp(3,entity.getActualDate());
        statement.setInt(4,entity.getCost());
        statement.setObject(5,entity.getShipmentMethod());


    }

    @Override
    public boolean create(Shipment entity)  {
        String sql = String.format(INSERT_QUERY,entity.getId(),
               entity.getExpectedDate(),
                entity.getActualDate(),
                entity.getCost(),entity.getShipmentMethod().getId());

        try {
            int executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
            return executeUpdateIndicator == 1;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }

    @Override
    public List<Shipment> readAll()  {
        try {
            return StatementProvider.getInstance().executeStatement(SELECT_ALL, ShipmentDAOImpl::extractShipment);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public DBEntity readById(Long id)  {
        try {
            List<Shipment> shipments = StatementProvider.executePreparedStatement(
                    String.format(READ_BY_QUERY,SHIPMENT_ID),
                    ShipmentDAOImpl::extractShipment, st -> st.setLong(1, id));
            return shipments.get(0);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return null;
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("no such element");
            return null;
        }
    }

    @Override
    public boolean update(Shipment entity, Long id)  {
        String sql = String.format(UPDATE_TABLE_QUERY,SHIPMENT_ID,entity.getId(),
                SHIPMENT_ACTUAL_DATE,entity.getActualDate(),
                SHIPMENT_EXPECTED_DATE,entity.getExpectedDate(),
                SHIPMENT_COST,entity.getCost(),
                SHIPMENT_METHOD_ID,entity.getShipmentMethod().getId(),SHIPMENT_ID,id);
        LoggerProvider.getLOG().info(sql);
        try {
            int executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
            return executeUpdateIndicator == 1;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = String.format(DELETE_BY_QUERY, SHIPMENT_ID, id);

        try {
           int  executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
            return executeUpdateIndicator == 1;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean delete(Shipment entity)  {
        String sql = String.format(DELETE_BY_QUERY, SHIPMENT_ID, entity.getId());

        try {
            int  executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
            return executeUpdateIndicator == 1;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }

    @Override
    public Timestamp returnExpectedDate(Long id) {
        String sql = String.format(READ_BY_QUERY, SHIPMENT_ID);

        try {
            List<Shipment> shipments = StatementProvider.executePreparedStatement(sql, ShipmentDAOImpl::extractShipment,
                    st -> st.setLong(1, id));
            Timestamp expectedDate = shipments.get(0).getExpectedDate();
            return expectedDate;
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
    public int returnShipmentCost(Long id) {
        String sql = String.format(READ_BY_QUERY, SHIPMENT_ID);

        try {
            List<Shipment> shipments = StatementProvider.executePreparedStatement(sql, ShipmentDAOImpl::extractShipment,
                    st -> st.setLong(1, id));
            int shipmentCost = shipments.get(0).getCost();
            return shipmentCost;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return 0;
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return 0;
        }
    }

    @Override
    public ShipmentMethod returnShipmentMethod(Long id) {
        String sql = String.format(READ_BY_QUERY, SHIPMENT_ID);

        try {
            List<Shipment> shipments = StatementProvider.executePreparedStatement(sql, ShipmentDAOImpl::extractShipment,
                    st -> st.setLong(1, id));
            ShipmentMethod shipmentMethod = shipments.get(0).getShipmentMethod();
            return shipmentMethod;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return null;
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return null;
        }
    }

    private static Shipment extractShipment(ResultSet resultSet) throws EntityExtractionFailedException {
        try {
            return new Shipment(
                    resultSet.getLong(SHIPMENT_ID),
                    resultSet.getTimestamp(SHIPMENT_EXPECTED_DATE),
                    resultSet.getTimestamp(SHIPMENT_ACTUAL_DATE),
                    resultSet.getInt(SHIPMENT_COST),
                    new ShipmentMethod(
                            resultSet.getString(SHIPMENT_METHOD),
                            resultSet.getLong(SHIPMENT_METHOD_ID)));

        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract shipment", e);
        }
    }


//    public static void main(String[] args) {
//        LoggerProvider.getLOG().trace("Starting program");
//        StatementProvider.getInstance();
//        ShipmentDAOImpl instance = new ShipmentDAOImpl(ConnectionPoolImpl.getInstance());
//        List<Shipment> shipments;
//        LocalDateTime actualTime = LocalDateTime.now();
//        Shipment shipment = new Shipment(25L,Timestamp.valueOf("2021-11-05 09:01:15"),
//                Timestamp.valueOf(actualTime),15,new ShipmentMethod("pickup",3L));
//        LoggerProvider.getLOG().info(instance.returnShipmentMethod(4L));
//
//
//       shipments = instance.readAll();
//        for (Shipment a : shipments) {
//            LoggerProvider.getLOG().info(a);
//        }
//
//        StatementProvider.getInstance().close();
//        LoggerProvider.getLOG().trace("program end");
//    }
}
