package com.epam.jwd.model;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.Objects;

public class Shipment implements DBEntity {

    private Long id;
    private Timestamp expectedDate;
    private Timestamp actualDate;
    private int cost;
    private ShipmentMethod shipmentMethod;
    private LocalDateTime actualTime = LocalDateTime.now();

    public Shipment(Long id, Timestamp expectedDate, Timestamp actualDate, int cost, ShipmentMethod shipmentMethod) {
        this.id = id;
        this.expectedDate = expectedDate;
        this.actualDate = Timestamp.valueOf(actualTime);
        this.cost = cost;
        this.shipmentMethod = shipmentMethod;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getExpectedDate() {
        return expectedDate;
    }

    public Timestamp getActualDate() {
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
