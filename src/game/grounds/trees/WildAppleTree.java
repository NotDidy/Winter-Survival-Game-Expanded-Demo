package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;
import game.statuses.Unripe;

/**
 * Represents Apple Tree that can drop apples.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class WildAppleTree extends AbstractAppleTree {
    public WildAppleTree() {
        super('T', "Wild Apple Tree", 3);
    }
}