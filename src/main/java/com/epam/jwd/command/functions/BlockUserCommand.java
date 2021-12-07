package com.epam.jwd.command.functions;

import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRequest;
import com.epam.jwd.command.CommandResponse;
import com.epam.jwd.controller.RequestFactory;
import com.epam.jwd.model.Account;
import com.epam.jwd.service.AccountService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Optional;

public enum BlockUserCommand implements Command {
    INSTANCE(ServiceFactory.getInstance().userService(), RequestFactory.getInstance());


    private static final String INDEX_PATH = "/";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String USER_SESSION_ATTRIBUTE_NAME = "user";
    private static final String ERROR_BLOCK_PASS_ATTRIBUTE = "errorBlockMessage";
    private static final String ERROR_LOGIN_EMAIL_MESSAGE = "Invalid login or email";

    private static final String BLOCK_USER_JSP = "/controller?command=block_user";

    private final AccountService accountService;
    private final RequestFactory requestFactory;

     BlockUserCommand(AccountService accountService, RequestFactory requestFactory) {
        this.accountService=accountService;
        this.requestFactory = requestFactory;

    }


    @Override
    public CommandResponse execute(CommandRequest request) throws InterruptedException {
        final String login= request.getParameter(LOGIN);
        final String email = request.getParameter(EMAIL);


           Optional<Account> blockedUsers = accountService.blockUser(login,email);
        if (!blockedUsers.isPresent()) {
            request.addAttributeToJsp(ERROR_BLOCK_PASS_ATTRIBUTE, ERROR_LOGIN_EMAIL_MESSAGE);
            return requestFactory.createForwardResponse(BLOCK_USER_JSP);
        }
        request.clearSession();
        request.createSession();
        request.addToSession(USER_SESSION_ATTRIBUTE_NAME, blockedUsers);
        return requestFactory.createRedirectResponse(BLOCK_USER_JSP);
    }
}
