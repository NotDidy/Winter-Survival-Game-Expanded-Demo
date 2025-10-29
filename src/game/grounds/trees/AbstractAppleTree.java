package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;
import game.statuses.Unripe;

public abstract class AbstractAppleTree extends Ground {
    private final int produceFruitTicks;
    /**
     * AppleTree Constructor.
     */
    public AbstractAppleTree(char displayChar, String name, int produceFruitTicks) {
        super(displayChar, name);
        this.produceFruitTicks = produceFruitTicks;
        this.enableAbility(TreeAbilities.CAN_PRODUCE);
        this.addStatus(new Unripe(produceFruitTicks));
    }

    /**
     * AppleTree tick. Current implementation processes whether it is time for the AppleTree to drop fruits.
     *
     * @param location The location of the Ground
     */
    public void tick(Location location) {
        super.tick(location);
        //handle fruit production
        if (this.hasAbility(TreeAbilities.CAN_PRODUCE) && !this.hasStatus(Unripe.class)) {
            produceFruit(location);
            this.addStatus(new Unripe(produceFruitTicks));
        }
    }

    /**
     * Generates Apple objects to be dropped around the AppleTree.
     * @param location  The location of the AppleTree
     */
    public void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }
}



