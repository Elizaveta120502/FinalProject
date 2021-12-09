package com.epam.jwd.command.functions;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.PropertyContext;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.model.Lot;
import com.epam.jwd.service.AccountService;
import com.epam.jwd.service.LotService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Optional;

public enum MakeBetCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().userService(),
            ServiceFactory.getInstance().lotService(),
            RequestFactory.getInstance(),PropertyContext.instance());


    private static final String INDEX_PATH = "/controller?command=show_lots";
    private static final String BET_JSP_PATH = "/WEB-INF/jsp/bet.jsp";


    private static final String LOT_SESSION_ATTRIBUTE_NAME = "lot";
    private static final String LOGIN_REQUEST_PARAM_NAME = "login";
    private static final String LOT_ID_REQUEST_PARAM_NAME = "id";
    private static final String PRICE_REQUEST_PARAM_NAME = "newCurrentPrice";
    private static final String ERROR_BET_MESSAGE = "Choose lot number and enter your login";
    private static final String OPERATION_WAS_UNSUCCESSFUL ="The operation was unsuccessful";

    private final AccountService accountService;
    private final LotService lotService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    MakeBetCommand(AccountService userService,LotService lotService,
                   RequestFactory requestFactory,PropertyContext propertyContext) {
        this.accountService = userService;
        this.lotService = lotService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {

        final String login = request.getParameter(LOGIN_REQUEST_PARAM_NAME);
        final int id = Integer.parseInt(request.getParameter(LOT_ID_REQUEST_PARAM_NAME));
        final int newCurrentPrice = Integer.parseInt(request.getParameter(PRICE_REQUEST_PARAM_NAME));
        final Optional<Lot> bet = lotService.makeBet(login,(long) id, newCurrentPrice);
        request.clearSession();
        request.createSession();
        request.addToSession(LOT_SESSION_ATTRIBUTE_NAME,bet.get());

        if (!bet.isPresent()) {
            request.addAttributeToJsp(LOT_SESSION_ATTRIBUTE_NAME, OPERATION_WAS_UNSUCCESSFUL);
            return requestFactory.createForwardResponse(propertyContext.get(BET_JSP_PATH));
        } else {
            return requestFactory.createRedirectResponse(INDEX_PATH);
        }
    }
}
