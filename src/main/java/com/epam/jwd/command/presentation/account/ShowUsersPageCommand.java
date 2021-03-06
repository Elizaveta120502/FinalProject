package com.epam.jwd.command.presentation.account;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;
import com.epam.jwd.service.EntityService;
import com.epam.jwd.service.ServiceFactory;

import java.util.List;

public enum ShowUsersPageCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().serviceFor(Account.class),
            RequestFactory.getInstance());

    private static List<Account> USERS = null;

    static {
        try {
            USERS = DAOFactory.getInstance().getAccountDAO().readAll();
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }
    }

    private static final String USERS_ATTRIBUTE_NAME = "users";

    private static final CommandResponse FORWARD_TO_USERS_PAGE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/account/users.jsp";
        }
    };

    private final EntityService<Account> service;
    private final RequestFactory requestFactory;

    ShowUsersPageCommand(EntityService<Account> service, RequestFactory requestFactory) {
        this.service = service;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {

        request.addAttributeToJsp(USERS_ATTRIBUTE_NAME, USERS);
        return FORWARD_TO_USERS_PAGE;
    }

}
