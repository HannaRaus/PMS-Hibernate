package ua.goit.jdbc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private HikariDataSource ds;

    public DatabaseConnectionManager(String host, String databaseName, String username, String password) {
        initDataSource(host, databaseName, username, password);
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void initDataSource(String host, String databaseName, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:postgresql://%s/%s", host, databaseName));
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(10_000);
        config.setConnectionTimeout(10_000);
        ds = new HikariDataSource(config);
    }
}
