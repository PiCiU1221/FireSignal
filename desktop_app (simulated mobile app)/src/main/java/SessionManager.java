package main.java;

import java.util.Objects;

public class SessionManager {

    private static String username;
    private static String token;
    private static MyFrame myFrame;

    public static void setMyFrame(MyFrame frame) {
        myFrame = frame;
    }

    public static void setLoggedInUser(String username, String token) {
        SessionManager.username = username;
        SessionManager.token = token;
        SSESubscriber.startSseSubscription(username, token, new MyMessageReceivedCallback());
    }

    public static String getLoggedInUser() {
        return Objects.requireNonNullElse(username, "");
    }

    public static String getToken() {
        return Objects.requireNonNullElse(token, "");
    }

    public static void resetLoggedInUser() {
        username = null;
        token = null;
    }

    private static class MyMessageReceivedCallback implements SSESubscriber.MessageReceivedCallback {
        @Override
        public void onMessageReceived(String message) {
            if (myFrame != null) {
                myFrame.showMessage(message); // Call showMessage method in MyFrame
            }
        }
    }
}
