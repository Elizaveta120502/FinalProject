package com.epam.jwd.service;

import com.epam.jwd.model.Lot;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LotService extends EntityService<Lot> {

    boolean approveLot(Lot newLot);

    Optional<Long> deleteLot(Long id);

    Optional<Lot> makeBet(String login, Long lotId, int newCurrentPrice);

    Set<Lot> blockLot(Long lotId);

    boolean notApproveLot(Lot newLot);

    Optional<Lot> buyLot(Long lotId, String shipmentMethod, String paymentMethod, String login);

    Optional<Lot> createNewLot(int startingPrice, int itemsAmount, String auctionItem, String login);
}
