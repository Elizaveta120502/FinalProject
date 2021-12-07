package com.epam.jwd.command.functions;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.PropertyContext;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.model.Account;
import com.epam.jwd.model.Lot;
import com.epam.jwd.model.Shipment;
import com.epam.jwd.service.*;

import java.util.Optional;

public enum BuyLotCommand implements Command {

    INSTANCE(ServiceFactory.getInstance().lotService(), RequestFactory.getInstance(),
            ServiceFactory.getInstance().shipmentService(), ServiceFactory.getInstance().paymentService(),
            ServiceFactory.getInstance().userService());

    private static final String INDEX_PATH = "/";
    private static final String BYU_JSP_PATH = "/WEB-INF/jsp/buy.jsp";


    private static final String LOT_TO_BUY_ID_PARAM_NAME = "lotid";
    private static final String LOT_PARAM_NAME = "lot";
    private static final String SHIPMENT_METHOD_PARAM = "shipment";
    private static final String PAYMENT_METHOD_PARAM = "payment";
    private static final String ACCOUNT_PARAM = "account";
    private static final String ERROR_LOT_MESSAGE = "Invalid lot parameters";


    private final LotService lotService;
    private final RequestFactory requestFactory;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;
    private final AccountService accountService;

    BuyLotCommand(LotService lotService, RequestFactory requestFactory,
                  ShipmentService shipmentService, PaymentService paymentService,
                  AccountService accountService) {
        this.lotService = lotService;
        this.requestFactory = requestFactory;
        this.shipmentService = shipmentService;
        this.paymentService = paymentService;
        this.accountService = accountService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {
     final int  lotId = Integer.parseInt(request.getParameter(LOT_TO_BUY_ID_PARAM_NAME));
     final String shipment = request.getParameter(SHIPMENT_METHOD_PARAM);
     final String payment = request.getParameter(PAYMENT_METHOD_PARAM);
     final String account = request.getParameter(ACCOUNT_PARAM);

     Optional<Lot> boughtLot = lotService.buyLot((long)lotId,shipment,payment,account);
        if (!boughtLot.isPresent()) {
            request.addAttributeToJsp(LOT_PARAM_NAME, ERROR_LOT_MESSAGE);
            return requestFactory.createForwardResponse(BYU_JSP_PATH);
        }
        request.clearSession();
        request.createSession();
        request.addToSession(LOT_PARAM_NAME, boughtLot);
        return requestFactory.createRedirectResponse(INDEX_PATH);
    }


}
