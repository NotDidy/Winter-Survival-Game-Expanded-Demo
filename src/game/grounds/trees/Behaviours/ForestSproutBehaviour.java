package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSprout;
import game.grounds.trees.WildAppleSapling;

public class ForestSproutBehaviour implements SproutGrowthBehaviour<AbstractSprout>{
    private static final int GROWTH_DURATION = 4;

    public void tick(Location location, AbstractSprout sprout){
        sprout.incrementAge();
        if (sprout.getAge() >= GROWTH_DURATION)
            sprout.growInto(location, "Forest");
    }
}





