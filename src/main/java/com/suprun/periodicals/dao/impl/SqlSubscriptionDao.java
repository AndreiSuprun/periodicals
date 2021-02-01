package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.SubscriptionDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlSubscriptionDao implements SubscriptionDao {

    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("subscription.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("subscription.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("subscription.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("subscription.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("subscription.count");
    private final static String IS_USER_ALREADY_SUBSCRIBED =
            Resource.QUERIES.getProperty("subscription.is.user.already.subscribed");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("subscription.where.id");
    private final static String WHERE_PAYMENT_ID =
            Resource.QUERIES.getProperty("subscription.where.payment");
    private final static String WHERE_ACTIVE_AND_USER_ID =
            Resource.QUERIES.getProperty("subscription.where.active.and.user");
    private final static String WHERE_EXPIRED_AND_USER_ID =
            Resource.QUERIES.getProperty("subscription.where.expired.and.user");
    private final static String ORDER_BY_END_DATE =
            Resource.QUERIES.getProperty("subscription.select.order");

    private final SqlBasicDao<Subscription> sqlBasicDao;

    public SqlSubscriptionDao() {
        this(MapperFactory.getSubscriptionMapper());
    }

    public  SqlSubscriptionDao(EntityMapper<Subscription> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public  SqlSubscriptionDao(SqlBasicDao<Subscription> sqlBasicDao) {
        this.sqlBasicDao = sqlBasicDao;
    }

    @Override
    public Optional<Subscription> findOne(Long id) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Subscription> findAll() throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL);
    }

    @Override
    public List<Subscription> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlBasicDao.findAll(SELECT_ALL + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public List<Subscription> findByUserAndStatus(User user, boolean isExpired, long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return isExpired
                ? sqlBasicDao.findAll(
                SELECT_ALL + WHERE_EXPIRED_AND_USER_ID + ORDER_BY_END_DATE + SqlBasicDao.LIMIT,
                user.getId(), skip, limit)
                : sqlBasicDao.findAll(
                SELECT_ALL + WHERE_ACTIVE_AND_USER_ID + ORDER_BY_END_DATE + SqlBasicDao.LIMIT,
                user.getId(), skip, limit);
    }

    @Override
    public List<Subscription> findByPayment(Payment payment) throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL + WHERE_PAYMENT_ID,
                payment.getId());
    }

    @Override
    public Subscription insert(Subscription obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to insert nullable subscription");
        }

        Long id = sqlBasicDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getPayment().getId(),
                obj.getUser().getId(),
                obj.getPeriodical().getId(),
                obj.getSubscriptionPeriod().getId(),
                obj.getStartDate(),
                obj.getEndDate());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Subscription obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to update nullable subscription");
        }

        sqlBasicDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getPayment().getId(),
                obj.getUser().getId(),
                obj.getPeriodical().getId(),
                obj.getSubscriptionPeriod().getId(),
                obj.getStartDate(),
                obj.getEndDate(),
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
    public long getCountByUserAndStatus(User user, boolean isExpired) throws DaoException {
        return isExpired
                ? sqlBasicDao.getRowsCount(COUNT + WHERE_EXPIRED_AND_USER_ID, user.getId())
                : sqlBasicDao.getRowsCount(COUNT + WHERE_ACTIVE_AND_USER_ID, user.getId());
    }

    @Override
    public boolean isUserAlreadySubscribed(User user, Periodical periodical) throws DaoException {
        long count = sqlBasicDao.getRowsCount(IS_USER_ALREADY_SUBSCRIBED,
                user.getId(), periodical.getId());
        if (count > 1) {
            throw new DaoException("User cannot be subscribed twice to one edition!");
        }
        return count == 1;
    }
}
