package com.suprun.periodicals.service;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.provider.EntityProvider;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.testng.Assert.*;

public class SubscriptionBinServiceTest {
    @InjectMocks
    private SubscriptionBinService subscriptionBinService = SubscriptionBinService.getInstance();
    @Mock
    private PeriodicalService periodicalService;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addItemToCartTest() {
        SubscriptionBin subscriptionBin = mock(SubscriptionBin.class);
        User user = User.newBuilder()
                .setId(1L)
                .build();
        Periodical periodical = Periodical.newBuilder()
                .setId(1L)
                .build();
        SubscriptionPeriod subscriptionPlan = EntityProvider.getOneMonthSubscriptionPeriod();

        subscriptionBinService.addItemToBin(subscriptionBin, user, periodical, subscriptionPlan);

        verify(subscriptionBin, times(1)).addItem(any(Subscription.class));
    }

    @Test
    void removeItemFromCartTest() {
        SubscriptionBin subscriptionBin = mock(SubscriptionBin.class);
        long cartItemId = 1L;

        subscriptionBinService.removeItemFromBin(subscriptionBin, cartItemId);

        verify(subscriptionBin, times(1)).removeItem(cartItemId);
    }

    @Test
    void removeAllItemFromCartTest() {
        SubscriptionBin subscriptionBin = mock(SubscriptionBin.class);

        subscriptionBinService.removeAllItemFromBin(subscriptionBin);

        verify(subscriptionBin, times(1)).removeAll();
    }

    @Test
    void updateShoppingCartItemsFromDatabaseTest() throws ServiceException {
        Long periodicalId = 1L;
        SubscriptionBin subscriptionBin = mock(SubscriptionBin.class);
        User user = User.newBuilder()
                .setId(1L)
                .build();
        Periodical periodical = Periodical.newBuilder()
                .setId(periodicalId)
                .build();
        SubscriptionPeriod subscriptionPeriod = EntityProvider.getOneMonthSubscriptionPeriod();
        Subscription item = Subscription.newBuilder()
                .setUser(user)
                .setPeriodical(periodical)
                .setSubscriptionPeriod(subscriptionPeriod)
                .build();
        List<Subscription> items = new ArrayList<Subscription>() {{
            add(item);
        }};
        when(subscriptionBin.getItems()).thenReturn(items);
        when(periodicalService.findPeriodicalById(periodicalId))
                .thenReturn(Optional.of(periodical));

        subscriptionBinService.updateSubscriptionBinItemsFromDatabase(subscriptionBin);

        verify(subscriptionBin, times(1)).updateItem(item);
    }

    @Test
    void updateShoppingCartItemsFromDatabaseWithExceptionTest() throws ServiceException {
        Long periodicalId = 1L;
        SubscriptionBin subscriptionBin = mock(SubscriptionBin.class);
        User user = User.newBuilder()
                .setId(1L)
                .build();
        Periodical periodical = Periodical.newBuilder()
                .setId(periodicalId)
                .build();
        SubscriptionPeriod subscriptionPeriod = EntityProvider.getOneMonthSubscriptionPeriod();
        Subscription item = Subscription.newBuilder()
                .setUser(user)
                .setPeriodical(periodical)
                .setSubscriptionPeriod(subscriptionPeriod)
                .build();
        List<Subscription> items = new ArrayList<>() {{
            add(item);
        }};
        when(subscriptionBin.getItems()).thenReturn(items);
        when(periodicalService.findPeriodicalById(periodicalId))
                .thenReturn(Optional.empty());

        ServiceException e = expectThrows(ServiceException.class, () -> subscriptionBinService.updateSubscriptionBinItemsFromDatabase(subscriptionBin));
        assertEquals("A subscription cannot refer to a non-existent periodical", e.getMessage());
        verify(subscriptionBin, never()).updateItem(item);
    }
}