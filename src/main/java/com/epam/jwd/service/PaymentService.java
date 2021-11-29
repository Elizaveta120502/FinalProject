package com.epam.jwd.service;

import com.epam.jwd.model.Payment;
import com.epam.jwd.model.PaymentMethod;

import java.util.Optional;

public interface PaymentService {

    Optional<Payment> makePayment(PaymentMethod paymentMethod);


    static PaymentMethod choosePaymentType(String paymentMethod){

        switch (paymentMethod){
            case "Erip":
                return PaymentMethod.ERIP;
            case "Mastercard":
                return PaymentMethod.MASTERCARD;
            case "on Credit":
                return PaymentMethod.ON_CREDIT;
            case "Maestro":
                return PaymentMethod.MAESTRO;
            default:
                return PaymentMethod.NOT_PAID;
        }
    }


}
