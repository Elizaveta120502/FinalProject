package com.epam.jwd.command.presentation.common;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.PropertyContext;
import com.epam.jwd.controller.RequestFactory;

public enum ShowMainPageCommand implements Command {
    INSTANCE(RequestFactory.getInstance(), PropertyContext.instance());


    private static final CommandResponse FORWARD_TO_MAIN_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/common/main.jsp";
        }
    };

    ShowMainPageCommand(RequestFactory instance, PropertyContext instance1) {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return FORWARD_TO_MAIN_PAGE_RESPONSE;
    }
}
