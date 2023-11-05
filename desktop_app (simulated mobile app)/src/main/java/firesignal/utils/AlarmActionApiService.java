package firesignal.utils;

public class AlarmActionApiService {
    static String baseUrl = ConfigReader.getProperty("api.baseurl");

    public static void updateAcceptance(int alarmId, int firefighterId, boolean accept) {
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
        } else {
            String endpoint = "/api/alarmed-firefighter/update-acceptance/" + alarmId + "/" + firefighterId;
            String jsonPayload = String.valueOf(accept); // True or False
            String response = ApiUtils.performHttpPatch(baseUrl + endpoint, jsonPayload);

            if (response == null) {
                System.err.println("Error during HTTP request.");
            }
        }
    }

    /*
    public static boolean acceptAlarm(int alarmId, int firefighterId) {
        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            System.err.println("Base URL not found in config.properties");
            return false;
        } else {
            String endpoint = "/api/accept-alarm"; // Replace with actual accept endpoint
            String jsonPayload = "{\"alarmId\": " + alarmId + ", \"firefighterId\": " + firefighterId + "}";
            String response = ApiUtils.performHttpPost(baseUrl + endpoint, jsonPayload);

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
            String response = ApiUtils.performHttpPost(baseUrl + endpoint, jsonPayload);

            if (response != null) {
                return Boolean.parseBoolean(response);
            } else {
                System.err.println("Error during HTTP request.");
                return false;
            }
        }
    }
    */
}
