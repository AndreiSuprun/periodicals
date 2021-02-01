package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.connection.ConnectionPool;
import com.suprun.periodicals.dao.connection.ConnectionProxy;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.util.Resource;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class SqlBasicDao<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlBasicDao.class);

    static final String LIMIT_ONE = Resource.QUERIES.getProperty("limit.one");
    static final String LIMIT = Resource.QUERIES.getProperty("limit");

    private EntityMapper<T> mapper;

    public SqlBasicDao(EntityMapper<T> mapper) {
        this.mapper = mapper;
    }

    public Optional<T> findOne(String query, Object... params) throws DaoException {
        List<T> results = findAll(query + LIMIT_ONE, params);
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    public List<T> findAll(String query, Object... params) throws DaoException {
        try (Connection connection = ConnectionProxy.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(query)) {

            setParamsToStatement(statement, params);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.debug("Received {} periodicals", resultSet.getMetaData().getColumnCount());
            return mapper.mapToObjectList(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Failed to execute query", e);
            throw new DaoException(e);
        }
    }

    public void executeUpdate(String query, Object... params) throws DaoException {
        try (Connection connection = ConnectionProxy.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setParamsToStatement(statement, params);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to execute query", e);
            throw new DaoException(e);
        }
    }

    public <PK> PK executeInsertWithGeneratedPrimaryKey(String query,
                                                        Class<PK> pkType,
                                                        Object... params) throws DaoException {
        try (Connection connection = ConnectionProxy.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParamsToStatement(statement, params);
            statement.executeUpdate();
            return getGeneratedPrimaryKey(statement, pkType);
        } catch (SQLException e) {
            LOGGER.error("Failed to execute query", e);
            throw new DaoException(e);
        }
    }

    public Long executeInsertWithGeneratedPrimaryKey(String query,
                                                     Object... params) throws DaoException {
        return executeInsertWithGeneratedPrimaryKey(query, Long.class, params);
    }

    public long getRowsCount(String query, Object... params) throws DaoException {
        try (Connection connection = ConnectionProxy.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setParamsToStatement(statement, params);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("Can't retrieve count of objects");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to execute query", e);
            throw new DaoException(e);
        }
    }

    public void setParamsToStatement(PreparedStatement statement, Object... params)
            throws SQLException {
        if (params == null) {
            throw new SQLException("Params cannot be null");
        }
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                statement.setObject(i + 1, params[i]);
            } else {
                statement.setNull(i + 1, Types.OTHER);
            }
        }
    }

    private <PK> PK getGeneratedPrimaryKey(PreparedStatement statement, Class<PK> primaryKeyType)
            throws SQLException {
        if (primaryKeyType == null) {
            throw new SQLException("Primary key type is null");
        }
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            if (primaryKeyType.isAssignableFrom(Integer.class)) {
                Integer key = rs.getInt(1);
                return primaryKeyType.cast(key);
            } else if (primaryKeyType.isAssignableFrom(Long.class)) {
                Long key = rs.getLong(1);
                return primaryKeyType.cast(key);
            } else {
                throw new SQLException("Unsupported key type");
            }
        } else {
            throw new SQLException("Can't retrieve generated key");
        }
    }
}
