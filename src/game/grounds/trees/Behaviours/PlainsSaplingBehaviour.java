package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSapling;


public class PlainsSaplingBehaviour implements SaplingGrowthBehaviour<AbstractSapling>{
    private static final int GROWTH_DURATION = 3;

    @Override
    public void tick(Location location, AbstractSapling sapling) {
        sapling.incrementAge();
        // Produce fruit every turn
        sapling.produceFruit(location);

        if (sapling.getAge() >= GROWTH_DURATION) {
            sapling.growInto(location, "Plains");
        }
    }
}
