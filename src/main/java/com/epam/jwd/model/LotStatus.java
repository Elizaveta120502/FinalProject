package com.epam.jwd.model;

public enum LotStatus implements DBEntity{

    CURRENT("current",1),
    INACTIVE("inactive",2);

    private int id;
    private String description;

    LotStatus(String description,int id) {
        this.description = description;
        this.id = id;
    }

    @Override
    public int getAccountId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "LotStatus{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
