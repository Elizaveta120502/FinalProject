package com.epam.jwd.model;


import java.util.Objects;


public class Account implements DBEntity {

    private Long accountId;
    private final String login;
    transient private String password;
    private String email;
    private Role role;
    private Status status;

    public Account(Long accountId, String login, String password, String email, Role role,Status status) {
        this.accountId = accountId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;

    }


    @Override
    public Long getId() {
        return accountId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) && Objects.equals(getLogin(), account.getLogin()) && Objects.equals(getPassword(), account.getPassword()) && Objects.equals(getEmail(), account.getEmail()) && getRole() == account.getRole() && getStatus() == account.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, getLogin(), getPassword(), getEmail(), getRole(), getStatus());
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
