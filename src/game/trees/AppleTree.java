package game.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;
import game.statuses.Unripe;

/**
 * Represents Apple Tree that can drop apples.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class AppleTree extends Ground {
    private static final int PRODUCE_FRUIT_TICKS = 3;
    /**
     * AppleTree Constructor.
     */
    public AppleTree() {
        super('T', "Apple Tree");
        this.enableAbility(TreeAbilities.CAN_PRODUCE);
        this.addStatus(new Unripe(PRODUCE_FRUIT_TICKS));
    }

    /**
     * AppleTree tick. Current implementation processes whether it is time for the AppleTree to drop fruits.
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
     * Generates Apple objects to be dropped around the AppleTree.
     * @param location  The location of the AppleTree
     */
    private void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }
}
