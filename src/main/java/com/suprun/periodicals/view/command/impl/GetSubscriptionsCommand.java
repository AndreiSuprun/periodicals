package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionService;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.PageManager;
import com.suprun.periodicals.view.util.ViewUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for receiving user subscriptions page.
 *
 * @author Andrei Suprun
 */
public class GetSubscriptionsCommand implements Command {
    private final SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        PageManager activeSubscriptionsPagination = new PageManager(5,
                RequestParameters.PAGINATION_ACTIVE_SUBSCRIPTIONS_PAGE,
                Attributes.PAGINATION_ACTIVE_SUBSCRIPTIONS_PAGE,
                Attributes.PAGINATION_ACTIVE_SUBSCRIPTIONS_NUMBER_OF_PAGES);
        PageManager expiredSubscriptionsPagination = new PageManager(5,
                RequestParameters.PAGINATION_EXPIRED_SUBSCRIPTIONS_PAGE,
                Attributes.PAGINATION_EXPIRED_SUBSCRIPTIONS_PAGE,
                Attributes.PAGINATION_EXPIRED_SUBSCRIPTIONS_NUMBER_OF_PAGES);
        String pill = request.getParameter(RequestParameters.PAGINATION_PILL);
        if (pill != null) {
            request.setAttribute(pill, true);
        }

        User user = ViewUtil.getAuthorizedUser(request.getSession());
        List<Subscription> activeSubscriptions;
        List<Subscription> expiredSubscriptions;
        try {
            long activeRowsCount = subscriptionService.getSubscriptionsCountByUserAndStatus(user, false);
            long expiredRowsCount = subscriptionService.getSubscriptionsCountByUserAndStatus(user, true);
            long activeSkip = activeSubscriptionsPagination.manage(request, activeRowsCount);
            long expiredSkip = expiredSubscriptionsPagination.manage(request, expiredRowsCount);
            activeSubscriptions = subscriptionService.findAllSubscriptionsByUserAndStatus(user, false,
                    activeSkip, activeSubscriptionsPagination.getRecordsPerPage());
            expiredSubscriptions = subscriptionService.findAllSubscriptionsByUserAndStatus(user, true,
                    expiredSkip, expiredSubscriptionsPagination.getRecordsPerPage());
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        request.setAttribute(Attributes.ACTIVE_SUBSCRIPTIONS, activeSubscriptions);
        request.setAttribute(Attributes.EXPIRED_SUBSCRIPTIONS, expiredSubscriptions);
        return CommandResult.forward(ViewsPath.SUBSCRIPTIONS_VIEW);
    }
}
