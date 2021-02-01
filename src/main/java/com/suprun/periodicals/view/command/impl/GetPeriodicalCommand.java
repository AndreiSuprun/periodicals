package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionService;
import com.suprun.periodicals.view.command.impl.admin.GetEditPeriodicalCommand;
import com.suprun.periodicals.view.exception.NotFoundException;
import com.suprun.periodicals.view.util.ViewUtil;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Command for receiving periodical description page.
 *
 * @author Andrei Suprun
 */
public class GetPeriodicalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPeriodicalCommand.class);
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();
    private final SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Attempt to get page for overview periodical");
        Long periodicalId = Long.valueOf(request.getParameter(RequestParameters.PERIODICAL_ID));
        Boolean isSubscribed = Boolean.parseBoolean(request.getParameter(RequestParameters.USER_IS_SUBSCRIBED));
        Optional<Periodical> periodicalOpt;
        try {
            periodicalOpt = periodicalService.findPeriodicalById(periodicalId);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        if (periodicalOpt.isPresent()) {
            List<SubscriptionPeriod> subscriptionPeriods;
            try {
                subscriptionPeriods = subscriptionService.findAllSubscriptionPeriods();
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            request.setAttribute(Attributes.SUBSCRIPTION_PERIODS, subscriptionPeriods);
            request.setAttribute(Attributes.PERIODICAL, periodicalOpt.get());
            request.setAttribute(Attributes.USER_IS_SUBSCRIBED, isSubscribed);
            ViewUtil.checkErrorParameter(request, RequestParameters.ERROR_ATTRIBUTE);
            LOGGER.debug("Attempt to get page for overview periodical is successful");
            return CommandResult.forward(ViewsPath.PERIODICAL_VIEW);
        }
        LOGGER.debug("Periodical with id {} doesn't exist", periodicalId);
        throw new NotFoundException();
    }
}
