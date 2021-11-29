package com.epam.jwd.model;

public enum ShipmentMethod implements DBEntity {

    BY_MAIL(1L,"by mail"),
    DELIVERY(2L,"dilivery"),
    PICKUP(3L,"pickup"),
    EUROMAIL(4L,"euromail"),
    UNSPECIFIED;

    private Long id;
    private String description;

    ShipmentMethod(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    ShipmentMethod() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public static ShipmentMethod of(String name) {
        for (ShipmentMethod method : values()) {
            if (method.description == name) {
                return method;
            }
        }
        return UNSPECIFIED;
    }


}


