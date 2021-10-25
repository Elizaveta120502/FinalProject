package com.epam.jwd.model;

public enum ShipmentMethod {

    BY_MAIL("by mail",1),
    DELIVERY("dilivery",2),
    PICKUP("pickup",3),
    EUROMAIL("euromail",4);

    private int id;
    private String description;

    ShipmentMethod(String description, int id) {
        this.description = description;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ShipmentMethod{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
