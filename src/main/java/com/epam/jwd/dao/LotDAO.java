package com.epam.jwd.dao;

import com.epam.jwd.model.Lot;
import com.epam.jwd.model.LotStatus;
import com.epam.jwd.model.Payment;
import com.epam.jwd.model.Shipment;

public interface LotDAO<T> extends DBEntityDAO<Lot> {

    int returnStartingPriceById(Long id);

    int returnCurrentPriceById(Long id);

    int returnAmountOfItemsInLot(Long id);

    LotStatus returnLotStatus(Lot lot);

    Shipment returnShipmentByLotId(Long id);

    Payment returnPaymentByLotId(Long id);


}
