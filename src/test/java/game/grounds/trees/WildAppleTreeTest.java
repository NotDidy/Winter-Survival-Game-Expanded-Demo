package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link WildAppleTree}.
 * <p>
 * This class ensures:
 * <ul>
 *     <li>Fruit production occurs according to the 30% chance per adjacent location.</li>
 *     <li>Edge cases with no exits are handled gracefully without exceptions.</li>
 * </ul>
 *
 * All tests are deterministic, use mocks/stubs for external dependencies, and cover
 * positive, negative, and edge cases.
 * Typical usage: attach this tree to a location to manage its fruiting behaviour.
 *
 * @author Wern Zhong Lei
 * @version 1.1.0
 * @since 1.1.0
 */
class WildAppleTreeTest {

    private Location mockLocation;

    // Custom subclass to make produceFruit deterministic
    private static class TestableWildAppleTree extends WildAppleTree {
        private final double[] randomValues;
        private int index = 0;

        TestableWildAppleTree(String mapName, double... randomValues) {
            super(mapName);
            this.randomValues = randomValues;
        }

        @Override
        protected double random() {
            if (index >= randomValues.length) return 0.0; // default
            return randomValues[index++];
        }
    }

    @BeforeEach
    void setUp() {
        mockLocation = mock(Location.class);
    }

    /**
     * Positive test: produceFruit adds apples to eligible exits deterministically.
     */
    @Test
    void produceFruit_shouldAddAppleToEligibleExits() {
        Exit exit1 = mock(Exit.class);
        Exit exit2 = mock(Exit.class);
        Location dest1 = mock(Location.class);
        Location dest2 = mock(Location.class);

        when(exit1.getDestination()).thenReturn(dest1);
        when(exit2.getDestination()).thenReturn(dest2);
        when(mockLocation.getExits()).thenReturn(List.of(exit1, exit2));

        // deterministic: first call >=0.7 -> add apple, second <0.7 -> no apple
        TestableWildAppleTree tree = new TestableWildAppleTree("Forest", 0.8, 0.6);

        tree.produceFruit(mockLocation);

        verify(dest1).addItem(any(Apple.class));
        verify(dest2, never()).addItem(any());
    }

    /**
     * Edge test: produceFruit with no exits should not throw exception.
     */
    @Test
    void produceFruit_withNoExits_shouldNotFail() {
        when(mockLocation.getExits()).thenReturn(List.of());
        TestableWildAppleTree tree = new TestableWildAppleTree("Forest", 0.8);
        tree.produceFruit(mockLocation);
        // No exception thrown
    }

    /**
     * Typical test: produceFruit with multiple exits and multiple calls.
     */
    @Test
    void produceFruit_multipleExitsMultipleCalls_shouldProduceCorrectly() {
        Exit exit1 = mock(Exit.class);
        Exit exit2 = mock(Exit.class);
        Exit exit3 = mock(Exit.class);
        Location dest1 = mock(Location.class);
        Location dest2 = mock(Location.class);
        Location dest3 = mock(Location.class);

        when(exit1.getDestination()).thenReturn(dest1);
        when(exit2.getDestination()).thenReturn(dest2);
        when(exit3.getDestination()).thenReturn(dest3);
        when(mockLocation.getExits()).thenReturn(List.of(exit1, exit2, exit3));

        // deterministic values: only 0.75 triggers apple
        TestableWildAppleTree tree = new TestableWildAppleTree("Forest", 0.75, 0.6, 0.8);

        tree.produceFruit(mockLocation); // first call
        tree.produceFruit(mockLocation); // second call

        // First produceFruit call:
        verify(dest1).addItem(any(Apple.class)); // 0.75 triggers
        verify(dest2, never()).addItem(any());   // 0.6 no apple
        verify(dest3).addItem(any(Apple.class)); // 0.8 triggers

        // Second call: index exceeded, defaults to 0.0 -> no apple
        verify(dest1, times(1)).addItem(any());
        verify(dest2, times(0)).addItem(any());
        verify(dest3, times(1)).addItem(any());
    }
}
