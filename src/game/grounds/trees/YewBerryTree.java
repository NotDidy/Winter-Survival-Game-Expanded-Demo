package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.YewBerry;

public class YewBerryTree extends AbstractFruitTree {
    public YewBerryTree() {
        super('Y', "Yew Berry Tree", 5);
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
