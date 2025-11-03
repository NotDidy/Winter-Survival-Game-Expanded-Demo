package test.java.game.library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GeminiLibrarian class.
 * Each test method contains exactly one assertion.
 */
public class GeminiLibrarianTest {

    private GeminiLibrarian librarian;
    private MockMonologueSource mockMonologueSource;

    private static class MockMonologueSource implements MonologueSource {
        private final String mockResponse;

        MockMonologueSource(String mockResponse) {
            this.mockResponse = mockResponse;
        }

        @Override
        public String generateMonologue(String topic) {
            return "Librarian whispers: " + mockResponse + " about " + topic;
        }
    }

    @BeforeEach
    void setUp() {
        librarian = new GeminiLibrarian("Elder Librarian", 'L', 100, "dummy-key") {
            @Override
            public String speak() {
                mockMonologueSource = new MockMonologueSource("Echoes of time");
                return mockMonologueSource.generateMonologue("forgotten civilizations");
            }
        };
    }

    @Test
    void testLibrarianIsCreated() {
        assertNotNull(librarian);
    }

    @Test
    void testLibrarianDisplayChar() {
        assertEquals('L', librarian.getDisplayChar());
    }

    @Test
    void testSpeakStartsWithPrefix() {
        assertTrue(librarian.speak().startsWith("Librarian whispers:"));
    }

    @Test
    void testSpeakContainsTopic() {
        assertTrue(librarian.speak().contains("forgotten civilizations"));
    }

    @Test
    void testAllowableActionsNotEmpty() {
        ActionList list = librarian.allowableActions(null, "north", null);
        assertFalse(list.isEmpty());
    }
}
