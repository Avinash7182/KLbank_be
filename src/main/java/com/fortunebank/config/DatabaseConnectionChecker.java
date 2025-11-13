package com.fortunebank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Component
public class DatabaseConnectionChecker implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String dbName = connection.getCatalog();
            System.out.println("✅ Connected to Database: " + dbName);
            System.out.println("🗄️  JDBC URL: " + metaData.getURL());
            System.out.println("👤 User: " + metaData.getUserName());
            System.out.println("💾 Driver: " + metaData.getDriverName());
        } catch (Exception e) {
            System.err.println("❌ Failed to connect to database: " + e.getMessage());
        }
    }
}
