package com.epam.jwd.dao;

import com.epam.jwd.model.*;

public interface LotDAO extends DBEntityDAO<Lot>{

    int returnStartingPriceById(Long id);

    int returnCurrentPriceById(Long id);

    int returnAmountOfItemsInLot(Long id);

    LotStatus returnLotStatus(Lot lot);

   Shipment returnShipmentByLotId(Long id);

   Payment returnPaymentByLotId(Long id);



}
