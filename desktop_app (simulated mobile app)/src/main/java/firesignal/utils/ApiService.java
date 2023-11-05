package firesignal.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static firesignal.utils.ApiUtils.performHttpPost;
import static firesignal.utils.ApiUtils.performVerificationHttpPost;

public class ApiService {
    static String baseUrl = ConfigReader.getProperty("api.baseurl");

    public static String validateUser(String username, String password) {
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/auth/authenticate";
            String jsonPayload = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            String response = performVerificationHttpPost(baseUrl + endpoint, jsonPayload);
            if (response != null) {
                return response;
            } else {
                System.err.println("Error during HTTP request.");
                return null;
            }
        }
    }

    public static void registerUser(String username, String password) {
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
        } else {
            String endpoint = "/api/user/register-user";
            String jsonPayload = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
            performHttpPost(baseUrl + endpoint, jsonPayload);
        }
    }

    public static String getFirefighterName(String username) {
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/user/get-firefighter-name";
            String jsonPayload = "{\"username\": \"" + username + "\"}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response == null) {
                return null;
            }

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response);

                if (responseJson.get("firefighterName") != null) {
                    return responseJson.get("firefighterName").asText();
                } else {
                    return null;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                // Handle the exception, return a default value, or rethrow as appropriate
                return null;
            }
        }
    }
}
