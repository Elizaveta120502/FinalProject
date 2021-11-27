package com.epam.jwd.service.impl;

import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.AuctionItem;
import com.epam.jwd.service.AuctionItemsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AuctionItemServiceImpl implements AuctionItemsService {
    @Override
    public List<AuctionItem> viewProducts() {
        try {
            List<AuctionItem> items =   DAOFactory.getInstance().getAuctionItemsDAO().readAll();
            return items;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public boolean addProduct(Long id,String title, int price,int inStock) {
        AuctionItem newAuctionItem = new AuctionItem(id,title,price,inStock);
        try {
           return DAOFactory.getInstance().getAuctionItemsDAO().create(newAuctionItem);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean deleteProduct(Long id) {
        try {
           return  DAOFactory.getInstance().getAuctionItemsDAO().deleteById(id);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }

    @Override
    public List<AuctionItem> sortProductsByAvailability() {
        List<AuctionItem> items ;
        ArrayList<AuctionItem> zeroItems = new ArrayList<>();
        try {
            items = DAOFactory.getInstance().getAuctionItemsDAO().readAll();
            for (AuctionItem item : items){
                if (item.getInStoke() ==0){
                    zeroItems.add(item);
                    items.remove(item);
                }
            }
            items.addAll(zeroItems);
            return items;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }
}
