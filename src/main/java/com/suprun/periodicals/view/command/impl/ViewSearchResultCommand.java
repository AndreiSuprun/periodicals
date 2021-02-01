package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionService;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.command.impl.admin.GetEditPeriodicalCommand;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.PageManager;
import com.suprun.periodicals.view.util.ViewUtil;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for receiving search result page.
 *
 * @author Andrei Suprun
 */
public class ViewSearchResultCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewSearchResultCommand.class);
    private final static long RECORDS_PER_PAGE = 5;
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();
    private final SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Attempt search item by tag");

        PageManager pageManager = new PageManager(RECORDS_PER_PAGE);
        List<Periodical> periodicals;
        String text = request.getParameter(RequestParameters.FULL_TEXT_SEARCH);
        if (text == null || text.isEmpty()) {
            request.setAttribute(Attributes.NO_QUERY_TO_FIND, true);
            LOGGER.debug("No query was entered");
            return CommandResult.forward(ViewsPath.SEARCH_RESULT_VIEW);
        }
        try {
            long rowsCount = periodicalService.getPeriodicalsCountByTag(text);
            long skip = pageManager.manage(request, rowsCount);
            periodicals =
                    periodicalService.findAllPeriodicalsByTag(text, skip, RECORDS_PER_PAGE);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        if (periodicals.isEmpty()) {
            request.setAttribute(Attributes.NOTHING_FOUND, true);
            LOGGER.debug("Nothing was found by {} tag", text);
            return CommandResult.forward(ViewsPath.SEARCH_RESULT_VIEW);
        }
        List<SubscriptionPeriod> subscriptionPeriods;
        try {
            subscriptionPeriods = subscriptionService.findAllSubscriptionPeriods();
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        request.setAttribute(Attributes.SEARCH_QUERY, text);
        request.setAttribute(Attributes.SUBSCRIPTION_PERIODS, subscriptionPeriods);
        request.setAttribute(Attributes.SEARCH_RESULT, periodicals);
        ViewUtil.checkErrorParameter(request, RequestParameters.ERROR_ATTRIBUTE);
        return CommandResult.forward(ViewsPath.SEARCH_RESULT_VIEW);
    }
}
