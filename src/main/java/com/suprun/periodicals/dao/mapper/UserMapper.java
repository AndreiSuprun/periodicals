package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.Role;
import com.suprun.periodicals.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Entity mapper for User entity.
 *
 * @author Andrei Suprun
 */
public class UserMapper implements EntityMapper<User> {
    private static final String ID_FIELD = "user_id";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";

    private final EntityMapper<Role> roleMapper;

    public UserMapper() {
        this(new RoleMapper());
    }

    public UserMapper(EntityMapper<Role> roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public User mapToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {
        Role tempRole = roleMapper.mapToObject(resultSet);
        return User.newBuilder()
                .setId(resultSet.getLong(
                        tablePrefix + ID_FIELD))
                .setRole(tempRole)
                .setFirstName(resultSet.getString(
                        tablePrefix + FIRST_NAME_FIELD))
                .setLastName(resultSet.getString(
                        tablePrefix + LAST_NAME_FIELD))
                .setEmail(resultSet.getString(
                        tablePrefix + EMAIL_FIELD))
                .setPassword(resultSet.getString(
                        tablePrefix + PASSWORD_FIELD))
                .build();
    }
}
