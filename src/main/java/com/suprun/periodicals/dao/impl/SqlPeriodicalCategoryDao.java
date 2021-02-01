package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.PeriodicalCategoryDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.PeriodicalCategory;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Optional;

public class SqlPeriodicalCategoryDao implements PeriodicalCategoryDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("periodical.category.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("periodical.category.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("periodical.category.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("periodical.category.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("periodical.category.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("periodical.category.where.id");

    private final SqlBasicDao<PeriodicalCategory> sqlBasicDao;

    public  SqlPeriodicalCategoryDao() {
        this(MapperFactory.getPeriodicalCategoryMapper());
    }

    public  SqlPeriodicalCategoryDao(EntityMapper<PeriodicalCategory> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public  SqlPeriodicalCategoryDao(SqlBasicDao<PeriodicalCategory> sqlBasicDao) {
        this.sqlBasicDao = sqlBasicDao;
    }

    @Override
    public Optional<PeriodicalCategory> findOne(Integer id) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<PeriodicalCategory> findAll() throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL);
    }

    public List<PeriodicalCategory> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlBasicDao.findAll(SELECT_ALL + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public PeriodicalCategory insert(PeriodicalCategory obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to insert nullable periodical type");
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
    public void update(PeriodicalCategory obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to update nullable periodical type");
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
