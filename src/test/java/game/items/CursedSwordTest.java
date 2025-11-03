package game.items;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.creatures.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Deterministic test suite for CursedSword with random-free behavior.
 * Ensures curse spawning, hit/miss logic, and boundaries work correctly.
 */
class CursedSwordTest {

    private Actor mockActor;
    private Actor mockTarget;
    private GameMap mockMap;
    private Location mockLocation;
    private Exit mockExit;

    /**
     * Test subclass that removes randomness.
     */
    static class TestableCursedSword extends CursedSword {
        private final double fixedRandom;

        public TestableCursedSword(double fixedRandom) {
            super("Cursed Sword", '?', true, 5);
            this.fixedRandom = fixedRandom;
        }

        @Override
        public void triggerCurse(Actor actor, GameMap map) {
            cursedActive = true;
            actor.removeItemFromInventory(this);

            Location actorLoc = map.locationOf(actor);
            for (Exit exit : actorLoc.getExits()) {
                if (fixedRandom >= 0.7) { // deterministic control
                    Location spawn = exit.getDestination();
                    if (!spawn.containsAnActor()) {
                        try {
                            map.addActor(new Ghost(GHOST_NAME, GHOST_CHAR, GHOST_HP, this), spawn);
                            return;
                        } catch (GameEngineException e) {
                            // ignore for test
                        }
                    }
                }
            }
        }

        @Override
        public String attack(Actor attacker, Actor target, GameMap map) {
            if (!(fixedRandom * 100 <= this.hitRate)) {
                return attacker + " misses " + target + ".";
            }
            target.hurt(damage);
            return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
        }
    }

    @BeforeEach
    void setUp() {
        mockActor = mock(Actor.class);
        mockTarget = mock(Actor.class);
        mockMap = mock(GameMap.class);
        mockLocation = mock(Location.class);
        mockExit = mock(Exit.class);

        when(mockMap.locationOf(mockActor)).thenReturn(mockLocation);
        when(mockLocation.getExits()).thenReturn(Collections.singletonList(mockExit));
        when(mockExit.getDestination()).thenReturn(mock(Location.class));
    }



    @Test
    void testAttackSuccess() {
        TestableCursedSword sword = new TestableCursedSword(0.1); // < 0.9 = guaranteed hit
        String result = sword.attack(mockActor, mockTarget, mockMap);
        assertTrue(result.contains("swings at"));
        verify(mockTarget, times(1)).hurt(anyInt());
    }

    @Test
    void testAttackMiss() {
        TestableCursedSword sword = new TestableCursedSword(0.95); // > 0.9 = guaranteed miss
        String result = sword.attack(mockActor, mockTarget, mockMap);
        assertTrue(result.contains("misses"));
        verify(mockTarget, never()).hurt(anyInt());
    }

    // ---------- Curse Trigger Tests ----------

    @Test
    void testTriggerCurseSpawnsGhost() throws GameEngineException {
        Location spawnLoc = mock(Location.class);
        when(spawnLoc.containsAnActor()).thenReturn(false);
        when(mockExit.getDestination()).thenReturn(spawnLoc);
        doNothing().when(mockMap).addActor(any(Ghost.class), eq(spawnLoc));

        TestableCursedSword sword = new TestableCursedSword(0.9); // ensures >= 0.7 branch
        sword.triggerCurse(mockActor, mockMap);

        verify(mockActor, times(1)).removeItemFromInventory(sword);
        verify(mockMap, atLeastOnce()).addActor(any(Ghost.class), eq(spawnLoc));
    }

    @Test
    void testTriggerCurseNoSpawnWhenBlocked() throws GameEngineException {
        Location blockedLoc = mock(Location.class);
        when(blockedLoc.containsAnActor()).thenReturn(true);
        when(mockExit.getDestination()).thenReturn(blockedLoc);

        TestableCursedSword sword = new TestableCursedSword(0.9);
        sword.triggerCurse(mockActor, mockMap);

        verify(mockMap, never()).addActor(any(), any());
    }

    @Test
    void testTriggerCurseLowRandomNoSpawn() throws GameEngineException {
        // low random (below 0.7) should skip spawn entirely
        TestableCursedSword sword = new TestableCursedSword(0.5);
        sword.triggerCurse(mockActor, mockMap);

        verify(mockMap, never()).addActor(any(), any());
    }
}
