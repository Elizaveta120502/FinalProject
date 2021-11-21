package com.epam.jwd.filter;


import com.epam.jwd.command.Command;
import com.epam.jwd.command.CommandRegistry;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Account;
import com.epam.jwd.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@WebFilter(urlPatterns = "/*")

public class RoleFilter implements Filter {


 private static String COMMAND_PARAMETER_NAME = "command";
 private static final String USER_SESSION_ATTRIBUTE_NAME = "user";
    private static final String ERROR_PAGE_URL = "/controller?command=show_error";

    private final Map<Role, Set<Command>> commandsByRoles;

    public RoleFilter() {
        commandsByRoles = new EnumMap<>(Role.class);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        for (CommandRegistry command : CommandRegistry.values()) {
            for (Role allowedRole : command.getAllowedRoles()) {
                final Set<Command> commands = commandsByRoles.computeIfAbsent(allowedRole, k -> new HashSet<>());
                commands.add(command.getCommand());
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final String commandName = req.getParameter(COMMAND_PARAMETER_NAME);
        LoggerProvider.getLOG().trace("Checking permissions for command. commandName: {}", commandName);
        if (currentUserHasPermissionForCommand(commandName, req)) {

            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).sendRedirect(ERROR_PAGE_URL);
        }
    }


    private boolean currentUserHasPermissionForCommand(String commandName, HttpServletRequest request) {
        Role currentUserRole = retrieveCurrentUserRole(request);
        final Command command = Command.of(commandName);
        final Set<Command> allowedCommands = commandsByRoles.get(currentUserRole);
        return allowedCommands.contains(command);
    }

   private Role retrieveCurrentUserRole(HttpServletRequest request) {
      return Optional.ofNullable(request.getSession(false))
              .map(s -> (Account) s.getAttribute(USER_SESSION_ATTRIBUTE_NAME))
              .map(Account::getRole)
              .orElse(Role.UNAUTHORIZED_USER);
   }




}
