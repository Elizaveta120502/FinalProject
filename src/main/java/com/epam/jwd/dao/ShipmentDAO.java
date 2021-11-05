package com.epam.jwd.dao;

import com.epam.jwd.model.Shipment;
import com.epam.jwd.model.ShipmentMethod;

import java.sql.Timestamp;

public interface ShipmentDAO<T> extends DBEntityDAO<Shipment> {

    Timestamp returnExpectedDate(Long id);
    int returnShipmentCost(Long id);
    ShipmentMethod returnShipmentMethod(Long id);

}
