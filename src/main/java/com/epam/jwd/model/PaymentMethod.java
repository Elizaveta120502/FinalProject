package com.epam.jwd.model;

public enum PaymentMethod {
    ERIP("ERIP", 1),
    MASTERCARD("Mastercard", 2),
    ON_CREDIT("on Credit", 3),
    MAESTRO("Maestro", 4);


    private int id;
    private String description;

    PaymentMethod(String description, int id) {
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
        return "PaymentMethod{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
