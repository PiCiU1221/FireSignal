package firesignal.utils;

public class DatabaseConfig {
    private String dbUrl;
    private String username;
    private String password;

    public DatabaseConfig() {
        loadConfig();
    }

    private void loadConfig() {
        dbUrl = ConfigReader.getProperty("db.url");
        username = ConfigReader.getProperty("db.username");
        password = ConfigReader.getProperty("db.password");
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
