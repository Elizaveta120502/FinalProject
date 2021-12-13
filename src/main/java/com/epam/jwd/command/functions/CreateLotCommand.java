package com.epam.jwd.command.functions;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.model.Lot;
import com.epam.jwd.service.LotService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Optional;

public enum CreateLotCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().lotService(), RequestFactory.getInstance());


    private static final String CREATE_LOT_JSP_PATH = "/WEB-INF/jsp/create_lot.jsp";
    private static final String INDEX_PATH = "/controller?command=show_create_lot";
    private static final String ERROR_LOT_CREATE_ATTRIBUTE = "errorLotCreateMessage";
    private static final String LOT_SESSION_ATTRIBUTE_NAME = "lot";
    private static final String STARTING_PRICE_REQUEST_PARAM_NAME = "startingPrice";
    private static final String ITEMS_AMOUNT_REQUEST_PARAM_NAME = "itemsAmount";
    private static final String AUCTION_ITEM_REQUEST_PARAM_NAME = "auctionItem";
    private static final String LOGIN_REQUEST_PARAM_NAME = "login";
    private static final String ERROR_LOT_CREATE_MESSAGE = "Failed to create lot";
    private static final String SUCCESS_LOT_CREATE_ATTRIBUTE = "successLotCreateMessage";
    private static final String SUCCESSFUL_CREATING_LOT_MASSAGE = "Success!";


    private final LotService lotService;
    private final RequestFactory requestFactory;


    CreateLotCommand(LotService lotService, RequestFactory requestFactory) {
        this.lotService = lotService;
        this.requestFactory = requestFactory;
    }


    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {
        final int startingPrice = Integer.parseInt(request.getParameter(STARTING_PRICE_REQUEST_PARAM_NAME));
        final int itemsAmount = Integer.parseInt(request.getParameter(ITEMS_AMOUNT_REQUEST_PARAM_NAME));
        final String auctionItem = request.getParameter(AUCTION_ITEM_REQUEST_PARAM_NAME);
        final String login = request.getParameter(LOGIN_REQUEST_PARAM_NAME);

        Optional<Lot> newLot = lotService.createNewLot(startingPrice, itemsAmount, auctionItem, login);

        if (!newLot.isPresent()) {
            request.addAttributeToJsp(ERROR_LOT_CREATE_ATTRIBUTE, ERROR_LOT_CREATE_MESSAGE);
            return requestFactory.createForwardResponse(CREATE_LOT_JSP_PATH);
        }
        request.addAttributeToJsp(SUCCESS_LOT_CREATE_ATTRIBUTE, SUCCESSFUL_CREATING_LOT_MASSAGE);
        request.addToSession(LOT_SESSION_ATTRIBUTE_NAME, newLot.get());
        return requestFactory.createRedirectResponse(INDEX_PATH);
    }
}
