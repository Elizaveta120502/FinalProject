package com.epam.jwd.model;


public class UserRole implements DBEntity {
//    ADMINISTRATOR("administrator", 1),
//    SIMPLE_USER("simple user", 2);

    private String roleName;
    private Long roleId;

    public UserRole(String roleName, Long roleId) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

    @Override
    public Long getId() {
        return roleId;
    }

    public UserRole(String roleName) {
        this.roleName = roleName;
    }


    public UserRole(Long roleId) {

        this.roleId = roleId;
    }

    public String getDescription() {
        return roleName;
    }


    @Override
    public String toString() {
        return "UserRole{" +
                "role_name='" + roleName + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
