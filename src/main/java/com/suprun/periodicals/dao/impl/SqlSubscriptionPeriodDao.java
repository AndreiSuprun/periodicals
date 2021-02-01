package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.SubscriptionPeriodDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Optional;

public class SqlSubscriptionPeriodDao implements SubscriptionPeriodDao {

    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("subscription.period.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("subscription.period.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("subscription.period.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("subscription.period.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("subscription.period.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("subscription.period.where.id");

    private final SqlBasicDao<SubscriptionPeriod> sqlBasicDao;

    public SqlSubscriptionPeriodDao() {
        this(MapperFactory.getSubscriptionPeriodMapper());
    }

    public SqlSubscriptionPeriodDao(EntityMapper<SubscriptionPeriod> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public SqlSubscriptionPeriodDao(SqlBasicDao<SubscriptionPeriod> sqlBasicDao) {
        this.sqlBasicDao = sqlBasicDao;
    }

    @Override
    public Optional<SubscriptionPeriod> findOne(Integer id) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<SubscriptionPeriod> findAll() throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL);
    }

    public List<SubscriptionPeriod> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlBasicDao.findAll(SELECT_ALL + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public SubscriptionPeriod insert(SubscriptionPeriod obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to insert nullable subscription plan");
        }

        Integer id = sqlBasicDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                Integer.class,
                obj.getName(),
                obj.getMonthsAmount(),
                obj.getRate());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(SubscriptionPeriod obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to update nullable subscription plan");
        }

        sqlBasicDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getName(),
                obj.getMonthsAmount(),
                obj.getRate(),
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
