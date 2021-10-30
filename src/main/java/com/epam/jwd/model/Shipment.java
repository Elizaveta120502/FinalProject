package com.epam.jwd.model;

import java.util.Date;
import java.util.Objects;

public class Shipment implements DBEntity {

    private Long id;
    private Date expectedDate;
    private Date actualDate;
    private int cost;
    private ShipmentMethod shipmentMethod;

    public Shipment(Long id, Date expectedDate, Date actualDate, int cost, ShipmentMethod shipmentMethod) {
        this.id = id;
        this.expectedDate = expectedDate;
        this.actualDate = actualDate;
        this.cost = cost;
        this.shipmentMethod = shipmentMethod;
    }

    public Long getId() {
        return id;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public int getCost() {
        return cost;
    }

    public ShipmentMethod getShipmentMethod() {
        return shipmentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shipment)) return false;
        Shipment shipment = (Shipment) o;
        return id == shipment.id && cost == shipment.cost && Objects.equals(expectedDate, shipment.expectedDate) && Objects.equals(actualDate, shipment.actualDate) && shipmentMethod == shipment.shipmentMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expectedDate, actualDate, cost, shipmentMethod);
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", expectedDate=" + expectedDate +
                ", actualDate=" + actualDate +
                ", cost=" + cost +
                ", shipmentMethod=" + shipmentMethod +
                '}';
    }
}
