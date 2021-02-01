package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.SubscriptionDao;
import com.suprun.periodicals.dao.SubscriptionPeriodDao;
import com.suprun.periodicals.entity.*;
import com.suprun.periodicals.provider.EntityProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.testng.Assert.*;

public class SubscriptionServiceTest {
    @InjectMocks
    private SubscriptionService subscriptionService = SubscriptionService.getInstance();
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private SubscriptionPeriodDao subscriptionPlanDao;
    @Mock
    private PaymentService paymentService;
    @Mock
    private PeriodicalService periodicalService;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAllSubscriptionsByUserAndStatusTest() throws DaoException, ServiceException {
        long skip = 0;
        long limit = 3;
        final boolean isExpired = false;
        User user = User.newBuilder()
                .setId(1L)
                .build();
        List<Subscription> expected = new ArrayList<Subscription>() {{
            add(Subscription.newBuilder().setId(1L).build());
            add(Subscription.newBuilder().setId(2L).build());
            add(Subscription.newBuilder().setId(3L).build());
        }};
        when(subscriptionDao.findByUserAndStatus(user, isExpired, skip, limit))
                .thenReturn(expected);

        List<Subscription> actual =
                subscriptionService.findAllSubscriptionsByUserAndStatus(user, isExpired, skip, limit);

        assertEquals(3, actual.size());
        verify(subscriptionDao, times(1))
                .findByUserAndStatus(user, isExpired, skip, limit);
    }

    @Test
    void findAllSubscriptionsByPaymentWithExistingSubscriptionsTest() throws DaoException, ServiceException {
        Payment payment = Payment.newBuilder()
                .setId(1L)
                .build();
        List<Subscription> expected = new ArrayList<Subscription>() {{
            add(Subscription.newBuilder().setId(1L).build());
            add(Subscription.newBuilder().setId(2L).build());
            add(Subscription.newBuilder().setId(3L).build());
        }};
        when(subscriptionDao.findByPayment(payment))
                .thenReturn(expected);

        List<Subscription> actual =
                subscriptionService.findAllSubscriptionsByPayment(payment);

        assertEquals(3, actual.size());
        verify(subscriptionDao, times(1)).findByPayment(payment);
    }

    @Test
    void findAllSubscriptionsByPaymentWithNotExistingSubscriptionsTest() throws DaoException {
        Payment payment = Payment.newBuilder()
                .setId(1L)
                .build();
        when(subscriptionDao.findByPayment(payment))
                .thenReturn(Collections.emptyList());

        assertThrows(ServiceException.class, () ->
                subscriptionService.findAllSubscriptionsByPayment(payment));
        verify(subscriptionDao, times(1)).findByPayment(payment);
    }

    @Test
    void getSubscriptionsCountByUserAndStatusTest() throws ServiceException, DaoException {
        final boolean isExpired = false;
        User user = User.newBuilder()
                .setId(1L)
                .build();
        long expected = 10;
        when(subscriptionDao.getCountByUserAndStatus(user, isExpired))
                .thenReturn(expected);

        long actual =
                subscriptionService.getSubscriptionsCountByUserAndStatus(user, isExpired);

        assertEquals(expected, actual);
        verify(subscriptionDao, times(1))
                .getCountByUserAndStatus(user, isExpired);
    }

    @Test
    void processSubscriptionsTestWithNotValidData() throws ServiceException, DaoException {
        User user = User.newBuilder()
                .setId(1L)
                .build();
        List<Subscription> subscriptions = Collections.emptyList();
        BigDecimal totalPrice = new BigDecimal("10");

        subscriptionService.processSubscriptions(user, subscriptions, totalPrice);

        verify(paymentService, never()).createPayment(user, totalPrice);
        verify(periodicalService, never()).findPeriodicalById(anyLong());
        verify(subscriptionDao, never()).insert(any(Subscription.class));
    }

    @Test
    void isAlreadySubscribed() throws DaoException, ServiceException {
        final boolean isSubscribed = false;
        User user = User.newBuilder()
                .setId(1L)
                .build();
        Periodical periodical = Periodical.newBuilder()
                .setId(1L)
                .build();
        when(subscriptionDao.isUserAlreadySubscribed(user, periodical)).thenReturn(isSubscribed);

        assertFalse(subscriptionService.isAlreadySubscribed(user, periodical));
    }

    @Test
    void findAllSubscriptionPlans() throws DaoException, ServiceException {
        List<SubscriptionPeriod> expected = new ArrayList<SubscriptionPeriod>() {{
            add(EntityProvider.getOneMonthSubscriptionPeriod());
            add(EntityProvider.getThreeMonthSubscriptionPeriod());
            add(EntityProvider.getSixMonthSubscriptionPeriod());
        }};
        when(subscriptionPlanDao.findAll()).thenReturn(expected);

        List<SubscriptionPeriod> actual = subscriptionService.findAllSubscriptionPeriods();

        assertEquals(3, actual.size());
        verify(subscriptionPlanDao, times(1)).findAll();
    }

    @Test
    void findSubscriptionPeriodByIdWithExistingSubscriptionPeriodTest() throws DaoException, ServiceException {
        Integer subscriptionPlanId = 1;
        Optional<SubscriptionPeriod> expected = Optional.of(
                SubscriptionPeriod.newBuilder()
                        .setId(subscriptionPlanId)
                        .build());
        when(subscriptionPlanDao.findOne(subscriptionPlanId)).thenReturn(expected);

        Optional<SubscriptionPeriod> actual =
                subscriptionService.findSubscriptionPeriodById(subscriptionPlanId);

        assertEquals(expected, actual);
        verify(subscriptionPlanDao, times(1)).findOne(subscriptionPlanId);
    }

    @Test
    void findSubscriptionPlanByIdWithNotExistingSubscriptionPlanTest() throws DaoException, ServiceException {
        Integer subscriptionPlanId = 1;
        when(subscriptionPlanDao.findOne(subscriptionPlanId)).thenReturn(Optional.empty());

        Optional<SubscriptionPeriod> periodicalOpt =
                subscriptionService.findSubscriptionPeriodById(subscriptionPlanId);

        assertFalse(periodicalOpt.isPresent());
        verify(subscriptionPlanDao, times(1)).findOne(subscriptionPlanId);
    }
}