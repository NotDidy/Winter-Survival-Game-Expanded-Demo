package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.YewBerry;

/**
 * YewBerry class representation
 * @author Ryu, Zhong Lei
 * @version 1.3.0
 * @since 1.3.0
 */
public class YewBerryTree extends AbstractTree {
    /** Determines whether the tree uses proximity-based fruit dropping logic. */
    private boolean proximityMode = false;

    /**
     * Constructs a standard {@code YewBerryTree} with a default 5-turn fruit timer.
     * In default mode, it produces fruit periodically at random exits.
     */

    public YewBerryTree() {
        super('Y', "Yew Berry Tree", 5);
    }

    /**
     * Enables proximity-based behavior for this tree.
     * When enabled, the tree will only drop fruit if there is an actor within one tile.
     */

    public void enableProximityMode() {
        this.proximityMode = true;
    }

    /**
     * Produces YewBerry fruit
     * @param location The location of the tree.
     */
    @Override
    protected void produceFruit(Location location) {
        // if proximity mode, drop when someone is nearby
        if (proximityMode) {
            boolean someoneNearby = location.getNearbyLocations(1).stream()
                    .anyMatch(loc -> loc.containsAnActor());
            if (someoneNearby) {
                var exits = location.getExits();
                if (!exits.isEmpty()) {
                    var exit = exits.get((int) (Math.random() * exits.size()));
                    exit.getDestination().addItem(new YewBerry());
                }
            }
            return; // skip normal drop
        }

        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new YewBerry());
            }
        }
    }
}
