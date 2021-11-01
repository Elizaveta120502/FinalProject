package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.LotDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Lot;
import com.epam.jwd.model.LotStatus;
import com.epam.jwd.model.Payment;
import com.epam.jwd.model.Shipment;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LotDAOImpl extends AbstractDAO<Lot> implements LotDAO<Lot> {


    private static final String TABLE_NAME = "lots";

    private static final String LOT_ID = "id";
    private static final String LOT_STARTING_PRICE = "starting_price";
    private static final String LOT_ITEMS_AMOUNT = "items_amount";
    private static final String LOT_CURRENT_PRICE = "current_price";

    private static final String LOT_STATUS_ID = "lot_status_id";
    private static final String AUCTION_ITEMS_ID = "auction_items_id";
    private static final String SHIPMENT_ID = "shipment_id";
    private static final String PAYMENT_ID = "payment_id";

    private static final String SPACE = " ";
    private final String LOT_INSERT_QUERY = "insert into " + getTableName() + SPACE +
            "values(%s,%s,%s,%s,%s,%s,%s,%s)";
    private final String SELECT_ALL_QUERY = "select %s,%s,%s,%s,%s,%s,%s,%s from " + getTableName();


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
        statement.setLong(5, entity.getLotStatus().getId());
        statement.setLong(6, entity.getAuctionItem().getId());
        statement.setLong(7, entity.getShipment().getId());
        statement.setLong(8, entity.getPayment().getId());
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
    public List<Lot> readAll() throws InterruptedException {
        return null;// String sql = String.format(SELECT_ALL_QUERY,)
    }

    @Override
    public Optional<Lot> readById(Long id) throws InterruptedException {
        return Optional.empty();
    }

    @Override
    public boolean update(Lot entity, Long id) throws InterruptedException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws InterruptedException {
        return false;
    }

    @Override
    public boolean delete(Lot entity) throws InterruptedException {
        return false;
    }


    @Override
    public int returnStartingPriceById(Long id) {
        return 0;
    }

    @Override
    public int returnCurrentPriceById(Long id) {
        return 0;
    }

    @Override
    public int returnAmountOfItemsInLot(Long id) {
        return 0;
    }

    @Override
    public LotStatus returnLotStatus(Lot lot) {
        return null;
    }

    @Override
    public Shipment returnShipmentByLotId(Long id) {
        return null;
    }

    @Override
    public Payment returnPaymentByLotId(Long id) {
        return null;
    }

//    private static Lot extractLot(ResultSet resultSet) throws EntityExtractionFailedException {
//        try {
//            return new Lot (resultSet.getLong(LOT_ID),
//                    resultSet.getInt(LOT_STARTING_PRICE),
//                    resultSet.getInt(LOT_ITEMS_AMOUNT),
//                    resultSet.getInt(LOT_CURRENT_PRICE),
//                    resultSet.getLong(LOT_STATUS_ID),
//                    resultSet.getLong(AUCTION_ITEMS_ID),
//                    resultSet.getLong(SHIPMENT_ID),
//                    resultSet.getLong(PAYMENT_ID));
//
//        } catch (SQLException e) {
//            LoggerProvider.getLOG().error("could not extract value from result set", e);
//            throw new EntityExtractionFailedException("failed to extract user", e);
//        }
//    }
}
