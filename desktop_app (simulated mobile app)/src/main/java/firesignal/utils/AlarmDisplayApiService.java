package firesignal.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlarmDisplayApiService {
    static String baseUrl = ConfigReader.getProperty("api.baseurl");

    public static List<Alarm> getAlarmsForFirefighter(int skip, int howMuch) {
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/alarm/get-alarms-for-firefighter";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"username\": \"" + username + "\", \"skip\": " + skip + ", \"howMuch\": " + howMuch + "}";
            String response = ApiUtils.performHttpPost(baseUrl + endpoint, jsonPayload);

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
                            // Create an utils.Alarm object with both sets of information
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
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/alarmed-firefighter/get-consolidated-alarm-info";
            String username = SessionManager.getLoggedInUser();
            String jsonPayload = "{\"alarmId\": " + alarmId + ", \"firefighterUsername\": \"" + username + "\"}";
            String response = ApiUtils.performHttpPost(baseUrl + endpoint, jsonPayload);

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
    public static List<utils.Alarm> getAlarmsForFirefighter(int skip, int howMuch) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return null;
        } else {
            String endpoint = "/api/get-alarms-for-firefighter";
            String username = utils.SessionManager.getLoggedInUser();
            String jsonPayload = "{\"username\": \"" + username + "\", \"skip\": " + skip + ", \"howMuch\": " + howMuch + "}";
            String response = performHttpPost(baseUrl + endpoint, jsonPayload);

            List<utils.Alarm> alarms = new ArrayList<>();

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

                        utils.Alarm alarm = new utils.Alarm(alarmId, alarmCity, alarmStreet, alarmDescription, dateTime);
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
            String username = utils.SessionManager.getLoggedInUser();
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
            String username = utils.SessionManager.getLoggedInUser();
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
            String username = utils.SessionManager.getLoggedInUser();
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
            String username = utils.SessionManager.getLoggedInUser();
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
            String username = utils.SessionManager.getLoggedInUser();
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
