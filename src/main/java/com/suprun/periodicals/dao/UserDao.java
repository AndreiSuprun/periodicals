package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.User;

import java.util.Optional;

/**
 * Dao interface for User entities.
 *
 * @author Andrei Suprun
 */
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Retrieve user from database identified by email.
     * @param email identifier of user
     * @return optional, which contains retrieved object or {@code null}
     */
    Optional<User> findOneByEmail(String email) throws DaoException;

    /**
     * Check if user exists in database.
     *
     * @param email user's identifier
     * @return {@code true} if exists else {@code false}
     */
    boolean existByEmail(String email) throws DaoException;
}
