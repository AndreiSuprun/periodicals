package com.suprun.periodicals.view.command;

import com.suprun.periodicals.view.command.impl.*;
import com.suprun.periodicals.view.command.impl.admin.*;
import com.suprun.periodicals.view.util.RequestMethod;
import com.suprun.periodicals.view.constants.PagesPath;

import java.util.HashMap;
import java.util.Map;
/**
 * Class that is responsible for holding application endpoints and commands that
 * correspond to them
 *
 * @author Andrei Suprun
 */
public class CommandFactory {

    private final static String DELIMITER = ":";

    private final Command DEFAULT_COMMAND = new DefaultCommand();
    private final Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {
        init();
    }

    private void init() {
        commands.put(buildKey(PagesPath.HOME_PATH, RequestMethod.GET),
                new HomeCommand());
        commands.put(buildKey(PagesPath.SIGN_IN_PATH, RequestMethod.GET),
                new GetSignInCommand());
        commands.put(buildKey(PagesPath.SIGN_IN_PATH, RequestMethod.POST),
                new PostSignInCommand());
        commands.put(buildKey(PagesPath.REGISTER_PATH, RequestMethod.GET),
                new GetRegisterCommand());
        commands.put(buildKey(PagesPath.REGISTER_PATH, RequestMethod.POST),
                new PostRegisterCommand());
        commands.put(buildKey(PagesPath.SIGN_OUT_PATH, RequestMethod.GET),
                new SignOutCommand());
        commands.put(buildKey(PagesPath.PROFILE_PATH, RequestMethod.GET),
                new GetProfileCommand());
        commands.put(buildKey(PagesPath.PERIODICAL_PATH, RequestMethod.GET),
                new GetPeriodicalCommand());
        commands.put(buildKey(PagesPath.CATALOG_PATH, RequestMethod.GET),
                new ViewCatalogCommand());
        commands.put(buildKey(PagesPath.SEARCH_PATH, RequestMethod.GET),
                new ViewSearchResultCommand());
        commands.put(buildKey(PagesPath.BIN_PATH, RequestMethod.GET),
                new GetSubscriptionBinCommand());
        commands.put(buildKey(PagesPath.BIN_ADD_ITEM_PATH, RequestMethod.POST),
                new PostAddItemCommand());
        commands.put(buildKey(PagesPath.BIN_REMOVE_ITEM_PATH, RequestMethod.POST),
                new PostBinRemoveItemCommand());
        commands.put(buildKey(PagesPath.BIN_REMOVE_ALL_ITEM_PATH, RequestMethod.POST),
                new PostRemoveAllItemsCommand());
        commands.put(buildKey(PagesPath.BIN_SUBSCRIPTION_PAYMENT_PATH, RequestMethod.POST),
                new PostSubscriptionProcessCommand());
        commands.put(buildKey(PagesPath.SUBSCRIPTIONS_PATH, RequestMethod.GET),
                new GetSubscriptionsCommand());
        commands.put(buildKey(PagesPath.ADMIN_CATALOG_PATH, RequestMethod.GET),
                new GetAdminCatalogCommand());
        commands.put(buildKey(PagesPath.CREATE_PERIODICAL_PATH, RequestMethod.GET),
                new GetCreatePeriodicalCommand());
        commands.put(buildKey(PagesPath.CREATE_PERIODICAL_PATH, RequestMethod.POST),
                new PostCreatePeriodicalCommand());
        commands.put(buildKey(PagesPath.EDIT_PERIODICAL_PATH, RequestMethod.GET),
                new GetEditPeriodicalCommand());
        commands.put(buildKey(PagesPath.EDIT_PERIODICAL_PATH, RequestMethod.POST),
                new PostEditPeriodicalCommand());
        commands.put(buildKey(PagesPath.ADD_PUBLISHER_PATH, RequestMethod.GET),
                new GetAddPublisherCommand());
        commands.put(buildKey(PagesPath.ADD_PUBLISHER_PATH, RequestMethod.POST),
                new PostAddPublisherCommand());
        commands.put(buildKey(PagesPath.CHANGE_STATUS_PERIODICAL_PATH, RequestMethod.POST),
                new PostChangePeriodicalAvailabilityCommand());
        commands.put(buildKey(PagesPath.PAYMENTS_PATH, RequestMethod.GET),
                new GetAllPaymentsCommand());
        commands.put(buildKey(PagesPath.PAYMENT_OVERVIEW_PATH, RequestMethod.GET),
                new GetPaymentOverviewCommand());
        commands.put(buildKey(PagesPath.USER_PATH, RequestMethod.GET),
                new GetUserProfileCommand());
        commands.put(buildKey(PagesPath.ERROR_PATH, RequestMethod.POST),
                new ErrorCommand());
        commands.put(buildKey(PagesPath.ERROR_PATH, RequestMethod.GET),
                new ErrorCommand());
    }

    public Command getCommand(String path, RequestMethod method) {
        return commands.getOrDefault(buildKey(path, method), DEFAULT_COMMAND);
    }

    private String buildKey(String path, RequestMethod method) {
        return method.name() + DELIMITER + path;
    }

    public static class Singleton {
        private final static CommandFactory INSTANCE = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return Singleton.INSTANCE;
    }
}
