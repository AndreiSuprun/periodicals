package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.PeriodicalCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Entity mapper for PeriodicalCategory entity.
 *
 * @author Andrei Suprun
 */
public class PeriodicalCategoryMapper implements EntityMapper<PeriodicalCategory> {
    private static final String ID_FIELD = "periodical_category_id";
    private static final String CATEGORY_NAME_FIELD = "periodical_category_name";
    private static final String CATEGORY_DESCRIPTION_FIELD = "periodical_category_description";

    @Override
    public PeriodicalCategory mapToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {
        return PeriodicalCategory.newBuilder()
                .setId(resultSet.getInt(
                        tablePrefix + ID_FIELD))
                .setName(resultSet.getString(
                        tablePrefix + CATEGORY_NAME_FIELD))
                .setDescription(resultSet.getString(
                        tablePrefix + CATEGORY_DESCRIPTION_FIELD))
                .build();
    }
}
