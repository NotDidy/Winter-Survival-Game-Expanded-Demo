package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;

public class WildAppleTree extends AbstractFruitTree {
    public WildAppleTree(String mapName) {
        super('T', "Wild Apple Tree", 3);
    }

    @Override
    protected void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }
}
