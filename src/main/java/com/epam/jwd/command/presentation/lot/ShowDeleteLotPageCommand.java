package com.epam.jwd.command.presentation.lot;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum ShowDeleteLotPageCommand implements Command {
    INSTANCE(RequestFactory.getInstance());

    private static final String DELETE_LOT_JSP_PATH = "/WEB-INF/jsp/lot/delete_lot.jsp";

    private final RequestFactory requestFactory;

    ShowDeleteLotPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(DELETE_LOT_JSP_PATH);
    }
}
