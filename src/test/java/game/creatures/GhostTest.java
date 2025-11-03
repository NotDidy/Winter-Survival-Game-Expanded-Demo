package game.creatures;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.CursedSword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link Ghost}.
 * Ensures ghosts drop cursed swords and lift curses upon death.
 */
class GhostTest {

    private Ghost ghost;
    private CursedSword mockSword;
    private GameMap mockMap;
    private Location mockLocation;

    @BeforeEach
    void setUp() {
        mockSword = mock(CursedSword.class);
        mockMap = mock(GameMap.class);
        mockLocation = mock(Location.class);

        ghost = new Ghost("Accursed Spectre", 'G', 50, mockSword);
        when(mockMap.locationOf(ghost)).thenReturn(mockLocation);
    }

    /**
     * Positive test: unconscious() should drop the cursed sword and lift its curse.
     */
    @Test
    void testUnconsciousDropsSwordAndLiftsCurse() {
        ghost.unconscious(mockMap);

        verify(mockMap, times(1)).locationOf(ghost);
        verify(mockLocation, times(1)).addItem(mockSword);
        verify(mockSword, times(1)).liftCurse();
        verify(mockMap, times(1)).removeActor(ghost);
    }

    /**
     * Positive test: overloaded unconscious(Actor, GameMap) behaves the same way.
     */
    @Test
    void testUnconsciousWithActorDropsSwordAndLiftsCurse() {
        ghost.unconscious(null, mockMap);

        verify(mockMap, times(1)).locationOf(ghost);
        verify(mockLocation, times(1)).addItem(mockSword);
        verify(mockSword, times(1)).liftCurse();
        verify(mockMap, times(1)).removeActor(ghost);
    }

    /**
     * Negative test: ghost without cursed weapon does not drop or lift anything.
     */
    @Test
    void testUnconsciousWithoutWeaponDoesNothing() {
        Ghost weakGhost = new Ghost("Restless Spirit", 'G', 40, null);
        when(mockMap.locationOf(weakGhost)).thenReturn(mockLocation);

        weakGhost.unconscious(mockMap);

        verify(mockLocation, never()).addItem(any());
        verify(mockMap, times(1)).removeActor(weakGhost);
    }
}
