package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.YewBerry;

public abstract class AbstractYewBerryTree extends AbstractFruitTree {

    public AbstractYewBerryTree(char displayChar, String name, int produceFruitTicks) {
        super(displayChar, name, produceFruitTicks);
    }

    @Override
    protected void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new YewBerry());
            }
        }
    }
}
