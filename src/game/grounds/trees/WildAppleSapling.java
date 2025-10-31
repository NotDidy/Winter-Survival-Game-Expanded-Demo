package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.SaplingGrowthBehaviour;
import game.items.Apple;

/**
 * Represents a {@code Wild Apple Sapling}, the intermediate growth stage between
 * a {@link game.grounds.trees.WildAppleSprout} and a {@link game.grounds.trees.WildAppleTree}.
 * <p>
 * This sapling may occasionally produce apples in surrounding locations and will
 * eventually grow into a {@code WildAppleTree} once its growth criteria are met.
 * </p>
 *
 * <p>
 * Growth timing and behaviour are determined by the associated
 * {@link SaplingGrowthBehaviour} provided at construction.
 * </p>
 *
 * @author Wern
 * @version 1.1.0
 * @since 1.1.0
 */
public class WildAppleSapling extends AbstractSapling {
    /**
     * Constructs a new {@code WildAppleSapling} instance with the given biome and behaviour.
     *
     * @param biome      the biome in which this sapling exists (e.g., "Earth", "Forest")
     * @param behaviour  the {@link SaplingGrowthBehaviour} that governs its growth and transformation
     */
    public WildAppleSapling(String biome, SaplingGrowthBehaviour<? super AbstractSapling> behaviour) {
        super('t', "Wild Apple Sapling", biome, behaviour);
    }

    /**
     * Produces apples in nearby locations with a 30% chance per adjacent tile.
     * <p>
     * This simulates the sapling dropping early fruit, which can be collected or consumed.
     * </p>
     *
     * @param location the {@link Location} of this sapling on the map
     */
    @Override
    public void produceFruit(Location location) {
        for (var exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }

    /**
     * Transforms this sapling into a fully grown {@link WildAppleTree}.
     * <p>
     * The transformation is biome-aware, meaning it respects the sapling’s environmental setting.
     * </p>
     *
     * @param location the {@link Location} where the transformation occurs
     * @param biome    the biome associated with this sapling
     */
    @Override
    public void growInto(Location location, String biome) {
        location.setGround(new WildAppleTree(biome));
    }
}
