package com.suprun.periodicals.view.util.mapper;

import javax.servlet.http.HttpServletRequest;

/**
 * Common interface for all request mappers.
 *
 * @param <T> type of domain object
 * @author Andrei Suprun
 */
public interface RequestEntityMapper<T> {

    /**
     * Read data from a result set and convert it to certain object.
     *
     * @param request request from client with object data in parameters
     * @return converted object
     * @throws NumberFormatException if the IDs of entities does not exist
     *                               or is not valid
     */
    T mapToObject(HttpServletRequest request);
}
