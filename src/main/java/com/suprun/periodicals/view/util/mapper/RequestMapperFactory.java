package com.suprun.periodicals.view.util.mapper;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Publisher;
import com.suprun.periodicals.entity.User;

/**
 * Factory class for creating entity mapper.
 *
 * @author Andrei Suprun
 */
public class RequestMapperFactory {

    private static final RequestEntityMapper<User> SIGN_IN_MAPPER = new SignInRequestMapper();
    private static final RequestEntityMapper<User> REGISTER_MAPPER = new RegisterRequestMapper();
    private static final RequestEntityMapper<Periodical> CREATE_PERIODICAL_MAPPER = new CreatePeriodicalRequestMapper();
    private static final RequestEntityMapper<Periodical> EDIT_PERIODICAL_MAPPER = new EditPeriodicalRequestMapper();
    private static final RequestEntityMapper<Publisher> ADD_PUBLISHER_MAPPER = new AddPublisherRequestMapper();

    public static RequestEntityMapper<User> getSignInMapper() {
        return SIGN_IN_MAPPER;
    }
    public static RequestEntityMapper<User> getRegisterMapper() {
        return REGISTER_MAPPER;
    }
    public static RequestEntityMapper<Periodical> getCreatePeriodicalMapper() {
        return CREATE_PERIODICAL_MAPPER;
    }
    public static RequestEntityMapper<Periodical> getEditPeriodicalMapper() {
        return EDIT_PERIODICAL_MAPPER;
    }
    public static RequestEntityMapper<Publisher> getAddPublisherMapper() {
        return ADD_PUBLISHER_MAPPER;
    }
}
