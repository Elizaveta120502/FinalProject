package com.epam.jwd.command.functions.account;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.model.Account;
import com.epam.jwd.service.AccountService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Optional;

public enum RegistrationCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().userService(), RequestFactory.getInstance());

    private static final String INDEX_PATH = "/";
    private static final String REGISTRATION_JSP_PATH = "/WEB-INF/jsp/account/registration.jsp";

    private static final String ERROR_LOGIN_PASS_ATTRIBUTE = "errorLoginPassMessage";
    private static final String USER_SESSION_ATTRIBUTE_NAME = "user";
    private static final String LOGIN_REQUEST_PARAM_NAME = "login";
    private static final String PASSWORD_REQUEST_PARAM_NAME = "password";
    private static final String EMAIL_REQUEST_PARAM_NAME = "email";
    private static final String ERROR_LOGIN_PASS_MESSAGE = "Invalid login or password";

    private final AccountService accountService;
    private final RequestFactory requestFactory;

    RegistrationCommand(AccountService userService, RequestFactory requestFactory) {
        this.accountService = userService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {

        final String login = request.getParameter(LOGIN_REQUEST_PARAM_NAME);
        final String password = request.getParameter(PASSWORD_REQUEST_PARAM_NAME);
        final String email = request.getParameter(EMAIL_REQUEST_PARAM_NAME);
        final Optional<Account> user = accountService.registrationForClients(login, password, email);
        if (!user.isPresent()) {
            request.addAttributeToJsp(ERROR_LOGIN_PASS_ATTRIBUTE, ERROR_LOGIN_PASS_MESSAGE);
            return requestFactory.createForwardResponse(REGISTRATION_JSP_PATH);
        }
        request.clearSession();
        request.createSession();
        request.addToSession(USER_SESSION_ATTRIBUTE_NAME, user.get());
        return requestFactory.createRedirectResponse(INDEX_PATH);
    }
}
