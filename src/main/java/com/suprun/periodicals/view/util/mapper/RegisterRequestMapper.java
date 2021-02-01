package com.suprun.periodicals.view.util.mapper;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.view.constants.RequestParameters;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for mapping register request to user entity.
 *
 * @author Andrei Suprun
 */
public class RegisterRequestMapper  implements RequestEntityMapper<User> {

    @Override
    public User mapToObject(HttpServletRequest request) {
        return User.newBuilder()
                .setEmail(request.getParameter(RequestParameters.USER_EMAIL))
                .setPassword(request.getParameter(RequestParameters.USER_PASSWORD))
                .setFirstName(request.getParameter(RequestParameters.USER_FIRST_NAME))
                .setLastName(request.getParameter(RequestParameters.USER_LAST_NAME))
                .build();
    }
}
