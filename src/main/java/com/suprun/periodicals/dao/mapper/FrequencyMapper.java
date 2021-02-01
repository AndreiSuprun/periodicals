package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.Frequency;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Entity mapper for Frequency entity.
 *
 * @author Andrei Suprun
 */
public class FrequencyMapper implements EntityMapper<Frequency> {

    private static final String ID_FIELD = "frequency_id";
    private static final String FREQUENCY_NAME_FIELD = "frequency_name";
    private static final String FREQUENCY_VALUE_FIELD = "frequency_value";
    private static final String FREQUENCY_DESCRIPTION_FIELD = "frequency_description";

    @Override
    public Frequency mapToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {
        return Frequency.newBuilder()
                .setId(resultSet.getInt(
                        tablePrefix + ID_FIELD))
                .setName(resultSet.getString(
                        tablePrefix + FREQUENCY_NAME_FIELD))
                .setValue(resultSet.getInt(
                        tablePrefix + FREQUENCY_VALUE_FIELD))
                .setDescription(resultSet.getString(
                        tablePrefix + FREQUENCY_DESCRIPTION_FIELD))
                .build();
    }
}
