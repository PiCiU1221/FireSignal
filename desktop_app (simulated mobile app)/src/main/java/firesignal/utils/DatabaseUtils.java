package firesignal.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {
    public static Connection getConnection() throws SQLException {
        DatabaseConfig config = new DatabaseConfig();

        String DB_URL = config.getDbUrl();
        String USERNAME = config.getUsername();
        String PASSWORD = config.getPassword();

        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public static boolean validateUserCredentials(String username, String password) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();

                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void registerUser(String username, String password) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("User registered successfully.");
                } else {
                    System.out.println("Failed to register user.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getFirefighterName() {
        try (Connection connection = getConnection()) {
            String query = "SELECT f.firefighter_name FROM firefighters f JOIN users u ON f.firefighter_username = u.username WHERE u.username = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, SessionManager.getLoggedInUser());

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("firefighter_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no name is found for the given username
    }
}
