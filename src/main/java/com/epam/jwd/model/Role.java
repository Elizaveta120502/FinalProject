package com.epam.jwd.model;

import java.util.Arrays;
import java.util.List;

public enum Role  {
    ADMINISTRATOR(1L,"administrator"),
    CLIENT(2L,"client"),
    UNAUTHORIZED_USER;


    private static final List<Role> ALL_AVAILABLE_ROLES = Arrays.asList(values());
    private String roleName;
    private Long roleId;

    Role() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    Role(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static List<Role> valuesAsList() {
        return ALL_AVAILABLE_ROLES;
    }

    public static Role of(String name) {
        for (Role role : values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return UNAUTHORIZED_USER;
    }
}
