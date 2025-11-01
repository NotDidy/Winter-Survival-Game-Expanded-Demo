package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;

/**
 * Represents a fully grown {@code Wild Apple Tree} capable of producing apples.
 * <p>
 * This class defines the final growth stage of the Wild Apple lifecycle.
 * It periodically produces apples in adjacent tiles, following the fruit
 * production timing controlled by its superclass {@link AbstractTree}.
 * </p>
 *
 * <p>
 * The fruit production logic gives each neighbouring tile a 30% chance
 * to receive an {@link Apple} item during the tree’s fruiting cycle.
 * </p>
 *
 * @author Wern
 * @version 1.1.0
 * @since 1.1.0
 */
public class WildAppleTree extends AbstractTree {

    /**
     * Constructs a new {@code WildAppleTree} instance.
     *
     * @param mapName the name of the map or biome this tree belongs to
     */
    public WildAppleTree(String mapName) {
        super('T', "Wild Apple Tree", 3);
    }

    /**
     * Produces apples around the tree at adjacent locations.
     * <p>
     * Each neighbouring tile (reachable via an {@link Exit}) has a 30% chance
     * to receive an {@link Apple} when this method is called.
     * </p>
     *
     * @param location the {@link Location} of the tree on the map
     */
    @Override
    protected void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }

    protected double random() {
        return Math.random();
    }
}
