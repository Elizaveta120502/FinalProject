package com.epam.jwd.model;


public class Client extends Account implements DBEntity {
    private Long id;
    private String name;
    private Account account;
    private Status status;

    public Client(Long accountId, String login,
                  String password, String email, Role role,
                  Long id, String name,
                  Account account, Status status) {
        super(accountId, login, password, email, role);
        this.id = id;
        this.name = name;
        this.account = account;
        this.status = status;
    }
}
