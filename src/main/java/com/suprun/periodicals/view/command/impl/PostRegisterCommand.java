package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.UserService;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.command.impl.admin.GetEditPeriodicalCommand;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.mapper.RequestMapperFactory;
import com.suprun.periodicals.view.util.validator.EntityValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Command for processing user registration bin.
 *
 * @author Andrei Suprun
 */
public class PostRegisterCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRegisterCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Start of new user registration");
        User userDTO = RequestMapperFactory.getRegisterMapper().mapToObject(request);
        Map<String, Boolean> errors = EntityValidatorFactory.getRegisterValidator().validate(userDTO);

        if (errors.isEmpty()) {
            boolean isRegistered;
            try {
                isRegistered = userService.registerUser(userDTO);
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            if (isRegistered) {
                LOGGER.debug("User was successfully registered");
                return CommandResult.redirect(PagesPath.SIGN_IN_PATH);
            } else {
                LOGGER.debug("Such user is already registered");
                errors.put(Attributes.ERROR_REGISTRATION, true);
            }
        } else {
            LOGGER.debug("Invalid registration parameters");
        }
        request.setAttribute(Attributes.ERRORS, errors);
        request.setAttribute(Attributes.USER_DTO, userDTO);
        LOGGER.debug("User registration fail");
        return CommandResult.forward(ViewsPath.REGISTER_VIEW);
    }
}
