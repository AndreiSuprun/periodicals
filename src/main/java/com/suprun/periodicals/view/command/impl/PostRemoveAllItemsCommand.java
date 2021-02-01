package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionBinService;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.util.ViewUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for removing all periodicals from subscription bin.
 *
 * @author Andrei Suprun
 */
public class PostRemoveAllItemsCommand implements Command {

    private final SubscriptionBinService shoppingCartService = ServiceFactory.getSubscriptionBinService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        SubscriptionBin subscriptionBin = ViewUtil.getSubscriptionBin(request.getSession());
        shoppingCartService.removeAllItemFromBin(subscriptionBin);
        return CommandResult.redirect(PagesPath.BIN_PATH);
    }
}
