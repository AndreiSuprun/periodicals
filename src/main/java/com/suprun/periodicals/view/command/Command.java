package com.suprun.periodicals.view.command;

import com.suprun.periodicals.view.FrontController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Specialized interface for command in front controller pattern.
 *
 * @author Andrei Suprun
 * @see FrontController
 */
public interface Command {

    /**
     * Process request of user.
     *
     * @param request  HttpServletRequest to be processed
     * @param response HttpServletRequest
     * @return object of {@code CommandResult} class which contains path to appropriate jsp page
     * @see CommandResult
     */
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
