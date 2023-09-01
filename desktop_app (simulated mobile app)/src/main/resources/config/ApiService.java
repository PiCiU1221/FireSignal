package main.resources.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static main.resources.config.ApiUtils.performHttpPost;

public class ApiService {
    private static final String CONFIG_FILE = "config.properties";
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

    public static boolean validateUser(String username, String password) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return false;
        } else {
            String endpoint = "/api/validate-user";
            String jsonPayload = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            String response = performHttpPost(baseUrl + endpoint, jsonPayload);
            if (response != null) {
                return Boolean.parseBoolean(response);
            } else {
                System.err.println("Error during HTTP request.");
                return false;
            }
        }
    }

    public static String registerUser(String username, String password) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/register-user";
            String jsonPayload = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
            return performHttpPost(baseUrl + endpoint, jsonPayload);
        }
    }

    public static String getFirefighterName(String username) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/get-firefighter-name";
            String jsonPayload = "{\"username\": \"" + username + "\"}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response);

                String firefighterName = responseJson.get("firefighterName").asText();
                return firefighterName;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                // Handle the exception, return a default value, or rethrow as appropriate
                return null;
            }
        }
    }
}
