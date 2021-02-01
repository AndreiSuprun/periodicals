package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.SubscriptionPeriod;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Entity mapper for SubscriptionPeriod entity.
 *
 * @author Andrei Suprun
 */
public class SubscriptionPeriodMapper implements EntityMapper<SubscriptionPeriod> {
    private static final String ID_FIELD = "subscription_period_id";
    private static final String PERIOD_NAME_FIELD = "subscription_period_name";
    private static final String MONTHS_AMOUNT_FIELD = "subscription_period_months_amount";
    private static final String RATE_FIELD = "subscription_period_rate";

    @Override
    public SubscriptionPeriod mapToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {
        return SubscriptionPeriod.newBuilder()
                .setId(resultSet.getInt(
                        tablePrefix + ID_FIELD))
                .setName(resultSet.getString(
                        tablePrefix + PERIOD_NAME_FIELD))
                .setMonthsAmount(resultSet.getInt(
                        tablePrefix + MONTHS_AMOUNT_FIELD))
                .setRate(resultSet.getBigDecimal(
                        tablePrefix + RATE_FIELD))
                .build();
    }
}
