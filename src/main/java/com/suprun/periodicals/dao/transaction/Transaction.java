package com.suprun.periodicals.dao.transaction;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.connection.ConnectionProxy;
import com.suprun.periodicals.dao.connection.PooledConnection;
import com.suprun.periodicals.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The interface for transaction support.
 *
 * @author Andrei Suprun
 * @see PooledConnection
 * @see ConnectionProxy
 */

@FunctionalInterface
public interface Transaction {

    Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    /**
     * Default transaction isolation level
     */
    int TRANSACTION_DEFAULT_LEVEL = Connection.TRANSACTION_SERIALIZABLE;

    /**
     * Method of functional interface for implementation of transaction body
     *
     * @throws DaoException if in process of
     */
    void execute() throws DaoException;

    /**
     * Performing transaction with provided transaction body implementation and default transaction isolation level
     *
     * @param t Transaction implementation of Transaction interface
     * @return true if transaction is successful
     * @throws DaoException
     */
    static boolean doTransaction(Transaction t) throws DaoException {
        return doTransaction(t, TRANSACTION_DEFAULT_LEVEL);
    }

    /**
     * Performing transaction with provided transaction body implementation and transaction isolation level
     *
     * @param t Transaction implementation of Transaction interface
     * @param transactionIsolationLevel Transaction isolation level
     * @return true if transaction is successful
     * @throws DaoException
     */
    static boolean doTransaction(Transaction t, int transactionIsolationLevel) throws DaoException {
        try (TransactionSupport transactionSupport =
                     new TransactionSupport(transactionIsolationLevel)) {

            LOGGER.debug("Transaction begin");
            transactionSupport.prepareConnectionForTransaction();
            try {
                t.execute();
                LOGGER.debug("Transaction body was successfully done");
            } catch (DaoException e) {
                LOGGER.debug("Transaction body fail. Trying rollback", e);
                try {
                    transactionSupport.doRollback();
                } catch (DaoException rollbackException) {
                    rollbackException.addSuppressed(e);
                    throw new DaoException(rollbackException);
                }
                throw new DaoException(e);
            }
            transactionSupport.commit();
            transactionSupport.returnDefaultConnectionState();

            LOGGER.debug("Transaction end");
            return transactionSupport.isTransactionDoneWithoutRollback();
        }
    }
}
