package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.*;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Publisher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class PublisherServiceTest {
    @InjectMocks
    private PublisherService publisherService = PublisherService.getInstance();
    @Mock
    private PublisherDao publisherDao;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createPublisherTest() throws DaoException, ServiceException {
        Long publisherId = 1L;
        Publisher publisher = new Publisher();
        when(publisherDao.insert(publisher)).then((Answer<Publisher>) invocationOnMock -> {
            Publisher toReturn = invocationOnMock.getArgumentAt(0, Publisher.class);
            toReturn.setId(publisherId);
            return toReturn;
        });
        publisherService.addPublisher(publisher);
        assertEquals(publisherId, publisher.getId());
        verify(publisherDao, times(1)).insert(publisher);
    }

    @Test
    void updatePublisherTest() throws ServiceException, DaoException {
        Publisher publisher = new Publisher();
        publisherService.updatePublisher(publisher);
        verify(publisherDao, times(1)).update(publisher);
    }

    @Test
    void findPublisherByIdWithExistingPublisherTest() throws DaoException, ServiceException {
        Long publisherId = 1L;
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
        Optional<Publisher> expected = Optional.of(publisher);
        when(publisherDao.findOne(publisherId)).thenReturn(expected);

        Optional<Publisher> actual = publisherService.findPublisherById(publisherId);

        assertEquals(expected, actual);
        verify(publisherDao, times(1)).findOne(publisherId);
    }

    @Test
    void findPublisherByIdWithNotExistingPeriodicalTest() throws DaoException, ServiceException {
        Long publisherId = 1L;
        when(publisherDao.findOne(publisherId)).thenReturn(Optional.empty());

        Optional<Publisher> publisherOpt = publisherService.findPublisherById(publisherId);

        assertFalse(publisherOpt.isPresent());
        verify(publisherDao, times(1)).findOne(publisherId);
    }

    @Test
    void findAllPublisherTest() throws ServiceException, DaoException {

        List<Publisher> expected = new ArrayList<Publisher>() {{
            add(new Publisher(1L,"A"));
            add(new Publisher(2L,"B"));
            add(new Publisher(3L,"C"));
        }};
        when(publisherDao.findAll()).thenReturn(expected);

        List<Publisher> actual = publisherService.findAllPublishers();

        assertEquals(3, actual.size());
        verify(publisherDao, times(1)).findAll();
    }

    @Test
    void getPublisherCountTest() throws ServiceException, DaoException {
        long expected = 10;
        when(publisherDao.getCount()).thenReturn(expected);

        long actual = publisherService.getPublishersCount();

        assertEquals(expected, actual);
        verify(publisherDao, times(1)).getCount();
    }
}