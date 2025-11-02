package game.apiintegration;

/**
 * Uses Gemini API to generate monologues (built-in Java HTTP only).
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class GeminiMonologueSource extends ApiCaller implements MonologueSource {

    private final String apiKey;
    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateText";

    public GeminiMonologueSource(String apiKey) {
        this.apiKey = "";
    }

    @Override
    public String generateMonologue(String topic) {
        try {
            String payload = String.format(
                    "{\"contents\":[{\"parts\":[{\"text\":\"Give a short mysterious monologue about %s.\"}]}]}",
                    topic
            );

            String response = sendPost(apiUrl + "?key=" + apiKey, payload);

            if (response.length() > 200) {
                response = response.substring(0, 200) + "...";
            }
            return "Librarian whispers: " + response;
        } catch (Exception e) {
            return "The Librarian cannot be reached (API unreachable)";
        }
    }
}
