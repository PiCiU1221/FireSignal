package firesignal.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static firesignal.utils.ApiUtils.performHttpPost;

public class ApiService {
    static String baseUrl = ConfigReader.getProperty("api.baseurl");

    public static ApiResponse<String> validateUser(String username, String password) {
        try {
            String endpoint = "/api/auth/authenticate";
            String jsonPayload = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                // Parse the JSON body to extract the token
                String responseBody = httpResponse.body();
                String token = parseFieldFromJson(responseBody, "data");

                if (token != null && !token.isEmpty()) {
                    return ApiResponse.success("Login successful", token);
                } else {
                    return ApiResponse.error("Token not found in the response");
                }
            } else {
                // Parse the JSON body to extract the error message
                String responseBody = httpResponse.body();
                String errorMessage = parseFieldFromJson(responseBody, "message");

                if (errorMessage != null && !errorMessage.isEmpty()) {
                    return ApiResponse.error(errorMessage);
                } else {
                    return ApiResponse.error("Invalid username or password");
                }
            }
        } catch (Exception e) {
            System.err.println("Exception during HTTP request: " + e.getMessage());
            return ApiResponse.error("Error during login. Please try again later.");
        }
    }

    public static ApiResponse<String> registerUser(String username, String password) {
        try {
            String endpoint = "/api/user/register";
            String jsonPayload = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 201) {
                // Registration successful
                return ApiResponse.success("Registration successful");
            } else {
                // Parse the JSON body to extract the error message
                String responseBody = httpResponse.body();
                String errorMessage = parseFieldFromJson(responseBody, "message");

                if (errorMessage != null && !errorMessage.isEmpty()) {
                    return ApiResponse.error(errorMessage);
                } else {
                    return ApiResponse.error("Failed to register user");
                }
            }
        } catch (Exception e) {
            System.err.println("Exception during HTTP request: " + e.getMessage());
            return ApiResponse.error("Error during registration. Please try again later.");
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
                // Parse the JSON response and extract the firefighterName
                return parseFieldFromJson(response, "firefighterName");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                // Handle the exception, return a default value, or rethrow as appropriate
                return null;
            }
        }
    }

    // Helper method to parse a string field from JSON
    private static String parseFieldFromJson(String responseBody, String fieldName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseBody);

        JsonNode fieldNode = responseJson.get(fieldName);
        if (fieldNode != null && fieldNode.isTextual()) {
            return fieldNode.asText();
        } else {
            return null;
        }
    }
}
