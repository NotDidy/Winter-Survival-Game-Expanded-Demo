package game.trading;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * It is used for fetching data from HTTP APIs
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.3.0
 * @since 1.3.0
 */
public abstract class ApiPriceFetcher {
    /**
     * Sends a simple HTTP GET request to a specified URL and returns the response as a string
     *
     * @param urlString the URL endpoint to which the GET request is sent
     * @return response from the API as a string
     * @throws Exception if an I/O or network error occurs while sending or
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
