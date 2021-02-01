package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.Periodical;

import java.util.List;

/**
 * Dao interface for Periodical entities.
 *
 * @author Andrei Suprun
 **/
public interface PeriodicalDao extends GenericDao<Periodical, Long> {

    /**
     * Retrieves all periodicals associated with specified tag.
     *
     * @param text tag to search
     * @param skip   skip
     * @param limit  limit
     * @return list of retrieved periodicals
     */
    List<Periodical> fullTextSearch(String text, long skip, long limit) throws DaoException;

    /**
     * Retrieves all periodicals associated with certain periodical status.
     *
     * @param isAvailable status of periodical
     * @param skip   skip
     * @param limit  limit
     * @return list of retrieved periodicals
     */
    List<Periodical> findByStatus(boolean isAvailable, long skip, long limit) throws DaoException;

    /**
     * Retrieves count of objects by status from database.
     *
     * @param text status of periodical
     * @return count of objects containing tag.
     */
    long getCountByTag(String text) throws DaoException;

    /**
     * Retrieves count of objects from database.
     *
     * @param isAvailable status of periodical
     * @return count of objects with same status.
     */
    long getCountByStatus(boolean isAvailable) throws DaoException;
}
