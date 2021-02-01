package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.ViewUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for receiving user sign in page.
 *
 * @author Andrei Suprun
 */
public class GetSignInCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(Attributes.REFERER, ViewUtil.getReferer(request));
        return CommandResult.forward(ViewsPath.SIGN_IN_VIEW);
    }
}
