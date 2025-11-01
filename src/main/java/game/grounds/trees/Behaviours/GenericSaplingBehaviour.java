package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSapling;

/**
 * A generic behaviour class that controls how any sapling grows and optionally produces fruit.
 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
public class GenericSaplingBehaviour implements SaplingGrowthBehaviour<AbstractSapling> {

    private final int growthDuration;
    private final boolean producesFruit;
    private final int fruitInterval;
    private final double growthChance;

    /**
     * Constructs a {@code GenericSaplingBehaviour} with a specified growth duration and fruit production flag.
     * <p>
     * This constructor defaults the fruit production interval to every 2 turns,
     * and assumes automatic growth (100% chance) after the specified duration.
     * </p>
     *
     * @param growthDuration number of turns before the sapling becomes eligible to grow
     * @param producesFruit  whether the sapling can produce fruit during its growth stage
     */
    public GenericSaplingBehaviour(int growthDuration, boolean producesFruit) {
        this(growthDuration, producesFruit, 2, 1.0); // default: grows automatically after duration
    }

    /**
     * Constructs a fully configurable {@code GenericSaplingBehaviour}.
     *
     * @param growthDuration number of turns before the sapling becomes eligible to grow
     * @param producesFruit  whether the sapling can produce fruit during its sapling stage
     * @param fruitInterval  number of turns between fruit production attempts
     * @param growthChance   probability (0.0–1.0) that the sapling will successfully grow each eligible check
     */
    public GenericSaplingBehaviour(int growthDuration, boolean producesFruit, int fruitInterval, double growthChance) {
        this.growthDuration = growthDuration;
        this.producesFruit = producesFruit;
        this.fruitInterval = fruitInterval;
        this.growthChance = growthChance;
    }

    /**
     * Called once per turn to update the sapling’s state.
     * <ul>
     *   <li>Increments the sapling’s internal age counter.</li>
     *   <li>If {@code producesFruit} is true, produces fruit every {@code fruitInterval} turns.</li>
     *   <li>After {@code growthDuration} turns, attempts to grow into a mature tree with probability {@code growthChance}.</li>
     * </ul>
     *
     * @param location the {@link Location} of the sapling in the game world
     * @param sapling  the sapling being updated
     */
    @Override
    public void tick(Location location, AbstractSapling sapling) {
        sapling.incrementAge();

        if (producesFruit && sapling.getAge() % fruitInterval == 0) {
            sapling.produceFruit(location);
        }

        // Try to grow only after reaching growthDuration
        if (sapling.getAge() >= growthDuration) {
            if (Math.random() < growthChance) {
                sapling.growInto(location, sapling.getBiome());
            }
        }
    }
}
