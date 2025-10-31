package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSapling;

public class GenericSaplingBehaviour implements SaplingGrowthBehaviour<AbstractSapling> {

    private final int growthDuration; // duration specific to this sapling type
    private final boolean producesFruit; // optionally allow fruit at sapling stage
    private final int fruitInterval;

    public GenericSaplingBehaviour(int growthDuration, boolean producesFruit) {
        this(growthDuration, producesFruit, 2); // default: every 2 rounds
    }

    public GenericSaplingBehaviour(int growthDuration, boolean producesFruit, int fruitInterval) {
        this.growthDuration = growthDuration;
        this.producesFruit = producesFruit;
        this.fruitInterval = fruitInterval;
    }

    @Override
    public void tick(Location location, AbstractSapling sapling) {
        sapling.incrementAge();

        if (producesFruit && sapling.getAge() % fruitInterval == 0) {
            sapling.produceFruit(location);
        }

        if (sapling.getAge() >= growthDuration) {
            sapling.growInto(location, sapling.getBiome()); // sapling carries its biome
        }
    }
}
