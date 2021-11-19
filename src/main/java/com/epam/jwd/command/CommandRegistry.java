package com.epam.jwd.command;


public enum CommandRegistry {

    MAIN_PAGE(ShowMainPageCommand.INSTANCE, "main_page"),
    SHOW_USERS(ShowUsersPageCommand.INSTANCE, "show_users"),
    SHOW_LOGIN(ShowLoginPageCommand.INSTANCE, "show_login"),
    LOGIN(LoginCommand.INSTANCE, "login"),
    LOGOUT(LogoutCommand.INSTANCE, "logout"),
    DEFAULT(ShowMainPageCommand.INSTANCE, "");

    private final Command command;
    private final String path;

    CommandRegistry(Command command, String path) {
        this.command = command;
        this.path = path;
    }

    public Command getCommand() {
        return command;
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