package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSapling;

public class GenericSaplingBehaviour implements SaplingGrowthBehaviour<AbstractSapling> {

    private final int growthDuration;
    private final boolean producesFruit;
    private final int fruitInterval;
    private final double growthChance; // probability to grow each eligible check

    public GenericSaplingBehaviour(int growthDuration, boolean producesFruit) {
        this(growthDuration, producesFruit, 2, 1.0); // default: grows automatically after duration
    }

    public GenericSaplingBehaviour(int growthDuration, boolean producesFruit, int fruitInterval, double growthChance) {
        this.growthDuration = growthDuration;
        this.producesFruit = producesFruit;
        this.fruitInterval = fruitInterval;
        this.growthChance = growthChance;
    }

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
