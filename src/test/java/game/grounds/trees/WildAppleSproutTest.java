package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.behaviours.SproutGrowthBehaviour;
import game.items.Apple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link WildAppleSprout}.
 * <p>
 * This class ensures:
 * <ul>
 *     <li>Fruit production occurs deterministically according to the 30% chance per adjacent location.</li>
 *     <li>Growth into WildAppleSapling or WildAppleTree occurs correctly based on biome rules.</li>
 *     <li>Tick behaviour triggers age increment, fruit production, and growth through the provided behaviour.</li>
 * </ul>
 *
 * All tests are deterministic, use mocks/stubs for external dependencies, and cover
 * positive, negative, and edge cases.
 * Typical usage: attach this behaviour to a sprout to manage its lifecycle
 * and optional early fruit production.
 * @author Wern
 * @version 1.1.0
 * @since 1.1.0
 */
class WildAppleSproutTest {

    private Location mockLocation;
    private SproutGrowthBehaviour<AbstractSprout> mockBehaviour;

    // Custom subclass to make produceFruit deterministic
    private static class TestableWildAppleSprout extends WildAppleSprout {
        private final double[] randomValues;
        private int index = 0;

        TestableWildAppleSprout(String biome, SproutGrowthBehaviour<? super AbstractSprout> behaviour, double... randomValues) {
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
        mockBehaviour = mock(SproutGrowthBehaviour.class);
    }

    /**
     * Positive test: tick delegates to behaviour tick.
     */
    @Test
    void tick_shouldCallBehaviourTick() {
        WildAppleSprout sprout = new WildAppleSprout("Forest", mockBehaviour);
        sprout.tick(mockLocation);

        verify(mockBehaviour).tick(mockLocation, sprout);
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

        // deterministic: first >=0.7 -> add apple, second <0.7 -> no apple
        TestableWildAppleSprout sprout = new TestableWildAppleSprout("Forest", mockBehaviour, 0.8, 0.6);

        sprout.produceFruit(mockLocation);

        verify(dest1).addItem(any(Apple.class));
        verify(dest2, never()).addItem(any());
    }

    /**
     * Edge test: no exits -> produceFruit should not throw exception.
     */
    @Test
    void produceFruit_withNoExits_shouldNotFail() {
        when(mockLocation.getExits()).thenReturn(List.of());
        TestableWildAppleSprout sprout = new TestableWildAppleSprout("Forest", mockBehaviour, 0.8);
        sprout.produceFruit(mockLocation);
        // No exception thrown
    }

    /**
     * Positive test: growInto in Forest biome should produce WildAppleSapling.
     */
    @Test
    void growInto_forestBiome_shouldProduceSapling() {
        WildAppleSprout sprout = new WildAppleSprout("Forest", mockBehaviour);
        sprout.growInto(mockLocation, "Forest");

        verify(mockLocation).setGround(isA(WildAppleSapling.class));
    }

    /**
     * Positive test: growInto in Plains biome should skip sapling stage.
     */
    @Test
    void growInto_plainsBiome_shouldProduceTreeDirectly() {
        WildAppleSprout sprout = new WildAppleSprout("Plains", mockBehaviour);
        sprout.growInto(mockLocation, "Plains");

        verify(mockLocation).setGround(isA(WildAppleTree.class));
    }

    /**
     * Typical test: multiple ticks call behaviour each time.
     */
    @Test
    void multipleTicks_shouldDelegateBehaviourEachTime() {
        WildAppleSprout sprout = new WildAppleSprout("Forest", mockBehaviour);

        for (int i = 0; i < 5; i++) {
            sprout.tick(mockLocation);
        }

        verify(mockBehaviour, times(5)).tick(mockLocation, sprout);
    }
}
