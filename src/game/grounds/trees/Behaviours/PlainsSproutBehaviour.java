package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.AbstractSprout;
import game.grounds.trees.WildAppleTree;

public class PlainsSproutBehaviour implements SproutGrowthBehaviour<AbstractSprout> {
    private static final int GROWTH_DURATION = 3;

    @Override
    public void tick(Location location, AbstractSprout sprout) {
        sprout.incrementAge();
        // Produce fruit every turn
        sprout.produceFruit(location);

        if (sprout.getAge() >= GROWTH_DURATION) {
            sprout.growInto(location, "Plains");
        }
    }
}