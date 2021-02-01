package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.User;

import java.util.List;

/**
 * Dao interface for Subscription entities.
 *
 * @author Andrei Suprun
 */
public interface SubscriptionDao extends GenericDao<Subscription, Long> {

    /**
     * Check if user is subscribed to periodical on the current date.
     *
     * @param user user for checked
     * @param periodical periodical to retrieve subscription related with him
     * @return {@code true} if user is already subscribed else {@code false}
     */
    boolean isUserAlreadySubscribed(User user, Periodical periodical) throws DaoException;

    /**
     * Retrieves all active subscriptions associated with certain user.
     *
     * @param user      user of system
     * @param isExpired this expired or active subscription
     * @param skip      skip
     * @param limit     limit
     * @return list of retrieved subscriptions
     */
    List<Subscription> findByUserAndStatus(User user, boolean isExpired, long skip, long limit) throws DaoException;

    /**
     * Retrieves all subscriptions associated with certain payment.
     *
     * @param payment payment
     * @return list of retrieved subscriptions
     */
    List<Subscription> findByPayment(Payment payment) throws DaoException;

    /**
     * Retrieves count of objects from database, which is expired
     *
     * @param user user of system
     * @param isExpired this expired or active subscription
     * @return count of active subscriptions associated with certain user.
     */
    long getCountByUserAndStatus(User user, boolean isExpired) throws DaoException;
}
