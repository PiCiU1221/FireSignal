package main.resources.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {
    private String dbUrl;
    private String username;
    private String password;

    public DatabaseConfig() {
        loadConfig();
    }

    private void loadConfig() {
        try (FileInputStream fis = new FileInputStream("src/main/resources/config/config.properties")) {
            Properties properties = new Properties();
            properties.load(fis);

            dbUrl = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
        }
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
