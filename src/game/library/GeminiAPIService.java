package game.library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Handles communication with the Gemini API.
 */
public class GeminiAPIService {

    private static final String API_KEY = ""; // 🔑 Replace with your Gemini API key
    private static final String REQUEST_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public String generateResponse(String prompt) {
        String result = "";
        try {
            URL url = new URL(REQUEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"contents\": [{\"parts\": [{\"text\": \"" + prompt + "\"}]}]}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = connection.getResponseCode();
            System.out.println("Response Code: " + code);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    if (responseLine.trim().startsWith("\"text\": ")) {
                        result = responseLine.trim().substring(9, responseLine.trim().length() - 1);
                    }
                }
            }

        } catch (Exception e) {
            return "Gemini is offline at the moment. I cannot respond.";
        }

        if (result == null || result.isEmpty()) {
            result = "I am not sure about that trade...";
        }

        return result;
    }
}
