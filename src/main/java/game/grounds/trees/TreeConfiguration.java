package game.grounds.trees;

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

    private TreeConfiguration() {}
}