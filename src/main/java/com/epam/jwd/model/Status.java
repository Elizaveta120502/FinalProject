package com.epam.jwd.model;

import java.util.Arrays;
import java.util.List;

public enum Status implements DBEntity {
    MYSTERY(1L,"mystery",Benefit.ZERO),
    FRESHMAN(2L,"freshman",Benefit.FIVE ),
    MIDDLE(3L,"middle",Benefit.TEN ),
    MASTER(4L,"master",Benefit.TWENTY ),
    SUPREME(5L,"supreme",Benefit.THIRTY_FIVE);

    private String description;
    private Long id;
    private Benefit benefit;
    private static final List<Status> ALL_STATUSES_AS_LIST = Arrays.asList(values());

    Status(Long id,String description, Benefit benefit) {
        this.id = id;
        this.description = description;
        this.benefit = benefit;
    }

    @Override
    public Long getId() {
        return id;
    }

    public static List<Status> valuesAsList() {
        return ALL_STATUSES_AS_LIST;
    }

    public static Status of(String name) {
        for (Status status : values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return MYSTERY;
    }


}
