package com.epam.jwd.controller;

import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;

import javax.servlet.http.HttpServletRequest;

public interface RequestFactory {
    CommandRequest createRequest(HttpServletRequest request);

    CommandResponse createForwardResponse(String path);

    CommandResponse createRedirectResponse(String path);

    static RequestFactory getInstance() {
        return SimpleRequestFactory.INSTANCE;
    }
}
