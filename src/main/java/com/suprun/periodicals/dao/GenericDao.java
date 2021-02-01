package com.suprun.periodicals.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Common interface for all DAOs.
 *
 * @param <T>  represents type of domain object
 * @param <ID> represents type of identifier
 */
public interface GenericDao<T, ID extends Serializable> {

    /**
     * Retrieves object from database identified by id.
     *
     * @param id identifier of domain object.
     * @return optional, which contains retrieved object or null
     */
    Optional<T> findOne(ID id) throws DaoException;

    /**
     * Retrieves all object data from database.
     *
     * @return List of objects which represent one row in database.
     */
    List<T> findAll() throws DaoException;

    /**
     * Retrieves all object data from database.
     *
     * @return List of objects which represent one row in database.
     * @param skip skip
     * @param limit limit
     */
    List<T> findAll(long skip, long limit) throws DaoException;

    /**
     * Insert object to a database.
     *
     * @param obj object to insert
     * @return inserted object
     */
    T insert(T obj) throws DaoException;

    /**
     * Update object's information in database.
     *
     * @param obj object to update
     */
    void update(T obj) throws DaoException;

    /**
     * Delete certain object, identified by id, from database.
     *
     * @param id identifier of the object.
     */
    void delete(ID id) throws DaoException;

    /**
     * Check if object exists in database.
     *
     * @param id identifier of the object.
     * @return {@code true} if exists else {@code false}
     */
    default boolean exist(ID id) throws DaoException {
        return findOne(id).isPresent();
    }

    /**
     * Retrieves count of objects from database.
     *
     * @return count of objects.
     */
    long getCount() throws DaoException;
}
