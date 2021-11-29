package com.epam.jwd.model;

public enum PaymentMethod implements DBEntity {

    ERIP(1L,"Erip"),
    MASTERCARD (2L,"Mastercard"),
    ON_CREDIT(3L,"on Credit"),
    MAESTRO(4L,"Maestro"),
    NOT_PAID;

    private Long id;
    private String description;

    PaymentMethod() {
    }

    PaymentMethod(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public static PaymentMethod of(String name) {
        for (PaymentMethod method : values()) {
            if (method.description == name) {
                return method;
            }
        }
        return NOT_PAID;
    }


}
