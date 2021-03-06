package com.epam.jwd.command.functions.account;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;

public enum LogoutCommand implements Command {

    INSTANCE(RequestFactory.getInstance());

    private static final String USER_SESSION_ATTRIBUTE_NAME = "user";
    private static final String INDEX_PATH = "/";


    private final RequestFactory requestFactory;

    LogoutCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (noLoggedInUserPresent(request)) {
            return requestFactory.createRedirectResponse(INDEX_PATH);
        }
        request.clearSession();
        return requestFactory.createRedirectResponse(INDEX_PATH);
    }

    private boolean noLoggedInUserPresent(CommandRequest request) {
        return !request.sessionExists() || (
                request.sessionExists()
                        && !request.retrieveFromSession(USER_SESSION_ATTRIBUTE_NAME)
                        .isPresent()
        );
    }
}
