package com.epam.jwd.command;

import com.epam.jwd.controller.RequestFactory;

public enum ShowRegistrationPageCommand implements Command {
    INSTANCE(RequestFactory.getInstance());

    private static final String REGISTRATION_JSP_PATH = "/WEB-INF/jsp/registration.jsp";

    private final RequestFactory requestFactory;


    ShowRegistrationPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(REGISTRATION_JSP_PATH);
    }
}
