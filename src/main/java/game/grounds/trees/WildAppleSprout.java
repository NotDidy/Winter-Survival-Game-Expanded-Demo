package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.behaviours.GenericSaplingBehaviour;
import game.grounds.trees.behaviours.SproutGrowthBehaviour;
import game.items.Apple;

/**
 * Represents a {@code Wild Apple Sprout}, the initial growth stage of a Wild Apple Tree.
 * <p>
 * This sprout may occasionally produce apples and, depending on the biome it grows in,
 * will eventually transform into either a {@link WildAppleSapling} or directly into
 * a {@link WildAppleTree}.
 * </p>
 *
 * <p>
 * Growth timing and transformation logic are controlled by the assigned
 * {@link SproutGrowthBehaviour}, while biome-specific rules determine whether
 * the sapling stage is skipped.
 * </p>
 *
 * @author Wern
 * @version 1.1.0
 * @since 1.1.0
 */
public class WildAppleSprout extends AbstractSprout {

    /**
     * Constructs a new {@code WildAppleSprout} instance with the given biome and behaviour.
     *
     * @param biome      the biome in which this sprout exists (e.g., "Forest", "Plains")
     * @param behaviour  the {@link SproutGrowthBehaviour} that defines its growth rate and transformation
     */
    public WildAppleSprout(String biome, SproutGrowthBehaviour<? super AbstractSprout> behaviour) {
        super(',', "Wild Apple Sprout", biome, behaviour);
    }

    /**
     * Produces apples in nearby locations with a 30% chance per adjacent tile.
     * <p>
     * This simulates the sprout producing early-stage fruit, which can be harvested or used
     * by nearby actors in the game world.
     * </p>
     *
     * @param location the {@link Location} of this sprout on the map
     */
    @Override
    public void produceFruit(Location location) {
        for (var exit : location.getExits()) {
            if (random() >= TreeConfiguration.FRUIT_DROP_THRESHOLD) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }

    protected double random() {
        return Math.random();
    }


    /**
     * Handles the growth transition of the sprout based on its biome.
     * <ul>
     *     <li>If the biome is "Plains", the sprout skips the sapling stage and grows directly
     *     into a {@link WildAppleTree}.</li>
     *     <li>Otherwise, it grows into a {@link WildAppleSapling} with a predefined
     *     {@link GenericSaplingBehaviour} controlling its next stage.</li>
     * </ul>
     *
     * @param location the {@link Location} of this sprout
     * @param biome    the biome type that determines its growth path
     */
    @Override
    public void growInto(Location location, String biome) {
        if (biome.equalsIgnoreCase("Plains")) {
            // Skip the sapling stage entirely
            location.setGround(new WildAppleTree(biome));
        } else {
            // Normal growth: sprout → sapling
            var saplingBehaviour = new GenericSaplingBehaviour(5, true);
            location.setGround(new WildAppleSapling(biome, saplingBehaviour));
        }
    }
}
