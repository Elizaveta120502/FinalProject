package com.epam.jwd.dao;

import com.epam.jwd.model.AuctionItem;

public interface AuctionItemsDAO extends DBEntityDAO <AuctionItem> {

    AuctionItem findAuctionItemByName(String auctionItemName);

    int returnAmountOfItemsInStock(String auctionItemName);

    AuctionItem returnPriceByName(String auctionItemName);
}
