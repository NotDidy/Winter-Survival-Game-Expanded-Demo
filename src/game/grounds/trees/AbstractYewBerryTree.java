package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.YewBerry;
import game.statuses.Unripe;

public abstract class AbstractYewBerryTree extends Ground{
    private final int produceFruitTicks;
    /**
     * YewBerryTree Constructor.
     */
    public AbstractYewBerryTree(char displayChar, String name, int produceFruitTicks) {
        super(displayChar, name);
        this.produceFruitTicks = produceFruitTicks;
        this.enableAbility(TreeAbilities.CAN_PRODUCE);
        this.addStatus(new Unripe(produceFruitTicks));

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
                this.addStatus(new Unripe(produceFruitTicks));
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

