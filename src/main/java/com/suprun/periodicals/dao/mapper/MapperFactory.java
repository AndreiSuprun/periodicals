package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.*;

public class MapperFactory {

    private static final EntityMapper<Frequency> FREQUENCY_MAPPER = new FrequencyMapper();
    private static final EntityMapper<Payment> PAYMENT_MAPPER = new PaymentMapper();
    private static final EntityMapper<Periodical> PERIODICAL_MAPPER = new PeriodicalMapper();
    private static final EntityMapper<PeriodicalCategory> PERIODICAL_CATEGORY_MAPPER = new PeriodicalCategoryMapper();
    private static final EntityMapper<Publisher> PUBLISHER_MAPPER = new PublisherMapper();
    private static final EntityMapper<Role> ROLE_MAPPER = new RoleMapper();
    private static final EntityMapper<Subscription> SUBSCRIPTION_MAPPER = new SubscriptionMapper();
    private static final EntityMapper<SubscriptionPeriod> SUBSCRIPTION_PERIOD_MAPPER = new SubscriptionPeriodMapper();
    private static final EntityMapper<User> USER_MAPPER = new UserMapper();

    public static EntityMapper<Frequency> getFrequencyMapper() {
        return FREQUENCY_MAPPER;
    }

    public static EntityMapper<Payment> getPaymentMapper() {
        return PAYMENT_MAPPER;
    }

    public static EntityMapper<Periodical> getPeriodicalMapper() {
        return PERIODICAL_MAPPER;
    }

    public static EntityMapper<PeriodicalCategory> getPeriodicalCategoryMapper() {
        return PERIODICAL_CATEGORY_MAPPER;
    }

    public static EntityMapper<Publisher> getPublisherMapper() {
        return PUBLISHER_MAPPER;
    }

    public static EntityMapper<Role> getRoleMapper() {
        return ROLE_MAPPER;
    }

    public static EntityMapper<Subscription> getSubscriptionMapper() {
        return SUBSCRIPTION_MAPPER;
    }

    public static EntityMapper<SubscriptionPeriod> getSubscriptionPeriodMapper() {
        return SUBSCRIPTION_PERIOD_MAPPER;
    }

    public static EntityMapper<User> getUserMapper() {
        return USER_MAPPER;
    }
}
