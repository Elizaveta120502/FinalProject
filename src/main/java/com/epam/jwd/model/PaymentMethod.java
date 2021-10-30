package com.epam.jwd.model;

public enum PaymentMethod implements DBEntity {
    ERIP("ERIP", 1L),
    MASTERCARD("Mastercard", 2L),
    ON_CREDIT("on Credit", 3L),
    MAESTRO("Maestro", 4L);


    private Long id;
    private String description;

    PaymentMethod(String description, Long id) {
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
        return "PaymentMethod{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
