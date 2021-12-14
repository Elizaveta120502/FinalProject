package com.epam.jwd.command.presentation.account;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum ShowPageToBlockUser implements Command {
    INSTANCE(RequestFactory.getInstance());

    private static final String BLOCK_JSP_PATH = "/WEB-INF/jsp/account/userblock.jsp";

    private final RequestFactory requestFactory;

    ShowPageToBlockUser(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(BLOCK_JSP_PATH);
    }
}
