package com.suprun.periodicals.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Common interface for all mappers.
 *
 * @param <T> type of domain object
 * @author Andrei Suprun
 */
public interface EntityMapper<T> {

    String EMPTY_STRING = "";

    /**
     * Read data from a result set and convert it to list of objects.
     *
     * @param resultSet result set from the database
     * @return list of converted objects
     */
    default List<T> mapToObjectList(ResultSet resultSet) throws SQLException {
        return mapToObjectList(resultSet, EMPTY_STRING);
    }

    /**
     * Read data from a result set and convert it to list of objects.
     *
     * @param resultSet   result set from the database
     * @param tablePrefix prefix of the table in result set
     * @return list of converted objects
     * @throws SQLException if the columns is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    default List<T> mapToObjectList(ResultSet resultSet, String tablePrefix) throws SQLException {
        List<T> convertedObjects = new ArrayList<>();
        while (resultSet.next()) {
            convertedObjects.add(mapToObject(resultSet, tablePrefix));
        }
        return convertedObjects;
    }

    /**
     * Read data from a result set and convert it to certain object.
     *
     * @param resultSet result set from the database
     * @return converted object
     * @throws SQLException if the columns is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    default T mapToObject(ResultSet resultSet) throws SQLException {
        return mapToObject(resultSet, EMPTY_STRING);
    }

    T mapToObject(ResultSet resultSet, String tablePrefix) throws SQLException;
}
