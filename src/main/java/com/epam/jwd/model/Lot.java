package com.epam.jwd.model;

import java.util.Objects;

public class Lot {
    private int id;
    private int startingPrice;
    private int itemsAmount;
    private int currentPrice;

    private AuctionItem auctionItem;
    private Shipment shipment;
    private Payment payment;
    private LotStatus lotStatus;

    public Lot(int id, int startingPrice, int itemsAmount,
               int currentPrice, AuctionItem auctionItem, Shipment shipment,
               Payment payment, LotStatus lotStatus) {
        this.id = id;
        this.startingPrice = startingPrice;
        this.itemsAmount = itemsAmount;
        this.currentPrice = currentPrice;
        this.auctionItem = auctionItem;
        this.shipment = shipment;
        this.payment = payment;
        this.lotStatus = lotStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getItemsAmount() {
        return itemsAmount;
    }

    public void setItemsAmount(int itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public AuctionItem getAuctionItem() {
        return auctionItem;
    }

    public void setAuctionItem(AuctionItem auctionItem) {
        this.auctionItem = auctionItem;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public LotStatus getLotStatus() {
        return lotStatus;
    }

    public void setLotStatus(LotStatus lotStatus) {
        this.lotStatus = lotStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot)) return false;
        Lot lot = (Lot) o;
        return getId() == lot.getId() && getStartingPrice() == lot.getStartingPrice() && getItemsAmount() == lot.getItemsAmount() && getCurrentPrice() == lot.getCurrentPrice() && Objects.equals(getAuctionItem(), lot.getAuctionItem()) && Objects.equals(getShipment(), lot.getShipment()) && Objects.equals(getPayment(), lot.getPayment()) && getLotStatus() == lot.getLotStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartingPrice(), getItemsAmount(), getCurrentPrice(), getAuctionItem(), getShipment(), getPayment(), getLotStatus());
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", startingPrice=" + startingPrice +
                ", itemsAmount=" + itemsAmount +
                ", currentPrice=" + currentPrice +
                ", auctionItem=" + auctionItem +
                ", shipment=" + shipment +
                ", payment=" + payment +
                ", lotStatus=" + lotStatus +
                '}';
    }
}
