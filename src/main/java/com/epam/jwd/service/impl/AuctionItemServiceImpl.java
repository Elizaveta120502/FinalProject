package com.epam.jwd.service.impl;

import com.epam.jwd.dao.AuctionItemsDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.AuctionItem;
import com.epam.jwd.service.AuctionItemsService;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
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
    public boolean addProduct(String title, int price, int inStock) {
        try {
            if (title == null || price <= 0 || inStock <= 0) {
                return Boolean.FALSE;
            } else {
                long id = DAOFactory.getInstance().getAuctionItemsDAO().readAll().size() + 1;
                AuctionItem newAuctionItem = new AuctionItem(id, title, price, inStock,null);
                return DAOFactory.getInstance().getAuctionItemsDAO().create(newAuctionItem);
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean deleteProduct(Long id) {
        try {
            if (id > 0 && id < DAOFactory.getInstance().getLotDAO().readAll().size()) {
                return DAOFactory.getInstance().getAuctionItemsDAO().deleteById(id);
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
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
