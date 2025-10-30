package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractFruitTree;

public interface TreeGrowthBehaviour {
    void tick(Location location, AbstractFruitTree tree);
}