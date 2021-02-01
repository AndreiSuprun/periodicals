package com.suprun.periodicals.view.util.mapper;

import com.suprun.periodicals.entity.Publisher;
import com.suprun.periodicals.view.constants.RequestParameters;
import javax.servlet.http.HttpServletRequest;

/**
 * Class for mapping request to publisher entity.
 *
 * @author Andrei Suprun
 */
public class AddPublisherRequestMapper implements RequestEntityMapper<Publisher> {

    @Override
    public Publisher mapToObject(HttpServletRequest request) {
        String publisherName = request.getParameter(RequestParameters.PUBLISHER_NAME);
        Publisher publisher = new Publisher();
        publisher.setName(publisherName);
        return publisher;
    }
}
