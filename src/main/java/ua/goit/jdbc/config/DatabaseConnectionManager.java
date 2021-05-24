package ua.goit.jdbc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ua.goit.jdbc.servises.util.PropertiesLoader;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private HikariDataSource ds;

    public DatabaseConnectionManager(PropertiesLoader propertiesLoader) {
        initDataSource(propertiesLoader);
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void initDataSource(PropertiesLoader propertiesLoader) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:postgresql://%s/%s", propertiesLoader.getProperty("host"),
                propertiesLoader.getProperty("database.name")));
        config.setUsername( propertiesLoader.getProperty("username"));
        config.setPassword(propertiesLoader.getProperty("password"));
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(10_000);
        config.setConnectionTimeout(10_000);
        ds = new HikariDataSource(config);
    }
}
