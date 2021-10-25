package com.epam.jwd.model;


public class Client extends Account {
    private int  id;
    private String name;
    private Account account;
    private Status status;

    public Client(int accountId, String login,
                  String password,String email, UserRole role,
                  int id, String name,
                  Account account, Status status) {
        super(accountId, login, password, email, role);
        this.id = id;
        this.name = name;
        this.account = account;
        this.status = status;
    }
}
