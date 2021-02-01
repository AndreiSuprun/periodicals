package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.ViewsPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for receiving user profile page.
 *
 * @author Andrei Suprun
 */
public class GetProfileCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return CommandResult.forward(ViewsPath.PROFILE_VIEW);
    }
}
