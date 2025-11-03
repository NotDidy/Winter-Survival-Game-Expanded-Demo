package game.grounds.trees.behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSprout;

/**
 * A behaviour interface that defines how a {@link AbstractSprout} grows.
 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
public interface SproutGrowthBehaviour<T extends AbstractSprout> {
    void tick(Location location, T sprout);
}
