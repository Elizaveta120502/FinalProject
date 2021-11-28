package com.epam.jwd.model;

public enum LotStatus implements DBEntity {

    CURRENT(1L,"current"),
    INACTIVE(2L,"inactive");

    private Long id;
    private String description;

    LotStatus(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public static LotStatus of(String name) {
        for (LotStatus lotStatus : values()) {
            if (lotStatus.name().equalsIgnoreCase(name)) {
                return lotStatus;
            }
        }
        return INACTIVE;
    }





}
