package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.PublisherDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Publisher;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlPublisherDao implements PublisherDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("publisher.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("publisher.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("publisher.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("publisher.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("publisher.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("publisher.where.id");
    private final static String WHERE_NAME =
            Resource.QUERIES.getProperty("publisher.where.name");

    private final SqlBasicDao<Publisher> sqlBasicDao;

    public SqlPublisherDao() {
        this(MapperFactory.getPublisherMapper());
    }

    public SqlPublisherDao(EntityMapper<Publisher> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public SqlPublisherDao(SqlBasicDao<Publisher> sqlBasicDao) {
        this.sqlBasicDao = sqlBasicDao;
    }

    @Override
    public Optional<Publisher> findOne(Long id) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public Optional<Publisher> findOneByName(String name) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_NAME, name);
    }

    @Override
    public List<Publisher> findAll() throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL);
    }

    public List<Publisher> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlBasicDao.findAll(SELECT_ALL + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public Publisher insert(Publisher obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to insert nullable publisher");
        }

        Long id = sqlBasicDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getName());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Publisher obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to update nullable publisher");
        }

        sqlBasicDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getName(),
                obj.getId());
    }

    @Override
    public void delete(Long id) throws DaoException {
        sqlBasicDao.executeUpdate(
                DELETE + WHERE_ID,
                id);
    }

    @Override
    public long getCount() throws DaoException {
        return sqlBasicDao.getRowsCount(COUNT);
    }

    @Override
    public boolean existByName(String name) throws DaoException {
        return findOneByName(name).isPresent();
    }
}