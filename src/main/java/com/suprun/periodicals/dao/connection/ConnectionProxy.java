package com.suprun.periodicals.dao.connection;

import com.suprun.periodicals.dao.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The class is a {@link java.sql.Connection } wrapper.
 * It allows to share a connection to different dao methods
 * inside transaction body.
 *
 * @author Andrei Suprun
 * @see PooledConnection
 * @see Transaction
 */

public class ConnectionProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionProxy.class);

    private static final ConnectionProxy INSTANCE = new ConnectionProxy();

    private final ThreadLocal<PooledConnection> connectionProxyThreadLocal = new ThreadLocal<>();

    private ConnectionProxy() {}

    public static ConnectionProxy getInstance() {
        return INSTANCE;
    }

    /**
     * Get connection from tread local of current tread, if not set to tread local get it from connection pool
     * and also set to tread local of current tread
     *
     * @return Connection proxied connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        PooledConnection pooledConnection = connectionProxyThreadLocal.get();
        if (pooledConnection == null) {
            LOGGER.debug("ConnectionProxy is null");
            pooledConnection = ConnectionPool.getInstance().getConnection();
            connectionProxyThreadLocal.set(pooledConnection);
            LOGGER.debug("ConnectionProxy was successfully created");
        } else {
            LOGGER.debug("ConnectionProxy was gotten from connectionProxyThreadLocal");
        }
        pooledConnection.incrementAccessCounter();
        return pooledConnection;
    }

    /**
     * Return connection to connection pool
     */
    void removeConnection() {
        PooledConnection pooledConnection = connectionProxyThreadLocal.get();
        if (pooledConnection != null) {
            connectionProxyThreadLocal.remove();
            LOGGER.debug("ConnectionProxy was successfully removed");
        } else {
            LOGGER.warn("ConnectionProxyThreadLocal is empty, can't remove ConnectionProxy");
        }
    }
}
