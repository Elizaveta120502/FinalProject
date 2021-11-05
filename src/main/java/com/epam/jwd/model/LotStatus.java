package com.epam.jwd.model;

public class  LotStatus implements DBEntity {

//    CURRENT("current", 1L),
//    INACTIVE("inactive", 2L);

    private Long id;
    private String description;

    public LotStatus(String description, Long id) {
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
        return "LotStatus{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
