package com.epam.jwd.service;

import com.epam.jwd.model.AuctionItem;

import java.util.List;

public interface AuctionItemsService {

    List<AuctionItem> viewProducts();

    boolean addProduct(Long id,String title, int price,int inStock);

    boolean deleteProduct(Long id);

    List<AuctionItem> sortProductsByAvailability();
}
