package com.epam.jwd.dao;


import com.epam.jwd.model.Payment;
import com.epam.jwd.model.PaymentMethod;

import java.util.Date;

public interface PaymentDAO extends DBEntityDAO<Payment> {

    Date returnDateById(Long id);

    Payment fetchPaymentByDate(Date date);

    PaymentMethod fetchPaymentTypeByDate(Date date);

}
