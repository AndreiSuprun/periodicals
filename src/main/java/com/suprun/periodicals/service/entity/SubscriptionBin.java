package com.suprun.periodicals.service.entity;

import com.suprun.periodicals.entity.Subscription;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents entity of subscription bin for storing items
 */
public class SubscriptionBin {

    private final List<Subscription> items = new ArrayList<>();

    public boolean addItem(Subscription subscription) {
        if (isInBin(subscription)) {
            return false;
        }
        return items.add(subscription);
    }

    public void removeItem(long binItemId) {
        items.removeIf(item -> getBinItemId(item) == binItemId);
    }

    public void removeAll() {
        items.clear();
    }

    public List<Subscription> getItems() {
        return new ArrayList<>(items);
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalValue = new BigDecimal(0);
        for (Subscription subscription : items) {
            BigDecimal monthAmount =
                    new BigDecimal(subscription.getSubscriptionPeriod().getMonthsAmount());
            BigDecimal price = subscription.getPeriodical().getPrice();
            totalValue = totalValue.add(
                    price.multiply(monthAmount));
        }
        return totalValue.setScale(2, RoundingMode.HALF_EVEN);
    }

    public int size() {
        return items.size();
    }

    public boolean isHasNotAvailablePeriodical() {
        return items.stream().anyMatch(this::isNotAvailablePeriodical);
    }

    public void updateItem(Subscription updatedSubscription) {
        if (isInBin(updatedSubscription)) {
            removeItem(getBinItemId(updatedSubscription));
            items.add(updatedSubscription);
        }
    }

    private boolean isInBin(Subscription subscription) {
        long binItemId = getBinItemId(subscription);
        return items.stream().anyMatch(item -> getBinItemId(item) == binItemId);
    }

    private long getBinItemId(Subscription subscription) {
        return subscription.getPeriodical().getId();
    }

    private boolean isNotAvailablePeriodical(Subscription subscription) {
        return !subscription.getPeriodical().getAvailability();
    }
}
