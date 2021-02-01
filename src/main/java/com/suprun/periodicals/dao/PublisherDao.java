package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.Publisher;

import java.util.Optional;

/**
 * Dao interface for Publisher entities.
 *
 * @author Andrei Suprun
 */
public interface PublisherDao extends GenericDao<Publisher, Long> {

    Optional<Publisher> findOneByName(String name) throws DaoException;

    boolean existByName(String name) throws DaoException;
}
