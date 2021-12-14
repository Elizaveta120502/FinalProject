package com.epam.jwd.service.impl;

import com.epam.jwd.dao.AuctionItemsDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.AuctionItem;
import com.epam.jwd.model.Picture;
import com.epam.jwd.service.AuctionItemsService;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AuctionItemServiceImpl implements AuctionItemsService {

    private static final ReentrantLock reentrantLock = new ReentrantLock();
    Condition condition = reentrantLock.newCondition();

    private final AuctionItemsDAO auctionItemsDAO;

    public AuctionItemServiceImpl(AuctionItemsDAO auctionItemsDAO) {
        this.auctionItemsDAO = auctionItemsDAO;
    }

    @Override
    public List<AuctionItem> viewProducts() {
        try {
            List<AuctionItem> items = DAOFactory.getInstance().getAuctionItemsDAO().readAll();
            return items;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<AuctionItem> addProduct(String title, int price, int inStock, String pictureURL) {
        try {
            if (title == null || price <= 0 || inStock <= 0) {
                return Optional.empty();
            } else {
                long id = (long) (30 + Math.random() * 9_223_372);
                Picture picture = new Picture(id, pictureURL, title + id);
                DAOFactory.getInstance().getPictureDAO().create(picture);
                AuctionItem newAuctionItem = new AuctionItem(id, title, price, inStock, picture);
                DAOFactory.getInstance().getAuctionItemsDAO().create(newAuctionItem);
                return Optional.of(newAuctionItem);
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> deleteProduct(Long id) {
        try {
            if (id > 0) {
                DAOFactory.getInstance().getAuctionItemsDAO().deleteById(id);
                DAOFactory.getInstance().getPictureDAO().deleteById(id);
                DAOFactory.getInstance().getLotDAO().deleteByAuctionItemId(id);
                return Optional.of(id);
            } else {
                return Optional.empty();
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public List<AuctionItem> sortProductsByAvailability() {
        List<AuctionItem> items;
        CopyOnWriteArrayList<AuctionItem> zeroItems = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<AuctionItem> notZeroItems = new CopyOnWriteArrayList<>();
        try {
            items = DAOFactory.getInstance().getAuctionItemsDAO().readAll();
            ListIterator<AuctionItem> itemsIterator = items.listIterator();
            if (!items.isEmpty()) {
                while (itemsIterator.hasNext()) {
                    if (itemsIterator.next().getInStoke() == 0) {
                        itemsIterator.previous();
                        zeroItems.add(itemsIterator.next());

                    } else {
                        itemsIterator.previous();
                        notZeroItems.add(itemsIterator.next());
                    }
                }
                List<AuctionItem> sortedItems = new CopyOnWriteArrayList<>();
                sortedItems.addAll(notZeroItems);
                sortedItems.addAll(notZeroItems.size(), zeroItems);
                return sortedItems;
            } else {
                return Collections.emptyList();
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }


}
