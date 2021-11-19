package com.epam.jwd.controller;

public enum PagePaths {

    INDEX("/"),
    MAIN("/WEB-INF/jsp/main.jsp"),
    LOGIN("/WEB-INF/jsp/login.jsp"),
    USERS("/WEB-INF/jsp/users.jsp");

    private final String path;

    PagePaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static PagePaths of(String name) {
        for (PagePaths page : values()) {
            if (page.name().equalsIgnoreCase(name)) {
                return page;
            }
        }
        return MAIN;
    }
}