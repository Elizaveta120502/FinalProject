package com.epam.jwd.command;


import com.epam.jwd.command.functions.*;
import com.epam.jwd.command.presentation.*;
import com.epam.jwd.model.Role;

import java.util.Arrays;
import java.util.List;


public enum CommandRegistry {

    MAIN_PAGE(ShowMainPageCommand.INSTANCE, "main_page"),

    ADD_AUCTION_ITEM(AddItemCommand.INSTANCE, "add_item", Role.CLIENT, Role.ADMINISTRATOR),
    SHOW_AUCTION_ITEMS(ShowAuctionItemsPageCommand.INSTANCE, "show_items",Role.CLIENT, Role.ADMINISTRATOR),
    SHOW_ADDING_ITEMS(ShowAddingAuctionItemPageCommand.INSTANCE, "show_add_item", Role.CLIENT, Role.ADMINISTRATOR),

    DELETE_ITEM(DeleteItemCommand.INSTANCE,"delete_item",Role.ADMINISTRATOR),
    SHOW_DELETE_ITEMS(ShowDeleteItemPageCommand.INSTANCE,"show_delete_item",Role.ADMINISTRATOR),


    LOGIN(LoginCommand.INSTANCE, "login", Role.UNAUTHORIZED_USER),
    SHOW_LOGIN(ShowLoginPageCommand.INSTANCE, "show_login", Role.UNAUTHORIZED_USER),

    REGISTRATION(RegistrationCommand.INSTANCE, "registration", Role.UNAUTHORIZED_USER),
    SHOW_REGISTRATION(ShowRegistrationPageCommand.INSTANCE, "show_registration", Role.UNAUTHORIZED_USER),

    SHOW_USERS(ShowUsersPageCommand.INSTANCE, "show_users", Role.ADMINISTRATOR),

    DELETE_ACCOUNT(DeleteAccountCommand.INSTANCE,"delete_account",Role.ADMINISTRATOR,Role.CLIENT),
    SHOW_DELETE_ACCOUNT(ShowDeleteUserPageCommand.INSTANCE,"show_delete_account",Role.ADMINISTRATOR,Role.CLIENT),

    SHOW_LOTS(ShowLotsPageCommand.INSTANCE, "show_lots"),
    MAKE_BET(MakeBetCommand.INSTANCE, "make_bet", Role.CLIENT, Role.ADMINISTRATOR),
    SHOW_MAKING_BET(ShowMakeBetPageCommand.INSTANCE, "show_bet", Role.CLIENT, Role.ADMINISTRATOR),
    BUY_LOT(BuyLotCommand.INSTANCE, "buy_lot", Role.CLIENT, Role.ADMINISTRATOR),
    SHOW_BUY_LOT(ShowBuyLotPageCommand.INSTANCE, "show_buy", Role.CLIENT, Role.ADMINISTRATOR),

    BLOCK_USER(BlockUserCommand.INSTANCE, "block_user", Role.ADMINISTRATOR),
    SHOW_BLOCK_USER(ShowPageToBlockUser.INSTANCE, "show_user_block", Role.ADMINISTRATOR),
    LOGOUT(LogoutCommand.INSTANCE, "logout"),


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
