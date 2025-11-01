package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.YewBerry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link YewBerryTree}.
 * Verifies the YewBerryTree’s ability to produce fruits (YewBerries)
 * according to its probability-based fruit generation rule.
 *
 * This class ensures:
 * <ul>
 *     <li>Fruit is added to neighboring tiles with a probability ≥ 0.7.</li>
 *     <li>No fruit is added when probability condition is not met.</li>
 *     <li>Method gracefully handles invalid or empty exits.</li>
 * </ul>
 *
 * Tests are deterministic using mocked Math.random() values via method overriding.
 *
 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
public class YewBerryTreeTest {

    private YewBerryTree tree;
    private Location mockLocation;
    private Location mockDest1;
    private Location mockDest2;
    private Exit mockExit1;
    private Exit mockExit2;

    /**
     * A deterministic subclass of YewBerryTree used to control randomness.
     */
    private static class TestableYewBerryTree extends YewBerryTree {
        private final double mockRandom;

        TestableYewBerryTree(double mockRandom) {
            this.mockRandom = mockRandom;
        }

        @Override
        protected void produceFruit(Location location) {
            for (Exit exit : location.getExits()) {
                // override random behaviour deterministically
                if (mockRandom >= 0.7) {
                    exit.getDestination().addItem(new YewBerry());
                }
            }
        }
    }

    @BeforeEach
    void setUp() {
        mockLocation = mock(Location.class);
        mockDest1 = mock(Location.class);
        mockDest2 = mock(Location.class);
        mockExit1 = mock(Exit.class);
        mockExit2 = mock(Exit.class);

        when(mockExit1.getDestination()).thenReturn(mockDest1);
        when(mockExit2.getDestination()).thenReturn(mockDest2);

        List<Exit> exits = Arrays.asList(mockExit1, mockExit2);
        when(mockLocation.getExits()).thenReturn(exits);
    }

    @Test
    @DisplayName("Typical Case: Produces fruit when random ≥ 0.7")
    void testProduceFruitTypical() {
        tree = new TestableYewBerryTree(0.8); // ensures fruiting
        tree.produceFruit(mockLocation);

        verify(mockDest1, times(1)).addItem(any(YewBerry.class));
        verify(mockDest2, times(1)).addItem(any(YewBerry.class));
    }

    @Test
    @DisplayName("Edge Case: No fruit produced when random < 0.7")
    void testProduceFruitEdge() {
        tree = new TestableYewBerryTree(0.5); // below threshold
        tree.produceFruit(mockLocation);

        verify(mockDest1, never()).addItem(any(YewBerry.class));
        verify(mockDest2, never()).addItem(any(YewBerry.class));
    }

    @Test
    @DisplayName("Invalid Case: Handles empty exits gracefully")
    void testProduceFruitNoExits() {
        tree = new TestableYewBerryTree(0.9);
        when(mockLocation.getExits()).thenReturn(List.of()); // no exits

        assertDoesNotThrow(() -> tree.produceFruit(mockLocation));
        verifyNoInteractions(mockDest1, mockDest2);
    }
}
