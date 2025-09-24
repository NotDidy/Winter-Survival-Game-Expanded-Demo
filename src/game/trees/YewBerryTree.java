package game.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.YewBerry;
import game.statuses.Unripe;

/**
 * Represents YewBerry Tree that can drop Yew berries.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class YewBerryTree extends Ground {
    private static final int PRODUCE_FRUIT_TICKS = 5;
    /**
     * YewBerryTree Constructor.
     */
    public YewBerryTree() {
        super('Y', "YewBerry Tree");
        this.enableAbility(TreeAbilities.CAN_PRODUCE);
        this.addStatus(new Unripe(PRODUCE_FRUIT_TICKS));
    }

    /**
     * YewBerryTree tick. Current implementation processes whether it is time for the YewBerryTree to drop fruits.
     *
     * @param location The location of the Ground
     */
    public void tick(Location location) {
        if (this.hasAbility(TreeAbilities.CAN_PRODUCE)) {
            if (!this.hasStatus(Unripe.class)) {
                this.produceFruit(location);
                this.addStatus(new Unripe(PRODUCE_FRUIT_TICKS));
            }
        }
    }

    /**
     * Generates YewBerry objects to be dropped around the YewBerryTree.
     * @param location  The location of the YewBerryTree
     */
    private void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new YewBerry());
            }
        }
    }
}
