package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.*;
import com.epam.jwd.database.impl.ConnectionPoolImpl;

public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();


    private final AccountDAO accountDAO = new AccountDAOImpl(ConnectionPoolImpl.getInstance());
    private final AuctionItemsDAO auctionItemsDAO = new AuctionItemsDAOImpl(ConnectionPoolImpl.getInstance());
    private final LotDAO lotDAO = new LotDAOImpl(ConnectionPoolImpl.getInstance());
    private final PaymentDAO paymentDAO = new PaymentDAOImpl(ConnectionPoolImpl.getInstance());
    private final ShipmentDAO shipmentDAO = new ShipmentDAOImpl(ConnectionPoolImpl.getInstance());

    public DAOFactory() {
    }

    public static DAOFactory getInstance(){
        return instance;
    }

    public AccountDAO getAccountDAO(){
        return accountDAO;
    }

    public AuctionItemsDAO getAuctionItemsDAO(){
        return auctionItemsDAO;
    }

    public LotDAO getLotDAO(){
        return lotDAO ;
    }

    public PaymentDAO getPaymentDAO(){
        return paymentDAO;
    }

    public ShipmentDAO getShipmentDAO(){
        return shipmentDAO;
    }








}
