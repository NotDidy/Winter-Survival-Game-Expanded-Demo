package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.SaplingGrowthBehaviour;
import game.items.Apple;

public class WildAppleSapling extends AbstractSapling {
    public WildAppleSapling(String biome, SaplingGrowthBehaviour<? super AbstractSapling> behaviour) {
        super('t', "Wild Apple Sapling", biome, behaviour);
    }

    @Override
    public void produceFruit(Location location) {
        for (var exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }
    @Override
    public void growInto(Location location, String biome) {
        location.setGround(new WildAppleTree(biome));
    }
}
