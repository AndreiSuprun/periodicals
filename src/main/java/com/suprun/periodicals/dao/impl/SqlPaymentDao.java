package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.PaymentDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlPaymentDao implements PaymentDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("payment.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("payment.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("payment.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("payment.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("payment.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("payment.where.id");
    private final static String ORDER_BY_DATE =
            Resource.QUERIES.getProperty("payment.select.order");

    private final SqlBasicDao<Payment> sqlBasicDao;

    public SqlPaymentDao() {
        this(MapperFactory.getPaymentMapper());
    }

    public SqlPaymentDao(EntityMapper<Payment> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public SqlPaymentDao(SqlBasicDao<Payment> sqlBasicDao) {
        this.sqlBasicDao = sqlBasicDao;
    }

    @Override
    public Optional<Payment> findOne(Long id) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Payment> findAll() throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL + ORDER_BY_DATE);
    }

    @Override
    public List<Payment> findAll(long skip, long limit) throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL + ORDER_BY_DATE + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public Payment insert(Payment payment) throws DaoException {
        if (payment == null) {
            throw new DaoException("Attempt to insert nullable payment");
        }
        Long id = sqlBasicDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                payment.getUser().getId(),
                payment.getTotalPrice(),
                payment.getPaymentDate());
        payment.setId(id);
        return payment;
    }

    @Override
    public void update(Payment payment) throws DaoException {

        sqlBasicDao.executeUpdate(
                UPDATE + WHERE_ID,
                payment.getUser().getId(),
                payment.getTotalPrice(),
                payment.getPaymentDate(),
                payment.getId());
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
}
