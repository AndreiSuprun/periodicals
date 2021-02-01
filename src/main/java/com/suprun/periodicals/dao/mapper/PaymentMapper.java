package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity mapper for Payment entity.
 *
 * @author Andrei Suprun
 */
public class PaymentMapper implements EntityMapper<Payment> {

    private static final String ID_FIELD = "payment_id";
    private static final String TOTAL_PRICE_FIELD = "payment_amount";
    private static final String PAID_FIELD = "paid";
    private static final String PAYMENT_DATE_FIELD = "payment_date";
    private final EntityMapper<User> userMapper;

    public PaymentMapper() {
        this(new UserMapper());
    }

    public PaymentMapper(EntityMapper<User> userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Payment mapToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {
        User tempUser = userMapper.mapToObject(resultSet);

        return Payment.newBuilder()
                .setId(resultSet.getLong(
                        tablePrefix + ID_FIELD))
                .setUser(tempUser)
                .setTotalPrice(resultSet.getBigDecimal(
                        tablePrefix + TOTAL_PRICE_FIELD))
                .setPaymentDate(resultSet.getObject(
                        tablePrefix + PAYMENT_DATE_FIELD, LocalDateTime.class))
               // .setPaid(Boolean.parseBoolean(resultSet.getString(tablePrefix + PAID_FIELD)))
                .build();
    }
}
