package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.behaviours.SaplingGrowthBehaviour;
import game.items.YewBerry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link YewBerrySapling}.
 * <p>
 * This test suite verifies the lifecycle logic of the {@code YewBerrySapling},
 * covering both its probabilistic growth and fruit production mechanisms.
 * Tests are deterministic by using a test subclass that overrides {@link YewBerrySapling#random()}
 * to ensure predictable outcomes instead of relying on {@link Math#random()}.
 * </p>
 *
 * <h3>Responsibilities Tested</h3>
 * <ul>
 *   <li>Growing into {@link YewBerryTree} when random &lt; 0.5</li>
 *   <li>Remaining as a sapling when random ≥ 0.5</li>
 *   <li>Producing {@link YewBerry} items with probability ≥ 0.7 for each exit</li>
 *   <li>Handling zero-exit and multiple-exit edge cases safely</li>
 * </ul>
 *
 * <p>
 * These tests are self-contained, reproducible, and require no network or timing dependencies.
 * </p>
 *
 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
public class YewBerrySaplingTest {

    private Location mockLocation;
    private SaplingGrowthBehaviour<AbstractSapling> mockBehaviour;

    /**
     * A deterministic subclass of {@link YewBerrySapling} that overrides {@link #random()}
     * to produce a fixed value. This makes growth and fruiting outcomes predictable.
     */
    static class TestableYewBerrySapling extends YewBerrySapling {
        private final double fixedRandom;

        public TestableYewBerrySapling(String biome,
                                       SaplingGrowthBehaviour<? super AbstractSapling> behaviour,
                                       double fixedRandom) {
            super(biome, behaviour, fixedRandom);
            this.fixedRandom = fixedRandom;
        }

        @Override
        protected double random() {
            return fixedRandom;
        }
    }

    @BeforeEach
    void setUp() {
        mockLocation = mock(Location.class);
        mockBehaviour = mock(SaplingGrowthBehaviour.class);
    }

    // -------------------------------------------------------------
    // GROWTH TESTS
    // -------------------------------------------------------------

    /**
     * Typical Case — Ensures the sapling grows into a {@link YewBerryTree}
     * when the random value is less than 0.5.
     */
    @Test
    @DisplayName("Should grow into YewBerryTree when random < 0.5")
    void testGrowInto_SuccessfulGrowth() {
        YewBerrySapling sapling = new TestableYewBerrySapling("Forest", mockBehaviour, 0.3);

        sapling.growInto(mockLocation, "Forest");

        verify(mockLocation, times(1)).setGround(any(YewBerryTree.class));
    }

    /**
     * Negative Case — Ensures the sapling does NOT grow when
     * the random value is 0.5 or higher.
     */
    @Test
    @DisplayName("Should not grow when random >= 0.5")
    void testGrowInto_NoGrowth() {
        YewBerrySapling sapling = new TestableYewBerrySapling("Forest", mockBehaviour, 0.8);

        sapling.growInto(mockLocation, "Forest");

        verify(mockLocation, never()).setGround(any());
    }

    /**
     * Edge Case — Ensures that exactly 0.5 is treated as “no growth”.
     */
    @Test
    @DisplayName("Edge case: random == 0.5 should not grow")
    void testGrowInto_EdgeCase_NoGrowth() {
        YewBerrySapling sapling = new TestableYewBerrySapling("Plains", mockBehaviour, 0.5);

        sapling.growInto(mockLocation, "Plains");

        verify(mockLocation, never()).setGround(any());
    }

    // -------------------------------------------------------------
    // FRUIT PRODUCTION TESTS
    // -------------------------------------------------------------

    /**
     * Typical Case — Ensures a {@link YewBerry} is produced when random >= 0.7.
     */
    @Test
    @DisplayName("Should produce fruit when random >= 0.7")
    void testProduceFruit_SuccessfulProduction() {
        Location mockDest = mock(Location.class);
        Exit mockExit = mock(Exit.class);
        when(mockExit.getDestination()).thenReturn(mockDest);

        Location mockLoc = mock(Location.class);
        when(mockLoc.getExits()).thenReturn(List.of(mockExit));

        // Spy to override Math.random() inside produceFruit
        YewBerrySapling sapling = spy(new TestableYewBerrySapling("Plains", mockBehaviour, 0.3));
        doReturn(0.8).when(sapling).random(); // simulate a high random value

        sapling.produceFruit(mockLoc);

        verify(mockDest, times(1)).addItem(any(YewBerry.class));
    }

    /**
     * Negative Case — Ensures no fruit is produced when random < 0.7.
     */
    @Test
    @DisplayName("Should not produce fruit when random < 0.7")
    void testProduceFruit_NoProduction() {
        Location mockDest = mock(Location.class);
        Exit mockExit = mock(Exit.class);
        when(mockExit.getDestination()).thenReturn(mockDest);

        Location mockLoc = mock(Location.class);
        when(mockLoc.getExits()).thenReturn(List.of(mockExit));

        YewBerrySapling sapling = spy(new TestableYewBerrySapling("Plains", mockBehaviour, 0.3));
        doReturn(0.5).when(sapling).random(); // low value → no fruit

        sapling.produceFruit(mockLoc);

        verify(mockDest, never()).addItem(any());
    }

    /**
     * Edge Case — Ensures no exception occurs when there are no exits.
     */
    @Test
    @DisplayName("Should safely handle locations with no exits")
    void testProduceFruit_NoExits() {
        Location mockLoc = mock(Location.class);
        when(mockLoc.getExits()).thenReturn(List.of());

        YewBerrySapling sapling = new TestableYewBerrySapling("Plains", mockBehaviour, 0.3);

        sapling.produceFruit(mockLoc);

        // Verify no interaction since there are no exits
        verify(mockLoc, never()).addItem(any());
    }
}
