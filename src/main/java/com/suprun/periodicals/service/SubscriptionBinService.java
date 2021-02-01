package com.suprun.periodicals.service;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Service responsible for processing some actions
 * that has to do with subscription bin
 *
 * @author Andrei Suprun
 * @see SubscriptionBin
 */
public class SubscriptionBinService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionBinService.class);
    private PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    private SubscriptionBinService() {
    }

    private static class Instance {
        private final static SubscriptionBinService INSTANCE = new SubscriptionBinService();
    }

    public static SubscriptionBinService getInstance() {
        return Instance.INSTANCE;
    }

    public boolean addItemToBin(SubscriptionBin subscriptionBin,
                                 User user,
                                 Periodical periodical,
                                 SubscriptionPeriod subscriptionPeriod) {
        LOGGER.debug("Attempt to add item to cart");
        Subscription subscription = Subscription.newBuilder()
                .setUser(user)
                .setPeriodical(periodical)
                .setSubscriptionPeriod(subscriptionPeriod)
                .build();
        return subscriptionBin.addItem(subscription);
    }

    public void removeItemFromBin(SubscriptionBin subscriptionBin, Long binItemId) {
        LOGGER.debug("Attempt to remove item from cart");
        subscriptionBin.removeItem(binItemId);
    }

    public void removeAllItemFromBin(SubscriptionBin subscriptionBin) {
        LOGGER.debug("Attempt to remove all item from cart");
        subscriptionBin.removeAll();
    }

    public void updateSubscriptionBinItemsFromDatabase(SubscriptionBin subscriptionBin) throws ServiceException {
        for (Subscription subscription : subscriptionBin.getItems()) {
            Optional<Periodical> periodicalOpt =
                    periodicalService.findPeriodicalById(subscription.getPeriodical().getId());
            if (periodicalOpt.isPresent()) {
                subscription.setPeriodical(periodicalOpt.get());
                subscriptionBin.updateItem(subscription);
            } else {
                LOGGER.error("A subscription cannot refer to a non-existent periodical");
                throw new ServiceException("A subscription cannot refer to a non-existent periodical");
            }
        }
    }
}
