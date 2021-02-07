package com.suprun.periodicals.view;

import com.suprun.periodicals.dao.connection.ConnectionPool;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandFactory;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.util.RedirectType;
import com.suprun.periodicals.view.util.RequestMethod;
import com.suprun.periodicals.view.util.ViewUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Provide a centralized request handling mechanism to
 * handle all types of requests coming to the application.
 * <p>
 * Application main servlet responsible for:
 * 1. Obtaining commands from incoming request
 * 2. Executing commands
 * 3. Redirecting request further by parameters obtained from CommandResult object
 *
 * @author Andrei Suprun
 * @see CommandResult
 */
@MultipartConfig
public class FrontController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);

    private CommandFactory commandFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        commandFactory = CommandFactory.getInstance();
    }

    /**
     * Method for processing all GET requests
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.debug("GET: {}", request.getPathInfo());
        processRequest(request, response, RequestMethod.GET);
    }

    /**
     * Method for processing all POST requests
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.debug("POST: {}", request.getPathInfo());
        processRequest(request, response, RequestMethod.POST);
    }

    /**
     * Main dispatching method for all types of requests
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response, RequestMethod method)
            throws ServletException, IOException {
        Command command = commandFactory.getCommand(getPath(request), method);
        CommandResult commandResult = command.execute(request, response);
        LOGGER.debug("Path of response: {}", commandResult.getPagePath());
        if (commandResult.getRedirectType() == RedirectType.REDIRECT) {
            ViewUtil.redirectTo(request, response, commandResult.getPagePath());
        } else {
            request.getRequestDispatcher(commandResult.getPagePath()).forward(request, response);
        }
    }

    private String getPath(HttpServletRequest request) {
        return request.getPathInfo();
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.getInstance().closeAllConnections();
        } catch (SQLException e) {
            LOGGER.debug("Error occurred while getting JDBC connection pool instance");
        }
    }
}

