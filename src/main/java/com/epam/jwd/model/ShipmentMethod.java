package com.epam.jwd.model;

public enum ShipmentMethod implements DBEntity {

    BY_MAIL("by mail", 1L),
    DELIVERY("dilivery", 2L),
    PICKUP("pickup", 3L),
    EUROMAIL("euromail", 4L);

    private Long id;
    private String description;

    ShipmentMethod(String description, Long id) {
        this.description = description;
        this.id = id;
    }

    @Override
    public Long getId() {
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
