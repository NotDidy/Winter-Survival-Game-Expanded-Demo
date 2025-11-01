package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSapling;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GenericSaplingBehaviour}.
 * Handles the generic growth behaviour for any tree sapling.
 * This behaviour is responsible for:
 * <ul>
 *     <li>Incrementing the sapling's age each game tick.</li>
 *     <li>Optionally producing fruit at the sapling stage according to a fruit interval.</li>
 *     <li>Attempting to transition the sapling into its next growth stage once
 *         its age exceeds the configured growth duration, using a configurable probability.</li>
 * </ul>
 *
 * This class is biome-agnostic: any rules specific to Forest, Plains, or
 * other maps are handled by the sapling subclasses during the growInto() call.

 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
class GenericSaplingBehaviourTest {

    private Location mockLocation;
    private AbstractSapling mockSapling;

    @BeforeEach
    void setUp() {
        mockLocation = mock(Location.class);
        mockSapling = mock(AbstractSapling.class);
        when(mockSapling.getBiome()).thenReturn("Forest");
    }

    /**
     * Positive test: sapling should increment age and produce fruit when enabled
     * and age is a multiple of fruitInterval.
     */
    @Test
    void tick_shouldIncrementAgeAndProduceFruit_whenFruitEnabledAndIntervalMatches() {
        GenericSaplingBehaviour behaviour = new GenericSaplingBehaviour(5, true, 2, 1.0);
        when(mockSapling.getAge()).thenReturn(2); // multiple of fruitInterval

        behaviour.tick(mockLocation, mockSapling);

        InOrder order = inOrder(mockSapling);
        order.verify(mockSapling).incrementAge();
        order.verify(mockSapling).produceFruit(mockLocation);
        verify(mockSapling, never()).growInto(any(), any());
    }

    /**
     * Positive test: sapling should attempt to grow into a tree after reaching
     * growthDuration and growthChance allows it.
     */
    @Test
    void tick_shouldGrowIntoTree_whenAgeMeetsGrowthDurationAndChanceSucceeds() {
        GenericSaplingBehaviour behaviour = new GenericSaplingBehaviour(3, false, 2, 1.0);
        when(mockSapling.getAge()).thenReturn(3);

        behaviour.tick(mockLocation, mockSapling);

        verify(mockSapling).growInto(mockLocation, "Forest");
    }

    /**
     * Negative test: sapling should not produce fruit if producesFruit is false.
     */
    @Test
    void tick_shouldNotProduceFruit_whenFruitDisabled() {
        GenericSaplingBehaviour behaviour = new GenericSaplingBehaviour(5, false, 2, 1.0);
        when(mockSapling.getAge()).thenReturn(4);

        behaviour.tick(mockLocation, mockSapling);

        verify(mockSapling).incrementAge();
        verify(mockSapling, never()).produceFruit(any());
        verify(mockSapling, never()).growInto(any(), any());
    }

    /**
     * Edge case: age is exactly at growthDuration and growthChance is 0
     * → sapling should not grow.
     */
    @Test
    void tick_shouldNotGrow_whenGrowthChanceZero() {
        GenericSaplingBehaviour behaviour = new GenericSaplingBehaviour(3, true, 1, 0.0);
        when(mockSapling.getAge()).thenReturn(3);

        behaviour.tick(mockLocation, mockSapling);

        verify(mockSapling, never()).growInto(any(), any());
        verify(mockSapling).produceFruit(mockLocation);
        verify(mockSapling).incrementAge();
    }

    /**
     * Edge case: fruit interval is 1 (produces every turn)
     */
    @Test
    void tick_shouldProduceFruitEveryTurn_whenFruitIntervalIsOne() {
        GenericSaplingBehaviour behaviour = new GenericSaplingBehaviour(5, true, 1, 1.0);
        when(mockSapling.getAge()).thenReturn(7);

        behaviour.tick(mockLocation, mockSapling);

        verify(mockSapling).produceFruit(mockLocation);
        verify(mockSapling).incrementAge();
    }

    /**
     * Edge case: negative growth duration should still allow growth immediately.
     */
    @Test
    void tick_shouldGrowImmediately_whenGrowthDurationNegative() {
        GenericSaplingBehaviour behaviour = new GenericSaplingBehaviour(-1, true, 2, 1.0);
        when(mockSapling.getAge()).thenReturn(0);

        behaviour.tick(mockLocation, mockSapling);

        verify(mockSapling).growInto(mockLocation, "Forest");
    }
}
