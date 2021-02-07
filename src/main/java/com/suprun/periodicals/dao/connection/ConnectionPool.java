package com.suprun.periodicals.dao.connection;

import com.suprun.periodicals.util.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Custom connection pool with lazy initialization
 *
 * @author Andrei Suprun
 */

public class ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private BlockingQueue<PooledConnection> pool;
    private Set<PooledConnection> busy;
    private int maxPoolSize;
    private int initialPoolSize;
    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;
    private String driverClassName;

    private static volatile ConnectionPool instance;

    public static ConnectionPool getInstance() throws SQLException {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    private ConnectionPool() throws SQLException {
        this.driverClassName = Resource.DATABASE.getProperty("driver");
        this.databaseUrl = Resource.DATABASE.getProperty("url");
        this.databaseUser = Resource.DATABASE.getProperty("username");
        this.databasePassword = Resource.DATABASE.getProperty("password");
        this.initialPoolSize = Integer.parseInt(Resource.DATABASE.getProperty("initialPoolSize"));
        this.maxPoolSize = Integer.parseInt(Resource.DATABASE.getProperty("maxPoolSize"));
        if (initialPoolSize > maxPoolSize) {
            initialPoolSize = maxPoolSize;
        }
        this.pool = new LinkedBlockingQueue<>(maxPoolSize);
        this.busy = new HashSet<>();
        initPooledConnections(driverClassName);
    }

    private void initPooledConnections(String driverClassName) throws SQLException {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            LOGGER.debug("Exception occurred while loading JDBC driver");
            throw new SQLException("Exception occurred while loading JDBC driver");
        }
        for (int i = 0; i < initialPoolSize; i++) {
            openAndPoolConnection();
        }
    }

    private synchronized void openAndPoolConnection() throws SQLException {
        if (currentPoolSize() == maxPoolSize) {
            return;
        }
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        pool.offer(new PooledConnection(connection));
    }

    public PooledConnection getConnection() throws SQLException {
        PooledConnection connection = null;
        if (pool.peek() == null && currentPoolSize() < maxPoolSize) {
            openAndPoolConnection();
        }
        try {
            connection = pool.take();
        } catch (InterruptedException e) {
            LOGGER.debug("Exception occurred while waiting to get connection from pool");
            throw new SQLException("Exception occurred while waiting to get connection from pool");
        }
        if (connection.isClosed()) {
            connection = getConnection();
        }
        busy.add(connection);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (!(connection instanceof PooledConnection)) {
            return;
        }
        pool.offer((PooledConnection) connection);
        busy.remove(connection);
    }

    private int currentPoolSize() {
        return pool.size() + busy.size();
    }

    public void closeAllConnections() {
        busy.forEach(this::releaseConnection);
        try {
            for (Connection connection : pool) {
                ((PooledConnection) connection).getConnection().close();
            }
        } catch (SQLException e) {
            LOGGER.debug("Exception occurred while closing connection");
        }
        pool.clear();
    }
}
