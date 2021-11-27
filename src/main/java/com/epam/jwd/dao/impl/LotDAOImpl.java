package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.LotDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LotDAOImpl extends AbstractDAO<Lot> implements LotDAO<Lot> {


    private static final String TABLE_NAME = "lots";
    private static final String TABLE_NAME_EXTENDED = "lots join lot_status on lots.lot_status_id = lot_status.id \n" +
            "join auction_items on auction_items.id = lots.auction_items_id \n" +
            "join shipment on shipment.id = lots.shipment_id \n" +
            "join payment on payment.id =lots.payment_id \n" +
            "join shipment_methods on shipment_methods.id = shipment.shipment_methods_id \n" +
            "join payment_methods on payment_methods.id = payment.payment_methods_id";

    private static final String LOT_ID = "lots.id";
    private static final String LOT_STARTING_PRICE = "lots.starting_price";
    private static final String LOT_ITEMS_AMOUNT = "lots.items_amount";
    private static final String LOT_CURRENT_PRICE = "lots.current_price";

    private static final String LOT_STATUS_ID = "lots.lot_status_id";
    private static final String LOT_STATUS_DESCR = "lot_status.description";

    private static final String AUCTION_ITEMS_ID = "lots.auction_items_id";
    private static final String AUCTION_ITEM_TITLE = "auction_items.title";
    private static final String AUCTION_ITEM_PRICE = "auction_items.price";
    private static final String AUCTION_ITEM_IN_STOCK = "auction_items.in_stock";

    private static final String SHIPMENT_ID = "lots.shipment_id";
    private static final String SHIPMENT_EXPECTED_DATE = "shipment.expected_date";
    private static final String SHIPMENT_ACTUAL_DATE = "shipment.actual_date";
    private static final String SHIPMENT_COST = "shipment.cost";
    private static final String SHIPMENT_METHOD_ID = "shipment.shipment_methods_id";
    private static final String SHIPMENT_METHOD = "shipment_methods.description";

    private static final String PAYMENT_ID = "lots.payment_id";
    private static final String PAYMENT_TIME = "payment.time";
    private static final String PAYMENT_METHOD_ID = "payment.payment_methods_id";
    private static final String PAYMENT_METHOD = "payment_methods.description";

    private final String WHERE_QUERY = "where %s = %s";
    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private final String COMMA = ",";
    private final String ALL_COLUMNS = LOT_ID + COMMA + LOT_STARTING_PRICE + COMMA +
            LOT_ITEMS_AMOUNT +  COMMA + LOT_CURRENT_PRICE  + COMMA + LOT_STATUS_ID +
            COMMA + LOT_STATUS_DESCR + COMMA + AUCTION_ITEMS_ID + COMMA + AUCTION_ITEM_TITLE + COMMA +
            AUCTION_ITEM_PRICE + COMMA + AUCTION_ITEM_IN_STOCK +COMMA +  SHIPMENT_ID+ COMMA +SHIPMENT_EXPECTED_DATE +
            COMMA +SHIPMENT_ACTUAL_DATE + COMMA +SHIPMENT_COST + COMMA +SHIPMENT_METHOD_ID + COMMA +SHIPMENT_METHOD +
            COMMA +PAYMENT_ID + COMMA +PAYMENT_TIME+ COMMA +PAYMENT_METHOD_ID + COMMA +PAYMENT_METHOD;

    private static final String SPACE = " ";
    private final String LOT_INSERT_QUERY = "insert into " + getTableName() + SPACE +
            "values(%s,%s,%s,%s,%s,%s,%s,%s)";
    private final String SELECT_ALL = "select " + ALL_COLUMNS + " from " + TABLE_NAME_EXTENDED;

    private final String READ_BY_QUERY = SELECT_ALL+ SPACE+ WHERE_QUERY_WITH_PARAM;
    private final String UPDATE_TABLE_QUERY = "update " + getTableName() + " set %s = '%s', %s = '%s', " +
            "%s = '%s',%s = '%s', %s = '%s',%s = '%s',%s = '%s',%s = '%s' " + WHERE_QUERY;
    private final String DELETE_BY_QUERY = "delete from " + getTableName() + SPACE + WHERE_QUERY;
    private final String FIND_PRICE = SELECT_ALL +SPACE + WHERE_QUERY_WITH_PARAM;


    protected LotDAOImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


    @Override
    protected void fillEntity(PreparedStatement statement, Lot entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setInt(2, entity.getStartingPrice());
        statement.setInt(3, entity.getItemsAmount());
        statement.setInt(4, entity.getCurrentPrice());
        statement.setObject(5, entity.getLotStatus());
        statement.setObject(6, entity.getAuctionItem());
        statement.setObject(7, entity.getShipment());
        statement.setObject(8, entity.getPayment());
    }

    @Override
    public boolean create(Lot entity) {
        String sql = String.format(LOT_INSERT_QUERY, entity.getId(), entity.getStartingPrice(),
                entity.getItemsAmount(), entity.getCurrentPrice(), entity.getLotStatus().getId(), entity.getAuctionItem().getId(),
                entity.getShipment().getId(), entity.getPayment().getId());
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
    public List<Lot> readAll() {
        try {
            return StatementProvider.getInstance().executeStatement(SELECT_ALL, LotDAOImpl::extractLot);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public DBEntity readById(Long id) {
        try {
            List<Lot> accounts = StatementProvider.executePreparedStatement(
                    String.format(READ_BY_QUERY,LOT_ID),
                    LotDAOImpl::extractLot, st -> st.setLong(1, id));
            return accounts.get(0);
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
    public boolean update(Lot entity, Long id)  {
        String sql = String.format(UPDATE_TABLE_QUERY, LOT_ID, entity.getId(),
                LOT_STARTING_PRICE, entity.getStartingPrice(),
                LOT_ITEMS_AMOUNT, entity.getItemsAmount(),
                LOT_CURRENT_PRICE, entity.getCurrentPrice(),
                LOT_STATUS_ID, entity.getLotStatus().getId(),
                AUCTION_ITEMS_ID, entity.getAuctionItem().getId(),
                SHIPMENT_ID, entity.getShipment().getId(),
                PAYMENT_ID, entity.getShipment().getId(),
                LOT_ID, id);
        int executeUpdateIndicator = 0;
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
    public boolean deleteById(Long id)  {
        String sql = String.format(DELETE_BY_QUERY, LOT_ID, id);
        int executeUpdateIndicator = 0;
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
    public boolean delete(Lot entity) {
        throw new UnsupportedOperationException("can not delete lot entity without id");
    }


    @Override
    public int returnStartingPriceById(Long id) {


        String sql = String.format(FIND_PRICE,LOT_ID);
        try {
            List<Lot> lots = StatementProvider.executePreparedStatement(sql, LotDAOImpl::extractLot,
                    st -> st.setLong(1, id));

            int startingPrice = lots.get(0).getStartingPrice();
            return startingPrice;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return 0;
        }catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("no such element");
            return 0;
        }
    }

    @Override
    public int returnCurrentPriceById(Long id)
    {
        int currentPrice;
        String sql = String.format(FIND_PRICE,LOT_ID);

        try {
            List<Lot>   lots = StatementProvider.executePreparedStatement(sql, LotDAOImpl::extractLot,
                    st -> st.setLong(1, id));
             currentPrice = lots.get(0).getCurrentPrice();
            return currentPrice;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return 0;
        }catch (IndexOutOfBoundsException e){
            LoggerProvider.getLOG().error("no such element");
            return 0;
        }
    }



    @Override
    public int returnAmountOfItemsInLot(Long id) {

        String sql = String.format(FIND_PRICE, LOT_ID);

        try {
            List<Lot> lots = StatementProvider.executePreparedStatement(sql, LotDAOImpl::extractLot,
                    st -> st.setLong(1, id));
            int amountOfItems = lots.get(0).getItemsAmount();
            return amountOfItems;
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
    public LotStatus returnLotStatus(Long id) {

        String sql = String.format(READ_BY_QUERY,LOT_ID);
        try {
            return StatementProvider.executePreparedStatement(
                    sql,
                    LotDAOImpl::extractLot, st -> st.setLong(1, id)).get(0).getLotStatus();
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
    public boolean changeCurrentPrice(Long id, int newCurrentPrice) {
        String sql = String.format(FIND_PRICE,LOT_ID);

        try {
            List<Lot>   lots = StatementProvider.executePreparedStatement(sql, LotDAOImpl::extractLot,
                    st -> st.setLong(1, id));
            lots.get(0).setCurrentPrice(newCurrentPrice);
            return true;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }catch (IndexOutOfBoundsException e){
            LoggerProvider.getLOG().error("no such element");
            return false;
        }
    }

    private static Lot extractLot(ResultSet resultSet) throws EntityExtractionFailedException {
        try {
            return new Lot(resultSet.getLong(LOT_ID),
                    resultSet.getInt(LOT_STARTING_PRICE),
                    resultSet.getInt(LOT_ITEMS_AMOUNT),
                    resultSet.getInt(LOT_CURRENT_PRICE),
                    new AuctionItem(resultSet.getLong(AUCTION_ITEMS_ID),
                            resultSet.getString(AUCTION_ITEM_TITLE),
                            resultSet.getInt(AUCTION_ITEM_PRICE),
                            resultSet.getInt(AUCTION_ITEM_IN_STOCK)),
                    new Shipment(resultSet.getLong(SHIPMENT_ID),
                            resultSet.getTimestamp(SHIPMENT_EXPECTED_DATE),
                            resultSet.getTimestamp(SHIPMENT_ACTUAL_DATE),
                            resultSet.getInt(SHIPMENT_COST),
                            new ShipmentMethod(resultSet.getString(SHIPMENT_METHOD),
                                    resultSet.getLong(SHIPMENT_METHOD_ID))),
                    new Payment(resultSet.getLong(PAYMENT_ID),
                            resultSet.getTimestamp(PAYMENT_TIME),
                            new PaymentMethod(resultSet.getString(PAYMENT_METHOD),
                                    resultSet.getLong(PAYMENT_METHOD_ID))),
                    new LotStatus(resultSet.getString(LOT_STATUS_DESCR),
                            resultSet.getLong(LOT_STATUS_ID)));

        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract user", e);
        }
    }

//    public static void main(String[] args) {
//        LoggerProvider.getLOG().trace("Starting program");
//        StatementProvider.getInstance();
//        LotDAOImpl instance = new  LotDAOImpl(ConnectionPoolImpl.getInstance());
//        final List<Lot> lots;
//        AuctionItemsDAOImpl auctionItem = new AuctionItemsDAOImpl(ConnectionPoolImpl.getInstance());
//        PaymentDAOImpl paymentDAO = new PaymentDAOImpl(ConnectionPoolImpl.getInstance());
//
//      LoggerProvider.getLOG().info(instance.readById(963105L));
////       LoggerProvider.getLOG().trace("-----------------");
//    //   LoggerProvider.getLOG().info(instance.readAll());
//
//        Lot newLot = new Lot(963111L,10,3,500,auctionItem.findAuctionItemByTitle("jasmin"),
//                new Shipment(0L,null,null,
//                        0,null),paymentDAO.readAll().get(0),new LotStatus("current",1L));
//     //   LoggerProvider.getLOG().info(instance.returnStartingPriceById(963105L));
////        LoggerProvider.getLOG().trace("-----------------");
//     //   LoggerProvider.getLOG().info(instance.returnLotStatus(963105L));
////        LoggerProvider.getLOG().info(instance.returnLotStatus(963104L));
////        LoggerProvider.getLOG().trace("-----------------");
//        LoggerProvider.getLOG().info(instance.returnAmountOfItemsInLot(963105L));
////
////        LoggerProvider.getLOG().trace("-----------------");
////        LoggerProvider.getLOG().info(instance.returnStartingPriceById(963102L));
//
////       lots = instance.readAll();
////        for (Lot a : lots) {
////            LoggerProvider.getLOG().info(a);
////        }
//
//        StatementProvider.getInstance().close();
//        LoggerProvider.getLOG().trace("program end");
}
