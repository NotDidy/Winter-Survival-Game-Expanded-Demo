package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.behaviours.SaplingGrowthBehaviour;
import game.items.YewBerry;

/**
 * Represents the sapling stage of a {@code Yew Berry Tree}.
 * <p>
 * This class defines the intermediate growth phase between a sprout and
 * a fully grown {@link YewBerryTree}. It handles age progression and
 * potential fruit production based on its associated {@link SaplingGrowthBehaviour}.
 * </p>
 *
 * <p>
 * Every eligible growth check, the sapling has a 50% chance to grow into
 * a {@link YewBerryTree}. It may also drop {@link YewBerry} items
 * around its location depending on its biome and growth configuration.
 * </p>
 *
 * @author Wern
 * @version 1.1.0
 * @since 1.1.0
 */
public class YewBerrySapling extends AbstractSapling {
    private final double growthChance;
    /**
     * Constructs a new {@code YewBerrySapling} instance.
     *
     * @param biome      the biome or map name where this sapling exists
     * @param behaviour  the growth behaviour logic controlling this sapling’s lifecycle
     */
    public YewBerrySapling(String biome, SaplingGrowthBehaviour<? super AbstractSapling> behaviour, double growthChance) {
        super('b', "Yew Berry Sapling", biome, behaviour);
        this.growthChance = growthChance;
    }

    /**
     * Attempts to grow the sapling into a {@link YewBerryTree}.
     * <p>
     * There is a 50% probability that the sapling matures into a tree
     * when the growth condition is met.
     * </p>
     *
     * @param location the current location of the sapling on the map
     * @param biome    the biome or map name where this sapling is located
     */
    @Override
    public void growInto(Location location, String biome) {
        if (random() < growthChance) {
            location.setGround(new YewBerryTree());
        }
    }

    protected double random() {
        return Math.random();
    }

    /**
     * Produces {@link YewBerry} fruits around the sapling.
     * <p>
     * Each neighbouring tile has a 30% chance to receive a YewBerry
     * when this method is triggered.
     * </p>
     *
     * @param location the location of the sapling on the map
     */
    @Override
    public void produceFruit(Location location) {
        for (var exit : location.getExits()) {
            if (Math.random() >=TreeConfiguration.FRUIT_DROP_THRESHOLD) {
                exit.getDestination().addItem(new YewBerry());
            }
        }
    }
}
