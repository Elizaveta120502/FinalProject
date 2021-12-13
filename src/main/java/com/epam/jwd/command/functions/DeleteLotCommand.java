package com.epam.jwd.command.functions;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.service.AuctionItemsService;
import com.epam.jwd.service.LotService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Optional;

public enum DeleteLotCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().lotService(),
            RequestFactory.getInstance());

    private static final String AUCTION_ITEM_JSP_PATH = "/WEB-INF/jsp/delete_lot.jsp";
    private static final String INDEX_PATH = "/";
    private static final String ERROR_LOT_DELETE_ATTRIBUTE = "errorLotDeleteMessage";
    private static final String LOT_SESSION_ATTRIBUTE_NAME = "lot";
    private static final String ID_LOT_REQUEST_PARAM_NAME = "id";
    private static final String ERROR_LOT_DELETE_MESSAGE = "Failed to delete lot";


    private final LotService lotService;
    private final RequestFactory requestFactory;

    DeleteLotCommand(LotService lotService, RequestFactory requestFactory) {
        this.lotService = lotService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {
        final Long id = Long.parseLong(request.getParameter(ID_LOT_REQUEST_PARAM_NAME));

        Optional<Long> deletedLotId = ServiceFactory.getInstance().lotService().deleteLot(id);


        request.addAttributeToJsp(LOT_SESSION_ATTRIBUTE_NAME, deletedLotId.get());
        return requestFactory.createRedirectResponse(INDEX_PATH);

    }
}
