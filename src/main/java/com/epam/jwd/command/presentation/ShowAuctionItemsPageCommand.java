package com.epam.jwd.command.presentation;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.AuctionItem;
import com.epam.jwd.service.EntityService;
import com.epam.jwd.service.ServiceFactory;

import java.util.List;

public enum ShowAuctionItemsPageCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().serviceFor(AuctionItem.class),
            RequestFactory.getInstance());

    private static List<AuctionItem> ITEMS = null;


    static {

        try {
            ITEMS = DAOFactory.getInstance().getAuctionItemsDAO().readAll();
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }

    }

    private static final String ITEMS_ATTRIBUTE_NAME = "auction_items";

    private static final CommandResponse FORWARD_TO_ITEMS_PAGE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/items.jsp";
        }
    };

    private final EntityService<AuctionItem> service;
    private final RequestFactory requestFactory;

    ShowAuctionItemsPageCommand(EntityService<AuctionItem> service, RequestFactory requestFactory) {
        this.service = service;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {

        request.addAttributeToJsp(ITEMS_ATTRIBUTE_NAME, ITEMS);
        return FORWARD_TO_ITEMS_PAGE;
    }
}
