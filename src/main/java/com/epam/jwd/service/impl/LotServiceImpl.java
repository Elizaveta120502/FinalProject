package com.epam.jwd.service.impl;

import com.epam.jwd.dao.LotDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.*;
import com.epam.jwd.service.LotService;
import com.epam.jwd.service.PaymentService;
import com.epam.jwd.service.ServiceFactory;
import com.epam.jwd.service.ShipmentService;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
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
    public Optional<Long> deleteLot(Long id) {
        try {
            if (id >= 0) {
                DAOFactory.getInstance().getLotDAO().deleteById(id);
                return Optional.of(id);
            } else {
                return Optional.empty();
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Lot> makeBet(String login, Long lotId, int newCurrentPrice) {

        try {
            if (login == null || lotId < 0
                    || newCurrentPrice <= 0) {
                return Optional.empty();
            } else {
                Optional<Account> user = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
                Optional<Lot> lot = DAOFactory.getInstance().getLotDAO().readById(lotId);
                lot.get().setCurrentPrice(newCurrentPrice);
                Lot lotWithNewCurrentPrice = new Lot(lot.get().getId(), lot.get().getStartingPrice(),
                        lot.get().getItemsAmount(), lot.get().getCurrentPrice(),
                        lot.get().getLotStatus(), lot.get().getAuctionItem(),
                        lot.get().getShipment(), lot.get().getPayment(), user.get());
                DAOFactory.getInstance().getLotDAO().update(lotWithNewCurrentPrice, lot.get().getId());
                return Optional.of(lotWithNewCurrentPrice);
            }
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
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
    public Optional<Lot> buyLot(Long lotId, String shipmentMethod, String paymentMethod, String login) {
        Lot lotToBuy;
        try {
            if (lotId > 0) {
                Optional<Lot> lot = DAOFactory.getInstance().getLotDAO().readById(lotId);
                if (lot.get().getLotStatus() == LotStatus.CURRENT) {
                    Optional<Account> customer = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);

                    lotToBuy = new Lot(lotId, lot.get().getStartingPrice(),
                            lot.get().getItemsAmount(), lot.get().getCurrentPrice(),
                            LotStatus.INACTIVE, lot.get().getAuctionItem(),
                            ServiceFactory.getInstance().shipmentService()
                                    .makeShipment(ShipmentService.chooseShipmentMethod(shipmentMethod)).get(),
                            ServiceFactory.getInstance().paymentService()
                                    .makePayment(PaymentService.choosePaymentType(paymentMethod)).get(),
                            customer.get());
                    DAOFactory.getInstance().getLotDAO().update(lotToBuy, lot.get().getId());

                } else {
                    return Optional.empty();
                }
            }
            return Optional.empty();
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }

    }

    @Override
    public Optional<Lot> createNewLot(int startingPrice, int itemsAmount, String auctionItem, String login) {
        try {
            Lot newLot = new Lot();
            if (startingPrice <= 0 || itemsAmount <= 0
                    || auctionItem == null) {
                return Optional.empty();
            } else {
                Optional<Account> account = getInstance().getAccountDAO().findUserByLogin(login);
                Optional<AuctionItem> item = getInstance().getAuctionItemsDAO().findAuctionItemByTitle(auctionItem);

                long newId = (long) (21 + Math.random() * 9_223_372);

                if (getInstance().getAuctionItemsDAO().readAll().toString().contains(item.get().getTitle())) {
                    Shipment sh = new Shipment();
                    newLot = new Lot(newId, startingPrice, itemsAmount, startingPrice,
                            LotStatus.CURRENT, item.get(), new Shipment(null, null, sh.getActualDate(), 0, null),
                            new Payment(null, null, null), account.get());

                    getInstance().getLotDAO().create(newLot);
                    int newItemsAmount = item.get().getInStoke() - itemsAmount;
                    if (newItemsAmount >= 0) {
                        item.get().setInStoke(newItemsAmount);
                        DAOFactory.getInstance().getAuctionItemsDAO().update(item.get(), item.get().getId());
                    } else {
                        return Optional.empty();
                    }

                } else {
                    Optional.empty();
                }
            }
            return Optional.of(newLot);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

}
