package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.UserService;
import com.suprun.periodicals.util.Resource;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.command.impl.admin.GetEditPeriodicalCommand;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.ViewUtil;
import com.suprun.periodicals.view.util.mapper.RequestMapperFactory;
import com.suprun.periodicals.view.util.validator.EntityValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * Command for processing user sign in.
 *
 * @author Andrei Suprun
 */
public class PostSignInCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostSignInCommand.class);
    private UserService userService = ServiceFactory.getUserService();
    private static  final String ADMIN = "admin";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Start of sign in process");
        User userDTO = RequestMapperFactory.getSignInMapper().mapToObject(request);
        Map<String, Boolean> errors = EntityValidatorFactory.getSignInValidator().validate(userDTO);
        if (errors.isEmpty()) {
            LOGGER.debug("Try to sign in");
            Optional<User> userOpt;
            try {
                userOpt = userService.signIn(userDTO.getEmail(), userDTO.getPassword());
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                request.getSession().setAttribute(Attributes.USER, user);
                LOGGER.debug("User successfully signed in");
                if (user.getRole().getName().equalsIgnoreCase(ADMIN)) {
                    return CommandResult.redirect(PagesPath.ADMIN_CATALOG_PATH);
                } else {
                    return CommandResult.redirect(request.getParameter(RequestParameters.REFERER));
                }
            } else {
                LOGGER.debug("Email and password don't matches");
                errors.put(Attributes.ERROR_AUTHENTICATION, true);
            }
        } else {
            LOGGER.debug("Invalid authentication parameters");
        }
        request.setAttribute(Attributes.ERRORS, errors);
        request.setAttribute(Attributes.USER_DTO, userDTO);
        LOGGER.debug("User fail sign in");
        return CommandResult.forward(ViewsPath.SIGN_IN_VIEW);
    }
}
