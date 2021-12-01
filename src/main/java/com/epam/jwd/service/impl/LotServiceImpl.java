package com.epam.jwd.service.impl;

import com.epam.jwd.dao.LotDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.*;
import com.epam.jwd.service.LotService;
import com.epam.jwd.service.PaymentService;
import com.epam.jwd.service.ServiceFactory;
import com.epam.jwd.service.ShipmentService;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.epam.jwd.dao.impl.DAOFactory.getInstance;


public class LotServiceImpl implements LotService {

    private final LotDAO lotDAO;
    private Object Shipment;

    private static Set<Lot> blockedLots = new CopyOnWriteArraySet<>();


    public LotServiceImpl(LotDAO lotDAO) {
        this.lotDAO = lotDAO;
    }

    @Override
    public boolean approveLot(int startingPrice, int itemsAmount,
                              int currentPrice, String auctionItem) {
        try {
            if (startingPrice <= 0 || itemsAmount <= 0 || currentPrice <= 0
                    || auctionItem == null) {
                return false;
            } else {
                long newId = getInstance().getLotDAO().readAll().size() + 1;
                AuctionItem item = getInstance().getAuctionItemsDAO().findAuctionItemByTitle(auctionItem);
                Shipment sh = new Shipment();
                Lot newLot = new Lot(newId, startingPrice, itemsAmount, currentPrice,
                        LotStatus.CURRENT, item, new Shipment(null, null, sh.getActualDate(), 0, null),
                        new Payment(null, null, null), new Account(null, null,
                        null, null, null, null));

                getInstance().getLotDAO().create(newLot);
                return true;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean deleteLot(Long id) {
        try {
            if (id <= DAOFactory.getInstance().getLotDAO().readAll().size() &&
                    id >= 0) {
                DAOFactory.getInstance().getLotDAO().deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean makeBet(String login, Long lotId, int newCurrentPrice) {

        try {
            if (login == null || lotId < 0
                    || lotId > DAOFactory.getInstance().getLotDAO().readAll().size()
                    || newCurrentPrice <= 0) {
                return false;
            } else {
                Optional<Account> user = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
                Optional<Lot> lot = DAOFactory.getInstance().getLotDAO().readById(lotId);
                lot.get().setCurrentPrice(newCurrentPrice);
                Lot lotWithNewCurrentPrice = new Lot(lot.get().getId(), lot.get().getStartingPrice(),
                        lot.get().getItemsAmount(), lot.get().getCurrentPrice(),
                        lot.get().getLotStatus(), lot.get().getAuctionItem(),
                        lot.get().getShipment(), lot.get().getPayment(), user.get());
                DAOFactory.getInstance().getLotDAO().update(lotWithNewCurrentPrice, lot.get().getId());
                return true;
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }

    }

    @Override
    public Set<Lot> blockLot(Long lotId) {

        try {
            if (lotId > 0 && lotId <= DAOFactory.getInstance().getLotDAO().readAll().size()) {


                Optional<Lot> lot = getInstance().getLotDAO().readById(lotId);
                lot.get().setLotStatus(LotStatus.INACTIVE);
                blockedLots.add(lot.get());
                Lot blockedLot = new Lot(lot.get().getId(), lot.get().getStartingPrice(),
                        lot.get().getItemsAmount(), lot.get().getCurrentPrice(),
                        lot.get().getLotStatus(), lot.get().getAuctionItem(),
                        lot.get().getShipment(), lot.get().getPayment(), lot.get().getAccount());
                getInstance().getLotDAO().update(blockedLot, lot.get().getId());
                return blockedLots;
            } else {
                return Collections.emptySet();
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return blockedLots;
        }
    }

    @Override
    public List<Lot> notApproveLot(int startingPrice, int itemsAmount,
                                   int currentPrice, String auctionItem) {
        long newId;
        List<Lot> notApprovedLots = new ArrayList<>();
        try {
            newId = (long) (Math.random() + getInstance().getLotDAO().readAll().size()) * 1000;
            AuctionItem item = getInstance().getAuctionItemsDAO().findAuctionItemByTitle(auctionItem);
            Shipment sh = new Shipment();
            Lot newLot = new Lot(newId, startingPrice, itemsAmount, currentPrice,
                    LotStatus.INACTIVE, item, new Shipment(null, null, sh.getActualDate(), 0, null),
                    new Payment(null, null, null), new Account(null, null,
                    null, null, null, null));
            notApprovedLots.add(newLot);
            return notApprovedLots;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return notApprovedLots;
        }
    }

    @Override
    public boolean buyLot(Long lotId, String shipmentMethod, String paymentMethod, String login) {

        try {
            if (lotId > 0 || lotId < DAOFactory.getInstance().getLotDAO().readAll().size()) {
                Optional<Lot> lot = DAOFactory.getInstance().getLotDAO().readById(lotId);
                if (lot.get().getLotStatus() == LotStatus.CURRENT) {
                    Optional<Account> customer = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);

                    Lot lotToBuy = new Lot(lotId, lot.get().getStartingPrice(),
                            lot.get().getItemsAmount(), lot.get().getCurrentPrice(),
                            LotStatus.INACTIVE, lot.get().getAuctionItem(),
                            ServiceFactory.getInstance().shipmentService()
                                    .makeShipment(ShipmentService.chooseShipmentMethod(shipmentMethod)).get(),
                            ServiceFactory.getInstance().paymentService()
                                    .makePayment(PaymentService.choosePaymentType(paymentMethod)).get(),
                            customer.get());
                    DAOFactory.getInstance().getLotDAO().update(lotToBuy, lot.get().getId());

                } else {
                    return false;
                }
            }
            return true;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }

    }

    public static void main(String[] args) {
        LoggerProvider.getLOG().trace("Starting program");
        StatementProvider.getInstance();

        LoggerProvider.getLOG().info(ServiceFactory.getInstance().lotService().buyLot(9L,
                "pickup", "Maestro", "Emily2013"));

//        for (Lot l : blockedLots){
//            LoggerProvider.getLOG().info(l);
//        }


        StatementProvider.getInstance().close();
        LoggerProvider.getLOG().trace("program end");
    }
}
