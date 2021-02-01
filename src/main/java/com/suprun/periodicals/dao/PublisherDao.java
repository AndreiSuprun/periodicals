package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.Publisher;

import java.util.Optional;

/**
 * Dao interface for Publisher entities.
 *
 * @author Andrei Suprun
 */
public interface PublisherDao extends GenericDao<Publisher, Long> {

    /**
     * Retrieves publisher from database identified by name.
     *
     * @param name name of publisher.
     * @return optional, which contains retrieved object or null
     */
    Optional<Publisher> findOneByName(String name) throws DaoException;

    /**
     * Check if publisher exists in database.
     *
     * @param name name of the publisher.
     * @return {@code true} if exists else {@code false}
     */
    boolean existByName(String name) throws DaoException;
}
