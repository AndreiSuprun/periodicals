package com.suprun.periodicals.service;
/**
 * Factory that returns Service entities
 *
 * @author Andrei Suprun
 */
public class ServiceFactory {

    public static UserService getUserService() {
        return UserService.getInstance();
    }

    public static PeriodicalService getPeriodicalService() {
        return PeriodicalService.getInstance();
    }

    public static PublisherService getPublisherService() {
        return PublisherService.getInstance();
    }

    public static PaymentService getPaymentService() {
        return PaymentService.getInstance();
    }

    public static SubscriptionService getSubscriptionService() {
        return SubscriptionService.getInstance();
    }

    public static SubscriptionBinService getSubscriptionBinService() {
        return SubscriptionBinService.getInstance();
    }
}
