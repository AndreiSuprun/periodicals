package com.suprun.periodicals.dao;

import com.suprun.periodicals.dao.impl.*;

/**
 * Factory class for Dao entities interfaces.
 *
 * @author Andrei Suprun
 */
public class SqlDaoFactory extends DaoFactory {
    private UserDao userDao;
    private RoleDao roleDao;
    private PeriodicalDao periodicalDao;
    private PeriodicalCategoryDao periodicalCategoryDao;
    private FrequencyDao frequencyDao;
    private PublisherDao publisherDao;
    private PaymentDao paymentDao;
    private SubscriptionDao subscriptionDao;
    private SubscriptionPeriodDao subscriptionPeriodDao;

    public SqlDaoFactory() {
        this.userDao = new SqlUserDao();
        this.roleDao = new SqlRoleDao();
        this.periodicalDao = new SqlPeriodicalDao();
        this.periodicalCategoryDao = new SqlPeriodicalCategoryDao();
        this.frequencyDao = new SqlFrequencyDao();
        this.publisherDao = new SqlPublisherDao();
        this.paymentDao = new SqlPaymentDao();
        this.subscriptionDao = new SqlSubscriptionDao();
        this.subscriptionPeriodDao = new SqlSubscriptionPeriodDao();
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public RoleDao getRoleDao() {
        return roleDao;
    }

    @Override
    public PeriodicalDao getPeriodicalDao() {
        return periodicalDao;
    }

    @Override
    public PeriodicalCategoryDao getPeriodicalCategoryDao() {
        return periodicalCategoryDao;
    }

    @Override
    public FrequencyDao getFrequencyDao() {
        return frequencyDao;
    }

    @Override
    public PublisherDao getPublisherDao() {
        return publisherDao;
    }


    @Override
    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    @Override
    public SubscriptionDao getSubscriptionDao() {
        return subscriptionDao;
    }

    @Override
    public SubscriptionPeriodDao getSubscriptionPeriodDao() {
        return subscriptionPeriodDao;
    }
}
