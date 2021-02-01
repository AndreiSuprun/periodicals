package com.suprun.periodicals.view.constants;

import com.suprun.periodicals.util.Resource;

/**
 * Path to jsp
 */
public final class ViewsPath {
    public static final String DIRECTORY = Resource.VIEW.getProperty("view.folder");
    public static final String HOME_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.home");
    public static final String SIGN_IN_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.signin");
    public static final String REGISTER_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.register");
    public static final String PROFILE_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.profile");
    public static final String PERIODICAL_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.periodical");
    public static final String CATALOG_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.catalog");
    public static final String SEARCH_RESULT_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.search.result");
    public static final String BIN_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.bin");
    public static final String SUBSCRIPTIONS_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.subscriptions");
    public static final String ADMIN_CATALOG_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.admin.catalog");
    public static final String CREATE_PERIODICAL_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.create.periodical");
    public static final String EDIT_PERIODICAL_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.edit.periodical");
    public static final String ADD_PUBLISHER_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.add.publisher");
    public static final String PAYMENTS_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.payments");
    public static final String PAYMENT_OVERVIEW_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.payment");
    public static final String USER_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.admin.user");
    public static final String ERROR_400_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.error.400");
    public static final String ERROR_403_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.error.403");
    public static final String ERROR_404_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.error.404");
    public static final String ERROR_GLOBAL_VIEW = DIRECTORY + Resource.VIEW.getProperty("view.error.global");

    private ViewsPath() {
    }
}
