package com.epam.jwd.service;

import com.epam.jwd.model.Shipment;
import com.epam.jwd.model.ShipmentMethod;

import java.util.Optional;

public interface ShipmentService extends EntityService<Shipment> {

    Optional<Shipment> makeShipment(ShipmentMethod shipmentMethod);


    static ShipmentMethod chooseShipmentMethod(String shipmentMethod) {
        switch (shipmentMethod) {
            case "by mail":
                return ShipmentMethod.BY_MAIL;
            case "delivery":
                return ShipmentMethod.DELIVERY;
            case "pickup":
                return ShipmentMethod.PICKUP;
            case "euromail":
                return ShipmentMethod.EUROMAIL;
            default:
                return ShipmentMethod.UNSPECIFIED;
        }
    }

}
