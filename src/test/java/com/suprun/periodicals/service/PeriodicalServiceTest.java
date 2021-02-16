package com.suprun.periodicals.service;


import com.suprun.periodicals.dao.*;
import com.suprun.periodicals.entity.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

class PeriodicalServiceTest {
    @InjectMocks
    private PeriodicalService periodicalService = PeriodicalService.getInstance();
    @Mock
    private PeriodicalDao periodicalDao;
    @Mock
    private PeriodicalCategoryDao periodicalTypeDao;
    @Mock
    private FrequencyDao frequencyDao;
    @Mock
    private PublisherDao publisherDao;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createPeriodicalTestWithNotValidData() throws DaoException, ServiceException {
        Periodical periodical = null;

        ServiceException e = expectThrows(ServiceException.class, () -> periodicalService.createPeriodical(periodical, InputStream.nullInputStream()));
        assertEquals("Attempt to insert nullable periodical", e.getMessage());
        verify(periodicalDao, never()).insert(periodical);
    }

    @Test
    void updatePeriodicalTestWithNotValidData() throws ServiceException, DaoException {
        Periodical periodical = null;
        ServiceException e = expectThrows(ServiceException.class, () -> periodicalService.updatePeriodical(periodical, InputStream.nullInputStream(), null));
        assertEquals("Attempt to update nullable periodical", e.getMessage());
        verify(periodicalDao, never()).update(periodical);
    }

    @Test
    void changeStatusOnAnotherStatusTest() throws DaoException, ServiceException {
        Periodical periodical = Periodical.newBuilder()
                .setAvailability(true)
                .build();

        periodicalService.changeStatus(periodical, false);

        assertEquals(periodical.getAvailability(), false);
        verify(periodicalDao, times(1)).update(periodical);
    }

    @Test
    void changeStatusOnSameStatusTest() throws DaoException, ServiceException {
        Periodical periodical = Periodical.newBuilder()
                .setAvailability(true)
                .build();

        periodicalService.changeStatus(periodical, true);

        assertEquals(periodical.getAvailability(), true);
        verify(periodicalDao, never()).update(periodical);
    }

    @Test
    void findPeriodicalByIdWithExistingPeriodicalTest() throws DaoException, ServiceException {
        Long periodicalId = 1L;
        Optional<Periodical> expected = Optional.of(
                Periodical.newBuilder()
                        .setId(periodicalId)
                        .build());
        when(periodicalDao.findOne(periodicalId)).thenReturn(expected);

        Optional<Periodical> actual = periodicalService.findPeriodicalById(periodicalId);

        assertEquals(expected, actual);
        verify(periodicalDao, times(1)).findOne(periodicalId);
    }

    @Test
    void findPeriodicalByIdWithNotExistingPeriodicalTest() throws DaoException, ServiceException {
        Long periodicalId = 1L;
        when(periodicalDao.findOne(periodicalId)).thenReturn(Optional.empty());

        Optional<Periodical> periodicalOpt = periodicalService.findPeriodicalById(periodicalId);

        assertFalse(periodicalOpt.isPresent());
        verify(periodicalDao, times(1)).findOne(periodicalId);
    }

    @Test
    void findAllPeriodicalsTest() throws ServiceException, DaoException {
        long skip = 0;
        long limit = 3;
        List<Periodical> expected = new ArrayList<Periodical>() {{
            add(Periodical.newBuilder().setId(1L).build());
            add(Periodical.newBuilder().setId(2L).build());
            add(Periodical.newBuilder().setId(3L).build());
        }};
        when(periodicalDao.findAll(skip, limit)).thenReturn(expected);

        List<Periodical> actual = periodicalService.findAllPeriodicals(skip, limit);

        assertEquals(3, actual.size());
        verify(periodicalDao, times(1)).findAll(skip, limit);
    }

    @Test
    void findAllPeriodicalsByStatusTest() throws DaoException, ServiceException {
        long skip = 0;
        long limit = 3;
        boolean isAvailable = true;
        List<Periodical> expected = new ArrayList<Periodical>() {{
            add(Periodical.newBuilder().setId(1L).build());
            add(Periodical.newBuilder().setId(2L).build());
            add(Periodical.newBuilder().setId(3L).build());
        }};
        when(periodicalDao.findByStatus(isAvailable, skip, limit))
                .thenReturn(expected);

        List<Periodical> actual = periodicalService.findAllPeriodicalsByStatus(isAvailable, skip, limit);

        assertEquals(3, actual.size());
        verify(periodicalDao, times(1)).findByStatus(isAvailable, skip, limit);
    }

    @Test
    void getPeriodicalsCountTest() throws ServiceException, DaoException {
        long expected = 10;
        when(periodicalDao.getCount()).thenReturn(expected);

        long actual = periodicalService.getPeriodicalsCount();

        assertEquals(expected, actual);
        verify(periodicalDao, times(1)).getCount();
    }

    @Test
    void getPeriodicalsCountByStatusTest() throws DaoException, ServiceException {
        boolean isAvailable = true;
        long expected = 10;
        when(periodicalDao.getCountByStatus(isAvailable)).thenReturn(expected);

        long actual = periodicalService.getPeriodicalsCountByStatus(isAvailable);

        assertEquals(expected, actual);
        verify(periodicalDao, times(1)).getCountByStatus(isAvailable);
    }

    @Test
    void findAllPeriodicalTypesTest() throws ServiceException, DaoException {
        List<PeriodicalCategory> expected = new ArrayList<PeriodicalCategory>() {{
            add(PeriodicalCategory.newBuilder().setId(1).build());
            add(PeriodicalCategory.newBuilder().setId(2).build());
            add(PeriodicalCategory.newBuilder().setId(3).build());
        }};
        when(periodicalTypeDao.findAll()).thenReturn(expected);

        List<PeriodicalCategory> actual = periodicalService.findAllPeriodicalCategory();

        assertEquals(3, actual.size());
        verify(periodicalTypeDao, times(1)).findAll();
    }

    @Test
    void findAllFrequenciesTest() throws DaoException, ServiceException {
        List<Frequency> expected = new ArrayList<Frequency>() {{
            add(Frequency.newBuilder().setId(1).build());
            add(Frequency.newBuilder().setId(2).build());
            add(Frequency.newBuilder().setId(3).build());
        }};
        when(frequencyDao.findAll()).thenReturn(expected);

        List<Frequency> actual = periodicalService.findAllFrequencies();

        assertEquals(3, actual.size());
        verify(frequencyDao, times(1)).findAll();
    }

    @Test
    void findAllPublishersTest() throws ServiceException, DaoException {
        List<Publisher> expected = new ArrayList<Publisher>() {{
            add(new Publisher(1L, "Publisher 1"));
            add(new Publisher(2L, "Publisher 2"));
            add(new Publisher(3L, "Publisher 3"));
        }};
        when(publisherDao.findAll()).thenReturn(expected);

        List<Publisher> actual = periodicalService.findAllPublishers();

        assertEquals(3, actual.size());
        verify(publisherDao, times(1)).findAll();
    }
}