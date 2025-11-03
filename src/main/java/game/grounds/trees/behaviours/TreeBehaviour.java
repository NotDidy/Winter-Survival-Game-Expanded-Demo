package game.grounds.trees.behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.YewBerryTree;

public interface TreeBehaviour {
    void tick(YewBerryTree tree, Location location);
}
