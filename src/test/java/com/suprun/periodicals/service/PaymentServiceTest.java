package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.PaymentDao;
import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.testng.Assert.*;

public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService = PaymentService.getInstance();
    @Mock
    private PaymentDao paymentDao;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findPaymentByIdWithExistingPaymentTest() throws DaoException, ServiceException {
        Long paymentId = 1L;
        Optional<Payment> expected = Optional.of(
                Payment.newBuilder()
                        .setId(paymentId)
                        .build());
        when(paymentDao.findOne(paymentId)).thenReturn(expected);

        Optional<Payment> actual = paymentService.findPaymentById(paymentId);

        assertEquals(expected, actual);
        verify(paymentDao, times(1)).findOne(paymentId);
    }

    @Test
    void findPaymentByIdWithNotExistingPaymentTest() throws DaoException, ServiceException {
        Long paymentId = 1L;
        when(paymentDao.findOne(paymentId)).thenReturn(Optional.empty());

        Optional<Payment> paymentOpt = paymentService.findPaymentById(paymentId);

        assertFalse(paymentOpt.isPresent());
        verify(paymentDao, times(1)).findOne(paymentId);
    }

    @Test
    void findAllPaymentsTest() throws ServiceException, DaoException {
        long skip = 0;
        long limit = 5;

        paymentService.findAllPayments(skip, limit);

        verify(paymentDao, times(1)).findAll(skip, limit);
    }

    @Test
    void createPayment() throws DaoException, ServiceException {
        Long paymentID = 1L;
        User user = User.newBuilder()
                .setId(1L)
                .build();
        BigDecimal totalPrice = new BigDecimal("10");
        when(paymentDao.insert(any(Payment.class))).then((Answer<Payment>) invocationOnMock -> {
            Payment payment = invocationOnMock.getArgumentAt(0, Payment.class);
            payment.setId(paymentID);
            return payment;
        });

        Payment actual = paymentService.createPayment(user, totalPrice);

        assertEquals(paymentID, actual.getId());
        assertEquals(user, actual.getUser());
        assertEquals(totalPrice, actual.getTotalPrice());
        assertNotNull(actual.getPaymentDate());
        verify(paymentDao, times(1)).insert(actual);
    }

    @Test
    void getPaymentsCountTest() throws DaoException, ServiceException {
        long expected = 10;
        when(paymentDao.getCount()).thenReturn(expected);

        long actual = paymentService.getPaymentsCount();

        assertEquals(expected, actual);
        verify(paymentDao, times(1)).getCount();
    }
}