package com.epam.jwd.model;

public enum UserRole {
    ADMINISTRATOR("administrator", 1),
    SIMPLE_USER("simple user", 2);

    private final String description;
    private int roleId;

    UserRole(String description, int roleId) {
        this.description = description;
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public  int getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "description='" + description + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
