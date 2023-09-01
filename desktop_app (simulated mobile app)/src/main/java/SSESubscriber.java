package main.java;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SSESubscriber {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void startSseSubscription(String username, MessageReceivedCallback callback) {
        executorService.execute(() -> {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://localhost:8080/api/subscribe/" + username);
            request.addHeader("Accept", MediaType.TEXT_EVENT_STREAM_VALUE);

            try {
                HttpResponse response = httpClient.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) { // Check if the line is not empty after trimming
                        System.out.println("Received SSE message: " + line); // Print received message
                        callback.onMessageReceived(line); // Call the callback method
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface MessageReceivedCallback {
        void onMessageReceived(String message);
    }

    public static void shutdownExecutorService() {
        executorService.shutdown();
    }
}
