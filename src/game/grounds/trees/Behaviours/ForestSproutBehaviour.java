package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.WildAppleSapling;
import game.grounds.trees.WildAppleSprout;

public class ForestSproutBehaviour implements SproutGrowthBehaviour{
    private static final int GROWTH_DURATION = 4;

    public void tick(Location location, WildAppleSprout sprout){
        sprout.incrementAge();
        if (sprout.getAge >= GROWTH_DURATION)
            location.setGround(new WildAppleSapling("Forest"));
    }
}





