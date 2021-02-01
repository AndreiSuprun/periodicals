package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.PageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for receiving admin catalog page.
 *
 * @author Andrei Suprun
 */
public class GetAdminCatalogCommand implements Command {
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        PageManager paginationManager = new PageManager();
        List<Periodical> periodicals;
        try{
        long rowsCount = periodicalService.getPeriodicalsCount();
        long skip = paginationManager.manage(request, rowsCount);
        periodicals = periodicalService.findAllPeriodicals(skip, paginationManager.getRecordsPerPage());
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        request.setAttribute(Attributes.CATALOG, periodicals);
        return CommandResult.forward(ViewsPath.ADMIN_CATALOG_VIEW);
    }
}
