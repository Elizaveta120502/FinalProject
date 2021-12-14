package com.epam.jwd.command.presentation.lot;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum ShowMakeBetPageCommand implements Command {
    INSTANCE(RequestFactory.getInstance());

    private static final String BET_JSP_PATH = "/WEB-INF/jsp/lot/bet.jsp";

    private final RequestFactory requestFactory;

    ShowMakeBetPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(BET_JSP_PATH);
    }
}
