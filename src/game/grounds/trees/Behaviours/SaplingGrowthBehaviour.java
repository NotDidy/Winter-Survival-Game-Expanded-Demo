package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSapling;

public interface SaplingGrowthBehaviour<T extends AbstractSapling> {
    void tick(Location location, T sapling);
}

