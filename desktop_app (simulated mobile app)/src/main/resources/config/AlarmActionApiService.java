package main.resources.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static main.resources.config.ApiUtils.performHttpPost;

public class AlarmActionApiService {
    private static final String API_BASE_URL_KEY = "api.baseurl";

    private static String getBaseUrl() {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/config/config.properties")) {
            if (fis != null) {
                properties.load(fis);
                return properties.getProperty(API_BASE_URL_KEY);
            } else {
                System.err.println("Config properties file not found.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean acceptAlarm(int alarmId, int firefighterId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return false;
        } else {
            String endpoint = "/api/accept-alarm"; // Replace with actual accept endpoint
            String jsonPayload = "{\"alarmId\": " + alarmId + ", \"firefighterId\": " + firefighterId + "}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response != null) {
                return Boolean.parseBoolean(response);
            } else {
                System.err.println("Error during HTTP request.");
                return false;
            }
        }
    }

    public static boolean declineAlarm(int alarmId, int firefighterId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return false;
        } else {
            String endpoint = "/api/decline-alarm"; // Replace with actual decline endpoint
            String jsonPayload = "{\"alarmId\": " + alarmId + ", \"firefighterId\": " + firefighterId + "}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response != null) {
                return Boolean.parseBoolean(response);
            } else {
                System.err.println("Error during HTTP request.");
                return false;
            }
        }
    }
}
