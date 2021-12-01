package com.epam.jwd.controller;

public class SimplePropertyContext implements PropertyContext {
    private SimplePropertyContext() {
    }

    @Override
    public String get(String name) {
        return PagePaths.of(name.substring(0)).getPath();

    }

    static SimplePropertyContext getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final SimplePropertyContext INSTANCE = new SimplePropertyContext();
    }
}
