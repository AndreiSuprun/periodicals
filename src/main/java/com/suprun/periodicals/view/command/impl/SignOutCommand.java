package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.PagesPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for processing user sign out.
 *
 * @author Andrei Suprun
 */
public class SignOutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return CommandResult.redirect(PagesPath.SIGN_IN_PATH);
    }
}
