package com.suprun.periodicals.view.constants;

import com.suprun.periodicals.util.Resource;

/**
 * Constants which relates to parameters in HttpServletRequest
 */
public final class RequestParameters {

    public static final String USER_FIRST_NAME = Resource.PARAMETER.getProperty("user.first.name");
    public static final String USER_LAST_NAME = Resource.PARAMETER.getProperty("user.last.name");
    public static final String USER_EMAIL = Resource.PARAMETER.getProperty("user.email");
    public static final String USER_PASSWORD = Resource.PARAMETER.getProperty("user.password");
    public static final String USER_IS_SUBSCRIBED = Resource.PARAMETER.getProperty("user.subscribed");
    public static final String REFERER = Resource.PARAMETER.getProperty("referer");
    public static final String PERIODICAL_NAME = Resource.PARAMETER.getProperty("periodical.name");
    public static final String PERIODICAL_AVAILABILITY = Resource.PARAMETER.getProperty("periodical.availability");
    public static final String PERIODICAL_DESCRIPTION = Resource.PARAMETER.getProperty("periodical.description");
    public static final String PERIODICAL_PICTURE = Resource.PARAMETER.getProperty("periodical.picture");
    public static final String PERIODICAL_PRICE = Resource.PARAMETER.getProperty("periodical.price");
    public static final String PERIODICAL_CATEGORY_ID = Resource.PARAMETER.getProperty("periodical.category.id");
    public static final String PERIODICAL_FREQUENCY_ID = Resource.PARAMETER.getProperty("periodical.frequency.id");
    public static final String PERIODICAL_PUBLISHER_ID = Resource.PARAMETER.getProperty("periodical.publisher.id");
    public static final String FULL_TEXT_SEARCH = Resource.PARAMETER.getProperty("search.text");

    public static final String PUBLISHER_NAME = Resource.PARAMETER.getProperty("publisher.name");

    public static final String PERIODICAL_ID = Resource.PARAMETER.getProperty("periodical.id");
    public static final String SUBSCRIPTION_PERIOD_ID = Resource.PARAMETER.getProperty("subscription.period.id");
    public static final String BIN_ITEM_ID = Resource.PARAMETER.getProperty("bin.item.id");
    public static final String PAYMENT_ID = Resource.PARAMETER.getProperty("payment.id");
    public static final String USER_ID = Resource.PARAMETER.getProperty("user.id");

    public static final String PAGINATION_PAGE = Resource.PARAMETER.getProperty("pagination.page");
    public static final String PAGINATION_PILL = Resource.PARAMETER.getProperty("pagination.pill");
    public static final String PAGINATION_ACTIVE_SUBSCRIPTIONS_PAGE = Resource.PARAMETER.getProperty("pagination.active.subscriptions.page");
    public static final String PAGINATION_EXPIRED_SUBSCRIPTIONS_PAGE = Resource.PARAMETER.getProperty("pagination.expired.subscriptions.page");

    public static final String ERROR_ATTRIBUTE = Resource.PARAMETER.getProperty("error.attribute");

    private RequestParameters() {
    }
}
