package com.epam.jwd.model;


import java.util.ArrayList;
import java.util.List;

public class UserRole implements DBEntity {



    private String roleName;
    private Long roleId;

    public static final UserRole ADMINISTRATOR = new UserRole("administrator",1L);
    public static final UserRole CLIENT = new UserRole("client",2L);
    public static final UserRole UNAUTHORIZED_USER = new UserRole(null,null);

    public UserRole(String roleName, Long roleId) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

    private static  List<UserRole> ALL_AVAILABLE_ROLES = new ArrayList<>();


    public static List<UserRole> valuesAsList() {
        ALL_AVAILABLE_ROLES.add(ADMINISTRATOR);
        ALL_AVAILABLE_ROLES.add(CLIENT);
        ALL_AVAILABLE_ROLES.add(UNAUTHORIZED_USER);

        return ALL_AVAILABLE_ROLES;
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
