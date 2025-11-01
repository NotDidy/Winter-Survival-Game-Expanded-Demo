package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.SaplingGrowthBehaviour;

/**
 * An abstract base class representing a sapling stage of a fruit-bearing tree.
 * @author Wern Zhong Lei (35695854)
 * @version 1.1.0
 * @since 1.1.0
 */
public abstract class AbstractSapling extends AbstractTree {
    private final SaplingGrowthBehaviour<? super AbstractSapling> behaviour;
    private final String biome;

    private int age = 0;

    /**
     * Constructs a new {@code AbstractSapling}.
     *
     * @param displayChar the character used to represent this sapling on the map
     * @param name        the name of this sapling (e.g., "Apple Sapling")
     * @param biome       the biome that this sapling grows in
     * @param behaviour   the {@link SaplingGrowthBehaviour} controlling how this sapling grows
     */
    public AbstractSapling(char displayChar, String name, String biome, SaplingGrowthBehaviour<? super AbstractSapling> behaviour) {
        super(displayChar, name, Integer.MAX_VALUE);
        this.behaviour = behaviour;
        this.biome = biome;
        this.disableAbility(TreeAbilities.CAN_PRODUCE);
    }

    /**
     * Called once per game tick to update this sapling's state.
     * <p>
     * In addition to the default tick logic from {@link AbstractTree},
     * this method triggers the sapling's {@link SaplingGrowthBehaviour} to
     * determine whether it should age, remain as a sapling, or grow into a tree.
     * </p>
     *
     * @param location the {@link Location} of this sapling on the map
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        behaviour.tick(location, this);
    }

    /**
     * Returns the biome this sapling belongs to.
     *
     * @return the biome name as a {@link String}
     */
    public String getBiome(){
        return biome;
    }

    /**
     * Returns the current age of this sapling.
     *
     * @return the sapling's age
     */
    public int getAge(){
        return age;
    }

    /**
     * Increments the age of this sapling by one turn.
     */
    public void incrementAge(){
        age++;
    }

    /**
     * Defines how this sapling produces fruit.
     * <p>
     * While most saplings cannot produce fruit, subclasses may override this method
     * if certain saplings can yield fruit before maturing into trees.
     * </p>
     *
     * @param location the {@link Location} of this sapling on the map
     */
    @Override
    public abstract void produceFruit(Location location);

    /**
     * Transforms this sapling into its mature tree form.
     * <p>
     * Subclasses define which specific tree type to grow into, depending on their
     * species and the biome they inhabit.
     * </p>
     *
     * @param location the {@link Location} where the new tree should replace this sapling
     * @param biome    the biome that influences what tree this sapling becomes
     */
    public abstract void growInto(Location location, String biome);
}