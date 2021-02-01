package com.suprun.periodicals.dao;

/**
 * Factory for DAO entities creation
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

    /**
     * Return DAO for User entity
     *
     * @return UserDao
     */
    public abstract UserDao getUserDao();

    /**
     * Return DAO for Role entity
     *
     * @return RoleDao
     */
    public abstract RoleDao getRoleDao();

    /**
     * Return DAO for Periodical entity
     *
     * @return PeriodicalDao
     */
    public abstract PeriodicalDao getPeriodicalDao();

    /**
     * Return DAO for PeriodicalCategory entity
     *
     * @return PeriodicalCategoryDao
     */
    public abstract PeriodicalCategoryDao getPeriodicalCategoryDao();

    /**
     * Return DAO for Frequency entity
     *
     * @return FrequencyDao
     */
    public abstract FrequencyDao getFrequencyDao();

    /**
     * Return DAO for Publisher entity
     *
     * @return PublisherDao
     */
    public abstract PublisherDao getPublisherDao();

    /**
     * Return DAO for Payment entity
     *
     * @return PaymentDao
     */
    public abstract PaymentDao getPaymentDao();

    /**
     * Return DAO for Subscription entity
     *
     * @return SubscriptionDao
     */
    public abstract SubscriptionDao getSubscriptionDao();

    /**
     * Return DAO for SubscriptionPeriod entity
     *
     * @return SubscriptionPeriodDao
     */
    public abstract SubscriptionPeriodDao getSubscriptionPeriodDao();
}
