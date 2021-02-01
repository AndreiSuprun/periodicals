package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.*;
import com.suprun.periodicals.dao.transaction.Transaction;
import com.suprun.periodicals.entity.*;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing subscription-related operations
 *
 * @author Andrei Suprun
 */
public class SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);
    private SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();
    private SubscriptionPeriodDao subscriptionPeriodDao = DaoFactory.getInstance().getSubscriptionPeriodDao();
    private PaymentDao paymentDao = DaoFactory.getInstance().getPaymentDao();
    private PeriodicalDao periodicalDao = DaoFactory.getInstance().getPeriodicalDao();

    private SubscriptionService() {
    }

    private static class Instance {

        private final static SubscriptionService INSTANCE = new SubscriptionService();
    }

    public static SubscriptionService getInstance() {
        return Instance.INSTANCE;
    }

    public List<Subscription> findAllSubscriptionsByUserAndStatus(User user,
                                                                  boolean isExpired,
                                                                  long skip,
                                                                  long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all subscriptions by user and status");
        try {
            return subscriptionDao.findByUserAndStatus(user, isExpired, skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Subscription> findAllSubscriptionsByPayment(Payment payment) throws ServiceException {
        LOGGER.debug("Attempt to find all subscriptions by payment");
        List<Subscription> subscriptions;
        try {
            subscriptions = subscriptionDao.findByPayment(payment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (subscriptions.size() > 0) {
            return subscriptions;
        } else {
            LOGGER.error("Payment cannot exist without subscription: {}", payment);
            throw new ServiceException("Payment cannot exist without subscription!");
        }
    }

    public long getSubscriptionsCountByUserAndStatus(User user, boolean isExpired) throws ServiceException {
        LOGGER.debug("Attempt to get active subscriptions count by user");
        try {
            return subscriptionDao.getCountByUserAndStatus(user, isExpired);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void processSubscriptions(User user,
                                     List<Subscription> subscriptions,
                                     BigDecimal totalPrice) throws ServiceException {
        LOGGER.debug("Attempt to process subscriptions");
        if (user == null || totalPrice == null) {
            throw new ServiceException("Attempt to process subscriptions for nullable user or total price");
        }
        if (subscriptions.size() != 0) {
            try {
                Transaction.doTransaction(() -> {
                    Payment payment = Payment.newBuilder()
                            .setUser(user)
                            .setTotalPrice(totalPrice)
                            .setPaymentDate(LocalDateTime.now())
                            .build();
                    Payment paymentInDB = paymentDao.insert(payment);
                    for (Subscription subscription : subscriptions) {
                        Periodical periodical =
                                periodicalDao.findOne(subscription.getPeriodical().getId())
                                        .orElseThrow(() -> new DaoException(
                                                "Subscription cannot refer to a non-existent periodical"));
                        if (!periodical.getAvailability()) {
                            throw new DaoException("Can't subscribe to periodical with SUSPEND status");
                        }
                        subscription.setPayment(paymentInDB);
                        LocalDate startDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
                        subscription.setStartDate(startDate);
                        LocalDate endDate = startDate.plusMonths(subscription.getSubscriptionPeriod().getMonthsAmount());
                        subscription.setEndDate(endDate);
                        subscriptionDao.insert(subscription);
                    }
                });
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    public boolean isAlreadySubscribed(User user, Periodical periodical) throws ServiceException {
        LOGGER.debug("Attempt to check that user is already subscribed");
        try {
            return subscriptionDao.isUserAlreadySubscribed(user, periodical);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<SubscriptionPeriod> findAllSubscriptionPeriods() throws ServiceException {
        LOGGER.debug("Attempt to find all subscription plans");
        try {
            return subscriptionPeriodDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<SubscriptionPeriod> findSubscriptionPeriodById(Integer id) throws ServiceException {
        LOGGER.debug("Attempt to find subscription plan by id");
        try {
            return subscriptionPeriodDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
