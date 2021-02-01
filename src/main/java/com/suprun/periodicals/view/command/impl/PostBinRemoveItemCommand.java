package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionBinService;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.util.ViewUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for removing periodical from subscription bin.
 *
 * @author Andrei Suprun
 */
public class PostBinRemoveItemCommand implements Command {
    private final SubscriptionBinService subscriptionBinService = ServiceFactory.getSubscriptionBinService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Long cartItemId = Long.valueOf(request.getParameter(RequestParameters.BIN_ITEM_ID));

        SubscriptionBin subscriptionBin = ViewUtil.getSubscriptionBin(request.getSession());
        subscriptionBinService.removeItemFromBin(subscriptionBin, cartItemId);

        return CommandResult.redirect(PagesPath.BIN_PATH);
    }
}
