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
        Optional<Account> user;
        try {
            user = DAOFactory.getInstance().getAccountDAO().findUserByLogin(login);

        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }
        return 0; //todo: do account_id int lots table, delete many_to_many relation
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
