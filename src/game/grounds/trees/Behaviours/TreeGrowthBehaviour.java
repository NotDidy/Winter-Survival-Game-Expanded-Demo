package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractFruitTree;

//for future extensions where tree could grow into something else etc
public interface TreeGrowthBehaviour {
    void tick(Location location, AbstractFruitTree tree);
}