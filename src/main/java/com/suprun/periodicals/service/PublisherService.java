package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.*;
import com.suprun.periodicals.dao.transaction.Transaction;
import com.suprun.periodicals.entity.Frequency;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.PeriodicalCategory;
import com.suprun.periodicals.entity.Publisher;
import com.suprun.periodicals.util.PasswordUtil;
import com.suprun.periodicals.util.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;
/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing publisher-related operations
 *
 * @author Andrei Suprun
 */
public class PublisherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherService.class);

    private PublisherDao publisherDao = DaoFactory.getInstance().getPublisherDao();

    private PublisherService() {
    }

    private static class Instance {
        private final static PublisherService INSTANCE = new PublisherService();
    }

    public static PublisherService getInstance() {
        return Instance.INSTANCE;
    }

    public boolean addPublisher(Publisher publisher) throws ServiceException {
        LOGGER.debug("Attempt to create publisher");
        if (publisher == null) {
            throw new ServiceException("Attempt to insert nullable publisher");
        }
        boolean publisherInDb;
        try {
            publisherInDb = publisherDao.existByName(publisher.getName());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (!publisherInDb) {
            try {
                LOGGER.debug("Attempt to add periodical to db");
                publisherDao.insert(publisher);
                LOGGER.debug("publisher in db");
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            return true;
        }
        return false;
    }

    public void updatePublisher(Publisher publisher) throws ServiceException {
        LOGGER.debug("Attempt to update periodical");
        if (publisher == null) {
            throw new ServiceException("Attempt to update nullable periodical");
        }
        try {
            LOGGER.debug("Attempt to update periodical to db");
            publisherDao.update(publisher);
            LOGGER.debug("publisher in db");
        } catch (
                DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Publisher> findPublisherById(Long id) throws ServiceException {
        LOGGER.debug("Attempt to find publisher by id");
        try {
            return publisherDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Publisher> findAllPublishers() throws ServiceException {
        LOGGER.debug("Attempt to find all periodicals");
        try {
            return publisherDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long getPublishersCount() throws ServiceException {
        LOGGER.debug("Attempt to get count of periodicals");
        try {
            return publisherDao.getCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
