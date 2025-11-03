package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.behaviours.SaplingGrowthBehaviour;
import game.items.Apple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link WildAppleSapling}.
 * <p>
 * This class ensures:
 * <ul>
 *     <li>Fruit production occurs according to the 30% chance per adjacent location.</li>
 *     <li>Transformation into a WildAppleTree occurs correctly according to biome.</li>
 *     <li>Tick behaviour triggers age increment, fruit production, and growth through the provided behaviour.</li>
 * </ul>
 *
 * All tests are deterministic, use mocks/stubs for external dependencies, and cover
 * positive, negative, and edge cases.
 * Typical usage: attach this behaviour to a sapling to manage its lifecycle
 * and optional early fruit production.
 * @author Wern
 * @version 1.1.0
 * @since 1.1.0
 */
class WildAppleSaplingTest {

    private Location mockLocation;
    private SaplingGrowthBehaviour<AbstractSapling> mockBehaviour;

    // Custom deterministic sapling for controlling randomness in produceFruit
    private static class TestableWildAppleSapling extends WildAppleSapling {
        private final double[] randomValues;
        private int index = 0;

        TestableWildAppleSapling(String biome, SaplingGrowthBehaviour<? super AbstractSapling> behaviour, double... randomValues) {
            super(biome, behaviour);
            this.randomValues = randomValues;
        }

        @Override
        protected double random() {
            if (index >= randomValues.length) return 0.0;
            return randomValues[index++];
        }
    }

    @BeforeEach
    void setUp() {
        mockLocation = mock(Location.class);
        mockBehaviour = mock(SaplingGrowthBehaviour.class);
    }

    /**
     * Positive test: tick should delegate to behaviour tick.
     */
    @Test
    void tick_shouldCallBehaviourTick() {
        WildAppleSapling sapling = new WildAppleSapling("Forest", mockBehaviour);
        sapling.tick(mockLocation);

        verify(mockBehaviour).tick(mockLocation, sapling);
    }

    /**
     * Positive test: growInto should replace sapling with WildAppleTree of same biome.
     */
    @Test
    void growInto_shouldSetWildAppleTreeAtLocation() {
        WildAppleSapling sapling = new WildAppleSapling("Forest", mockBehaviour);
        sapling.growInto(mockLocation, "Forest");

        verify(mockLocation).setGround(any(WildAppleTree.class));
    }

    /**
     * Positive test: produceFruit should add apples to exits deterministically
     * based on overridden random values.
     */
    @Test
    void produceFruit_shouldAddAppleToEligibleExits() {
        Exit mockExit1 = mock(Exit.class);
        Exit mockExit2 = mock(Exit.class);
        Location dest1 = mock(Location.class);
        Location dest2 = mock(Location.class);

        when(mockExit1.getDestination()).thenReturn(dest1);
        when(mockExit2.getDestination()).thenReturn(dest2);
        when(mockLocation.getExits()).thenReturn(List.of(mockExit1, mockExit2));

        // deterministic: first >=0.7 -> add apple, second <0.7 -> no apple
        TestableWildAppleSapling sapling = new TestableWildAppleSapling("Forest", mockBehaviour, 0.8, 0.6);

        sapling.produceFruit(mockLocation);

        verify(dest1).addItem(any(Apple.class));
        verify(dest2, never()).addItem(any());
    }

    /**
     * Edge test: no exits -> produceFruit should not throw exception.
     */
    @Test
    void produceFruit_withNoExits_shouldNotFail() {
        when(mockLocation.getExits()).thenReturn(List.of());
        TestableWildAppleSapling sapling = new TestableWildAppleSapling("Forest", mockBehaviour, 0.8);

        sapling.produceFruit(mockLocation);
        // No exception thrown, nothing to verify
    }

    /**
     * Typical test: multiple ticks should eventually grow via behaviour.
     */
    @Test
    void multipleTicks_shouldDelegateBehaviourEachTime() {
        WildAppleSapling sapling = new WildAppleSapling("Forest", mockBehaviour);

        for (int i = 0; i < 5; i++) {
            sapling.tick(mockLocation);
        }

        verify(mockBehaviour, times(5)).tick(mockLocation, sapling);
    }
}
