package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.command.impl.admin.GetEditPeriodicalCommand;
import com.suprun.periodicals.view.constants.ViewsPath;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for forwarding to error page.
 *
 * @author Andrei Suprun
 */
public class ErrorCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        LOGGER.error("Something go wrong!", exception);
        return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
    }
}
