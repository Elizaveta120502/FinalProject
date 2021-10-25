package com.epam.jwd.model;

import java.util.Objects;

public class Benefit {

    private final int id;
    private final int size;

    public Benefit(int id, int size) {
        this.id = id;
        this.size = size;
    }


    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Benefit)) return false;
        Benefit benefits = (Benefit) o;
        return id == benefits.id && size == benefits.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size);
    }

    @Override
    public String toString() {
        return "Benefits{" +
                "id=" + id +
                ", size=" + size +
                '}';
    }
}
