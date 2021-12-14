package com.epam.jwd.command.functions.account;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.model.Account;
import com.epam.jwd.service.AccountService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Optional;

public enum DeleteAccountCommand implements Command {


    INSTANCE(ServiceFactory.getInstance().userService(),
            RequestFactory.getInstance());

    private static final String DELETE_ACCOUNT_JSP = "/WEB-INF/jsp/account/delete_account.jsp";
    private static final String INDEX_PATH = "/";
    private static final String ERROR_USER_ATTRIBUTE = "errorUserMessage";
    private static final String ITEM_SESSION_ATTRIBUTE_NAME = "user";
    private static final String LOGIN_REQUEST_PARAM_NAME = "login";
    private static final String PASSWORD_REQUEST_PARAM_NAME = "password";

    private static final String ERROR_USER_MESSAGE = "Failed to delete account";

    private static final String SUCCESS_USER_ATTRIBUTE = "successUserMessage";
    private static final String SUCCESS_USER_MESSAGE = "Account deleted!";


    private final AccountService userService;
    private final RequestFactory requestFactory;

    DeleteAccountCommand(AccountService userService, RequestFactory requestFactory) {
        this.userService = userService;
        this.requestFactory = requestFactory;
    }


    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {
        final String login = request.getParameter(LOGIN_REQUEST_PARAM_NAME);
        final String password = request.getParameter(PASSWORD_REQUEST_PARAM_NAME);

        Optional<Account> deletedAccount = userService.deleteAccount(login, password);
        if (!deletedAccount.isPresent()) {
            request.addAttributeToJsp(ERROR_USER_ATTRIBUTE, ERROR_USER_MESSAGE);
            return requestFactory.createForwardResponse(DELETE_ACCOUNT_JSP);
        }

        request.addAttributeToJsp(SUCCESS_USER_ATTRIBUTE, SUCCESS_USER_MESSAGE);
        request.addToSession(ITEM_SESSION_ATTRIBUTE_NAME, deletedAccount.get());
        return requestFactory.createForwardResponse(DELETE_ACCOUNT_JSP);

    }
}
