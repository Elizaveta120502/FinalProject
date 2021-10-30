package com.epam.jwd.model;

public enum Status implements DBEntity {
    MYSTERY("mystery", 1L),
    FRESHMAN("freshman", 2L),
    MIDDLE("middle", 3L),
    MASTER("master", 4L),
    SUPREME("supreme", 5L);

    private String description;
    private Long id;
    private Benefit benefit;

    Status(String description, Long id) {
        this.description = description;
        this.id = id;

    }

    Status(Benefit benefit) {
        this.benefit = benefit;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Long getId() {

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
