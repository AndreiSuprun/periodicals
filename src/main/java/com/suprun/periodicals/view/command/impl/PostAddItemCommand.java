package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.*;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.command.impl.admin.GetEditPeriodicalCommand;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.ViewUtil;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Command for adding periodical to subscription bin.
 *
 * @author Andrei Suprun
 */
public class PostAddItemCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostAddItemCommand.class);
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();
    private final SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();
    private final SubscriptionBinService subscriptionBinService = ServiceFactory.getSubscriptionBinService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Attempt to add item to subscription bin");
        String referer = ViewUtil.getReferer(request);
        referer = ViewUtil.removeParameterFromURI(referer, RequestParameters.ERROR_ATTRIBUTE);

        User user = ViewUtil.getAuthorizedUser(request.getSession());
        Long periodicalId = Long.valueOf(request.getParameter(RequestParameters.PERIODICAL_ID));
        Integer subscriptionPeriodId = Integer.valueOf(request.getParameter(RequestParameters.SUBSCRIPTION_PERIOD_ID));
        Optional<Periodical> periodicalOpt;
        SubscriptionPeriod subscriptionPeriod;
        try{
        periodicalOpt = periodicalService.findPeriodicalById(periodicalId);
        subscriptionPeriod = subscriptionService.findSubscriptionPeriodById(subscriptionPeriodId)
                        .orElseThrow(IllegalArgumentException::new);
        } catch (ServiceException e){
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        if (!periodicalOpt.isPresent() || !periodicalOpt.get().getAvailability()) {
            referer = ViewUtil.addParameterToURI(referer, RequestParameters.ERROR_ATTRIBUTE,
                    Attributes.ERROR_PERIODICAL_INVALID);
            LOGGER.debug("Invalid periodical with id {} for subscription", periodicalId);
            return CommandResult.redirect(referer);
        }

        Periodical periodical = periodicalOpt.get();
        boolean isAlreadySubscribed;
        try {
            isAlreadySubscribed = subscriptionService.isAlreadySubscribed(user, periodical);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        if (isAlreadySubscribed) {
            referer = ViewUtil.addParameterToURI(referer, RequestParameters.ERROR_ATTRIBUTE,
                    Attributes.ERROR_IS_ALREADY_SUBSCRIBED);
            LOGGER.debug("User is already subscribed to periodical with id {}", periodicalId);
            return CommandResult.redirect(referer);
        }

        SubscriptionBin subscriptionBin = ViewUtil.getSubscriptionBin(request.getSession());
        boolean isAddedToCart = subscriptionBinService.addItemToBin(subscriptionBin, user, periodical, subscriptionPeriod);
        if (!isAddedToCart) {
            referer = ViewUtil.addParameterToURI(referer, RequestParameters.ERROR_ATTRIBUTE,
                    Attributes.ERROR_IS_ALREADY_IN_BIN);
            LOGGER.debug("Item already exists in bin");
        } else {
            LOGGER.debug("Item successfully added to bin");
        }
        return CommandResult.redirect(referer);
    }
}
