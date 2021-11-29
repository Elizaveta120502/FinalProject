package com.epam.jwd.service;

import com.epam.jwd.model.Payment;
import com.epam.jwd.model.PaymentMethod;

public interface PaymentService {

    boolean makePayment(Payment payment);

    boolean makeBackPayment(Payment payment);

    PaymentMethod choosePaymentType(PaymentMethod... methods);


}
