package com.epam.jwd.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Payment implements DBEntity {

    private Long id;
    private Timestamp dateTime;

    private PaymentMethod paymentMethod;

    public Payment(Long id, Timestamp dateTime, PaymentMethod paymentMethod) {
        this.id = id;
        this.dateTime = dateTime;
        this.paymentMethod = paymentMethod;
    }




    @Override
    public Long getId() {
        return id;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return getId() == payment.getId() && Objects.equals(getDateTime(), payment.getDateTime()) && getPaymentMethod() == payment.getPaymentMethod();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateTime(), getPaymentMethod());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
