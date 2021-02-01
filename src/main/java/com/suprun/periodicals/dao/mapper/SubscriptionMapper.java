package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Entity mapper for Subscription entity.
 *
 * @author Andrei Suprun
 */
public class SubscriptionMapper implements EntityMapper<Subscription> {
    private static final String ID_FIELD = "subscription_id";
    private static final String START_DATE_FIELD = "subscription_start_date";
    private static final String END_DATE_FIELD = "subscription_end_date";

    private final EntityMapper<User> userMapper;
    private final EntityMapper<Payment> paymentMapper;
    private final EntityMapper<Periodical> periodicalMapper;
    private final EntityMapper<SubscriptionPeriod> subscriptionPeriodMapper;

    public SubscriptionMapper() {
        this(new UserMapper(),
                new PaymentMapper(),
                new PeriodicalMapper(),
                new SubscriptionPeriodMapper());
    }

    public SubscriptionMapper(EntityMapper<User> userMapper,
                              EntityMapper<Payment> paymentMapper,
                              EntityMapper<Periodical> periodicalMapper,
                              EntityMapper<SubscriptionPeriod> subscriptionPeriodMapper) {
        this.userMapper = userMapper;
        this.paymentMapper = paymentMapper;
        this.periodicalMapper = periodicalMapper;
        this.subscriptionPeriodMapper = subscriptionPeriodMapper;
    }

    @Override
    public Subscription mapToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {
        User tempUser = userMapper.mapToObject(resultSet);
        Payment tempPayment = paymentMapper.mapToObject(resultSet);
        Periodical tempPeriodical = periodicalMapper.mapToObject(resultSet);
        SubscriptionPeriod tempSubscriptionPeriod = subscriptionPeriodMapper.mapToObject(resultSet);

        return Subscription.newBuilder()
                .setId(resultSet.getLong(
                        tablePrefix + ID_FIELD))
                .setUser(tempUser)
                .setPayment(tempPayment)
                .setPeriodical(tempPeriodical)
                .setSubscriptionPeriod(tempSubscriptionPeriod)
                .setStartDate(resultSet.getObject(
                        tablePrefix + START_DATE_FIELD, LocalDate.class))
                .setEndDate(resultSet.getObject(
                        tablePrefix + END_DATE_FIELD, LocalDate.class))
                .build();
    }
}
