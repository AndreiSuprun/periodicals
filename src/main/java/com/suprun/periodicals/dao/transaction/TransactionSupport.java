package com.suprun.periodicals.dao.transaction;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.connection.ConnectionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * Class responsible for transactions managing: preparing and restoring connection, commit and rollback processing
 *
 * @author Andrei Suprun
 */
public class TransactionSupport implements AutoCloseable {

    Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    private Connection connection;

    private final int transactionIsolationLevel;

    private boolean isTransactionDoneWithoutRollback = true;
    private boolean autoCommitState;
    private int oldTransactionIsolation;

    public TransactionSupport(int transactionIsolationLevel) {
        this.transactionIsolationLevel = transactionIsolationLevel;
    }

    /**
     * Check if transaction was performed without rollback
     *
     * @return true if transaction was performed without rollback action
     */
    public boolean isTransactionDoneWithoutRollback() {
        return isTransactionDoneWithoutRollback;
    }

    /**
     * Preparing transaction: set autocommit to false and transaction isolation level to specified value
     *
     * @throws DaoException if in process of connection preparing is thrown SQLException
     */
    void prepareConnectionForTransaction() throws DaoException {
        connection = initConnection();
        try {
            autoCommitState = connection.getAutoCommit();
            connection.setAutoCommit(false);
            oldTransactionIsolation = connection.getTransactionIsolation();
            if (oldTransactionIsolation != transactionIsolationLevel)
                connection.setTransactionIsolation(transactionIsolationLevel);

            LOGGER.debug("Changed connection autoCommit state to false");
        } catch (SQLException prepareException) {
            throw new DaoException(prepareException);
        }
    }

    /**
     * Commits successful transaction
     *
     * @throws DaoException if in process of commiting transaction is thrown SQLException
     */
    void commit() throws DaoException {
        if (isTransactionDoneWithoutRollback) {
            try {
                connection.commit();
                LOGGER.debug("Commit successfully done");
            } catch (SQLException commitException) {
                throw new DaoException(commitException);
            }
        }
    }

    /**
     * Perform rollback action when transaction ends unsuccessfully
     *
     * @throws DaoException if in process of rollback transaction is thrown SQLException
     */
    void doRollback() throws DaoException {
        try {
        connection.rollback();
        isTransactionDoneWithoutRollback = false;
        LOGGER.debug("Rollback successfully done");
        } catch (SQLException commitException) {
            throw new DaoException(commitException);
        }
    }

    /**
     * Restore default connection properties: autocommit option and transaction isolation level
     *
     * @throws DaoException if in process of restoring properties of transaction is thrown SQLException
     */
    void returnDefaultConnectionState() throws DaoException {
        try {
            connection.setAutoCommit(autoCommitState);
            if (connection.getTransactionIsolation() != oldTransactionIsolation)
                connection.setTransactionIsolation(oldTransactionIsolation);

            LOGGER.debug("Changed connection state to default");
        } catch (SQLException afterCommitException) {
            throw new DaoException(afterCommitException);
        }
    }

    /**
     * Implementation of close() method of Autocloseable interface
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Can't close connection", e);
        }
    }

    /**
     * Initialize connection to instance of proxied connection from connection pool
     *
     * @return Connection Proxied connection
     * @throws DaoException if failed to get proxied connection
     */
    private Connection initConnection() throws DaoException {
        try {
        Connection connection = ConnectionProxy.getInstance().getConnection();
        if (connection != null) {
            LOGGER.debug("Get connection");
            return connection;
        } else {
            throw new DaoException("Connection equals null");
        }
        } catch (SQLException e) {
            LOGGER.error("Failed to get connection", e);
            throw new DaoException("Failed to get connection", e);
        }
    }
}
