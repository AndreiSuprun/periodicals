package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.exception.BadRequestException;
import com.suprun.periodicals.view.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Command for receiving form for edit periodicals.
 *
 * @author Andrei Suprun
 */
public class GetEditPeriodicalCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetEditPeriodicalCommand.class);
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Attempt to get page for editing periodical");
        Long periodicalId = Long.valueOf(request.getParameter(RequestParameters.PERIODICAL_ID));
        Optional<Periodical> periodicalOpt;
        try {
            periodicalOpt = periodicalService.findPeriodicalById(periodicalId);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }

        if (periodicalOpt.isPresent()) {
            Periodical periodicalDTO = periodicalOpt.get();
            if (!periodicalDTO.getAvailability()) {
                LOGGER.debug("Can't edit periodical with suspended status");
                throw new BadRequestException();
            }
            request.setAttribute(Attributes.PERIODICAL_DTO, periodicalDTO);
            try {
                request.setAttribute(Attributes.PERIODICAL_CATEGORIES, periodicalService.findAllPeriodicalCategory());
                request.setAttribute(Attributes.FREQUENCIES, periodicalService.findAllFrequencies());
                request.setAttribute(Attributes.PUBLISHERS, periodicalService.findAllPublishers());
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            LOGGER.debug("Attempt to get page for editing periodical is successful");
            return CommandResult.forward(ViewsPath.EDIT_PERIODICAL_VIEW);
        } else {
            LOGGER.debug("Periodical with id {} doesn't exist", periodicalId);
            throw new NotFoundException();
        }
    }
}
