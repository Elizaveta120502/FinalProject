package com.epam.jwd.model;

import java.util.Objects;

public class AuctionItem {

    private int id;
    private final String title;
    private int price;
    private int inStoke;

    public AuctionItem(int id, String title, int price, int inStoke) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.inStoke = inStoke;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getInStoke() {
        return inStoke;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuctionItem)) return false;
        AuctionItem that = (AuctionItem) o;
        return getId() == that.getId() && getPrice() == that.getPrice() && getInStoke() == that.getInStoke() && Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPrice(), getInStoke());
    }

    @Override
    public String toString() {
        return "AuctionItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", inStoke=" + inStoke +
                '}';
    }
}
