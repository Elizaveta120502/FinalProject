package com.epam.jwd.service;

import com.epam.jwd.model.Lot;

import java.util.List;

public interface LotService {

    boolean approveLot(int startingPrice, int itemsAmount,
                       int currentPrice, String auctionItem);

    boolean deleteLot(Long id);

    boolean makeBet(String login, Long lotId, int newCurrentPrice);

    List<Lot> blockLot(Long lotId);

    List<Lot> notApproveLot(int startingPrice, int itemsAmount,
                            int currentPrice, String auctionItem);
}
