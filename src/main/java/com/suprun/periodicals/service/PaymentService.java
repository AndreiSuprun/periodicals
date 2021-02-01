package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.DaoFactory;
import com.suprun.periodicals.dao.PaymentDao;
import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing payment-related operations
 *
 * @author Andrei Suprun
 */
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    private PaymentDao paymentDao = DaoFactory.getInstance().getPaymentDao();

    private PaymentService() {
    }

    private static class Instance {
        private final static PaymentService INSTANCE = new PaymentService();
    }

    public static PaymentService getInstance() {
        return Instance.INSTANCE;
    }

    public Optional<Payment> findPaymentById(Long id) throws ServiceException {
        LOGGER.debug("Attempt to find payment by id");
        try{
            return paymentDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Payment> findAllPayments(long skip, long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all payments");
        if (skip < 0 || limit < 0) {
            throw new ServiceException("Skip or limit params cannot be negative");
        }
        try{
            return paymentDao.findAll(skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Payment createPayment(User user, BigDecimal totalPrice) throws ServiceException {
        LOGGER.debug("Attempt to create payment");
        if (user == null || totalPrice == null) {
            throw new ServiceException("Attempt to create payment for nullable user or total price");
        }
        Payment payment = Payment.newBuilder()
                .setUser(user)
                .setTotalPrice(totalPrice)
                .setPaymentDate(LocalDateTime.now())
                .build();
        try{
            return paymentDao.insert(payment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long getPaymentsCount() throws ServiceException {
        LOGGER.debug("Attempt to get payments count");
        try{
            return paymentDao.getCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
