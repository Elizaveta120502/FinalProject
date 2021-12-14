package com.epam.jwd.command.presentation.lot;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Shipment;
import com.epam.jwd.service.EntityService;
import com.epam.jwd.service.ServiceFactory;

import java.util.List;

public enum ShowShipmentsPageCommand implements Command {

    INSTANCE(ServiceFactory.getInstance().serviceFor(Shipment.class),
            RequestFactory.getInstance());

    private static List<Shipment> SHIPMENTS = null;
    private static List<Shipment> LOWER_SH = null;
    private static List<Shipment> HIGHER_SH = null;

    static {
        try {
            SHIPMENTS = DAOFactory.getInstance().getShipmentDAO().readAll();

        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }
    }

    private static final String SHIPMENTS_ATTRIBUTE_NAME = "shipments";

    private static final CommandResponse FORWARD_TO_SHIPMENTS_PAGE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/lot/show_shipments.jsp";
        }
    };

    private final EntityService<Shipment> service;
    private final RequestFactory requestFactory;

    ShowShipmentsPageCommand(EntityService<Shipment> service, RequestFactory requestFactory) {
        this.service = service;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {

        request.addAttributeToJsp(SHIPMENTS_ATTRIBUTE_NAME, SHIPMENTS);
        return FORWARD_TO_SHIPMENTS_PAGE;
    }
}
