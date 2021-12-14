package com.epam.jwd.command.presentation.account;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum ShowDeleteUserPageCommand implements Command {
    INSTANCE(RequestFactory.getInstance());

    private static final String DELETE_ACCOUNT_JSP_PATH = "/WEB-INF/jsp/account/delete_account.jsp";

    private final RequestFactory requestFactory;

    ShowDeleteUserPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(DELETE_ACCOUNT_JSP_PATH);
    }
}
