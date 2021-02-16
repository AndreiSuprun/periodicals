package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.*;
import com.suprun.periodicals.dao.transaction.Transaction;
import com.suprun.periodicals.entity.Frequency;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.PeriodicalCategory;
import com.suprun.periodicals.entity.Publisher;
import com.suprun.periodicals.util.PictureService;
import com.suprun.periodicals.util.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing periodical-related operations
 *
 * @author Andrei Suprun
 */
public class PeriodicalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicalService.class);

    private PeriodicalDao periodicalDao =
            DaoFactory.getInstance().getPeriodicalDao();
    private PeriodicalCategoryDao periodicalCategoryDao =
            DaoFactory.getInstance().getPeriodicalCategoryDao();
    private FrequencyDao frequencyDao =
            DaoFactory.getInstance().getFrequencyDao();
    private PublisherDao publisherDao =
            DaoFactory.getInstance().getPublisherDao();
    private static final String SLASH = "\\";
    private static final String UPLOAD_DIRECTORY = Resource.FILE_UPLOAD.getProperty("upload.path");

    private PeriodicalService() {
    }

    private static class Instance {
        private final static PeriodicalService INSTANCE = new PeriodicalService();
    }

    public static PeriodicalService getInstance() {
        return Instance.INSTANCE;
    }

    public void createPeriodical(Periodical periodical, InputStream stream) throws ServiceException {
        LOGGER.debug("Attempt to create periodical");
        if (periodical == null) {
            throw new ServiceException("Attempt to insert nullable periodical");
        }
        try {
            Transaction.doTransaction(() -> {

                LOGGER.debug("Attempt to add periodical to db");
                periodicalDao.insert(periodical);
                LOGGER.debug("periodical in db");
                try {
                    PictureService.uploadFile(stream, periodical.getPicture());
                } catch (IOException e) {
                    LOGGER.error("Exception occurred while picture file processing");
                    throw new DaoException("Exception occurred while picture file processing", e);
                }
            });
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updatePeriodical(Periodical periodical, InputStream stream, String oldPicturePath) throws ServiceException {
        LOGGER.debug("Attempt to update periodical");
        if (periodical == null) {
            throw new ServiceException("Attempt to update nullable periodical");
        }
        try {
            Transaction.doTransaction(() -> {
                LOGGER.debug("Attempt to update periodical to db");
                periodicalDao.update(periodical);
                LOGGER.debug("periodical in db");
                try {
                    PictureService.uploadFile(stream, periodical.getPicture());
                    if (oldPicturePath != null && !oldPicturePath.equals(periodical.getPicture())) {
                        Files.deleteIfExists(Paths.get(UPLOAD_DIRECTORY + SLASH + oldPicturePath));
                    }
                } catch (IOException e) {
                    LOGGER.error("Exception occurred while picture file processing");
                    throw new DaoException("Exception occurred while picture file processing", e);
                }
            });
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updatePeriodical(Periodical periodical) throws ServiceException {
        LOGGER.debug("Attempt to update periodical");
        if (periodical == null) {
            throw new ServiceException("Attempt to update nullable periodical");
        }
        try {
            periodicalDao.update(periodical);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeStatus(Periodical periodical, boolean newStatus) throws ServiceException {
        LOGGER.debug("Attempt to change status of periodical");
        if (periodical == null) {
            throw new ServiceException("Attempt to change nullable periodical");
        }
        if (periodical.getAvailability() != newStatus) {
            periodical.setAvailability(newStatus);
            updatePeriodical(periodical);
        }
    }

    public Optional<Periodical> findPeriodicalById(Long id) throws ServiceException {
        LOGGER.debug("Attempt to find periodical by id");
        try {
            return periodicalDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Periodical> findAllPeriodicals(long skip, long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all periodicals");
        if (skip < 0 || limit < 0) {
            throw new ServiceException("Skip or limit params cannot be negative");
        }
        try {
            return periodicalDao.findAll(skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Periodical> findAllPeriodicalsByTag(String text, long skip, long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all periodicals by tag");
        if (skip < 0 || limit < 0) {
            throw new ServiceException("Skip or limit params cannot be negative");
        }
        try {
            return periodicalDao.fullTextSearch(text, skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Periodical> findAllPeriodicalsByStatus(boolean status, long skip, long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all periodicals by status");
        if (skip < 0 || limit < 0) {
            throw new ServiceException("Skip or limit params cannot be negative");
        }
        try {
            return periodicalDao.findByStatus(status, skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long getPeriodicalsCountByTag(String text) throws ServiceException {
        LOGGER.debug("Attempt to get count of periodicals by tag");
        try {
            return periodicalDao.getCountByTag(text);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long getPeriodicalsCountByStatus(boolean status) throws ServiceException {
        LOGGER.debug("Attempt to get count of periodicals by status");
        try {
            return periodicalDao.getCountByStatus(status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long getPeriodicalsCount() throws ServiceException {
        LOGGER.debug("Attempt to get count of periodicals");
        try {
            return periodicalDao.getCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<PeriodicalCategory> findAllPeriodicalCategory() throws ServiceException {
        LOGGER.debug("Attempt to find all periodical types");
        try {
            return periodicalCategoryDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Frequency> findAllFrequencies() throws ServiceException {
        LOGGER.debug("Attempt to find all frequencies");
        try {
            return frequencyDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Publisher> findAllPublishers() throws ServiceException {
        LOGGER.debug("Attempt to find all publishers");
        try {
            return publisherDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
