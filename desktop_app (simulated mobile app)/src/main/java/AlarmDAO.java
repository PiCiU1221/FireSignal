package main.java;

import main.resources.config.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class AlarmDAO {
    /*
    public static List<Alarm> getNewestAlarmsForFirefighter(int skip, int howMuch) {
        List<Alarm> alarms = new ArrayList<>();

        try (Connection connection = DatabaseUtils.getConnection()) {
            String query = "SELECT a.* FROM alarms AS a " +
                    "JOIN alarmed_firefighters AS af ON a.alarm_id = af.alarm_id " +
                    "JOIN firefighters AS f ON af.firefighter_id = f.firefighter_id " +
                    "WHERE f.firefighter_username = ? " +
                    "ORDER BY a.date_time DESC " +
                    "LIMIT ?, ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, SessionManager.getLoggedInUser());
                statement.setInt(2, skip);  // Adjust from index to 0-based index
                statement.setInt(3, howMuch);  // Calculate the limit based on the range

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int alarmId = resultSet.getInt("alarm_id");
                    String city = resultSet.getString("alarm_city");
                    String street = resultSet.getString("alarm_street");
                    String description = resultSet.getString("alarm_description");
                    LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();

                    Alarm alarm = new Alarm(alarmId, city, street, description, dateTime);
                    alarms.add(alarm);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alarms;
    }

    public static int getAlarmedFirefightersCount(int alarm_id) {
        int result = 0;
        try (Connection connection = DatabaseUtils.getConnection()) {
            // Subquery to retrieve the department_id based on the firefighter_username
            String subquery = "SELECT department_id FROM firefighters WHERE firefighter_username = ?";

            // Main query to count the firefighters based on the department_id and alarm_id
            String query = "SELECT COUNT(*) AS firefighter_count " +
                    "FROM alarmed_firefighters af " +
                    "INNER JOIN firefighters f ON af.firefighter_id = f.firefighter_id " +
                    "WHERE f.department_id = (" + subquery + ") AND af.alarm_id = ?";

            try (PreparedStatement queryStatement = connection.prepareStatement(query)) {
                // Set the parameter values for the query
                queryStatement.setString(1, SessionManager.getLoggedInUser());
                queryStatement.setInt(2, alarm_id);

                // Execute the query to count the firefighters
                ResultSet queryResult = queryStatement.executeQuery();
                if (queryResult.next()) {
                    result = queryResult.getInt("firefighter_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int getAcceptedFirefightersCount(int alarm_id) {
        int result = 0;
        try (Connection connection = DatabaseUtils.getConnection()) {
            // Subquery to retrieve the department_id based on the firefighter_username
            String subquery = "SELECT department_id FROM firefighters WHERE firefighter_username = ?";

            // Main query to count the firefighters based on the department_id and alarm_id
            String query = "SELECT COUNT(*) AS firefighter_count " +
                    "FROM alarmed_firefighters af " +
                    "INNER JOIN firefighters f ON af.firefighter_id = f.firefighter_id " +
                    "WHERE f.department_id = (" + subquery + ") AND af.alarm_id = ? AND af.accepted = 1";

            try (PreparedStatement queryStatement = connection.prepareStatement(query)) {
                // Set the parameter values for the query
                queryStatement.setString(1, SessionManager.getLoggedInUser());
                queryStatement.setInt(2, alarm_id);

                // Execute the query to count the firefighters
                ResultSet queryResult = queryStatement.executeQuery();
                if (queryResult.next()) {
                    result = queryResult.getInt("firefighter_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean hasAcceptedCommander(int alarm_id) {
        boolean result = false;
        try (Connection connection = DatabaseUtils.getConnection()) {
            // Subquery to retrieve the department_id based on the firefighter_username
            String subquery = "SELECT department_id FROM firefighters WHERE firefighter_username = ?";

            // Main query to count the firefighters based on the department_id and alarm_id
            String query = "SELECT COUNT(*) AS firefighter_count " +
                    "FROM alarmed_firefighters af " +
                    "INNER JOIN firefighters f ON af.firefighter_id = f.firefighter_id " +
                    "WHERE f.department_id = (" + subquery + ") AND af.alarm_id = ? AND af.accepted = 1 AND f.firefighter_commander = 1";

            try (PreparedStatement queryStatement = connection.prepareStatement(query)) {
                // Set the parameter values for the query
                queryStatement.setString(1, SessionManager.getLoggedInUser());
                queryStatement.setInt(2, alarm_id);

                // Execute the query to count the firefighters
                ResultSet queryResult = queryStatement.executeQuery();
                if (queryResult.next()) {
                    if (queryResult.getInt("firefighter_count") > 0) result = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int getAcceptedDriversCount(int alarm_id) {
        int result = 0;
        try (Connection connection = DatabaseUtils.getConnection()) {
            // Subquery to retrieve the department_id based on the firefighter_username
            String subquery = "SELECT department_id FROM firefighters WHERE firefighter_username = ?";

            // Main query to count the firefighters based on the department_id and alarm_id
            String query = "SELECT COUNT(*) AS firefighter_count " +
                    "FROM alarmed_firefighters af " +
                    "INNER JOIN firefighters f ON af.firefighter_id = f.firefighter_id " +
                    "WHERE f.department_id = (" + subquery + ") AND af.alarm_id = ? AND af.accepted = 1 AND f.firefighter_driver = 1";

            try (PreparedStatement queryStatement = connection.prepareStatement(query)) {
                // Set the parameter values for the query
                queryStatement.setString(1, SessionManager.getLoggedInUser());
                queryStatement.setInt(2, alarm_id);

                // Execute the query to count the firefighters
                ResultSet queryResult = queryStatement.executeQuery();
                if (queryResult.next()) {
                    result = queryResult.getInt("firefighter_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean hasAcceptedTechnicalRescue(int alarm_id) {
        boolean result = false;
        try (Connection connection = DatabaseUtils.getConnection()) {
            // Subquery to retrieve the department_id based on the firefighter_username
            String subquery = "SELECT department_id FROM firefighters WHERE firefighter_username = ?";

            // Main query to count the firefighters based on the department_id and alarm_id
            String query = "SELECT COUNT(*) AS firefighter_count " +
                    "FROM alarmed_firefighters af " +
                    "INNER JOIN firefighters f ON af.firefighter_id = f.firefighter_id " +
                    "WHERE f.department_id = (" + subquery + ") AND af.alarm_id = ? AND af.accepted = 1 AND f.firefighter_technical_rescue = 1";

            try (PreparedStatement queryStatement = connection.prepareStatement(query)) {
                // Set the parameter values for the query
                queryStatement.setString(1, SessionManager.getLoggedInUser());
                queryStatement.setInt(2, alarm_id);

                // Execute the query to count the firefighters
                ResultSet queryResult = queryStatement.executeQuery();
                if (queryResult.next()) {
                    if (queryResult.getInt("firefighter_count") > 0) result = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    */
}
