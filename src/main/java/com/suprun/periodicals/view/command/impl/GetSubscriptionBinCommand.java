package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionBinService;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.ViewUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for receiving subscription bin page.
 *
 * @author Andrei Suprun
 */
public class GetSubscriptionBinCommand implements Command {

    SubscriptionBinService subscriptionBinService = ServiceFactory.getSubscriptionBinService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        SubscriptionBin subscriptionBin = ViewUtil.getSubscriptionBin(request.getSession());
        try {
            subscriptionBinService.updateSubscriptionBinItemsFromDatabase(subscriptionBin);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        return CommandResult.forward(ViewsPath.BIN_VIEW);
    }
}
