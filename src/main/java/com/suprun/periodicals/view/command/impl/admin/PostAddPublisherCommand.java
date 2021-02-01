package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Publisher;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.PublisherService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.PictureUploader;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.ViewsPath;
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

/**
 * Command for processing publisher addition.
 *
 * @author Andrei Suprun
 */
public class PostAddPublisherCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostAddPublisherCommand.class);
    private final PublisherService publisherService = ServiceFactory.getPublisherService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Start of adding new publisher");
        Publisher publisherDTO = RequestMapperFactory.getAddPublisherMapper().mapToObject(request);

        Map<String, Boolean> errors = EntityValidatorFactory.getPublisherValidator().validate(publisherDTO);
        if (errors.isEmpty()) {
            boolean isAdded;
            try {
                isAdded = publisherService.addPublisher(publisherDTO);
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            if (isAdded) {
            LOGGER.debug("Publisher was successfully created");
            return CommandResult.redirect(PagesPath.ADMIN_CATALOG_PATH);
            } else {
            LOGGER.debug("Such publisher is already in database");
            errors.put(Attributes.ERROR_PUBLISHER_IN_DB, true);
        }
        }
        LOGGER.debug("Invalid creation parameters");
        request.setAttribute(Attributes.ERRORS, errors);
        request.setAttribute(Attributes.PUBLISHER_DTO, publisherDTO);
        LOGGER.debug("Publisher addition fail");
        return CommandResult.forward(ViewsPath.ADD_PUBLISHER_VIEW);
    }
}
