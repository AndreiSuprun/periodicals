package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.DaoFactory;
import com.suprun.periodicals.dao.UserDao;
import com.suprun.periodicals.entity.Role;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing user-related operations
 *
 * @author Andrei Suprun
 */
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final Role defaultRole = new Role(2, "user");
    private UserDao userDao = DaoFactory.getInstance().getUserDao();

    private UserService() {
    }

    private static class Instance {
        private final static UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return Instance.INSTANCE;
    }

    public Optional<User> signIn(String email, String password) throws ServiceException {
        LOGGER.debug("Attempt to sign in");
        if (email == null || password == null) {
            return Optional.empty();
        }
        Optional<User> user;
        try {
            user = userDao.findOneByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        if (user.isPresent()) {
            boolean isPasswordMatches;
            try {
                isPasswordMatches = PasswordUtil.checkSecurePassword(password, user.get().getPassword());
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                LOGGER.error("Exception occurred while password hashing");
                throw new ServiceException(e);
            }
            if (isPasswordMatches) {
                user.get().setPassword(null);
                return user;
            }
        }
        return Optional.empty();
    }

    public Optional<User> findUserById(Long id) throws ServiceException {
        LOGGER.debug("Attempt to find user by id");
        Optional<User> user;
        try {
            user = userDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user.map(u -> {
            u.setPassword(null);
            ;
            return u;
        });
    }

    public boolean registerUser(User user) throws ServiceException {
        LOGGER.debug("Attempt to register new user");
        if (user.getEmail() == null ||
                user.getPassword() == null) {
            return false;
        }
        if (user.getRole() == null) {
            user.setRole(defaultRole);
        }
        boolean userIsPresent;
        try {
            userIsPresent = userDao.existByEmail(user.getEmail());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (!userIsPresent) {
            String hash = null;
            try {
                hash = PasswordUtil.hashPassword(
                        user.getPassword());
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                LOGGER.error("Exception occurred while password hashing");
                throw new ServiceException(e);
            }
            user.setPassword(hash);
            try {
                userDao.insert(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            return true;
        }
        return false;
    }
}
