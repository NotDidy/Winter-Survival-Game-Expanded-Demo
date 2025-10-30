package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSprout;

public interface SproutGrowthBehaviour {
    void tick(Location location, AbstractSprout sprout);
}
