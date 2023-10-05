package main.resources.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.Alarm;
import main.java.SessionManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static main.resources.config.ApiUtils.performHttpPost;

public class AlarmDisplayApiService {
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

    public static List<Alarm> getAlarmsForFirefighter(int skip, int howMuch) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/get-alarms-for-firefighter";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"username\": \"" + username + "\", \"skip\": " + skip + ", \"howMuch\": " + howMuch + "}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            List<Alarm> alarms = new ArrayList<>();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response);

                if (responseJson.isArray()) {
                    for (JsonNode alarmNode : responseJson) {
                        int alarmId = alarmNode.get("alarmId").asInt();
                        String alarmCity = alarmNode.get("alarmCity").asText();
                        String alarmStreet = alarmNode.get("alarmStreet").asText();
                        String alarmDescription = alarmNode.get("alarmDescription").asText();
                        LocalDateTime dateTime = LocalDateTime.parse(alarmNode.get("dateTime").asText());

                        // Call the function to get consolidated alarm info
                        Alarm consolidatedInfo = getConsolidatedAlarmInfo(alarmId);

                        if (consolidatedInfo != null) {
                            // Create an Alarm object with both sets of information
                            Alarm alarm = new Alarm(alarmId, alarmCity, alarmStreet, alarmDescription, dateTime,
                                    consolidatedInfo.getCount(), consolidatedInfo.isHasAcceptedCommander(),
                                    consolidatedInfo.getAcceptedDriversCount(), consolidatedInfo.getAcceptedFirefightersCount(),
                                    consolidatedInfo.isHasAcceptedTechnicalRescue());

                            alarms.add(alarm);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return alarms;
        }
    }

    private static Alarm getConsolidatedAlarmInfo(int alarmId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/get-consolidated-alarm-info";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"alarmId\": " + alarmId + ", \"firefighterUsername\": \"" + username + "\"}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            try {
                ObjectMapper consolidatedAlarmInfoMapper = new ObjectMapper();
                JsonNode consolidatedAlarmInfoJson = consolidatedAlarmInfoMapper.readTree(response);

                int count = consolidatedAlarmInfoJson.get("count").asInt();
                boolean hasAcceptedCommander = consolidatedAlarmInfoJson.get("hasAcceptedCommander").asBoolean();
                int acceptedDriversCount = consolidatedAlarmInfoJson.get("acceptedDriversCount").asInt();
                int acceptedFirefightersCount = consolidatedAlarmInfoJson.get("acceptedFirefightersCount").asInt();
                boolean hasAcceptedTechnicalRescue = consolidatedAlarmInfoJson.get("hasAcceptedTechnicalRescue").asBoolean();

                return new Alarm(count, hasAcceptedCommander, acceptedDriversCount,
                        acceptedFirefightersCount, hasAcceptedTechnicalRescue);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /*
    public static List<Alarm> getAlarmsForFirefighter(int skip, int howMuch) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/get-alarms-for-firefighter";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"username\": \"" + username + "\", \"skip\": " + skip + ", \"howMuch\": " + howMuch + "}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            List<Alarm> alarms = new ArrayList<>();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response);

                if (responseJson.isArray()) {
                    for (JsonNode alarmNode : responseJson) {
                        int alarmId = alarmNode.get("alarmId").asInt();
                        String alarmCity = alarmNode.get("alarmCity").asText();
                        String alarmStreet = alarmNode.get("alarmStreet").asText();
                        String alarmDescription = alarmNode.get("alarmDescription").asText();
                        LocalDateTime dateTime = LocalDateTime.parse(alarmNode.get("dateTime").asText());

                        Alarm alarm = new Alarm(alarmId, alarmCity, alarmStreet, alarmDescription, dateTime);
                        alarms.add(alarm);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return alarms;
        }
    }

    public static int getAlarmedFirefightersCount(int alarmId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return 0;
        } else {
            String endpoint = "/api/get-alarmed-firefighters-count";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"alarmId\": \"" + alarmId + "\", \"firefighterUsername\": \"" + username + "\"}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response != null) {
                return Integer.parseInt(response);
            } else {
                System.err.println("Error during HTTP request.");
                return 0;
            }
        }
    }

    public static boolean hasAcceptedCommander(int alarmId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return false;
        } else {
            String endpoint = "/api/has-accepted-commander";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"alarmId\": \"" + alarmId + "\", \"firefighterUsername\": \"" + username + "\"}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response != null) {
                return Boolean.parseBoolean(response);
            } else {
                System.err.println("Error during HTTP request.");
                return false;
            }
        }
    }

    public static int getAcceptedDriversCount(int alarmId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return 0;
        } else {
            String endpoint = "/api/get-accepted-drivers-count";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"alarmId\": \"" + alarmId + "\", \"firefighterUsername\": \"" + username + "\"}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response != null) {
                return Integer.parseInt(response);
            } else {
                System.err.println("Error during HTTP request.");
                return 0;
            }
        }
    }

    public static int getAcceptedFirefightersCount(int alarmId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return 0;
        } else {
            String endpoint = "/api/get-accepted-firefighters-count";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"alarmId\": \"" + alarmId + "\", \"firefighterUsername\": \"" + username + "\"}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response != null) {
                return Integer.parseInt(response);
            } else {
                System.err.println("Error during HTTP request.");
                return 0;
            }
        }
    }

    public static boolean hasAcceptedTechnicalRescue(int alarmId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return false;
        } else {
            String endpoint = "/api/has-accepted-technical-rescue";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"alarmId\": \"" + alarmId + "\", \"firefighterUsername\": \"" + username + "\"}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response != null) {
                return Boolean.parseBoolean(response);
            } else {
                System.err.println("Error during HTTP request.");
                return false;
            }
        }
    }*/
}
