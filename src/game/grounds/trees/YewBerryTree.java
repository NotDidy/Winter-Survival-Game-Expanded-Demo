package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.YewBerry;

/**
 * Concrete Yew Berry tree that produces YewBerry items around it
 * every time it ripens (every 5 ticks in this implementation).
 */
public class YewBerryTree extends AbstractTree {

    /**
     * Creates a YewBerry tree with display 'Y', name "Yew Berry Tree",
     * and a 5-tick ripening period.
     */
    public YewBerryTree() {
        super('Y', "Yew Berry Tree", 5);
    }

    /**
     * Drops berries on neighbouring tiles with 30% chance per exit.
     */
    @Override
    protected void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {   // 30% chance
                exit.getDestination().addItem(new YewBerry());
            }
        }
    }
}
