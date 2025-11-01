package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSprout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link GenericSproutBehaviour}.
 * Handles the generic growth behaviour for any tree sprout.
 * This behaviour is responsible for:
 * <ul>
 *     <li>Incrementing the sprout's age each game tick.</li>
 *     <li>Optionally producing fruit at the sprout stage, if enabled.</li>
 *     <li>Transitioning the sprout into its next growth stage once its age
 *         exceeds the configured growth duration, passing along the biome
 *         information for biome-specific growth logic.</li>
 * </ul>
 *
 * This class is biome-agnostic: any rules specific to Forest, Plains, or
 * other maps are handled by the sprout subclasses during the growInto() call.

 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
class GenericSproutBehaviourTest {

    private Location mockLocation;
    private AbstractSprout mockSprout;

    @BeforeEach
    void setUp() {
        mockLocation = mock(Location.class);
        mockSprout = mock(AbstractSprout.class);
        when(mockSprout.getBiome()).thenReturn("Forest");
    }

    // --- Positive test: fruit enabled, within duration ---
    @Test
    void tick_shouldIncrementAgeAndProduceFruit_beforeGrowthLimit() {
        GenericSproutBehaviour behaviour = new GenericSproutBehaviour(3, true);
        when(mockSprout.getAge()).thenReturn(2); // before growth threshold

        behaviour.tick(mockLocation, mockSprout);

        InOrder order = inOrder(mockSprout);
        order.verify(mockSprout).incrementAge();
        order.verify(mockSprout).produceFruit(mockLocation);
        verify(mockSprout, never()).growInto(any(), any());
    }

    // --- Positive test: triggers growth after duration exceeded ---
    @Test
    void tick_shouldGrowIntoNextStage_whenAgeExceedsGrowthDuration() {
        GenericSproutBehaviour behaviour = new GenericSproutBehaviour(3, true);
        when(mockSprout.getAge()).thenReturn(4); // already beyond growth duration

        behaviour.tick(mockLocation, mockSprout);

        verify(mockSprout).growInto(mockLocation, "Forest");
    }

    // --- Negative test: produces no fruit if disabled ---
    @Test
    void tick_shouldNotProduceFruit_whenFruitProductionDisabled() {
        GenericSproutBehaviour behaviour = new GenericSproutBehaviour(3, false);
        when(mockSprout.getAge()).thenReturn(1);

        behaviour.tick(mockLocation, mockSprout);

        verify(mockSprout).incrementAge();
        verify(mockSprout, never()).produceFruit(any());
        verify(mockSprout, never()).growInto(any(), any());
    }

    // --- Edge test: growthDuration == 0 ---
    @Test
    void tick_shouldImmediatelyGrowIntoNextStage_whenGrowthDurationIsZero() {
        GenericSproutBehaviour behaviour = new GenericSproutBehaviour(0, false);
        when(mockSprout.getAge()).thenReturn(1); // any positive age > 0

        behaviour.tick(mockLocation, mockSprout);

        verify(mockSprout).growInto(mockLocation, "Forest");
    }

    // --- Edge test: negative growth duration (invalid config) ---
    @Test
    void tick_shouldAlwaysGrowIfGrowthDurationNegative() {
        GenericSproutBehaviour behaviour = new GenericSproutBehaviour(-1, true);
        when(mockSprout.getAge()).thenReturn(0);

        behaviour.tick(mockLocation, mockSprout);

        verify(mockSprout).growInto(mockLocation, "Forest");
    }
}
