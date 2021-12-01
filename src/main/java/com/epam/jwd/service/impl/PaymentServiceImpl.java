package com.epam.jwd.service.impl;

import com.epam.jwd.dao.PaymentDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Payment;
import com.epam.jwd.model.PaymentMethod;
import com.epam.jwd.service.PaymentService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    private static final long PREDICATE_OF_ID = 220900L;
    private static final long ADDING_TO_ID = 2L;

    @Override
    public Optional<Payment> makePayment(PaymentMethod paymentMethod) {

        if (paymentMethod == PaymentMethod.NOT_PAID) {
            return Optional.empty();
        } else {

            try {
                LocalDateTime actualTime = LocalDateTime.now();
                Timestamp actual = Timestamp.valueOf(actualTime);
                long paymentId = PREDICATE_OF_ID + DAOFactory.getInstance().getPaymentDAO().readAll().size() + ADDING_TO_ID;
                Optional<Payment> newPayment = Optional.of(new Payment(paymentId,
                        actual, paymentMethod));
                DAOFactory.getInstance().getPaymentDAO().create(newPayment.get());
                return newPayment;
            } catch (InterruptedException e) {
                LoggerProvider.getLOG().error("takeConnection interrupted");
                Thread.currentThread().interrupt();
                return Optional.empty();
            }

        }
    }


}
