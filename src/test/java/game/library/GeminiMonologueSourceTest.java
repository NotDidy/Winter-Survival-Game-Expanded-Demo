package game.library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GeminiMonologueSource.
 * Uses one assertion per method for atomic verification.
 */
public class GeminiMonologueSourceTest {

    @Test
    void testFallbackMessageWhenApiFails() {
        GeminiMonologueSource source = new GeminiMonologueSource() {
            @Override
            protected String sendPost(String urlString, String payload) throws Exception {
                throw new RuntimeException("Simulated failure");
            }
        };
        assertEquals("The Librarian cannot be reached (API unreachable)",
                source.generateMonologue("ancient ruins"));
    }

    @Test
    void testMonologueStartsWithWhisperPrefix() {
        GeminiMonologueSource source = new GeminiMonologueSource() {
            @Override
            protected String sendPost(String urlString, String payload) {
                return "Mysterious echoes...";
            }
        };
        assertTrue(source.generateMonologue("time").startsWith("Librarian whispers:"));
    }

    @Test
    void testGeneratedTextShortensWhenLong() {
        GeminiMonologueSource source = new GeminiMonologueSource() {
            @Override
            protected String sendPost(String urlString, String payload) {
                return "x".repeat(300);
            }
        };
        String result = source.generateMonologue("endless night");
        assertTrue(result.length() < 250);
    }
}
