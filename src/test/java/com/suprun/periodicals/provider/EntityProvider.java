package com.suprun.periodicals.provider;

import com.suprun.periodicals.entity.SubscriptionPeriod;

import java.math.BigDecimal;

public class EntityProvider {

    public static SubscriptionPeriod getOneMonthSubscriptionPeriod() {
        return SubscriptionPeriod.newBuilder()
                .setMonthsAmount(1)
                .setRate(new BigDecimal(1))
                .build();
    }

    public static SubscriptionPeriod getThreeMonthSubscriptionPeriod() {
        return SubscriptionPeriod.newBuilder()
                .setMonthsAmount(3)
                .setRate(new BigDecimal(3))
                .build();
    }

    public static SubscriptionPeriod getSixMonthSubscriptionPeriod() {
        return SubscriptionPeriod.newBuilder()
                .setMonthsAmount(6)
                .setRate(new BigDecimal(6))
                .build();
    }
}
