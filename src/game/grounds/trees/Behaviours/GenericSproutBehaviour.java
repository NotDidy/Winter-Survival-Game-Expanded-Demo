package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSprout;

public class GenericSproutBehaviour implements SproutGrowthBehaviour<AbstractSprout> {

    private final int growthDuration; // duration specific to this sprout type
    private final boolean producesFruit; // optionally allow fruit at sprout stage

    public GenericSproutBehaviour(int growthDuration, boolean producesFruit) {
        this.growthDuration = growthDuration;
        this.producesFruit = producesFruit;
    }

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
