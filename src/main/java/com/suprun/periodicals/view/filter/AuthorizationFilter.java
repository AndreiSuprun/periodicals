package com.suprun.periodicals.view.filter;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.util.Resource;
import com.suprun.periodicals.view.constants.PagesPath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The filter restricts access for non-authorized users.
 *
 * @author Andrei Suprun
 */
public class AuthorizationFilter implements Filter {

    private static final Set<String> secureAdminPaths = new HashSet<>();
    private static final Set<String> secureUserPaths = new HashSet<>();
    private static final String USER = "user";
    private static final String ADMIN = "admin";

    @Override
    public void init(FilterConfig filterConfig) {
        secureAdminPaths.add(PagesPath.ADMIN_CATALOG_PATH);
        secureAdminPaths.add(PagesPath.CREATE_PERIODICAL_PATH);
        secureAdminPaths.add(PagesPath.EDIT_PERIODICAL_PATH);
        secureAdminPaths.add(PagesPath.CHANGE_STATUS_PERIODICAL_PATH);
        secureAdminPaths.add(PagesPath.PAYMENTS_PATH);
        secureAdminPaths.add(PagesPath.PAYMENT_OVERVIEW_PATH);
        secureAdminPaths.add(PagesPath.USER_PATH);
        secureAdminPaths.add(PagesPath.ADD_PUBLISHER_PATH);
        secureUserPaths.add(PagesPath.BIN_PATH);
        secureUserPaths.add(PagesPath.BIN_ADD_ITEM_PATH);
        secureUserPaths.add(PagesPath.BIN_REMOVE_ITEM_PATH);
        secureUserPaths.add(PagesPath.BIN_REMOVE_ALL_ITEM_PATH);
        secureUserPaths.add(PagesPath.BIN_SUBSCRIPTION_PAYMENT_PATH);
        secureUserPaths.add(PagesPath.SUBSCRIPTIONS_PATH);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute(Resource.ATTRIBUTE.getProperty("user")) : null;
        boolean isUser = false;
        boolean isAdmin = false;
        if (user != null){
            isUser = USER.equalsIgnoreCase(user.getRole().getName());
            isAdmin = ADMIN.equalsIgnoreCase(user.getRole().getName());
        }
        boolean isOnlyAdminRequest = secureAdminPaths.contains(req.getPathInfo());
        boolean isOnlyUserRequest = secureUserPaths.contains(req.getPathInfo());
        if (isOnlyAdminRequest) {
            doFilter(isAdmin, req, resp, chain);
        } else if (isOnlyUserRequest) {
            doFilter(isUser, req, resp, chain);
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }

    private void doFilter(boolean isAuthorized, HttpServletRequest request, HttpServletResponse response,
                          FilterChain chain) throws IOException, ServletException {
        if (isAuthorized) {
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
