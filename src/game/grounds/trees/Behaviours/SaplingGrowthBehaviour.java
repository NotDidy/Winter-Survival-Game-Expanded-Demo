package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSapling;

/**
 * A behaviour interface that defines how a {@link AbstractSapling} grows.
 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
public interface SaplingGrowthBehaviour<T extends AbstractSapling> {
    void tick(Location location, T sapling);
}

