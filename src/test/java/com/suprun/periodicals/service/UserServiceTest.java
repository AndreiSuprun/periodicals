package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.UserDao;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.util.PasswordUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class UserServiceTest {
    @InjectMocks
    private UserService userService = UserService.getInstance();
    @Mock
    private UserDao userDao;
    @Mock
    private PasswordUtil passwordUtil;
    private static final String SALT_HASH_DELIMITER_REGEX = "\\$";
    private static final String EMAIL = "email@mail.com";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_HASH = "uQJ3CUSejNzYjZCe4fBNXXsbMieX7A7JdHWiSUBJyEs=$LuWCwxTTJWkoPObFNk2oZe3yXW1SKlNFYwJImZ/FBQw=";
    private static final String USER_ROLE = "user";
    private static final String ANOTHER_PASSWORD ="another_password";

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findUserByIdWithExistingUserTest() throws ServiceException, DaoException {
        Long userId = 1L;
        Optional<User> expected = Optional.of(
                User.newBuilder()
                        .setId(userId)
                        .build());
        when(userDao.findOne(userId)).thenReturn(expected);

        Optional<User> actual = userService.findUserById(userId);

        assertEquals(expected, actual);
        verify(userDao, times(1)).findOne(userId);
    }

    @Test
    void findUserByIdWithNotExistingUserTest() throws DaoException, ServiceException {
        Long userId = 1L;
        when(userDao.findOne(userId)).thenReturn(Optional.empty());

        Optional<User> periodicalOpt = userService.findUserById(userId);

        assertFalse(periodicalOpt.isPresent());
        verify(userDao, times(1)).findOne(userId);
    }

    @Test
    void signInWithCorrectEmailAndPasswordTest() throws DaoException, ServiceException {
        Optional<User> expected = Optional.of(User.newBuilder()
                .setEmail(EMAIL)
                .setPassword(PASSWORD_HASH)
                .build());
        when(userDao.findOneByEmail(EMAIL)).thenReturn(expected);

        Optional<User> actual = userService.signIn(EMAIL, PASSWORD);

        assertEquals(actual, expected);
        verify(userDao, times(1)).findOneByEmail(EMAIL);
    }

    @Test
    void signInWithWrongPasswordTest() throws DaoException, ServiceException {
        Optional<User> findByEmailUser = Optional.of(User.newBuilder()
                .setEmail(EMAIL)
                .setPassword(PASSWORD_HASH)
                .build());
        when(userDao.findOneByEmail(EMAIL)).thenReturn(findByEmailUser);

        Optional<User> actual = userService.signIn(EMAIL, ANOTHER_PASSWORD);

        assertFalse(actual.isPresent());
        verify(userDao, times(1)).findOneByEmail(EMAIL);
    }

    @Test
    void signInWithWrongEmailTest() throws DaoException, ServiceException {
        when(userDao.findOneByEmail(EMAIL)).thenReturn(Optional.empty());

        Optional<User> actual = userService.signIn(EMAIL, PASSWORD);

        assertFalse(actual.isPresent());
        verify(userDao, times(1)).findOneByEmail(EMAIL);
    }

    @Test
    void signInWithNullEmailTest() throws ServiceException, DaoException {
        Optional<User> actual = userService.signIn(null, PASSWORD);

        assertFalse(actual.isPresent());
        verify(userDao, never()).findOneByEmail(EMAIL);
    }

    @Test
    void signInWithNullPasswordTest() throws ServiceException, DaoException {
        Optional<User> actual = userService.signIn(EMAIL, null);

        assertFalse(actual.isPresent());
        verify(userDao, never()).findOneByEmail(EMAIL);
    }

    @Test
    void registerUserWithValidParametersTest() throws DaoException, ServiceException {
        User user = User.newBuilder()
                .setEmail(EMAIL)
                .setPassword(PASSWORD_HASH)
                .build();
        when(userDao.existByEmail(EMAIL)).thenReturn(false);

        assertTrue(userService.registerUser(user));
        assertTrue(user.getRole().getName().equalsIgnoreCase(USER_ROLE));
        verify(userDao, times(1)).insert(any(User.class));
    }

    @Test
    void registerUserWithSameEmailTest() throws DaoException, ServiceException {
        User user = User.newBuilder()
                .setEmail(EMAIL)
                .setPassword(PASSWORD_HASH)
                .build();
        when(userDao.existByEmail(EMAIL)).thenReturn(true);

        assertFalse(userService.registerUser(user));
        verify(userDao, never()).insert(any(User.class));
    }

    @Test
    void registerUserWithNullEmailTest() throws ServiceException, DaoException {
        User user = User.newBuilder()
                .setEmail(null)
                .setPassword(PASSWORD_HASH)
                .build();

        assertFalse(userService.registerUser(user));
        verify(userDao, never()).insert(any(User.class));
    }

    @Test
    void registerUserWithNullPasswordTest() throws ServiceException, DaoException {
        User user = User.newBuilder()
                .setEmail(EMAIL)
                .setPassword(null)
                .build();

        assertFalse(userService.registerUser(user));
        verify(userDao, never()).insert(any(User.class));
    }

}