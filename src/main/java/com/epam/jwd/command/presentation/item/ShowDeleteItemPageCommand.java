package com.epam.jwd.command.presentation.item;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum ShowDeleteItemPageCommand implements Command {


    INSTANCE(RequestFactory.getInstance());

    private static final String AUCTION_ITEM_JSP_PATH = "/WEB-INF/jsp/item/delete_item.jsp";

    private final RequestFactory requestFactory;

    ShowDeleteItemPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(AUCTION_ITEM_JSP_PATH);
    }
}
