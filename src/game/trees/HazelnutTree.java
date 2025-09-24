package game.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Hazelnut;
import game.statuses.Unripe;

/**
 * Represents Hazelnut Tree that can drop hazelnuts.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class HazelnutTree extends Ground {
    private static final int PRODUCE_FRUIT_TICKS = 10;
    /**
     * HazelnutTree Constructor.
     */
    public HazelnutTree() {
        super('A', "Hazelnut Tree");
        this.enableAbility(TreeAbilities.CAN_PRODUCE);
        this.addStatus(new Unripe(PRODUCE_FRUIT_TICKS));
    }

    /**
     * HazelnutTree tick. Current implementation processes whether it is time for the HazelnutTree to drop fruits.
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
     * Generates Hazelnut objects to be dropped around the HazelnutTree.
     * @param location  The location of the HazelnutTree
     */
    private void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Hazelnut());
            }
        }
    }
}
