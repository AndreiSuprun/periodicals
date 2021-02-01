package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.ViewsPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for receiving form for create periodicals.
 *
 * @author Andrei Suprun
 */
public class GetCreatePeriodicalCommand implements Command {

    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute(Attributes.PERIODICAL_CATEGORIES, periodicalService.findAllPeriodicalCategory());
            request.setAttribute(Attributes.FREQUENCIES, periodicalService.findAllFrequencies());
            request.setAttribute(Attributes.PUBLISHERS, periodicalService.findAllPublishers());
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        return CommandResult.forward(ViewsPath.CREATE_PERIODICAL_VIEW);
    }
}
