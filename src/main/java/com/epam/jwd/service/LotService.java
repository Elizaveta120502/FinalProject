package com.epam.jwd.service;

import com.epam.jwd.model.Lot;

import java.util.List;
import java.util.Set;

public interface LotService extends EntityService<Lot> {

    boolean approveLot(int startingPrice, int itemsAmount,
                       int currentPrice, String auctionItem);

    boolean deleteLot(Long id);

    boolean makeBet(String login, Long lotId, int newCurrentPrice);

    Set<Lot> blockLot(Long lotId);

    List<Lot> notApproveLot(int startingPrice, int itemsAmount,
                            int currentPrice, String auctionItem);

    boolean buyLot(Long lotId, String shipmentMethod, String paymentMethod, String login);
}
