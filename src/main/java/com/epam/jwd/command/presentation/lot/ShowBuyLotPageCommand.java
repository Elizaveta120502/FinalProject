package com.epam.jwd.command.presentation.lot;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum ShowBuyLotPageCommand implements Command {

    INSTANCE(RequestFactory.getInstance());

    private static final String BUY_JSP_PATH = "/WEB-INF/jsp/lot/buy.jsp";

    private final RequestFactory requestFactory;

    ShowBuyLotPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(BUY_JSP_PATH);
    }
}
