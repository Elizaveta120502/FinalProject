package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.AbstractDAO;
import com.epam.jwd.dao.PaymentDAO;
import com.epam.jwd.database.ConnectionPool;
import com.epam.jwd.database.impl.StatementProvider;
import com.epam.jwd.exception.EntityExtractionFailedException;
import com.epam.jwd.logger.LoggerProvider;
import com.epam.jwd.model.DBEntity;
import com.epam.jwd.model.Payment;
import com.epam.jwd.model.PaymentMethod;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl extends AbstractDAO<Payment> implements PaymentDAO<Payment> {

    private static final String TABLE_NAME_EXTENDED = "payment join payment_methods on payment.payment_methods_id = payment_methods.id";
    private static final String TABLE_NAME = "payment";
    private static final String PAYMENT_ID = "payment.id";
    private static final String PAYMENT_TIME = "payment.time";
    private static final String PAYMENT_METHOD_ID = "payment.payment_methods_id";

    private static final String PAYMENT_METHOD_DESCRIPTION = "payment_methods.description";


    private final String WHERE_QUERY_WITH_PARAM = "where %s = ?";
    private static final String SPACE = " ";


    private final String PAYMENT_INSERT_QUERY = "insert into " + TABLE_NAME + SPACE +
            "values(%s,%s,%s)";
    private final String SELECT_ALL_QUERY = "select %s,%s,%s,%s from " + getTableName();
    private final String FETCH_BY_QUERY = "select %s,%s,%s,%s from " + getTableName() + SPACE + WHERE_QUERY_WITH_PARAM;
    private final String READ_BY_ID_QUERY = String.format("select %s,%s,%s,%s from %s",PAYMENT_ID,
            PAYMENT_TIME,PAYMENT_METHOD_ID,PAYMENT_METHOD_DESCRIPTION,getTableName())+ SPACE+ String.format(WHERE_QUERY_WITH_PARAM,PAYMENT_ID);
    private final String FETCH_TYPE_BY_DATE = String.format("select %s,%s,%s,%s from %s",PAYMENT_ID,PAYMENT_TIME,PAYMENT_METHOD_ID,PAYMENT_METHOD_DESCRIPTION,getTableName())
            + SPACE + WHERE_QUERY_WITH_PARAM;
    private final String CURRENT_TIME = "now()";

    protected PaymentDAOImpl(ConnectionPool pool) {
        super(pool);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME_EXTENDED;
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Payment entity) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setTimestamp(2, entity.getDateTime());
        statement.setLong(3, entity.getPaymentMethod().getId());

    }

    @Override
    public boolean create(Payment entity)  {
        String sql = String.format(PAYMENT_INSERT_QUERY, entity.getId(),
                CURRENT_TIME, entity.getPaymentMethod().getId());

        int executeUpdateIndicator = 0;
        try {
            executeUpdateIndicator = StatementProvider.getInstance().executeUpdate(sql);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
        }
        return executeUpdateIndicator == 1;
    }

    @Override
    public List<Payment> readAll() {
        try {
            return StatementProvider.getInstance().executeStatement(String.format(SELECT_ALL_QUERY,PAYMENT_ID,
                    PAYMENT_TIME,PAYMENT_METHOD_ID,PAYMENT_METHOD_DESCRIPTION),
                    PaymentDAOImpl::extractPayment);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Payment> readById(Long id)  {
        try {
            List<Payment> payments = StatementProvider.executePreparedStatement(
                   READ_BY_ID_QUERY,
                    PaymentDAOImpl::extractPayment, st -> st.setLong(1, id));
            return Optional.of(payments.get(0));
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return Optional.empty();
        }catch (IndexOutOfBoundsException e){
            LoggerProvider.getLOG().error("payment not found");
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Payment entity, Long id) throws InterruptedException {
        throw new UnsupportedOperationException("can not change payment operations history");
    }

    @Override
    public boolean deleteById(Long id)  {
        throw new UnsupportedOperationException("can not change payment operations history");
    }

    @Override
    public boolean delete(Payment entity) {
        throw new UnsupportedOperationException("can not change payment operations history");
    }

    @Override
    public Timestamp returnDateById(Long id) {
        String sql = String.format(READ_BY_ID_QUERY, PAYMENT_ID, id);
        try {
            return StatementProvider.executePreparedStatement(
                    sql,
                    PaymentDAOImpl::extractPayment, st -> st.setLong(1, id)).get(0).getDateTime();
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return null;
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return null;
        }
    }

    @Override
    public Payment fetchPaymentByDate(Timestamp date) {
        String sql = String.format(FETCH_BY_QUERY, PAYMENT_ID, PAYMENT_TIME,
                PAYMENT_METHOD_ID,PAYMENT_METHOD_DESCRIPTION, PAYMENT_TIME, date);
        try {
            return StatementProvider.executePreparedStatement(
                    sql,
                    PaymentDAOImpl::extractPayment, st -> st.setTimestamp(1, date)).get(0);
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return null;
        }catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return null;
        }
    }

    @Override
    public PaymentMethod fetchPaymentTypeByDate(Timestamp date) {
        String sql = String.format(FETCH_TYPE_BY_DATE, PAYMENT_TIME, date);
        try {
            return StatementProvider.executePreparedStatement(
                    sql,
                    PaymentDAOImpl::extractPayment, st -> st.setTimestamp(1, date)).get(0).getPaymentMethod();
        } catch (InterruptedException e) {
            LoggerProvider.getLOG().error("takeConnection interrupted");
            Thread.currentThread().interrupt();
            return null;
        } catch (IndexOutOfBoundsException e) {
            LoggerProvider.getLOG().error("index out of bound");
            return null;
        }


    }

    private static Payment extractPayment(ResultSet resultSet) throws EntityExtractionFailedException {
        try {
            return new Payment(
                    resultSet.getLong(PAYMENT_ID),
                    resultSet.getTimestamp(PAYMENT_TIME),
                    PaymentMethod.of(PAYMENT_METHOD_DESCRIPTION));


        } catch (SQLException e) {
            LoggerProvider.getLOG().error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract payment", e);
        }
    }

}
