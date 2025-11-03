package game.items;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.creatures.Zombie;
import game.statuses.BlessedStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Deterministic unit tests for CursedAmulet.
 * Random behavior is controlled by subclassing and overriding randomness.
 */
class CursedAmuletTest {

    private CursedAmulet cursedAmulet;
    private Actor mockActor;
    private GameMap mockMap;
    private Location mockLocation;
    private Exit mockExit;

    /**
     * Deterministic subclass that overrides randomness.
     */
    static class TestableCursedAmulet extends CursedAmulet {
        private final double fixedRandom;

        public TestableCursedAmulet(double fixedRandom) {
            super("Cursed Amulet", '&', true, 5);
            this.fixedRandom = fixedRandom;
        }

        @Override
        public void triggerCurse(Actor actor, GameMap map) {
            cursedActive = true;
            actor.removeItemFromInventory(this);

            var exits = map.locationOf(actor).getExits();
            for (Exit exit : exits) {
                if (fixedRandom >= 0.7) { // deterministic behavior
                    Location spawn = exit.getDestination();
                    if (!spawn.containsAnActor()) {
                        try {
                            map.addActor(new Zombie("Zombie", '@', 80, this), spawn);
                            return;
                        } catch (GameEngineException e) {
                            // ignore in tests
                        }
                    }
                }
            }
        }
    }

    @BeforeEach
    void setUp() {
        mockActor = mock(Actor.class);
        mockMap = mock(GameMap.class);
        mockLocation = mock(Location.class);
        mockExit = mock(Exit.class);

        when(mockMap.locationOf(mockActor)).thenReturn(mockLocation);
        when(mockLocation.getExits()).thenReturn(Collections.singletonList(mockExit));
        when(mockExit.getDestination()).thenReturn(mock(Location.class));
        when(mockLocation.map()).thenReturn(mockMap);
    }

    @Test
    void testBlessedStatusAddedWhenHeld() {
        cursedAmulet = new TestableCursedAmulet(0.8); // fixed random
        when(mockActor.hasStatus(BlessedStatus.class)).thenReturn(false);

        cursedAmulet.tick(mockLocation, mockActor);

        verify(mockActor, times(1)).addStatus(any(BlessedStatus.class));
    }

    @Test
    void testBlessedStatusNotAddedTwice() {
        cursedAmulet = new TestableCursedAmulet(0.8);
        when(mockActor.hasStatus(BlessedStatus.class)).thenReturn(true);

        cursedAmulet.tick(mockLocation, mockActor);
        verify(mockActor, never()).addStatus(any(BlessedStatus.class));
    }

    @Test
    void testTriggerCurseSpawnsZombie() throws GameEngineException {
        cursedAmulet = new TestableCursedAmulet(0.8); // ensures spawn
        Location spawnLoc = mock(Location.class);
        when(spawnLoc.containsAnActor()).thenReturn(false);
        when(mockExit.getDestination()).thenReturn(spawnLoc);
        doNothing().when(mockMap).addActor(any(Zombie.class), eq(spawnLoc));

        cursedAmulet.triggerCurse(mockActor, mockMap);

        verify(mockActor).removeItemFromInventory(cursedAmulet);
        verify(mockMap).addActor(any(Zombie.class), eq(spawnLoc));
    }

    @Test
    void testTriggerCurseNoSpawnWhenRandomLow() throws GameEngineException {
        cursedAmulet = new TestableCursedAmulet(0.5); // random < 0.7, no spawn
        Location spawnLoc = mock(Location.class);
        when(spawnLoc.containsAnActor()).thenReturn(false);
        when(mockExit.getDestination()).thenReturn(spawnLoc);

        cursedAmulet.triggerCurse(mockActor, mockMap);

        verify(mockMap, never()).addActor(any(), any());
    }

    @Test
    void testCurseTriggersAfterThreshold() throws GameEngineException {
        cursedAmulet = new TestableCursedAmulet(0.8);
        Location spawnLoc = mock(Location.class);
        when(spawnLoc.containsAnActor()).thenReturn(false);
        when(mockExit.getDestination()).thenReturn(spawnLoc);
        doNothing().when(mockMap).addActor(any(Zombie.class), eq(spawnLoc));

        for (int i = 0; i < 5; i++) {
            cursedAmulet.tick(mockLocation, mockActor);
        }

        verify(mockMap, atLeastOnce()).addActor(any(Zombie.class), eq(spawnLoc));
    }
}
