package com.epam.jwd.command;


import com.epam.jwd.command.functions.LoginCommand;
import com.epam.jwd.command.functions.LogoutCommand;
import com.epam.jwd.command.functions.RegistrationCommand;
import com.epam.jwd.command.presentation.*;
import com.epam.jwd.model.Role;

import java.util.Arrays;
import java.util.List;


public enum CommandRegistry {

    MAIN_PAGE(ShowMainPageCommand.INSTANCE, "main_page"),
    SHOW_USERS(ShowUsersPageCommand.INSTANCE, "show_users", Role.ADMINISTRATOR),
    SHOW_LOGIN(ShowLoginPageCommand.INSTANCE, "show_login", Role.UNAUTHORIZED_USER),
    LOGIN(LoginCommand.INSTANCE, "login", Role.UNAUTHORIZED_USER),
    LOGOUT(LogoutCommand.INSTANCE, "logout"),
    REGISTRATION(RegistrationCommand.INSTANCE,"registration",Role.UNAUTHORIZED_USER),
    SHOW_REGISTRATION(ShowRegistrationPageCommand.INSTANCE,"show_registration",Role.UNAUTHORIZED_USER),
    SWOT_LOTS(ShowLotsPageCommand.INSTANCE,"show_lots"),
    SHOW_LOTS_PICTURES(ShowLotsPicturesCommand.INSTANCE,"show_lots_pictures"),
    DEFAULT(ShowMainPageCommand.INSTANCE, ""),
    ERROR(ShowErrorPageCommand.INSTANCE, "show_error");

    private final Command command;
    private final String path;

    private final List<Role> allowedRoles;

    CommandRegistry(Command command, String path, Role... roles) {
        this.command = command;
        this.path = path;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    public Command getCommand() {
        return command;
    }

    public List<Role> getAllowedRoles() {
        return allowedRoles;
    }

    static Command of(String name) {
        for (CommandRegistry constant : values()) {
            if (constant.path.equalsIgnoreCase(name)) {
                return constant.command;
            }
        }
        return DEFAULT.command;
    }
}
