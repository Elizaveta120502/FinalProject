package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.LotDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.ConnectionPoolImpl;
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
    private static final String TABLE_NAME_EXTENDED = "lots inner join lot_status on lots.lot_status_id = lot_status.id  \n" +
            "            left join auction_items on auction_items.id = lots.auction_items_id \n" +
            "            left join pictures on auction_items.pictures_id = pictures.id \n" +
            "            left join account on account.id = lots.account_id\n" +
            "            left join role on role.id = account.role_id\n" +
            "            left join status on account.status_id = status.id\n" +
            "            left join shipment on shipment.id = lots.shipment_id\n" +
            "            left join shipment_methods on shipment_methods.id = shipment.shipment_methods_id \n" +
            "            left join payment on payment.id =lots.payment_id \n" +
            "            left join payment_methods on payment_methods.id = payment.payment_methods_id";

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

    private static final String ACCOUNT_ID = "lots.account_id";
    private static final String ACCOUNT_LOGIN = "account.login";
    private static final String ACCOUNT_PASSWORD = "account.password";
    private static final String ACCOUNT_EMAIL = "account.email";

    private static final String ACCOUNT_ROLE_ID = "account.role_id";
    private static final String ACCOUNT_ROLE_NAME = "role.role_name";

    private static final String ACCOUNT_STATUS_ID = "account.status_id";
    private static final String ACCOUNT_STATUS_DESCRIPTION = "status.description";

    private static final String BENEFIT_ID = "status.benefits_id";
    private static final String BENEFIT_SIZE = "benefits.size";
    private static final String PICTURE_ID = "pictures.id";
    private static final String PICTURE_URL = "pictures.url";
    private static final String PICTURE_NAME = "pictures.name";


    private final String WHERE_QUERY = "where %s = %s";
    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private final String COMMA = ",";
    private final String ALL_COLUMNS = LOT_ID + COMMA + LOT_STARTING_PRICE + COMMA +
            LOT_ITEMS_AMOUNT + COMMA + LOT_CURRENT_PRICE + COMMA + LOT_STATUS_ID +
            COMMA + LOT_STATUS_DESCR + COMMA + AUCTION_ITEMS_ID + COMMA + AUCTION_ITEM_TITLE + COMMA +
            AUCTION_ITEM_PRICE + COMMA + AUCTION_ITEM_IN_STOCK + COMMA + PICTURE_ID + COMMA
            + PICTURE_URL + COMMA + PICTURE_NAME + COMMA
            + SHIPMENT_ID + COMMA + SHIPMENT_EXPECTED_DATE +
            COMMA + SHIPMENT_ACTUAL_DATE + COMMA + SHIPMENT_COST + COMMA + SHIPMENT_METHOD_ID + COMMA + SHIPMENT_METHOD +
            COMMA + PAYMENT_ID + COMMA + PAYMENT_TIME + COMMA + PAYMENT_METHOD_ID + COMMA + PAYMENT_METHOD + COMMA +
            ACCOUNT_ID + COMMA + ACCOUNT_LOGIN + COMMA + ACCOUNT_PASSWORD + COMMA + ACCOUNT_EMAIL + COMMA + ACCOUNT_ROLE_ID + COMMA
            + ACCOUNT_ROLE_NAME + COMMA + ACCOUNT_STATUS_ID + COMMA + ACCOUNT_STATUS_DESCRIPTION;

    private static final String SPACE = " ";
    private final String LOT_INSERT_QUERY = "insert into lots" + SPACE +
            "values(%s,%s,%s,%s,%s,%s,%s,%s,%s)";
    private final String SELECT_ALL = "select " + ALL_COLUMNS + " from " + TABLE_NAME_EXTENDED;

    private final String READ_BY_QUERY = SELECT_ALL + SPACE + WHERE_QUERY_WITH_PARAM;
    private final String UPDATE_TABLE_QUERY = "update " + getTableName() + " set  %s = '%s', " +
            "%s = '%s',%s = '%s', %s = '%s',%s = '%s',%s = '%s',%s = '%s',%s = '%s' " + WHERE_QUERY;
    private final String DELETE_BY_QUERY = "delete from lots" + SPACE + WHERE_QUERY;
    private final String FIND_PRICE = SELECT_ALL + SPACE + WHERE_QUERY_WITH_PARAM;




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
        statement.setObject(9, entity.getAccount());

    }

    @Override
    public boolean create(Lot entity) {
        String sql = String.format(LOT_INSERT_QUERY, entity.getId(), entity.getStartingPrice(),
                entity.getItemsAmount(), entity.getCurrentPrice(), entity.getLotStatus().getId(), entity.getAuctionItem().getId(),
                entity.getShipment().getId(), entity.getPayment().getId(), entity.getAccount().getId());
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
    public Optional<Lot> readById(Long id) {
        try {
            List<Lot> accounts = StatementProvider.executePreparedStatement(
                    String.format(READ_BY_QUERY, LOT_ID),
                    LotDAOImpl::extractLot, st -> st.setLong(1, id));
            return Optional.of(accounts.get(0));
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("no such element");
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Lot entity, Long id) {
        String sql = String.format(UPDATE_TABLE_QUERY,
                LOT_STARTING_PRICE, entity.getStartingPrice(),
                LOT_ITEMS_AMOUNT, entity.getItemsAmount(),
                LOT_CURRENT_PRICE, entity.getCurrentPrice(),
                LOT_STATUS_ID, entity.getLotStatus().getId(),
                AUCTION_ITEMS_ID, entity.getAuctionItem().getId(),
                SHIPMENT_ID, entity.getShipment().getId(),
                PAYMENT_ID, entity.getPayment().getId(),
                ACCOUNT_ID, entity.getAccount().getId(),
                LOT_ID, id);
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
    public boolean deleteById(Long id) {
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
    public boolean deleteByAuctionItemId(Long auctionItemId){
        String sql = String.format(DELETE_BY_QUERY,AUCTION_ITEMS_ID,auctionItemId);
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


        String sql = String.format(FIND_PRICE, LOT_ID);
        try {
            List<Lot> lots = StatementProvider.executePreparedStatement(sql, LotDAOImpl::extractLot,
                    st -> st.setLong(1, id));

            int startingPrice = lots.get(0).getStartingPrice();
            return startingPrice;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return 0;
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("no such element");
            return 0;
        }
    }

    @Override
    public int returnCurrentPriceById(Long id) {
        int currentPrice;
        String sql = String.format(FIND_PRICE, LOT_ID);

        try {
            List<Lot> lots = StatementProvider.executePreparedStatement(sql, LotDAOImpl::extractLot,
                    st -> st.setLong(1, id));
            currentPrice = lots.get(0).getCurrentPrice();
            return currentPrice;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return 0;
        } catch (IndexOutOfBoundsException e) {
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

        String sql = String.format(READ_BY_QUERY, LOT_ID);
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
        String sql = String.format(FIND_PRICE, LOT_ID);

        try {
            List<Lot> lots = StatementProvider.executePreparedStatement(sql, LotDAOImpl::extractLot,
                    st -> st.setLong(1, id));
            lots.get(0).setStartingPrice(newCurrentPrice);
            return true;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        } catch (IndexOutOfBoundsException e) {
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
                    LotStatus.of(resultSet.getString(LOT_STATUS_DESCR)),
                    new AuctionItem(resultSet.getLong(AUCTION_ITEMS_ID),
                            resultSet.getString(AUCTION_ITEM_TITLE),
                            resultSet.getInt(AUCTION_ITEM_PRICE),
                            resultSet.getInt(AUCTION_ITEM_IN_STOCK),
                            new Picture(resultSet.getLong(PICTURE_ID),
                                    resultSet.getString(PICTURE_URL),
                                    resultSet.getString(PICTURE_NAME))),
                    new Shipment(resultSet.getLong(SHIPMENT_ID),
                            resultSet.getTimestamp(SHIPMENT_EXPECTED_DATE),
                            resultSet.getTimestamp(SHIPMENT_ACTUAL_DATE),
                            resultSet.getInt(SHIPMENT_COST),
                            ShipmentMethod.of(SHIPMENT_METHOD)),
                    new Payment(resultSet.getLong(PAYMENT_ID),
                            resultSet.getTimestamp(PAYMENT_TIME),
                            PaymentMethod.of(PAYMENT_METHOD)),
                    new Account(resultSet.getLong(ACCOUNT_ID),
                            resultSet.getString(ACCOUNT_LOGIN),
                            resultSet.getString(ACCOUNT_PASSWORD),
                            resultSet.getString(ACCOUNT_EMAIL),
                            Role.of(resultSet.getString(ACCOUNT_ROLE_NAME)),
                            Status.of(resultSet.getString(ACCOUNT_STATUS_DESCRIPTION))));

        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract lot", e);
        }
    }

}

