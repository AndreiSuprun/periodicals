package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.UserDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Optional;

public class SqlUserDao implements UserDao {

    private final static String SELECT_ALL = Resource.QUERIES.getProperty("user.select.all");
    private final static String INSERT = Resource.QUERIES.getProperty("user.insert");
    private final static String UPDATE = Resource.QUERIES.getProperty("user.update");
    private final static String DELETE = Resource.QUERIES.getProperty("user.delete");
    private final static String COUNT = Resource.QUERIES.getProperty("user.count");
    private final static String WHERE_ID = Resource.QUERIES.getProperty("user.where.id");
    private final static String WHERE_EMAIL = Resource.QUERIES.getProperty("user.where.email");

    private final SqlBasicDao<User> userSqlBasicDao;

    public SqlUserDao() {
        this(MapperFactory.getUserMapper());
    }

    public SqlUserDao(EntityMapper<User> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public SqlUserDao(SqlBasicDao<User> userSqlBasicDao) {
        this.userSqlBasicDao = userSqlBasicDao;
    }

    @Override
    public Optional<User> findOne(Long id) throws DaoException {
        return userSqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public Optional<User> findOneByEmail(String email) throws DaoException {
        return userSqlBasicDao.findOne(SELECT_ALL + WHERE_EMAIL, email);
    }

    @Override
    public List<User> findAll() throws DaoException {
        return userSqlBasicDao.findAll(SELECT_ALL);
    }

    public List<User> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return userSqlBasicDao.findAll(SELECT_ALL + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public User insert(User obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to insert nullable user");
        }

        long id = userSqlBasicDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getRole().getId(),
                obj.getFirstName(),
                obj.getLastName(),
                obj.getEmail(),
                obj.getPassword());

        obj.setId(id);
        return obj;
    }

    @Override
    public void update(User obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to update nullable user");
        }
        userSqlBasicDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getRole().getId(),
                obj.getFirstName(),
                obj.getLastName(),
                obj.getEmail(),
                obj.getPassword(),
                obj.getId());
    }

    @Override
    public void delete(Long id) throws DaoException {
        userSqlBasicDao.executeUpdate(
                DELETE + WHERE_ID,
                id);
    }

    @Override
    public long getCount() throws DaoException {
        return userSqlBasicDao.getRowsCount(COUNT);
    }

    @Override
    public boolean existByEmail(String email) throws DaoException {
        return findOneByEmail(email).isPresent();
    }
}
