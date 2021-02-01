package com.suprun.periodicals.service.entity;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.provider.EntityProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class SubscriptionBinTest {
    private SubscriptionBin subscriptionBin;

    @BeforeMethod
    private void setUp() {
        subscriptionBin = new SubscriptionBin();
    }

    @Test
    void addItemTest() {
        Periodical periodical = Periodical.newBuilder()
                .setId(1L)
                .build();
        Subscription subscription = Subscription.newBuilder()
                .setPeriodical(periodical)
                .build();

        assertEquals(0, subscriptionBin.size());
        assertTrue(subscriptionBin.addItem(subscription));
        assertEquals(1, subscriptionBin.size());
        assertFalse(subscriptionBin.addItem(subscription));
        assertEquals(1, subscriptionBin.size());
    }

    @Test
    void removeItemTest() {
        long id = 1L;
        Periodical periodical = Periodical.newBuilder()
                .setId(id)
                .build();
        Subscription subscription = Subscription.newBuilder()
                .setPeriodical(periodical)
                .build();

        assertTrue(subscriptionBin.addItem(subscription));
        assertEquals(1, subscriptionBin.size());
        subscriptionBin.removeItem(id);
        assertEquals(0, subscriptionBin.size());
    }

    @Test
    void removeItemInvalidIdTest() {
        long id = 1L;
        Periodical periodical = Periodical.newBuilder()
                .setId(id)
                .build();
        Subscription subscription = Subscription.newBuilder()
                .setPeriodical(periodical)
                .build();

        assertTrue(subscriptionBin.addItem(subscription));
        assertEquals(1, subscriptionBin.size());

        subscriptionBin.removeItem(999);

        assertEquals(1, subscriptionBin.size());
    }

    @Test
    void getTotalPriceTest() {
        Periodical periodical1 = Periodical.newBuilder()
                .setId(1L)
                .setPrice(new BigDecimal(1))
                .build();
        Periodical periodical2 = Periodical.newBuilder()
                .setId(2L)
                .setPrice(new BigDecimal(2))
                .build();
        Periodical periodical3 = Periodical.newBuilder()
                .setId(3L)
                .setPrice(new BigDecimal(3))
                .build();

        List<Subscription> subscriptions = new ArrayList<>() {{
            add(Subscription.newBuilder()
                    .setPeriodical(periodical1)
                    .setSubscriptionPeriod(EntityProvider.getOneMonthSubscriptionPeriod())
                    .build());
            add(Subscription.newBuilder()
                    .setPeriodical(periodical2)
                    .setSubscriptionPeriod(EntityProvider.getThreeMonthSubscriptionPeriod())
                    .build());
            add(Subscription.newBuilder()
                    .setPeriodical(periodical3)
                    .setSubscriptionPeriod(EntityProvider.getSixMonthSubscriptionPeriod())
                    .build());
        }};

        BigDecimal expectedTotalPrice = new BigDecimal(1);
        subscriptionBin.addItem(subscriptions.get(0));
        assertEquals(0, expectedTotalPrice.compareTo(
                subscriptionBin.getTotalPrice()));

        expectedTotalPrice = new BigDecimal(7);
        subscriptionBin.addItem(subscriptions.get(1));
        assertEquals(0, expectedTotalPrice.compareTo(
                subscriptionBin.getTotalPrice()));

        expectedTotalPrice = new BigDecimal(25);
        subscriptionBin.addItem(subscriptions.get(2));
        assertEquals(0, expectedTotalPrice.compareTo(
                subscriptionBin.getTotalPrice()));

    }

    @Test
    void isHasSuspendedPeriodicalTest() {
        Periodical activePeriodical = Periodical.newBuilder()
                .setId(1L)
                .setAvailability(true)
                .build();
        Periodical suspendedPeriodical = Periodical.newBuilder()
                .setId(2L)
                .setAvailability(false)
                .build();
        Subscription subscriptionWithActive = Subscription.newBuilder()
                .setPeriodical(activePeriodical)
                .setSubscriptionPeriod(EntityProvider.getOneMonthSubscriptionPeriod())
                .build();
        Subscription subscriptionWithSuspended = Subscription.newBuilder()
                .setPeriodical(suspendedPeriodical)
                .setSubscriptionPeriod(EntityProvider.getOneMonthSubscriptionPeriod())
                .build();

        subscriptionBin.addItem(subscriptionWithActive);
        assertFalse(subscriptionBin.isHasNotAvailablePeriodical());

        subscriptionBin.addItem(subscriptionWithSuspended);
        assertTrue(subscriptionBin.isHasNotAvailablePeriodical());
    }

    @Test
    void getItemsTest() {
        Periodical periodical1 = Periodical.newBuilder()
                .setId(1L)
                .build();
        Periodical periodical2 = Periodical.newBuilder()
                .setId(2L)
                .build();
        List<Subscription> expected = new ArrayList<>() {{
            add(Subscription.newBuilder()
                    .setPeriodical(periodical1)
                    .build());
            add(Subscription.newBuilder()
                    .setPeriodical(periodical2)
                    .build());
        }};

        subscriptionBin.addItem(expected.get(0));
        subscriptionBin.addItem(expected.get(1));

        List<Subscription> actual = subscriptionBin.getItems();

        assertNotSame(expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateItemWithSameItemTest() {
        Periodical periodical = Periodical.newBuilder()
                .setId(1L)
                .setAvailability(true)
                .build();
        Periodical samePeriodicalWithUpdatedStatus = Periodical.newBuilder()
                .setId(1L)
                .setAvailability(false)
                .build();
        Subscription subscriptionWithActive = Subscription.newBuilder()
                .setPeriodical(periodical)
                .build();
        Subscription sameSubscriptionWithSuspended = Subscription.newBuilder()
                .setPeriodical(samePeriodicalWithUpdatedStatus)
                .build();

        subscriptionBin.addItem(subscriptionWithActive);
        subscriptionBin.updateItem(sameSubscriptionWithSuspended);

        List<Subscription> expected = new ArrayList<>() {{
            add(sameSubscriptionWithSuspended);
        }};
        List<Subscription> actual = subscriptionBin.getItems();

        assertNotSame(subscriptionWithActive, sameSubscriptionWithSuspended);
        assertNotSame(expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateItemWithAnotherItemTest() {
        Periodical periodical = Periodical.newBuilder()
                .setId(1L)
                .setAvailability(true)
                .build();
        Periodical anotherPeriodicalWithUpdatedStatus = Periodical.newBuilder()
                .setId(2L)
                .setAvailability(false)
                .build();
        Subscription subscriptionWithActive = Subscription.newBuilder()
                .setPeriodical(periodical)
                .build();
        Subscription anotherSubscriptionWithSuspended = Subscription.newBuilder()
                .setPeriodical(anotherPeriodicalWithUpdatedStatus)
                .build();

        subscriptionBin.addItem(subscriptionWithActive);
        subscriptionBin.updateItem(anotherSubscriptionWithSuspended);

        List<Subscription> expected = new ArrayList<>() {{
            add(subscriptionWithActive);
        }};
        List<Subscription> actual = subscriptionBin.getItems();

        assertNotSame(subscriptionWithActive, anotherSubscriptionWithSuspended);
        assertNotSame(expected, actual);
        assertEquals(expected, actual);
    }
}