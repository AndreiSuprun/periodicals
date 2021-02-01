package com.suprun.periodicals.dao;

/**
 * Artificial exception that should be thrown out of the DAO layer
 */
public class DaoException extends Exception{

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
