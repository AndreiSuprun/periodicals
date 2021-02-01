package com.suprun.periodicals.dao;

/**
 * Factory that creates DAO entities
 *
 * @author Andrei Suprun
 */

public abstract class DaoFactory {

    private static volatile DaoFactory instance;

    public static DaoFactory getInstance() {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    instance = new SqlDaoFactory();
                }
            }
        }
        return instance;
    }

    public abstract UserDao getUserDao();

    public abstract RoleDao getRoleDao();

    public abstract PeriodicalDao getPeriodicalDao();

    public abstract PeriodicalCategoryDao getPeriodicalCategoryDao();

    public abstract FrequencyDao getFrequencyDao();

    public abstract PublisherDao getPublisherDao();

    public abstract PaymentDao getPaymentDao();

    public abstract SubscriptionDao getSubscriptionDao();

    public abstract SubscriptionPeriodDao getSubscriptionPeriodDao();
}
