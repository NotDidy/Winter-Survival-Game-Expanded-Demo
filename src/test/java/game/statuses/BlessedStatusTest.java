package game.statuses;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.items.CursedItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link BlessedStatus}.
 * Ensures proper healing behaviour and deactivation when the cursed item is dropped.
 */
class BlessedStatusTest {

    private BlessedStatus blessedStatus;
    private Actor mockActor;
    private Location mockLocation;
    private CursedItem mockItem;

    @BeforeEach
    void setUp() {
        mockActor = mock(Actor.class);
        mockLocation = mock(Location.class);
        mockItem = mock(CursedItem.class);
        blessedStatus = new BlessedStatus(mockItem);
    }

    /**
     * Positive test: healing is applied when the actor holds the cursed item.
     */
    @Test
    void testHealsActorWhenHoldingItem() {
        when(mockActor.getItemInventory()).thenReturn(java.util.List.of(mockItem));
        blessedStatus.tickStatus(mockActor, mockLocation);

        verify(mockActor, times(1)).heal(anyInt());
        assertTrue(blessedStatus.isStatusActive());
    }

    /**
     * Negative test: no healing when actor drops the cursed item.
     */
    @Test
    void testNoHealWhenItemNotHeld() {
        when(mockActor.getItemInventory()).thenReturn(java.util.List.of());

        blessedStatus.tickStatus(mockActor, mockLocation);

        verify(mockActor, never()).heal(anyInt());
        assertFalse(blessedStatus.isStatusActive());
    }

    /**
     * Edge test: toString() should describe the healing correctly.
     */
    @Test
    void testToStringFormat() {
        String desc = blessedStatus.toString();
        assertTrue(desc.contains("Blessed"));
        assertTrue(desc.contains("HP/turn"));
    }
}
