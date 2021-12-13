package com.epam.jwd.command.functions;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.model.AuctionItem;
import com.epam.jwd.service.AuctionItemsService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Optional;

public enum DeleteItemCommand implements Command {

    INSTANCE(ServiceFactory.getInstance().auctionItemsService(),
            RequestFactory.getInstance());

    private static final String AUCTION_ITEM_JSP_PATH = "/WEB-INF/jsp/delete_item.jsp";
    private static final String INDEX_PATH = "/";
    private static final String ERROR_ITEM_ATTRIBUTE = "errorItemMessage";
    private static final String ITEM_SESSION_ATTRIBUTE_NAME = "auction_item";
    private static final String ID_ITEM_REQUEST_PARAM_NAME = "id";
    private static final String ERROR_ITEM_MESSAGE = "Failed to delete product";


    private final AuctionItemsService auctionItemsService;
    private final RequestFactory requestFactory;

    DeleteItemCommand(AuctionItemsService auctionItemsService, RequestFactory requestFactory) {
        this.auctionItemsService = auctionItemsService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {
        final Long id = Long.parseLong(request.getParameter(ID_ITEM_REQUEST_PARAM_NAME));

        Optional<Long> deletedItemId = ServiceFactory.getInstance()
                .auctionItemsService().deleteProduct(id);


        request.addAttributeToJsp(ITEM_SESSION_ATTRIBUTE_NAME, deletedItemId);
        return requestFactory.createRedirectResponse(INDEX_PATH);

    }
}
