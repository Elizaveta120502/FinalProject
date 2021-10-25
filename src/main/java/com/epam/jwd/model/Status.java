package com.epam.jwd.model;

public enum Status {
    MYSTERY("mystery", 1),
    FRESHMAN("freshman", 2),
    MIDDLE("middle", 3),
    MASTER("master", 4),
    SUPREME("supreme", 5);

    private String description;
    private int id;
    private Benefit benefit;

    Status(String description, int id) {
        this.description = description;
        this.id = id;

    }

    Status(Benefit benefit) {
        this.benefit = benefit;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    @Override
    public String toString() {
        return "Status{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", benefit=" + benefit +
                '}';
    }
}
