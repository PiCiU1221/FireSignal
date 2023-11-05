package firesignal.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApiUtils {

    public static String performHttpPost(String url, String jsonPayload) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            // Set the content type header explicitly
            httpPost.setHeader("Content-Type", "application/json");

            String token = SessionManager.getToken();

            if (token != null && token.isEmpty()) {
                return null;
            }

            // Set the authorization header with the token
            httpPost.setHeader("Authorization", "Bearer " + token);

            StringEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            // Execute the request and get the response
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // Handle the response
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Post without Authorization header (just for verification)
    public static String performVerificationHttpPost(String url, String jsonPayload) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            // Set the content type header explicitly
            httpPost.setHeader("Content-Type", "application/json");

            StringEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            // Execute the request and get the response
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // Handle the response
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String performHttpPatch(String url, String jsonPayload) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPatch httpPatch = new HttpPatch(url); // Use HttpPatch for PATCH requests

            httpPatch.setHeader("Content-Type", "application/json");
            String token = SessionManager.getToken();

            if (token == null || token.isEmpty()) {
                return null; // Handle missing token scenario
            }

            // Set the authorization header with the token
            httpPatch.setHeader("Authorization", "Bearer " + token);

            StringEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
            httpPatch.setEntity(entity);

            // Execute the request and get the response
            CloseableHttpResponse response = httpClient.execute(httpPatch);

            // Handle the response
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle exceptions or errors during the request
        }
    }
}
