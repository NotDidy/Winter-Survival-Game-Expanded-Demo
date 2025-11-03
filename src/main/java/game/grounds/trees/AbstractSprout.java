package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.behaviours.SproutGrowthBehaviour;

/**
 * An abstract base class representing the sprout stage of a fruit-bearing tree.
 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
public abstract class AbstractSprout extends AbstractTree {
    private final SproutGrowthBehaviour<? super AbstractSprout> behaviour;
    private final String biome;

    private int age = 0;

    /**
     * Constructs a new {@code AbstractSprout}.
     *
     * @param displayChar the character representing this sprout on the map
     * @param name        the name of this sprout (e.g., "Apple Sprout")
     * @param biome       the biome this sprout grows in
     * @param behaviour   the {@link SproutGrowthBehaviour} defining how this sprout evolves
     */
    public AbstractSprout(char displayChar, String name, String biome, SproutGrowthBehaviour<? super AbstractSprout> behaviour) {
        super(displayChar, name, Integer.MAX_VALUE);
        this.behaviour = behaviour;
        this.biome = biome;
        this.disableAbility(TreeAbilities.CAN_PRODUCE);
    }

    /**
     * Called once per game tick to update this sprout's state.
     * <p>
     * In addition to the default tick logic inherited from {@link AbstractTree},
     * this method delegates growth logic to the associated
     * {@link SproutGrowthBehaviour}, which determines whether the sprout
     * should age, produce fruit, or transition into a sapling.
     * </p>
     *
     * @param location the {@link Location} of this sprout on the map
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        behaviour.tick(location, this);
    }

    /**
     * Returns the biome this sprout belongs to.
     *
     * @return the biome name as a {@link String}
     */
    public String getBiome(){
        return biome;
    }

    /**
     * Returns the current age of this sprout.
     *
     * @return the sprout's age
     */
    public int getAge(){
        return age;
    }

    /**
     * Increments this sprout's age by one tick.
     */
    public void incrementAge(){
        age++;
    }

    /**
     * Defines how this sprout produces fruit.
     * <p>
     * While most sprouts do not produce fruit, subclasses can override this method
     * to enable early fruit-bearing behaviour if applicable.
     * </p>
     *
     * @param location the {@link Location} of this sprout on the map
     */
    @Override
    public abstract void produceFruit(Location location);

    /**
     * Transforms this sprout into its next growth stage.
     * <p>
     * Subclasses implement this method to specify what type of sapling
     * the sprout becomes and how the transformation occurs based on the biome.
     * </p>
     *
     * @param location the {@link Location} where the new sapling will be placed
     * @param biome    the biome that influences what sapling this sprout grows into
     */
    public abstract void growInto(Location location, String biome);
}

