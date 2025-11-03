package game.creatures;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.CursedAmulet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link Zombie}.
 * Verifies its curse-linked death and item drop behaviour.
 */
class ZombieTest {

    private Zombie zombie;
    private CursedAmulet mockAmulet;
    private GameMap mockMap;
    private Location mockLocation;

    @BeforeEach
    void setUp() {
        mockAmulet = mock(CursedAmulet.class);
        mockMap = mock(GameMap.class);
        mockLocation = mock(Location.class);

        zombie = new Zombie("Rotting Zombie", 'Z', 100, mockAmulet);

        when(mockMap.locationOf(zombie)).thenReturn(mockLocation);
    }

    /**
     * Positive test: unconscious() drops amulet and lifts curse.
     */
    @Test
    void testUnconsciousDropsAmuletAndLiftsCurse() {
        zombie.unconscious(mockMap);

        verify(mockMap, times(1)).locationOf(zombie);
        verify(mockLocation, times(1)).addItem(mockAmulet);
        verify(mockAmulet, times(1)).liftCurse();
        verify(mockMap, times(1)).removeActor(zombie);
    }

    /**
     * Positive test: overloaded unconscious(Actor, GameMap) also drops and lifts.
     */
    @Test
    void testUnconsciousWithActorDropsAndLifts() {
        zombie.unconscious(null, mockMap);

        verify(mockMap, times(1)).locationOf(zombie);
        verify(mockLocation, times(1)).addItem(mockAmulet);
        verify(mockAmulet, times(1)).liftCurse();
        verify(mockMap, times(1)).removeActor(zombie);
    }

    /**
     * Negative test: if linkedAmulet is null, nothing should be added or lifted.
     */
    @Test
    void testUnconsciousWithoutAmuletDoesNothing() {
        Zombie plainZombie = new Zombie("Walker", 'Z', 80, null);
        when(mockMap.locationOf(plainZombie)).thenReturn(mockLocation);

        plainZombie.unconscious(mockMap);

        verify(mockLocation, never()).addItem(any());
        verify(mockMap, times(1)).removeActor(plainZombie);
    }
}
