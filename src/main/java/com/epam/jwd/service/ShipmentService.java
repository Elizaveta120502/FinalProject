package com.epam.jwd.service;

import com.epam.jwd.model.Shipment;
import com.epam.jwd.model.ShipmentMethod;

public interface ShipmentService {

    boolean makeShipment(ShipmentMethod  shipmentMethod);

    boolean approveShipment(Shipment shipment);

    Shipment viewShipmentInformation(Shipment shipment);

    ShipmentMethod chooseShipmentMethod(ShipmentMethod ... methods);

    boolean cancelShipment(Shipment shipment);

}
