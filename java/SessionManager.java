package main.java;

public class SessionManager {
    private static String username;

    public static void setLoggedInUser(String username) {
        SessionManager.username = username;
    }

    public static String getLoggedInUser() {
        return username;
    }

    public static void resetLoggedInUser() {
        username = null;
    }

}

