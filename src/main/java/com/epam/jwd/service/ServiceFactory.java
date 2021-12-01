package com.epam.jwd.service;

import com.epam.jwd.model.*;
import com.epam.jwd.service.impl.ServiceFactoryImpl;

public interface ServiceFactory {

    <T extends DBEntity> EntityService<T> serviceFor(Class<T> modelClass);

    default AccountService userService() {
        return (AccountService) serviceFor(Account.class);
    }

    default LotService lotService() {
        return (LotService) serviceFor(Lot.class);
    }

    default AuctionItemsService auctionItemsService() {
        return (AuctionItemsService) serviceFor(AuctionItem.class);
    }

    default PaymentService paymentService() {
        return (PaymentService) serviceFor(Payment.class);
    }

    default ShipmentService shipmentService() {
        return (ShipmentService) serviceFor(Shipment.class);
    }

    static ServiceFactoryImpl getInstance() {
        return ServiceFactoryImpl.INSTANCE;
    }
}
