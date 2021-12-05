package com.epam.jwd.command.presentation;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.model.Lot;
import com.epam.jwd.service.EntityService;
import com.epam.jwd.service.ServiceFactory;

import java.util.List;

public enum ShowLotsPageCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().serviceFor(Lot.class),
            RequestFactory.getInstance());

    private static List<Lot> LOTS = null;

    static {
        try {
            LOTS = DAOFactory.getInstance().getLotDAO().readAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static final String LOTS_ATTRIBUTE_NAME = "lots";

    private static final CommandResponse FORWARD_TO_LOTS_PAGE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/lots.jsp";
        }
    };

    private final EntityService<Lot> service;
    private final RequestFactory requestFactory;

    ShowLotsPageCommand(EntityService<Lot> service, RequestFactory requestFactory) {
        this.service = service;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {

        request.addAttributeToJsp(LOTS_ATTRIBUTE_NAME, LOTS);
        return FORWARD_TO_LOTS_PAGE;
    }
}
