package com.epam.jwd.service;

import com.epam.jwd.model.Account;
import com.epam.jwd.model.Lot;

public interface LotService {

    boolean approveLot(Lot lot);

    boolean deleteLot(Long id);

    int makeBet(String login, Long lotId, int newCurrentPrice);

    boolean blockLot(Lot lot);

    boolean notApproveLot(Lot lot);
}
