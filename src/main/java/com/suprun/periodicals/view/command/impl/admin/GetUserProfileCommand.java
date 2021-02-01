package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.UserService;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Command for receiving user profile page.
 *
 * @author Andrei Suprun
 */
public class GetUserProfileCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserProfileCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Attempt to get a user profile page");
        Long userId = Long.valueOf(request.getParameter(RequestParameters.USER_ID));
        Optional<User> userOpt;
        try {
            userOpt = userService.findUserById(userId);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        if (userOpt.isPresent()) {
            request.setAttribute(Attributes.USER_DTO, userOpt.get());
            LOGGER.debug("Attempt to get a user profile page is successful");
            return CommandResult.forward(ViewsPath.USER_VIEW);
        }
        LOGGER.debug("User with id {} doesn't exist", userId);
        throw new NotFoundException();
    }
}
