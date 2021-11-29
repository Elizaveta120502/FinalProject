package com.epam.jwd.service.impl;

import com.epam.jwd.dao.PaymentDAO;
import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.database.impl.ConnectionPoolImpl;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.Payment;
import com.epam.jwd.model.PaymentMethod;
import com.epam.jwd.service.PaymentService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public Optional<Payment> makePayment(PaymentMethod paymentMethod) {

        if (paymentMethod == PaymentMethod.NOT_PAID){
            return Optional.empty();
        }else{
            ConnectionPoolImpl cp = new ConnectionPoolImpl();
            try {
                cp.takeConnection().setAutoCommit(false);
                LocalDateTime actualTime = LocalDateTime.now();
                Timestamp actual = Timestamp.valueOf(actualTime);
                long paymentId = DAOFactory.getInstance().getPaymentDAO().readAll().size() +1;
                Optional<Payment> newPayment =  Optional.ofNullable(new Payment(paymentId,
                        actual,paymentMethod));
                DAOFactory.getInstance().getPaymentDAO().create(newPayment.get());
                cp.takeConnection().commit();
                return newPayment;
            } catch (SQLException e) {
                LoggerProvider.getLOG().error("SQL exception");
                return Optional.empty();
            } catch (InterruptedException e) {
                LoggerProvider.getLOG().error("takeConnection interrupted");
                Thread.currentThread().interrupt();
                return Optional.empty();
            }

        }
    }



}
