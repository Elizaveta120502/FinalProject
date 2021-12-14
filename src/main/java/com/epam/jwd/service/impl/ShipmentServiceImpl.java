package com.epam.jwd.service.impl;

import com.epam.jwd.dao.ShipmentDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Shipment;
import com.epam.jwd.model.ShipmentMethod;
import com.epam.jwd.service.ShipmentService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentDAO shipmentDAO;

    public ShipmentServiceImpl(ShipmentDAO shipmentDAO) {
        this.shipmentDAO = shipmentDAO;
    }

    @Override
    public Optional<Shipment> makeShipment(ShipmentMethod shipmentMethod) {
        if (shipmentMethod == ShipmentMethod.UNSPECIFIED) {
            return Optional.empty();
        } else {
            try {
                LocalDateTime actualTime = LocalDateTime.now();
                Timestamp actual = Timestamp.valueOf(actualTime);
                LocalDateTime expectedDate = actualTime.plusDays(30L);
                Timestamp expected = Timestamp.valueOf(expectedDate);
                long id = (long) (30 + Math.random() * 9_223_372);

                Optional<Shipment> newShipment = Optional.of(new Shipment(id, expected, actual,
                        DAOFactory.getInstance().getShipmentDAO().
                                determineShipmentPrice(shipmentMethod),
                        shipmentMethod));
                DAOFactory.getInstance().getShipmentDAO().create(newShipment.get());

                return newShipment;
            } catch (InterruptedException e) {
                LoggerProvider.getLOG().error("takeConnection interrupted");
                Thread.currentThread().interrupt();
                return Optional.empty();
            }
        }
    }


}
