package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.ViewUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for receiving default page.
 *
 * @author Andrei Suprun
 */
public class HomeCommand implements Command {

    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<Periodical> periodicals;
        try {
            long rowsCount = periodicalService.getPeriodicalsCountByStatus(true);
            periodicals =
                    periodicalService.findAllPeriodicalsByStatus(true, 0, rowsCount);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        request.setAttribute(Attributes.CATALOG, periodicals);
        ViewUtil.checkErrorParameter(request, RequestParameters.ERROR_ATTRIBUTE);
        return CommandResult.forward(ViewsPath.HOME_VIEW);
    }
}
