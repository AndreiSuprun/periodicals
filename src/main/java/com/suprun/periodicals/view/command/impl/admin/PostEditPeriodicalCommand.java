package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.PictureUploader;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.exception.BadRequestException;
import com.suprun.periodicals.view.util.mapper.RequestMapperFactory;
import com.suprun.periodicals.view.util.validator.EntityValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

/**
 * Command for processing periodical editing.
 *
 * @author Andrei Suprun
 */
public class PostEditPeriodicalCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostEditPeriodicalCommand.class);
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Start editing of periodical");
        Periodical periodicalDTO = RequestMapperFactory.getEditPeriodicalMapper().mapToObject(request);

        Map<String, Boolean> errors = EntityValidatorFactory.getPeriodicalValidator().validate(periodicalDTO);

        if (errors.isEmpty()) {
            Optional<Periodical> periodicalOpt;
            try {
                periodicalOpt = periodicalService.findPeriodicalById(periodicalDTO.getId());
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            if (!periodicalOpt.isPresent() || !periodicalOpt.get().getAvailability()) {
                LOGGER.debug("Periodical with id {} doesn't exist or has suspend status",
                        periodicalDTO.getId());
                throw new BadRequestException();
            }
            String oldPicturePath = periodicalOpt.get().getPicture();
            InputStream stream = null;
            String fileName = null;
            try {
                stream = PictureUploader.receiveInputStream(request);
                fileName = PictureUploader.receiveFileName(request);
            } catch (ServletException | IOException e) {
                LOGGER.error("Error occurred while picture processing");
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            if (fileName != null) {
                periodicalDTO.setPicture(fileName);
            } else {
                periodicalDTO.setPicture(oldPicturePath);
            }
            try {
                periodicalService.updatePeriodical(periodicalDTO, stream, oldPicturePath);
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            LOGGER.debug("Periodical was successfully edit");
            return CommandResult.redirect(PagesPath.ADMIN_CATALOG_PATH);
        }

        LOGGER.debug("Invalid editing parameters");
        request.setAttribute(Attributes.PERIODICAL_DTO, periodicalDTO);
        request.setAttribute(Attributes.ERRORS, errors);
        try {
            request.setAttribute(Attributes.PERIODICAL_CATEGORIES, periodicalService.findAllPeriodicalCategory());
            request.setAttribute(Attributes.FREQUENCIES, periodicalService.findAllFrequencies());
            request.setAttribute(Attributes.PUBLISHERS, periodicalService.findAllPublishers());
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        LOGGER.debug("Periodical editing failed");
        return CommandResult.forward(ViewsPath.EDIT_PERIODICAL_VIEW);
    }
}
