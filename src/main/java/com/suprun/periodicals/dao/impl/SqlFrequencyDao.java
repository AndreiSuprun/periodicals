package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.FrequencyDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Frequency;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlFrequencyDao implements FrequencyDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("frequency.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("frequency.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("frequency.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("frequency.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("frequency.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("frequency.where.id");

    private final SqlBasicDao<Frequency> sqlBasicDao;

    public SqlFrequencyDao() {
        this(MapperFactory.getFrequencyMapper());
    }

    public SqlFrequencyDao(EntityMapper<Frequency> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public SqlFrequencyDao(SqlBasicDao<Frequency> sqlBasicDao) {
        this.sqlBasicDao = sqlBasicDao;
    }

    @Override
    public Optional<Frequency> findOne(Integer id) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Frequency> findAll() throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL);
    }

    @Override
    public List<Frequency> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlBasicDao.findAll(SELECT_ALL + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public Frequency insert(Frequency obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to insert nullable frequency");
        }

        Integer id = sqlBasicDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                Integer.class,
                obj.getName(),
                obj.getDescription());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Frequency obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to update nullable frequency");
        }

        sqlBasicDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getName(),
                obj.getDescription(),
                obj.getId());
    }

    @Override
    public void delete(Integer id) throws DaoException {
        sqlBasicDao.executeUpdate(
                DELETE + WHERE_ID,
                id);
    }

    @Override
    public long getCount() throws DaoException {
        return sqlBasicDao.getRowsCount(COUNT);
    }
}
