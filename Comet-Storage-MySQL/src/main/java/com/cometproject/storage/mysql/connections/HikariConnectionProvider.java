package com.cometproject.storage.mysql.connections;

import com.cometproject.api.config.Configuration;
import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HikariConnectionProvider extends MySQLConnectionProvider {

    private final Logger log = LogManager.getLogger(HikariConnectionProvider.class.getName());

    private HikariDataSource hikariDataSource;

    public HikariConnectionProvider() {
        boolean isConnectionFailed = true;
        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(
                    "jdbc:mysql://" + Configuration.currentConfig().get("comet.db.host") +
                            "/" + Configuration.currentConfig().get("comet.db.name") + "?tcpKeepAlive=" + Configuration.currentConfig().get("comet.db.pool.tcpKeepAlive") +
                            "&autoReconnect=" + Configuration.currentConfig().get("comet.db.pool.autoReconnect") + "&useSSL=false&characterEncoding=utf8&useTimezone=true&serverTimezone=UTC" +
                            "&maxPoolSize=" + Integer.parseInt(Configuration.currentConfig().get("comet.db.pool.max"))
            );

            config.setUsername(Configuration.currentConfig().get("comet.db.username"));
            config.setPassword(Configuration.currentConfig().get("comet.db.password"));

            config.setMaximumPoolSize(Integer.parseInt(Configuration.currentConfig().get("comet.db.pool.max")));

//            config.setMaxConnectionsPerPartition(Integer.parseInt(Configuration.currentConfig().get("comet.db.pool.max")));
//            config.setPartitionCount(Integer.parseInt(Configuration.currentConfig().get("comet.db.pool.count")));
//
//            config.setIdleMaxAge(Integer.valueOf(Configuration.currentConfig().get("comet.db.pool.idleMaxAgeSeconds")), TimeUnit.SECONDS);
//            config.setMaxConnectionAge(Integer.valueOf(Configuration.currentConfig().get("comet.db.pool.maxConnectionAgeSeconds")), TimeUnit.SECONDS);
//
//            config.setAcquireRetryAttempts(Integer.valueOf(Configuration.currentConfig().get("comet.db.pool.acquireRetryAttempts")));

            log.info("Connecting to the MySQL server");

            isConnectionFailed = false;
            this.hikariDataSource = new HikariDataSource(config);
        } catch (Exception e) {
            isConnectionFailed = true;
            log.error("Failed to connect to MySQL server", e);
            System.exit(0);
        } finally {
            if (!isConnectionFailed) {
                log.info("Connection to MySQL server was successful");
            }
        }

    }

    @Override
    public Connection getConnection() throws Exception {
        return this.hikariDataSource.getConnection();
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            log.error("Failed to close Hikari connection", e);
        }
    }

    @Override
    public void closeStatement(PreparedStatement statement) {
        try {
            statement.close();
        } catch (Exception e) {
            log.error("Failed to close prepared statement", e);
        }
    }

    @Override
    public void closeResults(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) {
            log.error("Failed to close the result set");
        }
    }

    public void shutdown() {
        this.hikariDataSource.close();
    }
}
