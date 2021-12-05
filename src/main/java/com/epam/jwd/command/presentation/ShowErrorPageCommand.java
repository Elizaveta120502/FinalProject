package com.epam.jwd.command.presentation;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.PropertyContext;
import com.epam.jwd.controller.RequestFactory;

public enum ShowErrorPageCommand implements Command {
    INSTANCE(RequestFactory.getInstance(),
            PropertyContext.instance());

    private static final String ERROR_PAGE = "error";

    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    ShowErrorPageCommand(RequestFactory requestFactory, PropertyContext propertyContext) {
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(propertyContext.get(ERROR_PAGE));

    }
}

