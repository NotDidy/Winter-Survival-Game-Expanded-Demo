package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSapling;

public class ForestSaplingBehaviour implements SaplingGrowthBehaviour<AbstractSapling> {
    private static final int GROWTH_DURATION = 6;

    @Override
    public void tick(Location location, AbstractSapling sapling){
        sapling.incrementAge();

        if (sapling.getAge() >= GROWTH_DURATION)
            sapling.growInto(location, "Forest");
    }
}
