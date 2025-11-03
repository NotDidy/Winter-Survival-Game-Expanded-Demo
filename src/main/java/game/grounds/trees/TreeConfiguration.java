package game.grounds.trees;

/**
 * A configuration class that defines all growth and fruit production parameters
 * for tree-related entities in the game world.
 * <p>
 * This class centralizes all the "magic numbers" used by the various tree types
 * (e.g., Wild Apple Tree, Yew Berry Tree) to improve code readability,
 * maintainability, and configurability. It provides constants for both
 * the "Forest" and "Plains" maps, describing how each tree behaves under
 * different environmental conditions.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *   <li>Stores constants defining the duration of each growth stage.</li>
 *   <li>Indicates whether a stage can produce fruit.</li>
 *   <li>Specifies fruit production intervals and growth probabilities.</li>
 *   <li>Defines global fruit drop probability thresholds.</li>
 * </ul>
 *
 * <p>
 * This class is marked as {@code final} and has a private constructor to prevent
 * instantiation or inheritance. It is intended for static access only.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * if (currentAge >= TreeConfiguration.FOREST_SPROUT_GROWTH_DURATION) {
 *     location.setGround(new WildAppleSapling());
 * }
 * </pre>
 *
 * @author Wern Zhong Lei (35695854)
 * @version 1.0
 */

public final class TreeConfiguration {
    // Forest growth parameters
    public static final int FOREST_SPROUT_GROWTH_DURATION = 3;
    public static final boolean FOREST_SPROUT_PRODUCES_FRUIT = false;

    public static final int FOREST_YEW_BERRY_SAPLING_GROWTH_DURATION = 3;
    public static final boolean FOREST_YEW_BERRY_SAPLING_PRODUCES_FRUIT = false;
    public static final int FOREST_YEW_BERRY_SAPLING_FRUIT_INTERVAL = 0;
    public static final double FOREST_YEW_BERRY_SAPLING_GROWTH_CHANCE = 0.5;

    // Plains growth parameters
    public static final int PLAINS_SPROUT_GROWTH_DURATION = 3;
    public static final boolean PLAINS_SPROUT_PRODUCES_FRUIT = true;

    public static final int PLAINS_YEW_BERRY_GROWTH_DURATION = 3;
    public static final boolean PLAINS_YEW_BERRY_PRODUCES_FRUIT = true;
    public static final int PLAINS_YEW_BERRY_FRUIT_INTERVAL = 2;
    public static final double PLAINS_YEW_BERRY_GROWTH_CHANCE = 0.5;

    // Fruit production chances
    public static final double DEFAULT_FRUIT_DROP_CHANCE = 0.3; // 30% chance
    // Calculate the threshold for easier use in conditionals
    public static final double FRUIT_DROP_THRESHOLD = 1.0 - DEFAULT_FRUIT_DROP_CHANCE;

    /**
     * Private constructor to prevent instantiation.
     * <p>
     * This class is a static configuration holder and should not be instantiated.
     * </p>
     */
    private TreeConfiguration() {}
}