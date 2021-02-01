package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.RoleDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Role;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlRoleDao implements RoleDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("role.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("role.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("role.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("role.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("role.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("role.where.id");

    private final SqlBasicDao<Role> sqlBasicDao;

    public SqlRoleDao() {
        this(MapperFactory.getRoleMapper());
    }

    public SqlRoleDao(EntityMapper<Role> mapper) {
        this(new SqlBasicDao<>(mapper));
    }

    public SqlRoleDao(SqlBasicDao<Role> sqlBasicDao) {
        this.sqlBasicDao = sqlBasicDao;
    }

    @Override
    public Optional<Role> findOne(Integer id) throws DaoException {
        return sqlBasicDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Role> findAll() throws DaoException {
        return sqlBasicDao.findAll(SELECT_ALL);
    }

    public List<Role> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlBasicDao.findAll(SELECT_ALL + SqlBasicDao.LIMIT, skip, limit);
    }

    @Override
    public Role insert(Role obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to insert nullable role");
        }

        Integer id = sqlBasicDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                Integer.class,
                obj.getName());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Role obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to update nullable role");
        }

        sqlBasicDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getName(),
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
