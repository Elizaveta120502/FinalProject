package com.epam.jwd.dao;

import com.epam.jwd.model.AuctionItem;

import java.util.Optional;


public interface AuctionItemsDAO<T> extends DBEntityDAO<AuctionItem> {

    Optional<AuctionItem> findAuctionItemByTitle(String auctionItemName) throws InterruptedException;

    int returnAmountOfItemsInStockByName(String auctionItemName) throws InterruptedException;

    int returnPriceByName(String auctionItemName) throws InterruptedException;
}
