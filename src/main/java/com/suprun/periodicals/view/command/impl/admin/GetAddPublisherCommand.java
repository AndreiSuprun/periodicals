package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.PublisherService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.ViewsPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for receiving page for adding publisher.
 *
 * @author Andrei Suprun
 */
public class GetAddPublisherCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        return CommandResult.forward(ViewsPath.ADD_PUBLISHER_VIEW);
    }
}
