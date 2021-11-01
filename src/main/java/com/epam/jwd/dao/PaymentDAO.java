package com.epam.jwd.dao;

import com.epam.jwd.model.DBEntity;
import com.epam.jwd.model.PaymentMethod;

import java.sql.Timestamp;

public interface PaymentDAO<Payment extends DBEntity> extends DBEntityDAO<Payment> {

    Timestamp returnDateById(Long id);

    Payment fetchPaymentByDate(Timestamp date);

    PaymentMethod fetchPaymentTypeByDate(Timestamp date);

}
