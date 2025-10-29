package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;
import game.statuses.Unripe;

public class AbstractAppleTree extends Ground {
    private final int produceFruitTicks;

    public AbstractAppleTree(char displayChar, String name, int produceFruitTicks) {
        super(displayChar, name);
        this.produceFruitTicks = produceFruitTicks;
        this.enableAbility(TreeAbilities.CAN_PRODUCE);
        this.addStatus(new Unripe(produceFruitTicks));
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        //handle fruit production
        if (this.hasAbility(TreeAbilities.CAN_PRODUCE) && !this.hasStatus(Unripe.class)) {
            produceFruit(location);
            this.addStatus(new Unripe(produceFruitTicks));
        }
    }

    public void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }
}

