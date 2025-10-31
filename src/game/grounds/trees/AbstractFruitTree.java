package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.Unripe;

/**
 * A general abstract class for all fruit-bearing trees.
 * Handles fruit production timing, and allows subclasses to define
 * what fruit they produce.
 */
public abstract class AbstractFruitTree extends Ground {
    private final int produceFruitTicks;

    /**
     * Constructor for any fruit tree.
     * @param displayChar character to display the tree on the map
     * @param name name of the tree
     * @param produceFruitTicks number of ticks required to produce fruit
     */
    public AbstractFruitTree(char displayChar, String name, int produceFruitTicks) {
        super(displayChar, name);
        this.produceFruitTicks = produceFruitTicks;
        this.enableAbility(TreeAbilities.CAN_PRODUCE);
        this.addStatus(new Unripe(produceFruitTicks));
    }

    /**
     * Called each turn. Handles fruit production logic.
     * @param location The location of the tree.
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        if (produceFruitTicks == Integer.MAX_VALUE) {
            return;
        }

        if (this.hasAbility(TreeAbilities.CAN_PRODUCE) && !this.hasStatus(Unripe.class)) {
            produceFruit(location);
            this.addStatus(new Unripe(produceFruitTicks));
        }
    }
    public void incrementAge(){}

    /**
     * Spawns fruit around the tree.
     * Each subclass defines its own specific fruit.
     * @param location The location of the tree.
     */
    protected abstract void produceFruit(Location location);
}
