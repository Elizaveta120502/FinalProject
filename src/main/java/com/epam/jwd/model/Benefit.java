package com.epam.jwd.model;

import java.util.Arrays;
import java.util.List;


public enum Benefit implements DBEntity {
    ZERO(1L, 0),
    FIVE(2L, 5),
    TEN(3L, 10),
    TWENTY(4L, 20),
    THIRTY_FIVE(5L, 35);

    private Long id;
    private int size;
    private static final List<Benefit> ALL_BENEFITS_AS_LIST = Arrays.asList(values());

    Benefit(Long id, int size) {
        this.id = id;
        this.size = size;
    }

    @Override
    public Long getId() {
        return id;
    }

    public static List<Benefit> valuesAsList() {
        return ALL_BENEFITS_AS_LIST;
    }

    public static Benefit of(int benefitSize) {
        for (Benefit benefit : values()) {
            if (benefit.size == benefitSize) {
                return benefit;
            }
        }
        return ZERO;
    }
}
