package com.epam.jwd.service.impl;

import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;
import com.epam.jwd.model.DBEntity;
import com.epam.jwd.model.Lot;
import com.epam.jwd.service.LotService;

import java.util.Optional;

public class LotServiceImpl implements LotService {
    @Override
    public boolean approveLot(Lot lot) {
        return false;
    }

    @Override
    public boolean deleteLot(Long id) {
        try {
            DAOFactory.getInstance().getLotDAO().deleteById(id);
            return true;
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public int makeBet(String login, Long lotId, int newCurrentPrice) {
        Account user;
        DBEntity lot;
        try {
            user = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);
            lot = DAOFactory.getInstance().getLotDAO().readById(lotId);


        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }
        return 0;
    }

    @Override
    public boolean blockLot(Lot lot) {
        return false;
    }

    @Override
    public boolean notApproveLot(Lot lot) {
        return false;
    }
}
