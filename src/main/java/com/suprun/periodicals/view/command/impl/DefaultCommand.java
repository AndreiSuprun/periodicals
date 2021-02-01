package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.util.Resource;
import com.suprun.periodicals.view.FrontController;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.command.impl.admin.GetEditPeriodicalCommand;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for redirecting to default page.
 *
 * @author Andrei Suprun
 */
public class DefaultCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.warn("Wrong request path");
        return CommandResult.redirect(Resource.PATH.getProperty("path.home"));
    }
}
