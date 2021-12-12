package com.epam.jwd.command.functions;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.model.AuctionItem;
import com.epam.jwd.service.AuctionItemsService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Optional;

public enum AddItemCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().auctionItemsService(),
            RequestFactory.getInstance());

    private static final String AUCTION_ITEM_JSP_PATH = "/WEB-INF/jsp/add_item.jsp";
    private static final String INDEX_PATH = "/";
    private static final String ERROR_ITEM_ATTRIBUTE = "errorItemMessage";
    private static final String ITEM_SESSION_ATTRIBUTE_NAME = "auction_item";
    private static final String TITLE_REQUEST_PARAM_NAME = "title";
    private static final String PRICE_REQUEST_PARAM_NAME = "price";
    private static final String IN_STOCK_REQUEST_PARAM_NAME = "inStock";
    private static final String PICTURE_URL_REQUEST_PARAM_NAME = "pictureURL";
    private static final String ERROR_ITEM_MESSAGE = "Failed to create a new product";


    private final AuctionItemsService auctionItemsService;
    private final RequestFactory requestFactory;

    AddItemCommand(AuctionItemsService auctionItemsService, RequestFactory requestFactory) {
        this.auctionItemsService = auctionItemsService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {
        final String title = request.getParameter(TITLE_REQUEST_PARAM_NAME);
        final int price = Integer.parseInt(request.getParameter(PRICE_REQUEST_PARAM_NAME));
        final int inStock = Integer.parseInt(request.getParameter(IN_STOCK_REQUEST_PARAM_NAME));
        final String pictureURL = request.getParameter(PICTURE_URL_REQUEST_PARAM_NAME);

        final Optional<AuctionItem> newItem = ServiceFactory.getInstance().
                auctionItemsService().addProduct(title, price, inStock, pictureURL);
        if (!newItem.isPresent()) {
            request.addAttributeToJsp(ERROR_ITEM_ATTRIBUTE, ERROR_ITEM_MESSAGE);
            return requestFactory.createForwardResponse(AUCTION_ITEM_JSP_PATH);
        }

        request.addToSession(ITEM_SESSION_ATTRIBUTE_NAME, newItem.get());
        return requestFactory.createRedirectResponse(INDEX_PATH);

    }
}
