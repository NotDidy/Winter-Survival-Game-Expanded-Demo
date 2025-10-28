package game.grounds.spawners;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.creatures.Bear;
import game.creatures.Crocodile;
import game.creatures.Deer;
import game.creatures.Wolf;
import game.grounds.trees.ProximityYewBerryTree;
import game.items.Apple;
import game.items.YewBerry;
import game.statuses.Poisonable;
import game.statuses.PoisonedStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralized post-spawn effects so ALL spawners behave consistently (REQ2).
 * - Deer: spawn 1 Apple in exactly 1 exit.
 * - Bear: each exit has 50% chance to spawn a YewBerry.
 * - Wolf: grow 1 ProximityYewBerryTree in exactly 1 exit.
 * - Crocodile: poison ALL actors in spawner surroundings (3 turns, 10 dmg/turn).
 */
public final class SpawnHooks {
    private SpawnHooks() {}

    private static final int CROC_NEARBY_POISON_TURNS = 3;
    private static final int CROC_NEARBY_POISON_DAMAGE = 10;

    public static void applyAfterSpawn(Location spawnerLocation, Actor spawned) {
        if (spawned instanceof Deer) {
            // one apple in exactly one exit
            List<Exit> exits = new ArrayList<>(spawnerLocation.getExits());
            if (!exits.isEmpty()) {
                Exit e = exits.get((int) (Math.random() * exits.size()));
                e.getDestination().addItem(new Apple());
            }
            return;
        }

        if (spawned instanceof Bear) {
            // each exit 50% chance of a yewberry
            for (Exit e : spawnerLocation.getExits()) {
                if (Math.random() < 0.5) {
                    e.getDestination().addItem(new YewBerry());
                }
            }
            return;
        }

        if (spawned instanceof Wolf) {
            // grow exactly one proximity yewberry tree
            List<Exit> exits = new ArrayList<>(spawnerLocation.getExits());
            if (!exits.isEmpty()) {
                Exit e = exits.get((int) (Math.random() * exits.size()));
                e.getDestination().setGround(new ProximityYewBerryTree());
            }
            return;
        }

        if (spawned instanceof Crocodile) {
            // poison ALL actors in surroundings
            for (Exit e : spawnerLocation.getExits()) {
                Location dest = e.getDestination();
                if (dest.containsAnActor()) {
                    Actor target = dest.getActor();
                    target.asCapability(Poisonable.class).ifPresent(p ->
                            target.addStatus(new PoisonedStatus(p, CROC_NEARBY_POISON_TURNS, CROC_NEARBY_POISON_DAMAGE))
                    );
                }
            }
        }
    }
}
