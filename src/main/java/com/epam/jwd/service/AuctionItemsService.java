package com.epam.jwd.service;

import com.epam.jwd.model.AuctionItem;

import java.util.List;
import java.util.Optional;

public interface AuctionItemsService extends EntityService<AuctionItem> {

    List<AuctionItem> viewProducts();

    Optional<AuctionItem> addProduct(String title, int price, int inStock, String pictureURL);

    Optional<Long> deleteProduct(Long id);

    List<AuctionItem> sortProductsByAvailability();
}
