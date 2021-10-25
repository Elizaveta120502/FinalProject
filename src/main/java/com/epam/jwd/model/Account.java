package com.epam.jwd.model;


import java.util.Objects;

public class Account implements DBEntity {

    private Long accountId;
    private String login;
    transient private String password;
    private String email;
    private UserRole role;

    public Account(Long accountId, String login, String password, String email, UserRole role) {
        this.accountId = accountId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }


    @Override
    public Long getAccountId() {
        return accountId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(getAccountId(), account.getAccountId()) && Objects.equals(getLogin(), account.getLogin()) && Objects.equals(getPassword(), account.getPassword()) && Objects.equals(getEmail(), account.getEmail()) && getRole() == account.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getLogin(), getPassword(), getEmail(), getRole());
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
