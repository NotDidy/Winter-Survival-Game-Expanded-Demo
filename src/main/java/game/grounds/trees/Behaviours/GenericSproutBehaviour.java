package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSprout;

/**
 * Represents the generic growth behaviour for any sprout stage of a tree.
 * Handles age progression, optional fruit production, and transition into the next growth stage.
 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
public class GenericSproutBehaviour implements SproutGrowthBehaviour<AbstractSprout> {

    private final int growthDuration; // duration specific to this sprout type
    private final boolean producesFruit; // optionally allow fruit at sprout stage

    public GenericSproutBehaviour(int growthDuration, boolean producesFruit) {
        this.growthDuration = growthDuration;
        this.producesFruit = producesFruit;
    }

    /**
     * Updates the state of the sprout each game tick.
     * <ul>
     *     <li>Increments the sprout's age.</li>
     *     <li>If fruit production is enabled, produces fruit at the current location.</li>
     *     <li>Once the sprout's age exceeds the growth duration, it transforms into the next stage
     *     (e.g., sapling or tree) based on its biome.</li>
     * </ul>
     *
     * @param location the current location of the sprout on the map
     * @param sprout   the sprout being updated
     */
    @Override
    public void tick(Location location, AbstractSprout sprout) {
        sprout.incrementAge();

        if (producesFruit) {
            sprout.produceFruit(location);
        }

        if (sprout.getAge() > growthDuration) {
            sprout.growInto(location, sprout.getBiome()); // sprout carries its biome
        }
    }
}
