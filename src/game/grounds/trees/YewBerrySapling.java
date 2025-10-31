package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.SaplingGrowthBehaviour;
import game.items.YewBerry;


public class YewBerrySapling extends AbstractSapling {

    public YewBerrySapling(String biome, SaplingGrowthBehaviour<? super AbstractSapling> behaviour) {
        super('b', "Yew Berry Sapling", biome, behaviour);
    }

    @Override
    public void growInto(Location location, String biome) {
        // 50% chance to become a Yew Berry Tree
        if (Math.random() < 0.5) {
            location.setGround(new YewBerryTree());
        }
    }

    @Override
    public void produceFruit(Location location) {
        // Drop YewBerries around the sapling
        for (var exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new YewBerry());
            }
        }
    }
}
