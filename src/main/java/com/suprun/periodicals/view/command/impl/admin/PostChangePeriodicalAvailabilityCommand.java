package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.ViewUtil;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Command for processing change periodicals availability.
 *
 * @author Andrei Suprun
 */
public class PostChangePeriodicalAvailabilityCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostChangePeriodicalAvailabilityCommand.class);
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Start the process of changing status of the periodical");
        String referer = ViewUtil.getReferer(request, PagesPath.ADMIN_CATALOG_PATH);
        Long periodicalId = Long.valueOf(request.getParameter(RequestParameters.PERIODICAL_ID));
        boolean newPeriodicalStatus = Boolean.parseBoolean(request.getParameter(RequestParameters.PERIODICAL_AVAILABILITY));
        Optional<Periodical> periodicalOpt;
        try {
            periodicalOpt = periodicalService.findPeriodicalById(periodicalId);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        if (periodicalOpt.isPresent()) {
            try {
                periodicalService.changeStatus(periodicalOpt.get(), newPeriodicalStatus);
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            LOGGER.debug("Status of the periodical was successfully changed");
        } else {
            LOGGER.debug("Periodical with id {} doesn't exist. Changing status of the periodical failed", periodicalId);
        }
        return CommandResult.redirect(referer);
    }
}
