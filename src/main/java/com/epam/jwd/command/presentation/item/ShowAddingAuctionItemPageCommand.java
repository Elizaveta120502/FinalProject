package com.epam.jwd.command.presentation.item;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum ShowAddingAuctionItemPageCommand implements Command {

    INSTANCE(RequestFactory.getInstance());

    private static final String AUCTION_ITEM_JSP_PATH = "/WEB-INF/jsp/item/add_item.jsp";

    private final RequestFactory requestFactory;

    ShowAddingAuctionItemPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(AUCTION_ITEM_JSP_PATH);
    }
}
