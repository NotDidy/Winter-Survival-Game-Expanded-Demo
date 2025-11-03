package game.library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Reusable class for sending HTTP requests.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public abstract class ApiCaller {
    /**
     *
     * @param urlString the URL of the API endpoint to send the POST request to
     * @param payload the JSON-formatted data to be sent in the request body
     * @return the full response returned by the API as a string
     * @throws Exception if a network or I/O error occurs while sending the request or reading the response
     */
    protected String sendPost(String urlString, String payload) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    /**
     * Sends an HTTP GET request to a specified URL and retrieves the response as a string.
     *
     * @param urlString the full URL of the API endpoint
     * @return the response content returned by the server
     * @throws Exception if an I/O or connection error occurs
     */
    protected String sendGet(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}