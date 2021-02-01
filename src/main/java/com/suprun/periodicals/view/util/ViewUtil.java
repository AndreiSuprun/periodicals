package com.suprun.periodicals.view.util;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import com.suprun.periodicals.util.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Util class for view part of application
 *
 * @author Andrei Suprun
 */
public class ViewUtil {

    private static final String EMPTY_SIGN = "";
    private static final String QUESTION_SIGN = "?";
    private static final String EQUALITY_SIGN = "=";
    private static final String AMPERSAND_SIGN = "&";
    private static final String AMPERSAND_REGEX = "[^&]+";
    private static final String DELIMITER_REGEX = "\\??&?";
    private static final String PARAM_VALUE_REGEX = "=((.+&)|[^&]+)";
    private static final String REFERER_HEADER = "referer";

    /**
     * Add next page to redirect
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param pageToRedirect page to redirect
     * @throws IOException IOException
     */
    public static void redirectTo(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String pageToRedirect) throws IOException {
        response.sendRedirect(
                request.getContextPath() + request.getServletPath() + pageToRedirect);
    }

    /**
     * Get authorized user
     *
     * @param session HttpSession
     * @return authorized user else {@code null}
     */
    public static User getAuthorizedUser(HttpSession session) {
        return (User) session.getAttribute(Resource.ATTRIBUTE.getProperty("user"));
    }

    /**
     * Get subscription bin for user
     *
     * @param session HttpSession
     * @return existing subscription bin or new one
     */
    public static SubscriptionBin getSubscriptionBin(HttpSession session) {
        SubscriptionBin subscriptionBin =
                (SubscriptionBin) session.getAttribute(Resource.ATTRIBUTE.getProperty("subscription.bin"));
        if (Objects.isNull(subscriptionBin)) {
            subscriptionBin = new SubscriptionBin();
            session.setAttribute(Resource.ATTRIBUTE.getProperty("subscription.bin"), subscriptionBin);
        }
        return subscriptionBin;
    }

    /**
     * @return referer path without servlet path at the beginning
     */
    public static String getReferer(HttpServletRequest request, String defaultPath) {
        String referer = defaultPath;
        String header = request.getHeader(REFERER_HEADER);
        if (header != null && !header.isEmpty()) {
            try {
                URI uri = new URI(header);
                String path = uri.getPath();
                String query = uri.getQuery();

                referer = Objects.isNull(query) ? path : path + QUESTION_SIGN + query;
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return referer.replaceFirst(request.getContextPath(), EMPTY_SIGN)
                .replaceFirst(request.getServletPath(), EMPTY_SIGN);
    }

    public static String getReferer(HttpServletRequest request) {
        return getReferer(request, Resource.PATH.getProperty("path.home"));
    }

    /**
     * Add parameter to exist URI
     */
    public static String addParameterToURI(String uri,
                                           String parameterName,
                                           String parameterValue) {
        Objects.requireNonNull(parameterName);
        Objects.requireNonNull(parameterValue);

        try {
            String newParameter = parameterName + EQUALITY_SIGN + parameterValue;
            URI oldUri = new URI(uri);
            String newQuery = oldUri.getQuery();

            if (Objects.isNull(newQuery)) {
                newQuery = newParameter;
            } else if (newQuery.contains(parameterName)) {
                newQuery = newQuery.replaceFirst(parameterName + EQUALITY_SIGN + AMPERSAND_REGEX, newParameter);
            } else {
                newQuery = newQuery + AMPERSAND_SIGN + newParameter;
            }

            URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                    oldUri.getPath(), newQuery, oldUri.getFragment());

            return newUri.getPath() + QUESTION_SIGN + newUri.getQuery();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Remove parameter from exist URI
     */
    public static String removeParameterFromURI(String uri, String parameterName) {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(parameterName);

        return uri.replaceFirst(DELIMITER_REGEX + parameterName + PARAM_VALUE_REGEX, EMPTY_SIGN);
    }

    /**
     * Checking the error parameter in the request.
     * If present, set it as a request attribute.
     */
    public static void checkErrorParameter(HttpServletRequest request,
                                           String requestAttribute) {
        String error = request.getParameter(requestAttribute);
        if (Objects.nonNull(error) && !error.isEmpty()) {
            request.setAttribute(error, true);
        }
    }
}
