package com.suprun.periodicals.view.constants;

import com.suprun.periodicals.util.Resource;

/**
 * Relative path of pages
 */
public final class PagesPath {
        public static final String SITE_PREFIX = Resource.PATH.getProperty("site.prefix");

        public static final String SIGN_IN_PATH = Resource.PATH.getProperty("path.signin");
        public static final String REGISTER_PATH = Resource.PATH.getProperty("path.register");

        public static final String HOME_PATH = Resource.PATH.getProperty("path.home");
        public static final String PERIODICAL_PATH = Resource.PATH.getProperty("path.periodical");
        public static final String CATALOG_PATH = Resource.PATH.getProperty("path.catalog");
        public static final String SEARCH_PATH = Resource.PATH.getProperty("path.fulltext.search");

        public static final String PROFILE_PATH = Resource.PATH.getProperty("path.profile");
        public static final String SIGN_OUT_PATH = Resource.PATH.getProperty("path.signout");

        public static final String BIN_PATH = Resource.PATH.getProperty("path.bin");
        public static final String BIN_ADD_ITEM_PATH = Resource.PATH.getProperty("path.bin.add.item");
        public static final String BIN_REMOVE_ITEM_PATH = Resource.PATH.getProperty("path.bin.remove.item");
        public static final String BIN_REMOVE_ALL_ITEM_PATH = Resource.PATH.getProperty("path.bin.remove.all.item");
        public static final String BIN_SUBSCRIPTION_PAYMENT_PATH = Resource.PATH.getProperty("path.bin.subscription.payment");
        public static final String SUBSCRIPTIONS_PATH = Resource.PATH.getProperty("path.user.subscriptions");

        public static final String ADMIN_CATALOG_PATH = Resource.PATH.getProperty("path.admin.catalog");
        public static final String CREATE_PERIODICAL_PATH = Resource.PATH.getProperty("path.admin.catalog.periodical.create");
        public static final String ADD_PUBLISHER_PATH = Resource.PATH.getProperty("path.admin.catalog.publisher.add");
        public static final String EDIT_PERIODICAL_PATH = Resource.PATH.getProperty("path.admin.catalog.periodical.edit");
        public static final String CHANGE_STATUS_PERIODICAL_PATH = Resource.PATH.getProperty("path.admin.catalog.change.status");
        public static final String PAYMENTS_PATH = Resource.PATH.getProperty("path.admin.payments");
        public static final String PAYMENT_OVERVIEW_PATH = Resource.PATH.getProperty("path.admin.payment");
        public static final String USER_PATH = Resource.PATH.getProperty("path.admin.user");

        public static final String ERROR_PATH = Resource.PATH.getProperty("path.error");

        private PagesPath() {
        }
}
