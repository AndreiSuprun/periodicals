package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionService;
import com.suprun.periodicals.util.Resource;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.PageManager;
import com.suprun.periodicals.view.util.ViewUtil;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for receiving periodicals catalog page.
 *
 * @author Andrei Suprun
 */
public class ViewCatalogCommand implements Command {
    private final static long RECORDS_PER_PAGE = 5;
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();
    private final SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        PageManager pageManager = new PageManager(RECORDS_PER_PAGE);
        List<Periodical> periodicals;
        try {
            long rowsCount = periodicalService.getPeriodicalsCountByStatus(true);
            long skip = pageManager.manage(request, rowsCount);
            periodicals =
                    periodicalService.findAllPeriodicalsByStatus(true, skip, pageManager.getRecordsPerPage());
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        request.setAttribute(Attributes.CATALOG, periodicals);
        if (!periodicals.isEmpty()) {
            List<SubscriptionPeriod> subscriptionPeriods;
            try {
                subscriptionPeriods = subscriptionService.findAllSubscriptionPeriods();
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            request.setAttribute(Attributes.SUBSCRIPTION_PERIODS, subscriptionPeriods);
        }
        ViewUtil.checkErrorParameter(request, RequestParameters.ERROR_ATTRIBUTE);
        return CommandResult.forward(ViewsPath.CATALOG_VIEW);
    }
}
