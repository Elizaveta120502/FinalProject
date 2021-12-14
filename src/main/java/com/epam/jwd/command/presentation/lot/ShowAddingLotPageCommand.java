package com.epam.jwd.command.presentation.lot;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum ShowAddingLotPageCommand implements Command {

    INSTANCE(RequestFactory.getInstance());

    private static final String CREATE_LOT_JSP_PATH = "/WEB-INF/jsp/lot/create_lot.jsp";

    private final RequestFactory requestFactory;

    ShowAddingLotPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(CREATE_LOT_JSP_PATH);
    }
}
