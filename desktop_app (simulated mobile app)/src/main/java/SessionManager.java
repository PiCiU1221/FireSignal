package main.java;

import java.util.Objects;

public class SessionManager {

    private static String username;
    private static MyFrame myFrame;

    public static void setMyFrame(MyFrame frame) {
        myFrame = frame;
    }

    public static void setLoggedInUser(String username) {
        SessionManager.username = username;
        SSESubscriber.startSseSubscription(username, new MyMessageReceivedCallback());
    }

    public static String getLoggedInUser() {
        return Objects.requireNonNullElse(username, "");
    }

    public static void resetLoggedInUser() {
        username = null;
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
