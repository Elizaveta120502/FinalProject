package com.epam.jwd.controller;

public enum PagePaths {

    INDEX("/"),
    MAIN("/WEB-INF/jsp/common/main.jsp"),
    ERROR("/WEB-INF/jsp/error/error.jsp");



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
