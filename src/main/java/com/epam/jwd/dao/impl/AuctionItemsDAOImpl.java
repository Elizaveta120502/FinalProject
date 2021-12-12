package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.AuctionItemsDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.AuctionItem;
import com.epam.jwd.model.Picture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AuctionItemsDAOImpl extends AbstractDAO<AuctionItem> implements AuctionItemsDAO<AuctionItem> {

    private static final String TABLE_NAME_EXTENDED = "auction_items join pictures on pictures.id = auction_items.pictures_id ";
    private static final String TABLE_NAME = "auction_items";
    private static final String AUCTION_ITEM_ID = "auction_items.id";
    private static final String AUCTION_ITEM_TITLE = "auction_items.title";
    private static final String AUCTION_ITEM_PRICE = "auction_items.price";
    private static final String AUCTION_ITEMS_IN_STOCK = "auction_items.in_stock";

    private static final String PICTURE_ID = "auction_items.pictures_id";
    private static final String PICTURE_URL = "pictures.url";
    private static final String PICTURES_NAME = "pictures.name";

    private static final String SPACE = " ";
    private final String WHERE_QUERY = "where %s = %s";
    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private final String WHERE_QUERY_FOR_STRING = "where %s = '%s'";

    private final String SELECT_ALL_QUERY = String.format("select %s,%s,%s,%s,%s,%s,%s from " + getTableName(),
            AUCTION_ITEM_ID, AUCTION_ITEM_TITLE, AUCTION_ITEM_PRICE, AUCTION_ITEMS_IN_STOCK,
            PICTURE_ID, PICTURE_URL, PICTURES_NAME);

    private final String INSERT_INTO_QUERY = "insert into  " + TABLE_NAME +
            SPACE + "values (%s,'%s',%s,%s,%s)";

    private final String READ_BY_ID_QUERY = String.format("select %s,%s,%s,%s,%s from %s where %s = ?",
            AUCTION_ITEM_ID, AUCTION_ITEM_TITLE, AUCTION_ITEM_PRICE, AUCTION_ITEMS_IN_STOCK, PICTURE_ID, getTableName(), AUCTION_ITEM_ID);

    private final String UPDATE_TABLE_QUERY = "update " + getTableName() + " set %s = '%s', %s = '%s', %s = '%s',%s = '%s',%s = '%s'" + WHERE_QUERY;

    private final String DELETE_ALL = "delete from " + getTableName();
    private final String DELETE_BY_QUERY = "delete from " + TABLE_NAME + SPACE + WHERE_QUERY;

    private final String FIND_ITEM_BY_QUERY = String.format("select %s,%s,%s,%s,%s from", AUCTION_ITEM_ID, AUCTION_ITEM_TITLE,
            AUCTION_ITEM_PRICE, AUCTION_ITEMS_IN_STOCK, PICTURE_ID) + SPACE + getTableName() + SPACE + WHERE_QUERY_WITH_PARAM;


    private final String FIND_ITEMS_IN_STOKE_BY_TITLE = "select " + AUCTION_ITEMS_IN_STOCK + " from " + getTableName() +
            SPACE + WHERE_QUERY_WITH_PARAM;

    private final String DELETE_FOR_STRINGS = "delete from " + getTableName() + SPACE + WHERE_QUERY_FOR_STRING;

    protected AuctionItemsDAOImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME_EXTENDED;
    }

    @Override
    public boolean create(AuctionItem entity) {
        String sql = String.format(INSERT_INTO_QUERY, entity.getId(),
                entity.getTitle(), entity.getPrice(), entity.getInStoke(),entity.getPicture().getId());

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
    public List<AuctionItem> readAll() {
        try {
            return StatementProvider.getInstance().executeStatement(SELECT_ALL_QUERY, AuctionItemsDAOImpl::extractAuctionItem);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }


    @Override
    public Optional<AuctionItem> readById(Long id) {
        try {
            List<AuctionItem> items = StatementProvider.executePreparedStatement(
                    READ_BY_ID_QUERY,
                    AuctionItemsDAOImpl::extractAuctionItem, st -> st.setLong(1, id));
            return Optional.of(items.get(0));
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();

        }
    }

    @Override
    public boolean update(AuctionItem entity, Long id) {
        String sql = String.format(UPDATE_TABLE_QUERY, AUCTION_ITEM_ID, entity.getId(), AUCTION_ITEM_TITLE,
                entity.getTitle(), AUCTION_ITEM_PRICE,
                entity.getPrice(), AUCTION_ITEMS_IN_STOCK, entity.getInStoke(), AUCTION_ITEM_ID, id);
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
        String sql = String.format(DELETE_BY_QUERY, AUCTION_ITEM_ID, id);
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
    public boolean delete(AuctionItem entity) {
        String sql = String.format(DELETE_FOR_STRINGS, AUCTION_ITEM_TITLE, entity.getTitle());
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
    public AuctionItem findAuctionItemByTitle(String auctionItemName) {
        String sql = String.format(FIND_ITEM_BY_QUERY, AUCTION_ITEM_TITLE, auctionItemName);
        try {
            return StatementProvider.executePreparedStatement(
                    sql,
                    AuctionItemsDAOImpl::extractAuctionItem, st -> st.setString(1, auctionItemName)).get(0);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return null;
        }
    }

    @Override
    public int returnAmountOfItemsInStockByName(String auctionItemName) {
        int amount = 0;
        String sql = String.format(FIND_ITEM_BY_QUERY, AUCTION_ITEM_TITLE, auctionItemName);
        List<AuctionItem> items = null;
        try {
            items = StatementProvider.executePreparedStatement(sql, AuctionItemsDAOImpl::extractAuctionItem,
                    st -> st.setString(1, auctionItemName));
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }
        for (AuctionItem item : items) {
            amount = item.getInStoke();
        }
        return amount;
    }

    @Override
    public int returnPriceByName(String auctionItemName) {
        int price = 0;
        String sql = String.format(FIND_ITEM_BY_QUERY, AUCTION_ITEM_TITLE, auctionItemName);
        List<AuctionItem> items = null;
        try {
            items = StatementProvider.executePreparedStatement(sql, AuctionItemsDAOImpl::extractAuctionItem,
                    st -> st.setString(1, auctionItemName));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (AuctionItem item : items) {
            price = item.getPrice();
        }
        return price;
    }


    @Override
    protected void fillEntity(PreparedStatement statement, AuctionItem entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getTitle());
        statement.setInt(3, entity.getPrice());
        statement.setInt(4, entity.getInStoke());
        statement.setObject(5,entity.getPicture());
    }

    private static AuctionItem extractAuctionItem(ResultSet resultSet) throws EntityExtractionFailedException {
        try {
            return new AuctionItem(
                    resultSet.getLong(AUCTION_ITEM_ID),
                    resultSet.getString(AUCTION_ITEM_TITLE),
                    resultSet.getInt(AUCTION_ITEM_PRICE),
                    resultSet.getInt(AUCTION_ITEMS_IN_STOCK),
                    new Picture(resultSet.getLong(PICTURE_ID),
                            resultSet.getString(PICTURE_URL),
                            resultSet.getString(PICTURES_NAME)));

        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract auction item", e);
        }
    }


}
