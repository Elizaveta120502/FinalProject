package com.epam.jwd.model;

import java.util.Objects;

public class AuctionItem implements DBEntity {

    private Long id;
    private final String title;
    private int price;
    private int inStoke;
    private Picture picture;

    public AuctionItem(Long id, String title, int price, int inStoke, Picture picture) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.inStoke = inStoke;
        this.picture = picture;
    }

    @Override
    public Long getId() {
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

    public void setInStoke(int inStoke) {
        this.inStoke = inStoke;
    }

    public Picture getPicture() {
        return picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuctionItem)) return false;
        AuctionItem that = (AuctionItem) o;
        return getPrice() == that.getPrice() && getInStoke() == that.getInStoke() && Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getPicture(), that.getPicture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPrice(), getInStoke(), getPicture());
    }

    @Override
    public String toString() {
        return "AuctionItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", inStoke=" + inStoke +
                ", picture=" + picture +
                '}';
    }
}
