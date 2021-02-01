package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.PeriodicalDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlPeriodicalDao implements PeriodicalDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("periodical.select.all");
    private final static String FULL_TEXT =
            Resource.QUERIES.getProperty("periodical.fulltext");
    private final static String INSERT =
            Resource.QUERIES.getProperty("periodical.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("periodical.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("periodical.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("periodical.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("periodical.where.id");
    private final static String WHERE_STATUS =
            Resource.QUERIES.getProperty("periodical.where.status");
    private final static String ORDER_BY_STATUS_AND_ID =
            Resource.QUERIES.getProperty("periodical.select.order");

    private final SqlBasicDao<Periodical> sqlBasicDao;

    public SqlPeriodicalDao() {
        this(MapperFactory.getPeriodicalMapper());
    }

    public SqlPeriodicalDao(EntityMapper<Periodical> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public SqlPeriodicalDao(SqlBasicDao<Periodical> utilMySqlDao) {
        this.sqlBasicDao = utilMySqlDao;
    }

    @Override
    public Optional<Periodical> findOne(Long id) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Periodical> findAll() throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL + ORDER_BY_STATUS_AND_ID);
    }

    @Override
    public List<Periodical> findAll(long skip, long limit) throws DaoException {

        return sqlBasicDao.findAll(SELECT_ALL + ORDER_BY_STATUS_AND_ID + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public List<Periodical> fullTextSearch(String text, long skip, long limit) throws DaoException {

        return sqlBasicDao.findAll(SELECT_ALL + FULL_TEXT + SqlBasicDao.LIMIT, text, skip, limit);
    }

    @Override
    public List<Periodical> findByStatus(boolean status, long skip, long limit) throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL + WHERE_STATUS + ORDER_BY_STATUS_AND_ID + SqlBasicDao.LIMIT,
                status, skip, limit);
    }

    @Override
    public Periodical insert(Periodical periodical) throws DaoException {

        Long id = sqlBasicDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                periodical.getPublisher().getId(),
                periodical.getFrequency().getId(),
                periodical.getCategory().getId(),
                periodical.getName(),
                periodical.getPrice(),
                periodical.getDescription(),
                periodical.getPicture());
        periodical.setId(id);

        return periodical;
    }

    @Override
    public void update(Periodical periodical) throws DaoException {

        sqlBasicDao.executeUpdate(
                UPDATE + WHERE_ID,
                periodical.getPublisher().getId(),
                periodical.getFrequency().getId(),
                periodical.getCategory().getId(),
                periodical.getName(),
                periodical.getAvailability(),
                periodical.getPrice(),
                periodical.getDescription(),
                periodical.getPicture(),
                periodical.getId());
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
    public long getCountByTag(String text) throws DaoException {
        return sqlBasicDao.getRowsCount(COUNT + FULL_TEXT, text);
    }

    @Override
    public long getCountByStatus(boolean periodicalStatus) throws DaoException {
        return sqlBasicDao.getRowsCount(COUNT + WHERE_STATUS, periodicalStatus);
    }
}
