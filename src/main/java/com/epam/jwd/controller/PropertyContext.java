package com.epam.jwd.controller;

public interface PropertyContext {

    String get(String name);

    static PropertyContext instance() {
        return SimplePropertyContext.getInstance();
    }
}
