package com.epam.jwd.model;

import java.util.Objects;

public class Lot implements DBEntity {
    private Long id;
    private int startingPrice;
    private int itemsAmount;
    private int currentPrice;

    private LotStatus lotStatus;
    private AuctionItem auctionItem;
    private Shipment shipment;
    private Payment payment;
    private Account account;

    public Lot(Long id, int startingPrice, int itemsAmount,
               int currentPrice, LotStatus lotStatus, AuctionItem auctionItem, Shipment shipment,
               Payment payment,Account account) {
        this.id = id;
        this.startingPrice = startingPrice;
        this.itemsAmount = itemsAmount;
        this.currentPrice = currentPrice;
        this.lotStatus = lotStatus;
        this.auctionItem = auctionItem;
        this.shipment = shipment;
        this.payment = payment;
        this.account = account;
    }

    public Lot() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public int getItemsAmount() {
        return itemsAmount;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public LotStatus getLotStatus() {
        return lotStatus;
    }

    public AuctionItem getAuctionItem() {
        return auctionItem;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Payment getPayment() {
        return payment;
    }

    public Account getAccount() {
        return account;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setItemsAmount(int itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setLotStatus(LotStatus lotStatus) {
        this.lotStatus = lotStatus;
    }

    public void setAuctionItem(AuctionItem auctionItem) {
        this.auctionItem = auctionItem;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot)) return false;
        Lot lot = (Lot) o;
        return getStartingPrice() == lot.getStartingPrice() && getItemsAmount() == lot.getItemsAmount() && getCurrentPrice() == lot.getCurrentPrice() && Objects.equals(getId(), lot.getId()) && getLotStatus() == lot.getLotStatus() && Objects.equals(getAuctionItem(), lot.getAuctionItem()) && Objects.equals(getShipment(), lot.getShipment()) && Objects.equals(getPayment(), lot.getPayment()) && Objects.equals(getAccount(), lot.getAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartingPrice(), getItemsAmount(), getCurrentPrice(), getLotStatus(), getAuctionItem(), getShipment(), getPayment(), getAccount());
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", startingPrice=" + startingPrice +
                ", itemsAmount=" + itemsAmount +
                ", currentPrice=" + currentPrice +
                ", lotStatus=" + lotStatus +
                ", auctionItem=" + auctionItem +
                ", shipment=" + shipment +
                ", payment=" + payment +
                ", account=" + account +
                '}';
    }
}
