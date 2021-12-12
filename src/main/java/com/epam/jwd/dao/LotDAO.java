package com.epam.jwd.dao;

import com.epam.jwd.model.Lot;
import com.epam.jwd.model.LotStatus;

public interface LotDAO<T> extends DBEntityDAO<Lot> {

    int returnStartingPriceById(Long id);

    int returnCurrentPriceById(Long id);

    int returnAmountOfItemsInLot(Long id);

    LotStatus returnLotStatus(Long id);

    boolean changeCurrentPrice(Long id, int newCurrentPrice);

    boolean deleteByAuctionItemId(Long id);
}
