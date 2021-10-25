package com.epam.jwd.model;


public class UserRole {
//    ADMINISTRATOR("administrator", 1),
//    SIMPLE_USER("simple user", 2);

    private  String roleName;
    private int roleId;

    public UserRole(String roleName, int roleId) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

    public UserRole(String roleName) {
        this.roleName = roleName;
    }


    public UserRole(int roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return roleName;
    }

    public  int getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "role_name='" + roleName + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
