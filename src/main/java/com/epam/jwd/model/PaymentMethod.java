package com.epam.jwd.model;

public class PaymentMethod implements DBEntity {

    private Long id;
    private String description;

    public PaymentMethod(String description, Long id) {
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
